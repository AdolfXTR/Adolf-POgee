package room.rental;

import java.util.Scanner;
import roomrental.Config;

public class Customer {

    public void Ctransaction(){
        Scanner sc = new Scanner(System.in);
        String response;
        do {
            System.out.println("\n-----------------------");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Exit");
            System.out.println("-----------------------");
            
            System.out.print("Enter Selection: ");
            int action = sc.nextInt();
            switch(action) {  
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewCustomer();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    break;
            }
            System.out.println("Do you want to continue? (yes/no):");
            response = sc.next();
        } while(response.equalsIgnoreCase("yes"));      
    }

    public void addCustomer() {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Customer First Name: ");
        String fname = sc.next();
        System.out.print("Customer Last Name: ");
        String lname = sc.next();
        System.out.print("Customer Email: ");
        String email = sc.next();
        System.out.print("Customer Status: ");
        String status = sc.next();
        
        String qry = "INSERT INTO tbl_customer(c_fname, c_lname, c_email, c_status) VALUES(?,?,?,?)";
        Config config = new Config(); 
        config.addRecord(qry, fname, lname, email, status);     
    }

    public void viewCustomer() {
        String qry = "SELECT * FROM tbl_customer";
        String[] hrds = {"ID", "Firstname", "Lastname", "Email", "Status"};
        String[] clms = {"c_id", "c_fname", "c_lname", "c_email", "c_status"};
        Config config = new Config();
        config.viewRecords(qry, hrds, clms);
    }

    public void updateCustomer() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();
        
        System.out.println("Enter ID to Update: ");
        int id = sc.nextInt();
        
        while(config.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Customer ID Again: ");
            id = sc.nextInt();
        }
        
        System.out.print("New Customer First Name: ");
        String fname = sc.next();
        System.out.print("New Customer Last Name: ");
        String lname = sc.next();
        System.out.print("New Customer Email: ");
        String email = sc.next();
        System.out.print("New Customer Status: ");
        String status = sc.next();
        
        String qry = "UPDATE tbl_customer SET c_fname = ?, c_lname = ?, c_email = ?, c_status = ? WHERE c_id = ?";
        config.updateRecord(qry, fname, lname, email, status, id);
    }

    public void deleteCustomer() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();
        
        System.out.println("Enter ID to Delete: ");
        int id = sc.nextInt();
        
        while(config.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Customer ID Again: ");
            id = sc.nextInt();
        }
        
        String qry = "DELETE FROM tbl_customer WHERE c_id = ?";
        config.deleteRecord(qry, id);
    }
}
