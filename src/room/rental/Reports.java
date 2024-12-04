package room.rental;

import java.util.Scanner;
import java.sql.*;

public class Reports {
    Scanner input = new Scanner(System.in);
    Config conf = new Config();  
    public void reportMenu() {
        boolean exit = true;
        do {
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.printf("|%-25s%-50s%-25s|\n", "", "Report Menu", "");
            System.out.printf("|%-5s%-95s|\n", "", "1. General Report");
            System.out.printf("|%-5s%-95s|\n", "", "2. Individual Report");
            System.out.printf("|%-5s%-95s|\n", "", "3. Exit");
            
            System.out.printf("%-5sEnter Choice: ", "");
            int choice;

            while (true) {
                try {
                    choice = input.nextInt();
                    if (choice > 0 && choice < 4) {
                        break;
                    } else {
                        System.out.printf("|%-5sEnter Choice Again: ", "");
                    }
                } catch (Exception e) {
                    input.next();
                    System.out.printf("|%-5sEnter Choice Again: ", "");
                }
            }

            switch (choice) {
                case 1:
                    generalReport();
                    break;
                case 2:
                    individualReport();
                    break;
                default:
                    exit = false;
                    break;
            }
        } while (exit);
    }

    private void generalReport() {
        String qry = "SELECT c.c_id, c.c_fname, c.c_lname, c.c_email, c.c_status, r.r_no, r.r_type, r.r_price " +
                     "FROM tbl_customer c " +
                     "JOIN tbl_room r ON r_id = r_id";
        String[] headers = {"Customer ID", "First Name", "Last Name", "Email", "Status", "Room Number", "Room Type", "Price"};
        String[] columns = {"c_id", "c_fname", "c_lname", "c_email", "c_status", "r_no", "r_type", "r_price"};

        try {
            viewRecords(qry, headers, columns);
        } catch (Exception e) {
            System.out.println("Error generating general report: " + e.getMessage());
        }
    }

    private void individualReport() {
        boolean exit = true;
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        System.out.printf("|%-25s%-50s%-25s|\n", "", "Individual Report", "");
        System.out.printf("|%-25s%-50s%-25s|\n", "", "Enter 0 in ID to Exit", "");
        System.out.print("|\tEnter Customer ID to View: ");

        int id;
        while (true) {
            try {
                id = input.nextInt();
                if (doesCustomerExist(id)) {
                    break;
                } else if (id == 0) {
                    exit = false;
                    break;
                } else {
                    System.out.print("|\tEnter a valid Customer ID to View: ");
                }
            } catch (Exception e) {
                input.next();
                System.out.print("|\tEnter a valid Customer ID to View: ");
            }
        }

        if (exit) {
            try {
                String customerSQL = "SELECT c_fname, c_lname, c_email, c_status FROM tbl_customer WHERE c_id = ?";
                PreparedStatement stmt = conf.connectDB().prepareStatement(customerSQL);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("+----------------------------------------------------------------------------------------------------+");
                    System.out.printf("|%-25s%-50s%-25s|\n", "", "Customer Information", "");
                    System.out.printf("|%-15s: %-60s|\n", "First Name", rs.getString("c_fname"));
                    System.out.printf("|%-15s: %-60s|\n", "Last Name", rs.getString("c_lname"));
                    System.out.printf("|%-15s: %-60s|\n", "Email", rs.getString("c_email"));
                    System.out.printf("|%-15s: %-60s|\n", "Status", rs.getString("c_status"));
                    System.out.println("+----------------------------------------------------------------------------------------------------+");

                    String roomSQL = "SELECT r_no, r_type, r_price FROM tbl_room WHERE r_id = (SELECT r_id FROM tbl_customer WHERE c_id = ?)";
                    PreparedStatement roomStmt = conf.connectDB().prepareStatement(roomSQL);
                    roomStmt.setInt(1, id);
                    ResultSet roomRs = roomStmt.executeQuery();

                    if (roomRs.next()) {
                        System.out.println("+----------------------------------------------------------------------------------------------------+");
                        System.out.printf("|%-25s%-50s%-25s|\n", "", "Room Information", "");
                        System.out.printf("|%-15s: %-60s|\n", "Room Number", roomRs.getString("r_no"));
                        System.out.printf("|%-15s: %-60s|\n", "Room Type", roomRs.getString("r_type"));
                        System.out.printf("|%-15s: %-60s|\n", "Price", roomRs.getString("r_price"));
                        System.out.println("+----------------------------------------------------------------------------------------------------+");
                    } else {
                        System.out.println("| No room assigned to this customer |");
                    }
                } else {
                    System.out.println("| No record found for Customer ID: " + id + " |");
                }

                rs.close();
            } catch (Exception e) {
                System.out.println("|\tError retrieving data: " + e.getMessage() + " |");
            }
        }
    }
    private boolean doesCustomerExist(int id) {
        String query = "SELECT COUNT(*) FROM tbl_customer WHERE c_id = ?";
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("|\tError checking customer ID: " + e.getMessage());
        }
        return false;
    }

    private void viewRecords(String qry, String[] headers, String[] columns) throws SQLException {
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(qry);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-12s | %-15s | %-15s | %-30s | %-10s | %-12s | %-15s | %-10s |\n",
                              headers[0], headers[1], headers[2], headers[3], headers[4], headers[5], headers[6], headers[7]);
            System.out.println("+----------------------------------------------------------------------------------------------------+");

            while (rs.next()) {
                System.out.printf("| %-12d | %-15s | %-15s | %-30s | %-10s | %-12d | %-15s | %-10s |\n",
                        rs.getInt(columns[0]),
                        rs.getString(columns[1]),
                        rs.getString(columns[2]),
                        rs.getString(columns[3]),
                        rs.getString(columns[4]),
                        rs.getInt(columns[5]),
                        rs.getString(columns[6]),
                        rs.getString(columns[7]));
            }

            System.out.println("+----------------------------------------------------------------------------------------------------+");
        }
    }
}
