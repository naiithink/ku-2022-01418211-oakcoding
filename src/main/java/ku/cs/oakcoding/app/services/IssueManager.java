package ku.cs.oakcoding.app.services;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.helpers.id.OakID;
import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.ComplaintStatus;
import ku.cs.oakcoding.app.models.complaints.Report;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public class IssueManager {

    private ObservableMap<String, ObservableSet<String>> complaintTable;

    private ObservableMap<String, String> reportTable;

    private AutoUpdateCSV complaintCategoryDB;

    private AutoUpdateCSV complaintDB;

    private AutoUpdateCSV reportDB;

    public IssueManager(AutoUpdateCSV complaintCategoryDB, AutoUpdateCSV complaintDB, AutoUpdateCSV reportDB) {
        this.complaintCategoryDB = complaintCategoryDB;
        this.complaintDB = complaintDB;
        this.reportDB = reportDB;

        this.complaintTable = FXCollections.observableHashMap();
        this.reportTable = FXCollections.observableHashMap();

        Set<String> complaintCategoryKeys = complaintCategoryDB.getPrimaryKeySet();

        for (String complaintCategoryKey : complaintCategoryKeys) {
            this.complaintTable.put(complaintCategoryKey, FXCollections.observableSet());
        }

        Set<String> complaintKeys = complaintDB.getPrimaryKeySet();

        for (String complaintKey : complaintKeys) {
            this.complaintTable.get(this.complaintDB.getDataWhere(complaintKey, "CATEGORY")).add(complaintKey);
        }

        Set<String> reportKeys = this.reportDB.getPrimaryKeySet();

        // for (String reportKey : reportKeys) {
        //     this.reportTable.put(reportKey, );
        // }
    }

    // public void newComplaint(String authorUID, String category, String subject, String description, Object evidence, Set<String> voters, ComplaintStatus status, String caseManagerUID) {
    //     Complaint complaint = new Complaint(OakID.generate(Complaint.class.getSimpleName()), authorUID, category, subject, description, evidence, voters, status, caseManagerUID);
    //     this.complaintTable.put(complaint.getComplaintID(), complaint);
    // }
}
