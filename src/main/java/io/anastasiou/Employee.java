package io.anastasiou;

import io.anastasiou.util.DataTransferObject;

public class Employee implements DataTransferObject {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private double salary;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("ID: %-16d | %-20s | %-20s | $%,.2f", this.id, this.firstName + " " + this.lastName, this.position, this.salary);
    }
}
