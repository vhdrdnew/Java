package model;

public interface Exportable {

    String toCsvLine();

    static String csvHeader() {
        return "title;priority;hours;deadline;completed";
    }
}
