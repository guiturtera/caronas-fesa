package com.carona.caronasfesa.models;

public class User extends AbstractModel {

    public User() {
    }

    public User(Integer id, String description, String studentId, String name, Course course, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
        this.phoneNumber = phoneNumber;
    }

    private String name;
    private String description;
    private String studentId;
    private Course course;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

enum Course {
    ComputerEngineering,
    FoodEngineering,
    BusinessAdministration,
    ControlAndAutomationEngineering
}