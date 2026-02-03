package model;

public class Workspace {
    private final int id;
    private final String name;
    private final double hourlyRate;

    public Workspace(int id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getHourlyRate() { return hourlyRate; }

    // Сеттеры удалены для целостности данных
}