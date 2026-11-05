package util;

import model.BaseEntity;
import java.util.Collection;

public class EntityPrinter {
    public static void printAll(Collection<? extends BaseEntity> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("Колекція порожня.");
            return;
        }
        for (BaseEntity item : items) {
            System.out.println(item);
        }
    }
}