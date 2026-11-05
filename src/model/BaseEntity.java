package model;

import java.util.Objects;

public abstract class BaseEntity {

    private final long id;
    private final String name;

    protected BaseEntity(long id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва/ім'я сутності не може бути порожнім.");
        }
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}