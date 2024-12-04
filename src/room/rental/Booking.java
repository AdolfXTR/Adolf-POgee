package room.rental;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Booking {
    Scanner sc = new Scanner(System.in);
    Config conf = new Config(); 
    Customer customer = new Customer();

    public void bookingInfo() {
        String response;
        do {
            System.out.println("|                                            |");
            System.out.println("|         ROOM BOOKING MANAGEMENT SYSTEM     |");
            System.out.println("|                                            |");
            System.out.println("|  1.            BOOK A ROOM                 |");
            System.out.println("|  2.            VIEW BOOKINGS               |");
            System.out.println("|  3.             EXIT                       |");

            int action = getUserActionChoice();

            switch (action) {
                case 1:
                    bookRoom();
                    break;
                case 2:
                    viewBookings();
                    break;
                case 3:
                    return;
            }

            response = getYesOrNoInput("Do you want to perform another action? (yes/no): ");
        } while (response.equals("yes"));

        System.out.println("THANK YOU FOR USING THE ROOM BOOKING MANAGEMENT SYSTEM!!");
    }

    private int getUserActionChoice() {
        int action = -1;
        while (action < 1 || action > 3) {
            try {
                System.out.print("CHOOSE A NUMBER (1-3): ");
                action = sc.nextInt();
                sc.nextLine();
                if (action < 1 || action > 3) {
                    System.out.print("Invalid option. Please enter a number between 1 and 3: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number: ");
                sc.nextLine();
            }
        }
        return action;
    }

    public void bookRoom() {
        try {
            int customerId = getCustomerId(); 
            int roomId = getRoomId();
            String bookingDate = getDateInput("Enter Booking Date (YYYY-MM-DD): ");
            String qry = "INSERT INTO tbl_booking(c_id, r_id, booking_date) VALUES(?, ?, ?)";
            conf.addRecord(qry, customerId, roomId, bookingDate);
            System.out.println("Room booked successfully!");
        } catch (Exception e) {
            System.err.println("Error booking room: " + e.getMessage());
        }
    }

    private int getCustomerId() {
        int customerId = -1;
        while (customerId < 1) {
            try {
                System.out.print("Enter Customer ID: ");
                customerId = sc.nextInt();
                sc.nextLine();
                if (conf.getSingleValue("SELECT c_id FROM tbl_customer WHERE c_id = ?", customerId) == 0) {
                    System.out.println("Customer ID doesn't exist. Enter again!");
                    customerId = -1;
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid integer for Customer ID: ");
                sc.nextLine();
            }
        }
        return customerId;
    }

    private int getRoomId() {
        int roomId = -1;
        while (roomId < 1) {
            try {
                System.out.print("Enter Room ID: ");
                roomId = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer for Room ID.");
                sc.nextLine();
            }
        }
        return roomId;
    }

    private String getDateInput(String prompt) {
        String dateInput;
        while (true) {
            System.out.print(prompt);
            dateInput = sc.nextLine();
            try {
                if (dateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    break;
                } else {
                    System.out.println("Invalid date format! Please use the format YYYY-MM-DD.");
                }
            } catch (Exception e) {
                System.out.println("Error parsing date.");
            }
        }
        return dateInput;
    }

    private String getYesOrNoInput(String prompt) {
        String response;
        while (true) {
            System.out.print(prompt);
            response = sc.nextLine().trim().toLowerCase();
            if (response.equals("yes") || response.equals("no")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no' only.");
            }
        }
        return response;
    }

    public void viewBookings() {
        try {
            String qry = "SELECT b.c_id, b.r_id, b.booking_date FROM tbl_booking b";
            String[] headers = {"Customer ID", "Room ID", "Booking Date"};
            String[] columns = {"c_id", "r_id", "booking_date"};
            conf.viewRecords(qry, headers, columns);
        } catch (Exception e) {
            System.err.println("Error viewing booking records: " + e.getMessage());
        }
    }

}
