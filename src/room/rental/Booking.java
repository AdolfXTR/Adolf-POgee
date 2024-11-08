package room.rental;

import java.util.Scanner;
import roomrental.Config;

public class Booking {

    public void manageBookings(){
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {
            System.out.println("\n-----------------------");
            System.out.println("1. Book Room");
            System.out.println("2. View Bookings");
            System.out.println("3. Exit");
            System.out.println("-----------------------");
            
            System.out.print("Enter Selection: ");
            int action = sc.nextInt();
            switch(action) {  
                case 1:
                    bookRoom();
                    break;
                case 2:
                    viewBookings();
                    break;
                case 3:
                    break;
            }
            System.out.println("Do you want to continue? (yes/no):");
            response = sc.next();
        } while(response.equalsIgnoreCase("yes"));
    }

    public void bookRoom() {
        Scanner sc = new Scanner(System.in);
        Config conf = new Config();
        
        Customer cm = new Customer();
        cm.viewCustomer();
        
        System.out.print("Enter Customer ID: ");
        int customerId = sc.nextInt();
        
        String cqry = "SELECT c_id FROM tbl_customer WHERE c_id = ?";
        while(conf.getSingleValue(cqry,customerId) == 0){
            System.out.println("Customer Id does not exist, Enter Again: ");
            customerId = sc.nextInt();
        }
                
               
        
        System.out.print("Enter Room ID: ");
        int roomId = sc.nextInt();
        
        
        System.out.print("Enter Booking Date (YYYY-MM-DD): ");
        String bookingDate = sc.next();
        
        // You can extend this by adding a check if the room is available for the selected date.
        String qry = "INSERT INTO tbl_booking(c_id, r_id, booking_date) VALUES(?, ?, ?)";
        
        conf.addRecord(qry, customerId, roomId, bookingDate);
    }

    public void viewBookings() {
        String qry = "SELECT b.b_id, c.c_fname, c.c_lname, r.r_no, r.r_type, b.booking_date " +
                     "FROM tbl_booking b " +
                     "JOIN tbl_customer c ON b.c_id = c.c_id " +
                     "JOIN tbl_room r ON b.r_id = r.r_id";
        String[] hrds = {"Booking ID", "Customer First Name", "Customer Last Name", "Room Number", "Room Type", "Booking Date"};
        String[] clms = {"b_id", "c_fname", "c_lname", "r_no", "r_type", "booking_date"};
        Config config = new Config();
        config.viewRecords(qry, hrds, clms);
    }
}

