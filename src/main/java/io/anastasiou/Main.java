package io.anastasiou;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
            "empm", "root", "changeit");
    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = dcm.getConnection();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        while(true) {
            System.out.println();
            System.out.println("1. Get all employees");
            System.out.println("2. Get an employee by ID");
            System.out.println("3. Add a new employee");
            System.out.println("4. Update an employee");
            System.out.println("5. Delete an employee");
            System.out.println("6. Exit");

            int opt = 0;

            while(true) {
                try {
                    opt = Integer.parseInt(scanner.nextLine());
                    break;
                } catch(NumberFormatException e) {
                    System.out.println("Please enter a valid number");
                }
            }

            switch(opt) {
                case 1:
                    getAllEmployees();

                    break;
                case 2:
                    getEmployee();

                    break;
                case 3:
                    addEmployee();

                    break;
                case 4:
                    updateEmployee();

                    break;
                case 5:
                    deleteEmployee();

                    break;
                case 6:
                    return;
                default:
                    System.out.println("Please enter a valid number");
                    break;
            }
        }
    }

    private static void deleteEmployee() {
        System.out.println("Please enter an ID");

        int deleteId = 0;

        while(true) {
            try {
                deleteId = Integer.parseInt(scanner.nextLine());
                break;
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }

        new EmployeeDAO(conn).delete(deleteId);
        System.out.printf("Employee with ID '%d' was deleted successfully%n", deleteId);
    }

    private static void updateEmployee() {
        System.out.println("Please enter an ID");

        int updateId = 0;

        while(true) {
            try {
                updateId = Integer.parseInt(scanner.nextLine());
                break;
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }

        Employee updateEmployee = new EmployeeDAO(conn).getById(updateId);

        if(updateEmployee == null) {
            return;
        }

        getEmployeeData(updateEmployee);

        System.out.println(new EmployeeDAO(conn).update(updateEmployee));
    }

    private static void addEmployee() {
        Employee insertEmployee = new Employee();

        getEmployeeData(insertEmployee);

        new EmployeeDAO(conn).create(insertEmployee);
        System.out.printf("Employee '%s %s' was added successfully%n", insertEmployee.getFirstName(), insertEmployee.getLastName());
    }

    private static void getEmployee() {
        System.out.println("Please enter an ID");

        int selectId = 0;

        while(true) {
            try {
                selectId = Integer.parseInt(scanner.nextLine());
                break;
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }

        Employee selectEmployee = new EmployeeDAO(conn).getById(selectId);

        if(selectEmployee == null) {
            System.out.println("Please enter a valid ID");
        } else {
            System.out.println(selectEmployee);
        }
    }

    private static void getAllEmployees() {
        List<Employee> employees = new EmployeeDAO(conn).getAll();

        for(Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void getEmployeeData(Employee updateEmployee) {
        System.out.println("Please enter a first name");

        while(true) {
            String tempFirstName = scanner.nextLine().strip();

            if(!tempFirstName.isBlank()) {
                updateEmployee.setFirstName(tempFirstName);
                break;
            } else {
                System.out.println("Please enter a valid first name");
            }
        }

        System.out.println("Please enter a last name");

        while(true) {
            String tempLastName = scanner.nextLine().strip();

            if(!tempLastName.isBlank()) {
                updateEmployee.setLastName(tempLastName);
                break;
            } else {
                System.out.println("Please enter a valid last name");
            }
        }

        System.out.println("Please enter a position");

        while(true) {
            String tempPosition = scanner.nextLine().strip();

            if(!tempPosition.isBlank()) {
                updateEmployee.setPosition(tempPosition);
                break;
            } else {
                System.out.println("Please enter a valid position");
            }
        }

        System.out.println("Please enter a salary");

        while(true) {
            try {
                updateEmployee.setSalary(Double.parseDouble(scanner.nextLine()));
                break;
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}
