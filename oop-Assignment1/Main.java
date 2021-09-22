// Joey Hodson
// OOP - Project 1 (9/20/21)

import java.util.*;


public class Main {

    public static void main(String args[]) {

    }
}


public class University {

}


public abstract class Person {

    private String name, id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


public abstract class DepartmentPerson extends Person {

    private String department;

    public DepartmentPerson(String name, String id, String department) {
        super(name, id);
        this.department = department;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}


public class Student extends Person {

    private double creditHourPrice = 236.45;
    private double standardFee = 52;

    private int currentCreditHours;
    private double gpa;

    public Student(String name, String id, int currentCreditHours, float gpa) {
        super(name, id);
        this.currentCreditHours = currentCreditHours;
        this.gpa = gpa;
    }

    public double getTuition() {
        return currentCreditHours*creditHourPrice;
    }

    public double getTotalFees() {
        return getTuition()+standardFee;
    }

    public int getCurrentCreditHours() {
        return currentCreditHours;
    }

    public void setCurrentCreditHours(int currentCreditHours) {
        this.currentCreditHours = currentCreditHours;
    }

    public double getGpa() {
        return this.gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}


public class Faculty extends DepartmentPerson {

    private String rank;

    public Faculty(String name, String id, String department, String rank) {
        super(name, id, department);
        this.rank = rank;
    }

    public String getRank() {
        return this.rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}


public class Staff extends DepartmentPerson {

    private String status;

    public Staff(String name, String id, String department, String status) {
        super(name, id, department);
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


public class Menu {

    public int getOption() {

        int option;

        System.out.println("1- Enter the information of the faculty member");
        System.out.println("2- Enter the information of the two students");
        System.out.println("3- Print the tuition invoive");
        System.out.println("4- Print faculty information");
        System.out.println("5- Enter the information of the staff member");
        System.out.println("6- Print the information of the staff member");
        System.out.println("7- Exit Program");

        System.out.print("\n\tEnter your selection: ");

        // todo - save scanner object in a variable to avoid instaniating manyt times
        option = (new Scanner(System.in)).nextInt();

        return option;
    }
}