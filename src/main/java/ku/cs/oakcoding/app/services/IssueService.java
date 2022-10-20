package ku.cs.oakcoding.app.services;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public class IssueService implements AppService {

    public IssueService() {}

    private static IssueManager issueManager;

    @Override
    public void start() {
        Path issueDir = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_ISSUE_DIR.value());
        Path complaintDir = issueDir.resolve(OakAppDefaults.APP_COMPLAINT_DIR.value());
        Path reportDir = issueDir.resolve(OakAppDefaults.APP_REPORT_DIR.value());

        AutoUpdateCSV complaintCategoryDB = null;
        AutoUpdateCSV complaintDB = null;
        AutoUpdateCSV reportDB = null;

        try {
            complaintCategoryDB = new AutoUpdateCSV("complaintCategoryDB", "CATEGORY", "complaintCategoryDB", complaintDir.resolve(OakAppDefaults.APP_COMPLAINT_CATEGORIES_FILE.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Complaint data file not found");
            System.exit(1);
        }

        try {
            complaintDB = new AutoUpdateCSV("complaintDB", "COMPLAINT_ID", "complaintDB", complaintDir.resolve(OakAppDefaults.APP_COMPLAINT_FILE.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Complaint data file not found");
            System.exit(1);
        }

        try {
            reportDB = new AutoUpdateCSV("reportDB", "REPORT_ID", "reportDB", reportDir.resolve(OakAppDefaults.APP_REPORT_FILE.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Complaint data file not found");
            System.exit(1);
        }

        issueManager = new IssueManager(complaintCategoryDB, complaintDB, reportDB);
    }

    public static IssueManager getIssueManager() {
        return issueManager;
    }

    public static void main(String[] args) {
        IssueService iss = new IssueService();
        iss.start();

        // IssueService.getIssueManager().newComplaint(null, null, null, null, null, null, null)
    }
}
