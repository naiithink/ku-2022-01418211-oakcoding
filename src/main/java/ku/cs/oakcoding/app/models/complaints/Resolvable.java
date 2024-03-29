package ku.cs.oakcoding.app.models.complaints;

@FunctionalInterface
public interface Resolvable<T> {

    void resolve(T t);
}
