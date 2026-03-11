package Model;


public interface Criterion<T> {
    public boolean isSatisfiedBy(T c);
}