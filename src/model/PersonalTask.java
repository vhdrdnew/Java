package model;
import exceptions.DomainException;

public non-sealed class PersonalTask extends Task {

    private final String location;

    public PersonalTask(String title, String priority, double estimatedHours, int deadlineDays, boolean isCompleted, CategoryTag tag, String location)
            throws DomainException {
        super(title, priority, estimatedHours, deadlineDays, isCompleted, tag);
        this.location = (location == null || location.isBlank()) ? "Home" : location;
    }

    @Override
    public String getTaskType() { return "PERSONAL"; }

    public String getLocation() { return location; }

    @Override
    public String toString() {
        return super.toString() + " | Локація: " + location;
    }
}