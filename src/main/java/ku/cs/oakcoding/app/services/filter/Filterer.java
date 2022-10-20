package ku.cs.oakcoding.app.services.filter;

public interface Filterer<T> {
    boolean check(T t);
}
