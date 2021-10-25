// Joey Hodson
// OOP - Project 2 (11/09/21)

import java.util.*;

/*
Inheritance Structure: 

University (holds an array of people)
Menu
Person (abstract)
    -> Student
    -> Employee (abstract)
        -> Faculty
        -> Staff
*/

public class Main {

    final int size = 100;
    public static void main(String args[]) {

        final int size = 100;

        University myUniversity = new University(size);
        Menu myMenu = new Menu();
        Scanner myScanner = new Scanner(System.in);
        String name, id, department;
        int option;

        System.out.println("\t\t\tWelcome to my Personal Management Program");
        System.out.println("\n\nChoose one of the options:");

        do {

            System.out.println("");
            option =  myMenu.getOption();
            switch(option) {

                case 1:

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
                    } while (Employee.isDepartmentValid(department.toLowerCase()) == false);
                    
                    myUniversity.insertPerson(new Faculty(name, id, department, rank), size);
                    break;

                // enter student info
                case 2:

                    double gpa;
                    int currentCreditHours;

                    System.out.println("\nEnter the student info:");
                    System.out.print("\t\tName of student: ");
                    name = myScanner.nextLine();

                    System.out.print("\t\tID: ");
                    id = myScanner.nextLine();

                    System.out.print("\t\tGpa: ");
                    gpa = myScanner.nextDouble();

                    System.out.print("\t\tCredit hours: ");
                    currentCreditHours = myScanner.nextInt();

                    myUniversity.insertPerson(new Student(name, id, currentCreditHours, gpa), size);
                    name = myScanner.nextLine(); // clears buffer
                    break;

                // print student info
                case 3:

                    System.out.print("\nEnter the student's id: ");
                    id = myScanner.nextLine();
                    myUniversity.print("Student", id);
                    break;

                // print faculty info
                case 4:

                    System.out.print("\nEnter the faculty's id: ");
                    id = myScanner.nextLine();
                    myUniversity.print("Faculty", id);
                    break;

                // enter staff info
                case 5:

                    String status;

                    System.out.println("\n\nEnter staff info:");
                    System.out.print("\t\tName of the staff member: ");
                    name = myScanner.nextLine();

                    System.out.print("\t\tEnter the id: ");
                    id = myScanner.nextLine();

                    do {
                        System.out.print("\t\tDepartment: ");
                        department = myScanner.nextLine();
                    } while (Employee.isDepartmentValid(department.toLowerCase()) == false);

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

                    myUniversity.insertPerson(new Staff(name, id, department, status), size);
                    break;

                // print staff info
                case 6:

                    System.out.print("\nEnter the staff's id: ");
                    id = myScanner.nextLine();
                    myUniversity.print("Staff", id);
                    break;

                // exit program
                case 7:
                    System.out.println("\n\n\n\nGoodbye!\n");
                    break;

                default:
                    System.out.println("\nInvalid Entry - please try again\n");
            }

        } while (option != 7);
    }
}

class University {

    private int peopleCount = 0;
    public Person [] list;

    public int getPeopleCount() {
        return this.peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public University(int size) {

        this.list = new Person[size];

        for (int i = 0; i < size; i++) {
            list[i] = null;
        }
    }

    public Person search(String personnelType, String id) {

        int personIndex = -1;
        for (int i = 0; i < this.getPeopleCount(); i++) {
            if(((this.list[i]).getId()).equals(id)) {
                personIndex = i;
                break;
            }
        }
        
        if (personIndex != -1) {
        
            switch(personnelType.toLowerCase()) {
                case "student":
                    if (((((this.list[personIndex]).getClass()).getName()).toLowerCase()).equals("student")) {
                        return this.list[personIndex];
                    }
                    break;
                case "faculty":
                    if (((((this.list[personIndex]).getClass()).getName()).toLowerCase()).equals("faculty")) {
                        return this.list[personIndex];
                    }
                    break;
                case "staff":
                    if (((((this.list[personIndex]).getClass()).getName()).toLowerCase()).equals("staff")) {
                        return this.list[personIndex];
                    }
                    break;
            }
        }

        return null;
    }

    public void insertPerson(Person person, int maxSize) {

        if (this.getPeopleCount() == maxSize) {
            System.out.println("\nMax people in system.\n"); // add removal option with id as input
            return;
        }

        this.setPeopleCount(this.getPeopleCount() + 1);
        this.list[this.getPeopleCount() - 1] = person;
        System.out.print("\n\t\t"+((this.list[this.getPeopleCount() - 1]).getClass()).getName()+" added!\n\n");
    }

    public void print(String personnelType, String id) {

        Person person = this.search(personnelType, id);
        if (person == null) {
            System.out.println("\tNo "+personnelType.toLowerCase()+" match!\n");
            return;
        }

        person.print();
    }


}

abstract class Person {

    private String name, id;
    private int index;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public abstract void print();
}


abstract class Employee extends Person {

    private String department;

    public Employee(String name, String id, String department) {
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
            department.equals("sciences")) {
            return true;
        }
        System.out.println("\t\t\t\""+department+"\" is invalid");
        return false;
    }

    public abstract void print();
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

    @Override
    public void print() {
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


class Faculty extends Employee {

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

    @Override
    public void print() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(this.getName()+"\t\t"+this.getId());
        System.out.println(this.getDepartment()+" Department, "+this.getRank());
        System.out.println("-----------------------------------------------------------------------");
    }
}


class Staff extends Employee {

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

    @Override
    public void print() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(this.getName()+"\t\t"+this.getId());
        System.out.println(this.getDepartment()+" Department, "+this.getStatus()+" Time");
        System.out.println("-----------------------------------------------------------------------");
    }
}


class Menu {

    Scanner myScanner = new Scanner(System.in);

    public int getOption() {

        try {

            String option;
            int intOption;

            System.out.println("1- Enter the information of a faculty member");
            System.out.println("2- Enter the information of a student");
            System.out.println("3- Print tuition invoive for a student");
            System.out.println("4- Print faculty information");
            System.out.println("5- Enter the information of a staff member");
            System.out.println("6- Print the information of a staff member");
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