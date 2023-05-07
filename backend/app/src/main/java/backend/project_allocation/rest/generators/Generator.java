package backend.project_allocation.rest.generators;

public interface Generator<T> {

    T generate(int count);

    void generate();
}
