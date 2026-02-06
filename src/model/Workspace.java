package model;

public class Workspace {
    private final int id;
    private final String name;
    private final double hourlyRate;
    private final String category;

    public Workspace(int id, String name, double hourlyRate, String category) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getHourlyRate() { return hourlyRate; }
    public String getCategory() { return category; }
}
