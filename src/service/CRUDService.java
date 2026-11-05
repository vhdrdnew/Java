package service;

import exceptions.EntityNotFoundException;
import model.BaseEntity;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface CRUDService<T extends BaseEntity> {

    T save(T entity);

    Optional<T> findById(long id);

    boolean delete(long id) throws EntityNotFoundException;

    List<T> findAll();

    List<T> findAllSorted(Comparator<? super T> comparator);

    default List<T> findAllSortedByName() {
        return findAllSorted(Comparator.comparing(BaseEntity::getName, String.CASE_INSENSITIVE_ORDER));
    }
}