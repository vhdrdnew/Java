package service;

import exceptions.EntityNotFoundException;
import model.BaseEntity;
import java.util.*;

public class InMemoryCrudService<T extends BaseEntity> implements CRUDService<T> {

    protected final Map<Long, T> storage = new LinkedHashMap<>();

    @Override
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Сутність не може бути null.");
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public boolean delete(long id) throws EntityNotFoundException {
        if (!storage.containsKey(id)) {
            throw new EntityNotFoundException(id);
        }
        storage.remove(id);
        return true;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<T> findAllSorted(Comparator<? super T> comparator) {
        List<T> list = findAll();
        list.sort(comparator);
        return list;
    }
}