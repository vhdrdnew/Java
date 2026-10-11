/** Окремий інтерфейс: вміння експортуватись у CSV-рядок. */
public interface Exportable {

    String toCsvLine();

    /** static-метод інтерфейсу: належить інтерфейсу, а не об'єкту. */
    static String csvHeader() {
        return "title;priority;hours;deadline;completed";
    }
}
