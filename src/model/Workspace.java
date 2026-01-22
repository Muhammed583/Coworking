public class Workspace {
    private int id;
    private String name;
    private double hourlyRate;

    public Workspace(int id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getHourlyRate() { return hourlyRate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
}