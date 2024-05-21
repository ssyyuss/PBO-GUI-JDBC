import java.sql.*;
import java.util.Scanner;

public class ViewMahasiswa {

    public static void main(String[] args) {
        // Replace with your database credentials
        String jdbcUrl = "jdbc:mysql://localhost:3306/dbmahasiswa";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            boolean exit = false;
            Scanner scanner = new Scanner(System.in);

            while (!exit) {
                System.out.println("Menu:");
                System.out.println("1. Lihat Data");
                System.out.println("2. Tambah Data");
                System.out.println("3. Ubah Data");
                System.out.println("4. Hapus Data");
                System.out.println("5. Keluar");
                System.out.print("Pilih menu (1-5): ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        displayMahasiswa(statement);
                        break;
                    case 2:
                        tambahMahasiswa(statement);
                        break;
                    case 3:
                        ubahMahasiswa(statement);
                        break;
                    case 4:
                        hapusMahasiswa(statement);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan pilih menu yang tersedia.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayMahasiswa(Statement statement) throws SQLException {
        String query = "SELECT * FROM mahasiswa";
        try (ResultSet resultSet = statement.executeQuery(query)) {
            // Process the result set and print the data
            while (resultSet.next()) {
                int npm = resultSet.getInt("npm");
                String nama = resultSet.getString("nama");
                String alamat = resultSet.getString("alamat");

                System.out.println("NPM: " + npm);
                System.out.println("Nama: " + nama);
                System.out.println("Alamat: " + alamat);
                System.out.println("------------------------");
            }
        }
    }

    private static void tambahMahasiswa(Statement statement) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama Mahasiswa: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan NPM: ");
        String npm = scanner.nextLine();
        System.out.print("Masukkan alamat Mahasiswa: ");
        String alamat = scanner.nextLine();

        String insertQuery = String.format("INSERT INTO mahasiswa (npm, nama, alamat) VALUES ('%s', '%s', '%s')", npm, nama, alamat);

        int rowsAffected = statement.executeUpdate(insertQuery);

        if (rowsAffected > 0) {
            System.out.println("Data mahasiswa berhasil ditambahkan!");
        } else {
            System.out.println("Gagal menambahkan data mahasiswa.");
        }
    }

    private static void ubahMahasiswa(Statement statement) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan NPM Mahasiswa yang akan diubah: ");
        String npmInput = scanner.nextLine();
        int npm = Integer.parseInt(npmInput);

        String checkQuery = String.format("SELECT * FROM mahasiswa WHERE npm = %d", npm);
        ResultSet rs = statement.executeQuery(checkQuery);
        if (!rs.next()) {
            System.out.println("NPM mahasiswa tidak ditemukan.");
            return;
        }

        System.out.print("Masukkan nama Mahasiswa baru: ");
        String namaBaru = scanner.nextLine();
        System.out.print("Masukkan alamat Mahasiswa baru: ");
        String alamatBaru = scanner.nextLine();

        String updateQuery = String.format("UPDATE mahasiswa SET nama = '%s', alamat = '%s' WHERE npm = %d",
                namaBaru, alamatBaru, npm);

        int rowsAffected = statement.executeUpdate(updateQuery);

        if (rowsAffected > 0) {
            System.out.println("Data mahasiswa berhasil diperbarui!");
        } else {
            System.out.println("Gagal memperbarui data mahasiswa.");
        }
    }

    private static void hapusMahasiswa(Statement statement) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan NPM Mahasiswa yang akan dihapus: ");
        String npmInput = scanner.nextLine();
        int npmToDelete = Integer.parseInt(npmInput);

        String checkQuery = String.format("SELECT * FROM mahasiswa WHERE npm = %d", npmToDelete);
        ResultSet rs = statement.executeQuery(checkQuery);
        if (!rs.next()) {
            System.out.println("NPM Mahasiswa tidak ditemukan.");
            return;
        }

        String deleteQuery = String.format("DELETE FROM mahasiswa WHERE npm = %d", npmToDelete);

        int rowsAffected = statement.executeUpdate(deleteQuery);

        if (rowsAffected > 0) {
            System.out.println("Data mahasiswa berhasil dihapus!");
        } else {
            System.out.println("Gagal menghapus data mahasiswa.");
        }
    }
}