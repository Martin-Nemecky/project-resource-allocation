package backend.project_allocation.services;

import java.util.List;

public interface Service<T, R> {

    R findById(T id);

    List<R> findAll();

    void save(R entity);

    void clear();
}
