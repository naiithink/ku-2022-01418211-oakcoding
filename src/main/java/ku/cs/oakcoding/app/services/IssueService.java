package ku.cs.oakcoding.app.services;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import ku.cs.oakcoding.app.models.complaints.ComplaintManager;
import ku.cs.oakcoding.app.models.complaints.ReportManager;

public final class IssueService {
    private IssueService() {}

    private static final ReadOnlyBooleanWrapper isActive = new ReadOnlyBooleanWrapper(false);

    private static ReportManager reportManager;

    private static ComplaintManager complaintManager;

    public static void start() {
        complaintManager = new ComplaintManager();
        reportManager = new ReportManager(complaintManager);

        isActive.set(true);
    }

    public static boolean isActive() {
        return isActive.get();
    }

    public static ReadOnlyBooleanProperty getReadOnlyIsActiveProperty() {
        return isActive.getReadOnlyProperty();
    }
}
