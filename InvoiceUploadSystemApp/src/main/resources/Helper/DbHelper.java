package Helper;

import java.io.File;
import java.sql.*;

public class DbHelper {
	protected DbHelper() {
	}

	public static Connection conn = null;
	public static String sqLiteServer = "jdbc:sqlite:";
	public static String resetPath = "";

	public static Connection dbConnect() {

		try {
			String getFilePath = new File("").getAbsolutePath();
			String fileAbsolutePath = getFilePath.concat("\\upload_system.db");
			if (isDatabaseExist(fileAbsolutePath)) {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(sqLiteServer + fileAbsolutePath);
				System.out.println(conn + " connected database");
			} else {
				createNewDatabase("upload_system");
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection(sqLiteServer + fileAbsolutePath);
				System.out.println(conn + " there was not database but it is created and connected");
			}

		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
		}
		return conn;
	}

	public static boolean isDatabaseExist(String dbFilePath) {
		File dbFile = new File(dbFilePath);
		return dbFile.exists();
	}

	public static void createNewDatabase(String fileName) {
		String getFilePath = new File("").getAbsolutePath();
		String fileAbsolutePath = "";
		fileAbsolutePath = getFilePath.concat("\\" + fileName + ".db");
		Statement stm = null;

		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(sqLiteServer + fileAbsolutePath);

			if (conn != null) {

				String query = "CREATE TABLE `customer` ( `id`	INTEGER NOT NULL,\r\n" + "`name` TEXT NOT NULL,\r\n"
						+ "`ssnNumber`	TEXT NOT NULL,\r\n" + "PRIMARY KEY(`id`));" + "\n"
						+ "CREATE TABLE invoice (\r\n" + "`id` INTEGER NOT NULL,\r\n" + "`seri` TEXT NOT NULL,\r\n"
						+ "`number` NUMERIC NOT NULL,\r\n" + "`totalAmount` TEXT NOT NULL,\r\n"
						+ "`discount` TEXT NOT NULL,\r\n" + "`amountToPay` TEXT NOT NULL,\r\n"
						+ "`customerId` INTEGER NOT NULL,\r\n"
						+ "FOREIGN KEY(`customerId`) REFERENCES customer(id),\r\n" + "PRIMARY KEY(`id`));" + "\n"
						+ "CREATE TABLE `item` (\r\n" + "`id` INTEGER NOT NULL,\r\n" + "`name` TEXT NOT NULL,\r\n"
						+ "`unitPrice` TEXT NOT NULL,\r\n" + "	PRIMARY KEY(`id`));" + "\n"
						+ "CREATE TABLE `invoiceItems` (\r\n" + "`invoiceId`	INTEGER NOT NULL,\r\n"
						+ "`itemId` INTEGER NOT NULL,\r\n" + "`quantity` INTEGER NOT NULL,\r\n"
						+ "`amount` INTEGER NOT NULL,\r\n" + "FOREIGN KEY(itemId) REFERENCES item(id),\r\n"
						+ "FOREIGN KEY(invoiceId) REFERENCES invoice(id));";

				stm = conn.createStatement();
				stm.executeUpdate(query);
				System.out.println("tables created");
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
