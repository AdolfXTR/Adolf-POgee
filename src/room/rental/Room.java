package room.rental;

import java.util.Scanner;

public class Room {

    public void manageRooms() {
        Scanner sc = new Scanner(System.in);
        String response;
        
        do {
            System.out.println("\n-----------------------");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
            System.out.println("5. Exit");
            System.out.println("-----------------------");
            
            System.out.print("Enter Selection: ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number:");
                sc.next(); // Clear invalid input
            }
            int action = sc.nextInt();
            
            switch (action) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    viewRooms();
                    updateRoom();
                    viewRooms();
                    break;
                case 4:
                    viewRooms();
                    deleteRoom();
                    viewRooms();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid selection! Please choose a valid option.");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next().trim();
        } while (response.equalsIgnoreCase("yes"));

        sc.close(); // Close the Scanner
    }

    public void addRoom() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Room Number: ");
        String roomNo = validateStringInput(sc, "^\\w+$", "Invalid room number! Please enter a valid alphanumeric room number:");

        System.out.print("Room Type: ");
        String type = validateStringInput(sc, "^[a-zA-Z]+$", "Invalid room type! Please enter alphabets only:");
        
        System.out.print("Room Price: ");
        double price = validateDoubleInput(sc, "Invalid price! Please enter a valid number:");

        String qry = "INSERT INTO tbl_room(r_no, r_type, r_price) VALUES(?, ?, ?)";
        Config config = new Config();
        try {
            config.addRecord(qry, roomNo, type, price);
            System.out.println("Room added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }

    public void viewRooms() {
        String qry = "SELECT * FROM tbl_room";
        String[] hrds = {"ID", "Room Number", "Type", "Price"};
        String[] clms = {"r_id", "r_no", "r_type", "r_price"};
        Config config = new Config();
        try {
            config.viewRecord(qry, hrds, clms);
        } catch (Exception e) {
            System.out.println("Error viewing rooms: " + e.getMessage());
        }
    }

    public void updateRoom() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();

        System.out.print("Enter Room ID to Update: ");
        int id = validateIntInput(sc, config, "SELECT r_id FROM tbl_room WHERE r_id = ?", "Invalid Room ID! Please enter a valid Room ID:");

        System.out.print("New Room Number: ");
        String roomNo = validateStringInput(sc, "^\\w+$", "Invalid room number! Please enter a valid alphanumeric room number:");

        System.out.print("New Room Type: ");
        String type = validateStringInput(sc, "^[a-zA-Z]+$", "Invalid room type! Please enter alphabets only:");
        
        System.out.print("New Room Price: ");
        double price = validateDoubleInput(sc, "Invalid price! Please enter a valid number:");

        String qry = "UPDATE tbl_room SET r_no = ?, r_type = ?, r_price = ? WHERE r_id = ?";
        try {
            config.updateRecord(qry, roomNo, type, price, id);
            System.out.println("Room updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }

    public void deleteRoom() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();

        System.out.print("Enter Room ID to Delete: ");
        int id = validateIntInput(sc, config, "SELECT r_id FROM tbl_room WHERE r_id = ?", "Invalid Room ID! Please enter a valid Room ID:");

        String qry = "DELETE FROM tbl_room WHERE r_id = ?";
        try {
            config.deleteRecord(qry, id);
            System.out.println("Room deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting room: " + e.getMessage());
        }
    }

    private String validateStringInput(Scanner sc, String regex, String errorMessage) {
        String input;
        while (true) {
            input = sc.next();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    private double validateDoubleInput(Scanner sc, String errorMessage) {
        while (!sc.hasNextDouble()) {
            System.out.println(errorMessage);
            sc.next(); // Clear invalid input
        }
        return sc.nextDouble();
    }

    private int validateIntInput(Scanner sc, Config config, String query, String errorMessage) {
        int id;
        while (true) {
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number:");
                sc.next(); // Clear invalid input
            }
            id = sc.nextInt();
            if (config.getSingleValue(query, id) != 0) {
                return id;
            }
            System.out.println(errorMessage);
        }
    }
}
