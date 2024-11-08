package room.rental;

import java.util.Scanner;

public class Roomrental {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;
        
        do {
            System.out.println("\n-----------------------");
            System.out.println("Welcome to the Room Rental App");
            System.out.println("1. Manage Customers");
            System.out.println("2. Manage Rooms");
            System.out.println("3. Manage Bookings");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.println("-----------------------");
            
            System.out.print("Enter Action: ");
            int action = sc.nextInt();
            
            switch(action){
                case 1:
                    Customer customer = new Customer();
                    customer.Ctransaction();
                    break;
                case 2:
                    Room room = new Room();
                    room.manageRooms();
                    break;
                case 3:
                    Booking booking = new Booking();
                    booking.manageBookings();
                    break;
                case 5:
                    System.out.print("Exit selected... type 'yes' to continue: ");
                    String resp = sc.next();
                    if(resp.equalsIgnoreCase("yes")){
                        exit = false;
                    }
                    break;
            }
        } while(exit);   
    }
}
