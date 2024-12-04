package room.rental;
import java.util.Scanner;

public class RoomRental {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        
        do {
            System.out.println("\nWELCOME TO ROOM RENTAL MANAGEMENT SYSTEM ");
            System.out.println("1. MANAGE CUSTOMERS ");
            System.out.println("2. MANAGE ROOMS ");
            System.out.println("3. MANAGE BOOKINGS");
            System.out.println("4. VIEW REPORTS ");
            System.out.println("5. EXIT  ");

            int choice = getUserChoice(input); 

            switch (choice) {
                case 1:
                    try {
                        Customer customer = new Customer();
                        customer.manageCustomer(); 
                    } catch (Exception e) {
                        System.out.println("An error occurred while managing customers: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        Room room = new Room();
                        room.manageRooms();  
                    } catch (Exception e) {
                        System.out.println("An error occurred while managing rooms: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        Booking booking = new Booking();
                        booking.bookingInfo();  
                    } catch (Exception e) {
                        System.out.println("An error occurred while managing bookings: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        Reports report = new Reports();
                        report.reportMenu(); 
                    } catch (Exception e) {
                        System.out.println("An error occurred while viewing reports: " + e.getMessage());
                    }
                    break;

                case 5:
                    if (confirmExit(input)) {
                        exit = true;
                        System.out.println("\nExiting the Room Rental Management System. Thank you for using!");
                    }
                    break;

                default:
                    System.out.println("Unexpected error occurred. Please try again.");
                    break;
            }

        } while (!exit);  

        input.close();  
    }

    private static int getUserChoice(Scanner input) {
        int choice = -1;
        while (choice < 1 || choice > 5) {
            System.out.print("Enter your choice (1-5): ");
            if (input.hasNextInt()) {
                choice = input.nextInt();
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice! Please select a number between 1 and 5.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                input.next();
            }
        }
        return choice;
    }

    private static boolean confirmExit(Scanner input) {
        String confirmation;
        System.out.print("Are you sure you want to exit? (yes/no): ");
        confirmation = input.next().trim();
        return confirmation.equalsIgnoreCase("yes");
    }
}
