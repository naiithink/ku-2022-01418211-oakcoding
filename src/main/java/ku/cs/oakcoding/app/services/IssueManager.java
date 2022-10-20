package ku.cs.oakcoding.app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.binding.MapBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.configurations.OakSystemDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResource;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.hotspot.OakHotspot;
import ku.cs.oakcoding.app.helpers.id.OakID;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.ComplaintStatus;
import ku.cs.oakcoding.app.models.reports.Report;
import ku.cs.oakcoding.app.models.reports.ReportResolvingStatus;
import ku.cs.oakcoding.app.models.reports.ReportStatus;
import ku.cs.oakcoding.app.models.reports.ReportType;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.ConsumerUser;
import ku.cs.oakcoding.app.models.users.StaffUser;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

/**
 * DataSources:
 *  - issues/complaints/categories.csv
 *  - issues/complaints/complaints.csv
 *  - issues/reports/reports.csv
 */
public class IssueManager {

    private ObservableSet<String> complaintCategorySet;

    private ObservableMap<String, Complaint> allComplaintTable;

    private MapBinding<String, ObservableSet<String>> categorizedComplaintTable;

    private ObservableMap<String, Report> reportTable;

    private AutoUpdateCSV complaintCategoryDB;

    private AutoUpdateCSV complaintDB;

    private AutoUpdateCSV reportDB;

    public IssueManager(AutoUpdateCSV complaintCategoryDB,
                        AutoUpdateCSV complaintDB,
                        AutoUpdateCSV reportDB) {

        this.complaintCategoryDB = complaintCategoryDB;
        this.complaintDB = complaintDB;
        this.reportDB = reportDB;

        this.complaintCategorySet = FXCollections.observableSet();
        this.allComplaintTable = FXCollections.observableHashMap();
        this.reportTable = FXCollections.observableHashMap();


        // All complaints table
        Set<String> complaintKeys = complaintDB.getPrimaryKeySet();

        for (String complaintKey : complaintKeys) {
            ObservableSet<String> voters = FXCollections.observableSet();
            Path evidencePath = null;
            String caseManagerUID = null;

            if (!this.complaintDB.getDataWhere(complaintKey, "EVIDENCE_PATH").equals(OakAppDefaults.NIL))
                evidencePath = OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_COMPLAINT_EVIDENCES_DIR.value()).resolve(this.complaintDB.getDataWhere(complaintKey, "EVIDENCE_PATH"));

            if (!this.complaintDB.getDataWhere(complaintKey, "VOTERS").equals(OakAppDefaults.NIL)) {
                List<String> voterList = OakHotspot.separateColonValues(this.complaintDB.getDataWhere(complaintKey, "VOTERS"));

                for (String voter : voterList)
                    voters.add(voter);
            }

            if (!this.complaintDB.getDataWhere(complaintKey, "CASE_MANAGER_UID").equals(OakAppDefaults.NIL))
                caseManagerUID = this.complaintDB.getDataWhere(complaintKey, "CASE_MANAGER_UID");

            Complaint complaint = new Complaint(complaintKey,
                                                this.complaintDB.getDataWhere(complaintKey, "AUTHOR_UID"),
                                                this.complaintDB.getDataWhere(complaintKey, "CATEGORY"),
                                                this.complaintDB.getDataWhere(complaintKey, "SUBJECT"),
                                                this.complaintDB.getDataWhere(complaintKey, "DESCRIPTION"),
                                                evidencePath,
                                                voters,
                                                Enum.valueOf(ComplaintStatus.class, this.complaintDB.getDataWhere(complaintKey, "STATUS")), 
                                                caseManagerUID);

            this.allComplaintTable.put(complaintKey, complaint);
        }


        // Complaint categories

        Set<String> categories = this.complaintCategoryDB.getPrimaryKeySet();
        this.complaintCategorySet.addAll(categories);


        // Categorized complaint table
        this.categorizedComplaintTable = new MapBinding<String, ObservableSet<String>>() {

            {
                super.bind(allComplaintTable, complaintCategorySet);
            }

            @Override
            public ObservableMap<String, ObservableSet<String>> computeValue() {
                ObservableMap<String, ObservableSet<String>> res = FXCollections.observableHashMap();

                Iterator<String> complaintCategories = complaintCategorySet.iterator();

                while (complaintCategories.hasNext()) {
                    res.put(complaintCategories.next(), FXCollections.observableSet());
                }

                Iterator<Entry<String, Complaint>> complaintEntries = allComplaintTable.entrySet().iterator();

                while (complaintEntries.hasNext()) {
                    Entry<String, Complaint> complaint = complaintEntries.next();

                    res.get(complaint.getValue().getCategory()).add(complaint.getValue().getComplaintID());
                }

                return res;
            }
        };


        // All reports table
        Set<String> reportKeys = this.reportDB.getPrimaryKeySet();

        for (String reportKey : reportKeys) {
            String result = null;

            if (!this.reportDB.getDataWhere(reportKey, "RESULT").equals(OakAppDefaults.NIL))
                result = this.reportDB.getDataWhere(reportKey, "RESULT");

            this.reportTable.put(reportKey, new Report(reportKey,
                                                       Enum.valueOf(ReportType.class, this.reportDB.getDataWhere(reportKey, "TYPE")),
                                                       this.reportDB.getDataWhere(reportKey, "AUTHOR_UID"),
                                                       this.reportDB.getDataWhere(reportKey, "TARGET_ID"),
                                                       this.reportDB.getDataWhere(reportKey, "DESCRIPTION"),
                                                       Enum.valueOf(ReportStatus.class, this.reportDB.getDataWhere(reportKey, "STATUS")),
                                                       result));
        }
    }


    /**
     * @section Complaints
     */

    public IssueManagerStatus newComplaint(String authorUID,
                                           String category,
                                           String subject,
                                           String description,
                                           Path evidencePath,
                                           ComplaintStatus status) {

        if (!this.complaintCategorySet.contains(category))
            return IssueManagerStatus.CATEGORY_NOT_FOUND;
        else if (Objects.nonNull(evidencePath) &&  !Files.exists(evidencePath))
            return IssueManagerStatus.EVIDENCE_PATH_DOES_NOT_EXIST;

        ComplaintStatus statusArg = ComplaintStatus.PENDING;

        if (Objects.nonNull(status))
            statusArg = status;

        Complaint complaint = new Complaint(OakID.generate(Complaint.class.getSimpleName()),
                                            authorUID,
                                            category,
                                            subject,
                                            description,
                                            evidencePath,
                                            null,
                                            statusArg,
                                            null);

        String evidenceFileName = null;

        if (Objects.nonNull(evidencePath)) {
            String evidenceFileExtension = null;
            Pattern extensionPattern = Pattern.compile("[^\\\\]*\\.(\\w+)$");
            Matcher extensionMatcher = extensionPattern.matcher(evidencePath.getFileName().toString());

            if (extensionMatcher.find() && extensionMatcher.groupCount() == 1)
                evidenceFileExtension = extensionMatcher.group(1);

            try {
                OakResource.copyFile(evidencePath, OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_COMPLAINT_EVIDENCES_DIR.value()).resolve(complaint.getComplaintID() + "." + evidenceFileExtension));
            } catch (IOException e) {
                OakLogger.log(Level.SEVERE, "Got 'IOException' while saving evidence file");
                System.exit(1);
            }

            evidenceFileName = complaint.getComplaintID() + "." + evidenceFileExtension;
        } else {
            evidenceFileName = OakAppDefaults.NIL;
        }

        this.allComplaintTable.put(complaint.getComplaintID(), complaint);

        String voterSerial = null;
        String caseManagerUID = null;

        if (complaint.getVoters().isEmpty())
            voterSerial = OakAppDefaults.NIL;
        else
            voterSerial = OakHotspot.toColonSeparatedValues(complaint.getVoters());

        if (Objects.nonNull(complaint.getCaseManagerUID()))
            caseManagerUID = complaint.getCaseManagerUID();
        else
            caseManagerUID = OakAppDefaults.NIL;

        this.complaintDB.addRecord(new String[] {
            complaint.getComplaintID(),
            complaint.getAuthorUID(),
            complaint.getCategory(),
            complaint.getSubject(),
            complaint.getDescription(),
            evidenceFileName,
            voterSerial,
            complaint.getStatus().name(),
            caseManagerUID
        });

        return IssueManagerStatus.SUCCESS;
    }

    public boolean complaintExist(String complaintID) {
        return this.allComplaintTable.containsKey(complaintID);
    }

    public IssueManagerStatus deleteComplaint(AdminUser admin, String complaintID) {
        if (Objects.isNull(admin) || !(admin instanceof AdminUser))
            return IssueManagerStatus.INVALID_ACCESS;
        else if (!this.allComplaintTable.containsKey(complaintID))
            return IssueManagerStatus.COMPLAINT_NOT_FOUND;

        this.allComplaintTable.remove(complaintID);
        this.complaintDB.removeRecordWhere(complaintID);

        return IssueManagerStatus.SUCCESS;
    }

    public IssueManagerStatus newComplaintCategory(AdminUser admin, String categoryName) {
        if (Objects.isNull(admin) || !(admin instanceof AdminUser))
            return IssueManagerStatus.INVALID_ACCESS;
        else if (this.complaintCategorySet.contains(categoryName))
            return IssueManagerStatus.CATEGORY_ALREADY_EXIST;

        this.complaintCategorySet.add(categoryName);
        this.complaintCategoryDB.addRecord(new String[] { categoryName, Long.toString(Instant.now().toEpochMilli()) });

        return IssueManagerStatus.SUCCESS;
    }

    public Complaint getComplaint(String complaintID) {
        return this.allComplaintTable.get(complaintID);
    }

    public ObservableSet<Complaint> getComplaintsWithCategory(String categoryName) {
        ObservableSet<Complaint> res = FXCollections.observableSet();

        Iterator<String> complaintIDs = this.categorizedComplaintTable.get(categoryName).iterator();

        while (complaintIDs.hasNext()) {
            res.add(getComplaint(complaintIDs.next()));
        }

        return res;
    }

    public IssueManagerStatus voteComplaint(ConsumerUser consumerUser, String complaintID) {
        if (Objects.isNull(consumerUser))
            return IssueManagerStatus.INVALID_ACCESS;
        else if (!complaintExist(complaintID))
            return IssueManagerStatus.COMPLAINT_NOT_FOUND;

        if (getComplaint(complaintID).vote(consumerUser.getUID())) {
            this.complaintDB.editRecord(complaintID, "VOTERS", OakHotspot.appendToColonSeparatedValues(this.complaintDB.getDataWhere(complaintID, "VOTERS"), consumerUser.getUID()), false);
        }

        Complaint complaint = getComplaint(complaintID);

        if (complaint.getVoteCount() == 0)
            this.complaintDB.editRecord(complaintID, "VOTERS", OakAppDefaults.NIL, false);
        else
            this.complaintDB.editRecord(complaintID, "VOTERS", OakHotspot.toColonSeparatedValues(complaint.getVoters()), false);

        return IssueManagerStatus.SUCCESS;
    }

    public IssueManagerStatus unVoteComplaint(ConsumerUser consumerUser, String complaintID) {
        if (Objects.isNull(consumerUser))
            return IssueManagerStatus.INVALID_ACCESS;
        else if (!complaintExist(complaintID))
            return IssueManagerStatus.COMPLAINT_NOT_FOUND;

        getComplaint(complaintID).unVote(consumerUser.getUID());

        Complaint complaint = getComplaint(complaintID);

        if (complaint.getVoteCount() == 0)
            this.complaintDB.editRecord(complaintID, "VOTERS", OakAppDefaults.NIL, false);
        else
            this.complaintDB.editRecord(complaintID, "VOTERS", OakHotspot.toColonSeparatedValues(complaint.getVoters()), false);

        return IssueManagerStatus.SUCCESS;
    }

    public IssueManagerStatus resolveComplaint(StaffUser staff, String complaintID) {
        if (Objects.isNull(staff) || !(staff instanceof StaffUser))
            return IssueManagerStatus.INVALID_ACCESS;

        Complaint complaint = getComplaint(complaintID);

        complaint.setStatus(staff, ComplaintStatus.RESOLVED);
        complaint.setCaseManager(staff.getUID());
        this.complaintDB.editRecord(complaintID, "STATUS", ComplaintStatus.RESOLVED.name(), false);
        this.complaintDB.editRecord(complaintID, "CASE_MANAGER_UID", staff.getUID(), false);

        return IssueManagerStatus.SUCCESS;
    }


    /**
     * @section Reports
     */

    public IssueManagerStatus newReport(ReportType type,
                                        String authorUID,
                                        String targetID,
                                        String description) {

        if (!AccountService.getUserManager().userExists(authorUID))
            return IssueManagerStatus.INVALID_ACCESS;

        switch (type) {
            case BEHAVIOR:
                if (!AccountService.getUserManager().userExists(targetID))
                    return IssueManagerStatus.TARGET_NOT_FOUND;
                else if (!OakID.isIDOfType(targetID, ConsumerUser.class))
                    return IssueManagerStatus.WRONG_REPORT_TYPE;

                break;
            case CONTENT:
                if (!complaintExist(targetID))
                    return IssueManagerStatus.TARGET_NOT_FOUND;
                else if (!OakID.isIDOfType(targetID, Complaint.class))
                    return IssueManagerStatus.WRONG_REPORT_TYPE;

                break;
            default:
                return IssueManagerStatus.NO_REPORT_TYPE_SPECIFIED;
        }

        Report report = new Report(OakID.generate(Report.class.getSimpleName()),
                                   type,
                                   authorUID,
                                   targetID,
                                   description,
                                   ReportStatus.PENDING,
                                   null);

        this.reportTable.put(report.getReportID(), report);
        this.reportDB.addRecord(new String[] {
            report.getReportID(),
            report.getType().name(),
            report.getAuthorID(),
            report.getTargetID(),
            report.getDescription(),
            report.getStatus().name(),
            OakAppDefaults.NIL
        });

        return IssueManagerStatus.SUCCESS;
    }

    public Report getReport(String reportID) {
        return new Report(reportID,
                          Enum.valueOf(ReportType.class, this.reportDB.getDataWhere(reportID, "TYPE")),
                          this.reportDB.getDataWhere(reportID, "AUTHOR_UID"),
                          this.reportDB.getDataWhere(reportID, "TARGET_ID"),
                          this.reportDB.getDataWhere(reportID, "DESCRIPTION"),
                          Enum.valueOf(ReportStatus.class, this.reportDB.getDataWhere(reportID, "STATUS")),
                          this.reportDB.getDataWhere(reportID, "RESULT"));
    }

    public IssueManagerStatus deleteReport(String reportID) {
        if (this.reportTable.containsKey(reportID))
            return IssueManagerStatus.REPORT_NOT_FOUND;

        this.reportTable.remove(reportID);
        this.reportDB.removeRecordWhere(reportID);

        return IssueManagerStatus.SUCCESS;
    }

    public IssueManagerStatus reviewReport(AdminUser admin, String reportID, boolean approve) {
        if (!this.reportTable.containsKey(reportID))
            return IssueManagerStatus.REPORT_NOT_FOUND;

        if (approve) {
            if (this.reportTable.get(reportID).approve(admin).equals(ReportResolvingStatus.SUCCESS))
                this.reportDB.removeRecordWhere(reportID);
            else
                return IssueManagerStatus.FAILURE;
        } else {
            if (this.reportTable.get(reportID).deny(admin).equals(ReportResolvingStatus.SUCCESS))
                this.reportDB.removeRecordWhere(reportID);
            else
                return IssueManagerStatus.FAILURE;
        }

        return IssueManagerStatus.SUCCESS;
    }

    public ObservableSet<Report> getBehavioralReportsRelatedToTarget(String targetUID) {
        ObservableSet<Report> res = FXCollections.observableSet();

        Iterator<Entry<String, Report>> reportEntries = this.reportTable.entrySet().iterator();

        while (reportEntries.hasNext()) {
            Entry<String, Report> reportEntry = reportEntries.next();

            if (reportEntry.getValue().getTargetID().equals(targetUID))
                res.add(reportEntry.getValue());
        }

        return res;
    }
}
