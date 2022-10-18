package ku.cs.oakcoding.app.models.complaints;

public interface CaseAssignable {

    boolean wasAssigned(String categoryName);

    void assign(String categoryName);

    void removeAssignment(String categoryName);

    default Class<?> getManagerType() {
        return getClass();
    }
}
