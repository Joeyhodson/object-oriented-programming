// Joey Hodson
// OOP - Project 1 (9/20/21)

import java.util.*;

/*
Inheritance Structure: 

Menu
University? (act on all people at once)
Person
    -> Student
    -> DepartmentPerson
        -> Faculty
        -> Staff
*/


public class Main {

    public static void main(String args[]) {

        Menu myMenu = new Menu();
        Scanner myScanner = new Scanner(System.in);
        String name, id;
        int option;

        Student students[] = new Student[2];
        Faculty faculty;
        Staff staff;


        System.out.println("\t\t\tWelcome to my Personal Management Program");
        System.out.println("\n\nChoose one of the options:");

        do {

            System.out.println("");
            option =  myMenu.getOption();
            switch(option) {

                case 1:

                    // check if students are occupied
                    String rank, department;

                    System.out.println("\n\nEnter faculty info:\n");
                    System.out.print("\t\tName of the faculty: ");
                    name = myScanner.nextLine();

                    System.out.print("\t\tID: ");
                    id = myScanner.nextLine();

                    do {
                        System.out.print("\t\tRank: ");
                        rank = myScanner.nextLine();
                    } while (Faculty.isRankValid(rank.toLowerCase()) == false);

                    do {
                        System.out.print("\t\tDepartment: ");
                        department = myScanner.nextLine();
                    } while (DepartmentPerson.isDepartmentValid(department.toLowerCase()) == false);
                    
                    faculty = new Faculty(name, id, department, rank);
                    System.out.print("\n\t\tFaculty successfully added!\n\n");
                    break;

                case 2:

                    // check if faculty is occupied
                    double gpa;
                    int currentCreditHours;

                    for (int i = 1; i <= 2; i++) {
                        System.out.println("\nEnter student "+i+" info:\n");
                        System.out.print("\t\tName of student: ");
                        name = myScanner.nextLine();

                        System.out.print("\t\tID: ");
                        id = myScanner.nextLine();

                        System.out.print("\t\tGpa: ");
                        gpa = myScanner.nextDouble();

                        System.out.print("\t\tCredit hours: ");
                        currentCreditHours = myScanner.nextInt();

                        students[i-1] = new Student(name, id, currentCreditHours, gpa);
                        System.out.print("Thanks!\n\n");
                        name = myScanner.nextLine(); // clears buffer
                    }
                    break;

                case 3:
                    // check if students exist, ask for specific student
                    break;

                case 4:
                    // check if factulty exists
                    break;

                case 5:
                    // check if staff if occupied
                    break;

                case 6:
                    // check if staff exists
                    break;

                case 7:
                    System.out.println("\n\n\n\nGoodbye!\n");
                    break;

                default:
                    System.out.println("\nInvalid Entry - please try again\n");
            }

        } while (option != 7);
    }
}

/*
public class University {

    ArrayList<
}
*/

abstract class Person {

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


abstract class DepartmentPerson extends Person {

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

    public static boolean isDepartmentValid(String department) {

        if (department.equals("mathematics") || 
            department.equals("engineering") || 
            department.equals("english")) {
            return true;
        }
        System.out.println("\t\t\t\""+department+"\" is invalid");
        return false;
    }
}


class Student extends Person {

    private double creditHourPrice = 236.45;
    private double standardFee = 52;

    private int currentCreditHours;
    private double gpa;

    public Student(String name, String id, int currentCreditHours, double gpa) {
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


class Faculty extends DepartmentPerson {

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

    public static boolean isRankValid(String rank) {

        if (rank.equals("professor") ||
            rank.equals("dean") ||
            rank.equals("researcher")) {
            return true;
        }
        System.out.println("\t\t\t\""+rank+"\" is invalid");
        return false;
    }

}


class Staff extends DepartmentPerson {

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


class Menu {

    public int getOption() {

        try {

            String option;
            int intOption;

            System.out.println("1- Enter the information of the faculty member");
            System.out.println("2- Enter the information of the two students");
            System.out.println("3- Print tuition invoive");
            System.out.println("4- Print faculty information");
            System.out.println("5- Enter the information of the staff member");
            System.out.println("6- Print the information of the staff member");
            System.out.println("7- Exit Program");

            System.out.print("\n\tEnter your selection: ");

            // todo - save scanner object in a variable to avoid instaniating manyt times
            option = (new Scanner(System.in)).nextLine();
            intOption = Integer.parseInt(option);

            return intOption;
        }

        catch (NumberFormatException ex) {
            return 0; // defaults in main switch block
        }
    }
}