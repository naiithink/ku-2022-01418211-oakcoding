package ku.cs.oakcoding.app.models.complaints;

@FunctionalInterface
public interface CaseAssignable {

    void assign(String categoryName);

    default Class<?> getManagerType() {
        return getClass();
    }
}
