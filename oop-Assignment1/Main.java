// Joey Hodson
// OOP - Project 1 (9/20/21)

import java.util.*;

/*
Inheritance Structure: 

To Do: University? (act on all people at once)
Menu
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
        String name, id, department;
        int option;

        Student students[] = new Student[2];
        Faculty faculty = null;
        Staff staff = null;


        System.out.println("\t\t\tWelcome to my Personal Management Program");
        System.out.println("\n\nChoose one of the options:");

        do {

            System.out.println("");
            option =  myMenu.getOption();
            switch(option) {

                case 1:

                    // check if faculty is occupied
                    if (faculty != null) {
                        System.out.println("\tYou already have a Faculty member filled in. Do you want to update their information?");
                        if (!(myMenu.updateOption())) {
                            break;
                        }
                    }

                    // enter faculty info
                    String rank;

                    System.out.println("\n\nEnter faculty info:");
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

                    if (students[0] != null) {
                        System.out.println("\tYou already have two students filled in. Do you want to update their information?");
                        if (!(myMenu.updateOption())) {
                            break;
                        }
                    }

                    double gpa;
                    int currentCreditHours;

                    for (int i = 1; i <= 2; i++) {
                        System.out.println("\nEnter student "+i+" info:");
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

                    // two students must be entered (i.e. if students[0] doesn't exist neither will students[1])
                    if (students[0] == null) {
                        System.out.println("\n\nSorry! No students have been entered yet\n");
                        break;
                    }

                    int studentChoice;

                    System.out.print("\nWhich student? Enter 1 for "+students[0].getName()+" or Enter 2 "+students[1].getName()+" ? ");
                    studentChoice = myScanner.nextInt();

                    if (studentChoice == 1 || studentChoice == 2) {
                        System.out.println("\nHere is the tuition invoice for "+students[studentChoice-1].getName()+" :");
                        students[studentChoice-1].displayTuition();
                    }
                    else {
                        System.out.println("Invalid Choice, try again.");
                    }
                    
                    myScanner.nextLine(); // clears buffer
                    break;

                case 4:

                    if(faculty == null) {
                        System.out.println("\n\nSorry! No Faculty member entered yet\n");
                        break;
                    }

                    faculty.displayFaculty();
                    break;

                case 5:
                    
                    // check if staff is occupied
                    if (staff != null) {
                        System.out.println("\tYou already have a Staff member filled in. Do you want to update their information?");
                        if (!(myMenu.updateOption())) {
                            break;
                        }
                    }

                    String status;

                    System.out.println("\n\nEnter staff info:");
                    System.out.print("\t\tName of the staff member: ");
                    name = myScanner.nextLine();

                    System.out.print("\t\tEnter the id: ");
                    id = myScanner.nextLine();

                    do {
                        System.out.print("\t\tDepartment: ");
                        department = myScanner.nextLine();
                    } while (DepartmentPerson.isDepartmentValid(department.toLowerCase()) == false);

                    do {
                        System.out.print("\t\tStatus, Enter P for Part Time or Enter F for Full Time: ");
                        status = myScanner.nextLine();
                        status = status.toLowerCase();
                    } while ((!(status.equals("p"))) && (!(status.equals("f"))));
                    
                    if (status.equals("p")) {
                        status = "Part";
                    }
                    else {
                        status = "Full";
                    }

                    staff = new Staff(name, id, department, status);
                    System.out.print("\n\t\tStaff member added!\n\n");
                    break;

                case 6:

                    if(staff == null) {
                        System.out.println("\n\nSorry! No Staff member entered yet\n");
                        break;
                    }

                    staff.displayStaff();
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
        return (this.currentCreditHours)*creditHourPrice;
    }

    public double getTotalFees() {
        return this.getTuition()+standardFee;
    }

    public double getBonus() {
        if (this.gpa >= 3.85) {
            return 0.15*this.getTotalFees();
        }
        return 0;
    }

    public int getCurrentCreditHours() {
        return this.currentCreditHours;
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

    public void displayTuition() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(this.getName()+"\t\t"+this.getId());
        System.out.println("Credit Hours: "+this.getCurrentCreditHours()+" ($"+creditHourPrice+"/credit hour)");
        System.out.print("Fees: $");
        System.out.printf("%.0f", standardFee);
        System.out.print("\n\nTotal payment (after discount): $");
        System.out.printf("%.2f", (this.getTotalFees()-this.getBonus()));
        System.out.print("\t($");
        System.out.printf("%.0f", this.getBonus());
        System.out.print(" discount applied)\n");
        System.out.println("-----------------------------------------------------------------------");
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

    public void displayFaculty() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(this.getName()+"\t\t"+this.getId());
        System.out.println(this.getDepartment()+" Department, "+this.rank);
        System.out.println("-----------------------------------------------------------------------");
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

    public void displayStaff() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(this.getName()+"\t\t"+this.getId());
        System.out.println(this.getDepartment()+" Department, "+this.status+" Time");
        System.out.println("-----------------------------------------------------------------------");
    }
}


class Menu {

    Scanner myScanner = new Scanner(System.in);

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

            option = myScanner.nextLine();
            intOption = Integer.parseInt(option);

            return intOption;
        }

        catch (NumberFormatException ex) {
            return 0; // defaults in main's switch block
        }
    }
    
    public boolean updateOption() {

        String option;

        do {
            System.out.print("\tYes or No: ");
            option = myScanner.nextLine();
            option = option.toLowerCase();
        } while ((!(option.equals("yes"))) && (!(option.equals("no"))));

        if (option.equals("no")) {
            return false;
        }
        return true;
    }
}