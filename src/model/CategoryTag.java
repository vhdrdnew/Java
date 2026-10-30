package model;
import java.util.Objects;

public class CategoryTag {
    private final String tagName;

    public CategoryTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() { return tagName; }

    @Override
    public String toString() { return tagName; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CategoryTag that = (CategoryTag) obj;
        return Objects.equals(tagName, that.tagName);
    }

    @Override
    public int hashCode() { return Objects.hash(tagName); }
}