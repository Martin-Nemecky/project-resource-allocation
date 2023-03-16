package backend.project_allocation.repositories;

import java.util.List;
import java.util.Optional;

public interface Repository<T, R> {

    Optional<R> findById(T id);

    List<R> findAll();

    void save(R entity);

    Optional<R> delete(T id);
}
