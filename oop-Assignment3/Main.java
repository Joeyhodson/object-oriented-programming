// Joey Hodson
// OOP - Project 3 (12/02/21)

import java.util.*;
import java.io.*;

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

    public static void main(String args[]) {

        final int maxSize = 100;
        University myUniversity = new University();
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

                    do {
                        try {
                            System.out.print("\t\tID: ");
                            id = myScanner.nextLine();
                            Person.validateId(myUniversity, id);
                            break;
                        }
                        catch (IdException ex) {
                            continue;
                        }
                    } while (true);

                    do {
                        System.out.print("\t\tRank: ");
                        rank = myScanner.nextLine();
                    } while (Faculty.isRankValid(rank.toLowerCase()) == false);

                    do {
                        System.out.print("\t\tDepartment: ");
                        department = myScanner.nextLine();
                    } while (Employee.isDepartmentValid(department.toLowerCase()) == false);
                    
                    myUniversity.insertPerson(new Faculty(name, id, department, rank), maxSize);
                    break;

                // enter student info
                case 2:

                    double gpa;
                    int currentCreditHours;

                    System.out.println("\nEnter the student info:");
                    System.out.print("\t\tName of student: ");
                    name = myScanner.nextLine();

                    do {
                        try {
                            System.out.print("\t\tID: ");
                            id = myScanner.nextLine();
                            Person.validateId(myUniversity, id);
                            break;
                        }
                        catch (IdException ex) {
                            continue;
                        }
                    } while (true);

                    System.out.print("\t\tGpa: ");
                    gpa = myScanner.nextDouble();

                    System.out.print("\t\tCredit hours: ");
                    currentCreditHours = myScanner.nextInt();

                    myUniversity.insertPerson(new Student(name, id, currentCreditHours, gpa), maxSize);
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

                    do {
                        try {
                            System.out.print("\t\tID: ");
                            id = myScanner.nextLine();
                            Person.validateId(myUniversity, id);
                            break;
                        }
                        catch (IdException ex) {
                            continue;
                        }
                    } while (true);

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

                    myUniversity.insertPerson(new Staff(name, id, department, status), maxSize);
                    break;

                // print staff info
                case 6:

                    System.out.print("\nEnter the staff's id: ");
                    id = myScanner.nextLine();
                    myUniversity.print("Staff", id);
                    break;

                // exit program
                case 7:

                    String generateReport;

                    do {
                        System.out.print("\n\nWould you like to create the report? (Y/N): ");
                        generateReport = myScanner.nextLine();
                        generateReport = generateReport.toLowerCase();
                    } while ((!(generateReport.equals("y"))) && (!(generateReport.equals("n"))));

                    if (generateReport.equals("y")) {
                        myUniversity.generateAndExportReport();
                    }

                    System.out.println("\n\n\n\nGoodbye!\n");
                    break;

                default:
                    System.out.println("\nInvalid Entry - please try again\n");
            }

        } while (option != 7);
    }
}

class IdException extends Exception {

    public IdException(String errorMessage) {
        System.out.println(errorMessage);
    }
}

class University {

    private HashMap<String, Person> list;

    public University() {
        this.list = new HashMap<String, Person>();
    }

    public HashMap<String, Person> getList() {
        return this.list;
    }

    public void insertPerson(Person person, int maxSize) {

        if (this.list.size() == maxSize) {
            System.out.println("\nMax people in system.\n"); // maybe add removal option with id as input
            return;
        }

        this.list.put(person.getId(), person);
        System.out.print("\n\t\t" + (this.list.get(person.getId()).getClass().getName() + " added!\n\n"));
    }

    public void print(String personnelType, String id) {

        Person person = this.list.get(id);

        if (person == null || !(person.getClass().getName().equals(personnelType))) {
            System.out.println("\tNo "+personnelType.toLowerCase()+" match!\n");
            return;
        }

        person.print();
    }

    public void generateAndExportReport() {

        ArrayList<Student> StudentList = new ArrayList<Student>();
        ArrayList<Faculty> FacultyList = new ArrayList<Faculty>();
        ArrayList<Staff> StaffList = new ArrayList<Staff>();

        for (Person value : this.list.values()) {
            if (value instanceof Student) {
                StudentList.add((Student)value);
            }
            else if (value instanceof Faculty) {
                FacultyList.add((Faculty)value);
            }
            else {
                StaffList.add((Staff)value);
            }
        }

        try {

            Date date = new Date();
            FileWriter reportStream = new FileWriter("report.txt");
            BufferedWriter report = new BufferedWriter(reportStream);

            report.write("\t\tReport created on " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() % 2000));
            report.write("\t\t**********************");

            report.write("Faculty Members:\n---------------\n");
            for (int i = 0; i < FacultyList.size(); i++) {
                report.write("\t"+ (i+1) + "." + FacultyList.get(i).getName());
                report.write("\tID: " + FacultyList.get(i).getId());
                report.write("\t" + FacultyList.get(i).getRank() + ", " + FacultyList.get(i).getDepartment());
            }

            report.write("Staff Members:\n---------------\n");
            for (int i = 0; i < StaffList.size(); i++) {
                report.write("\t"+ (i+1) + "." + StaffList.get(i).getName());
                report.write("\tID: " + StaffList.get(i).getId());
                report.write("\t" + StaffList.get(i).getDepartment() + ", " + StaffList.get(i).getStatus() + " Time");
            }

            report.write("Students:\n---------------\n");
            for (int i = 0; i < StudentList.size(); i++) {
                report.write("\t"+ (i+1) + "." + StudentList.get(i).getName());
                report.write("\tID: " + StudentList.get(i).getId());
                report.write("\tGPA: " + StudentList.get(i).getGpa());
                report.write("\tCredit hours: " + StudentList.get(i).getCurrentCreditHours());
            }

            System.out.println("Report created and saved to hard drive!");
        }
        catch (Exception ex) {
            System.out.println("Error generating report.");
        }
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

    public static void validateId(University myUniversity, String id) throws IdException {

        id = id.toLowerCase();

        if (myUniversity.getList().containsKey(id)) {
            throw new IdException("\t\tUser ID already in system.\n");
        }

        if (id.length() == 6 &&
        Character.isLetter(id.charAt(0)) &&
        Character.isLetter(id.charAt(1)) &&
        Character.isDigit(id.charAt(2)) &&
        Character.isDigit(id.charAt(3)) &&
        Character.isDigit(id.charAt(4)) &&
        Character.isDigit(id.charAt(5)))
        {
            return;
        }

        throw new IdException("\t\tInvalid ID format. Must be LetterLetterDigitDigitDigitDigit.\n");
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
        System.out.println("\t\t\""+department+"\" is invalid\n");
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
            return 0.25*this.getTotalFees();
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
        System.out.println("\t\t\""+rank+"\" is invalid\n");
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