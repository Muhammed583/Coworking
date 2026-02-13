package model;

public class Workspace {
    private final int id;
    private final String name;
    private final double hourlyRate;
    private final String category;
    private boolean isOccupied;

    public Workspace(int id, String name, double hourlyRate, String category) {
        this(id, name, hourlyRate, category, false);
    }

    public Workspace(int id, String name, double hourlyRate, String category, boolean isOccupied) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.category = category;
        this.isOccupied = isOccupied;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getHourlyRate() { return hourlyRate; }
    public String getCategory() { return category; }
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { this.isOccupied = occupied; }

    @Override
    public String toString() {
        String statusText = isOccupied ? "[OCCUPIED]" : String.format("%.0f tg/h", hourlyRate);
        return String.format("%-3d | %-20s | %-15s", id, name, statusText);
    }
}
