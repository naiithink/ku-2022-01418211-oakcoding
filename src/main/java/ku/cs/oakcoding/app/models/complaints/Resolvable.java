package ku.cs.oakcoding.app.models.complaints;

@FunctionalInterface
public interface Resolvable<T> {

    void resolve(Resolver<T> resolver, IssueStatus<T> resolveStatus);
}
