package model;

public class User {
    private int id;
    private String fullName;
    private String membershipType; // STUDENT, RESIDENT, etc.

    public User(String fullName, String membershipType) {
        this.fullName = fullName;
        this.membershipType = membershipType;
    }

    public User(int id, String fullName, String membershipType) {
        this.id = id;
        this.fullName = fullName;
        this.membershipType = membershipType;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public String getMembershipType() { return membershipType; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
}