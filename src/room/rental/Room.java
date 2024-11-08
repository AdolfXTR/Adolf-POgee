package room.rental;

import java.util.Scanner;
import roomrental.Config;

public class Room {

    public void manageRooms(){
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
            int action = sc.nextInt();
            switch(action) {  
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 5:
                    break;
            }
            System.out.println("Do you want to continue? (yes/no):");
            response = sc.next();
        } while(response.equalsIgnoreCase("yes"));
    }

    public void addRoom() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Room Number: ");
        String roomNo = sc.next();
        System.out.print("Room Type: ");
        String type = sc.next();
        System.out.print("Room Price: ");
        double price = sc.nextDouble();

        String qry = "INSERT INTO tbl_room(r_no, r_type, r_price) VALUES(?, ?, ?)";
        Config config = new Config();
        config.addRecord(qry, roomNo, type, price);
    }

    public void viewRooms() {
        String qry = "SELECT * FROM tbl_room";
        String[] hrds = {"ID", "Room Number", "Type", "Price"};
        String[] clms = {"r_id", "r_no", "r_type", "r_price"};
        Config config = new Config();
        config.viewRecords(qry, hrds, clms);
    }

    public void updateRoom() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();

        System.out.println("Enter Room ID to Update: ");
        int id = sc.nextInt();

        while(config.getSingleValue("SELECT r_id FROM tbl_room WHERE r_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Room ID Again: ");
            id = sc.nextInt();
        }

        System.out.print("New Room Number: ");
        String roomNo = sc.next();
        System.out.print("New Room Type: ");
        String type = sc.next();
        System.out.print("New Room Price: ");
        double price = sc.nextDouble();

        String qry = "UPDATE tbl_room SET r_no = ?, r_type = ?, r_price = ? WHERE r_id = ?";
        config.updateRecord(qry, roomNo, type, price, id);
    }

    public void deleteRoom() {
        Scanner sc = new Scanner(System.in);
        Config config = new Config();

        System.out.println("Enter Room ID to Delete: ");
        int id = sc.nextInt();

        while(config.getSingleValue("SELECT r_id FROM tbl_room WHERE r_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.println("Select Room ID Again: ");
            id = sc.nextInt();
        }

        String qry = "DELETE FROM tbl_room WHERE r_id = ?";
        config.deleteRecord(qry, id);
    }
}
