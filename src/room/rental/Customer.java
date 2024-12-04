    package room.rental;

import java.util.Scanner;

public class Customer {

    public void manageCustomer() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("\n-----------------------");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Exit");
            System.out.println("-----------------------");

            System.out.print("Enter Selection: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number:");
                sc.next(); 
            }
            int action = sc.nextInt();

            switch (action) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewCustomers();
                    break;
                case 3:
                     viewCustomers();
                    updateCustomer();
                     viewCustomers();
                    break;
                case 4:
                     viewCustomers();
                    deleteCustomer();
                     viewCustomers();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    sc.close();  
                    return;
                default:
                    System.out.println("Invalid selection! Please choose a valid option.");
            }

            while (true) {
    System.out.print("Do you want to continue? (yes/no): ");
    response = sc.next().trim().toLowerCase();
    
    if (response.equals("yes")) {
        break; 
    } else if (response.equals("no")) {
        return;
    } else {
        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
    }
}


        } while (response.equalsIgnoreCase("yes"));

        sc.close(); 
    }

    public void addCustomer() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Customer First Name: ");
        String fname = validateStringInput(sc, "^[a-zA-Z]+$", "Invalid name! Please enter alphabets only.");

        System.out.print("Customer Last Name: ");
        String lname = validateStringInput(sc, "^[a-zA-Z]+$", "Invalid name! Please enter alphabets only.");

        String email;
        do {
            sc.nextLine();  
            System.out.print("Customer Email: ");
            email = sc.nextLine().trim();

            if (!email.matches("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("Invalid email format! Please enter a valid email.");
                email = null;
            } else if (!isEmailUnique(email)) {
                System.out.println("This email is already in use. Please enter a different email.");
                email = null;
            }
        } while (email == null);

        String status = validateStringInput(sc, "^(active|inactive)$", "Invalid status! Please enter 'active' or 'inactive'.");

        String qry = "INSERT INTO tbl_customer(c_fname, c_lname, c_email, c_status) VALUES(?, ?, ?, ?)";
        Config config = new Config();
        try {
            config.addRecord(qry, fname, lname, email, status);
            System.out.println("Customer added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    public void viewCustomers() {
        String qry = "SELECT c_id, c_fname, c_lname, c_email, c_status FROM tbl_customer";
        String[] hrds = {"ID", "First Name", "Last Name", "Email", "Status"};
        String[] clms = {"c_id", "c_fname", "c_lname", "c_email", "c_status"};
        Config config = new Config();
        try {
            config.viewRecord(qry, hrds, clms);
        } catch (Exception e) {
            System.out.println("Error viewing customers: " + e.getMessage());
        }
    }

    public void updateCustomer() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();

        System.out.print("Enter Customer ID to Update: ");
        int id = validateIntInput(sc, config, "SELECT c_id FROM tbl_customer WHERE c_id = ?", "Invalid ID! Please enter a valid Customer ID:");

        System.out.print("New Customer First Name: ");
        String fname = validateStringInput(sc, "^[a-zA-Z]+$", "Invalid name! Please enter alphabets only.");

        System.out.print("New Customer Last Name: ");
        String lname = validateStringInput(sc, "^[a-zA-Z]+$", "Invalid name! Please enter alphabets only.");

        System.out.print("New Customer Email: ");
        String email = validateStringInput(sc, "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$", "Invalid email format! Please enter a valid email.");

        System.out.print("New Customer Status (active/inactive): ");
        String status = validateStringInput(sc, "^(active|inactive)$", "Invalid status! Please enter 'active' or 'inactive'.");

        String qry = "UPDATE tbl_customer SET c_fname = ?, c_lname = ?, c_email = ?, c_status = ? WHERE c_id = ?";
        try {
            config.updateRecord(qry, fname, lname, email, status, id);
            System.out.println("Customer updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    public void deleteCustomer() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();

        System.out.print("Enter Customer ID to Delete: ");
        int id = validateIntInput(sc, config, "SELECT c_id FROM tbl_customer WHERE c_id = ?", "Invalid ID! Please enter a valid Customer ID:");

        String qry = "DELETE FROM tbl_customer WHERE c_id = ?";
        try {
            config.deleteRecord(qry, id);
            System.out.println("Customer deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    private String validateStringInput(Scanner sc, String regex, String errorMessage) {
        String input;
        while (true) {
            input = sc.next().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    private int validateIntInput(Scanner sc, Config config, String query, String errorMessage) {
        int id;
        while (true) {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number:");
                sc.next(); 
            }
            id = sc.nextInt();
            if (config.getSingleValue(query, id) != 0) {
                return id;
            }
            System.out.println(errorMessage);
        }
    }

    private boolean isEmailUnique(String email) {
        Config config = new Config();
        String qry = "SELECT COUNT(*) FROM tbl_customer WHERE c_email = ?";
        try {
            return config.getRecordCount(qry, email) == 0;
        } catch (Exception e) {
            System.out.println("Error checking email uniqueness: " + e.getMessage());
            return false;
        }
    }

    void Ctransaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
