package Helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonHelper {

	public static void jsonConn(JTable table, String invSerNo, String invNo) {

		File jsonFile = new File("sampleoutput.json");
		FileWriter fileWriter = null;
		JSONObject root = new JSONObject();
		JSONObject uploadSystem = new JSONObject();
		try {
			System.out.println("File created: " + jsonFile.getName());
			LinkedHashMap customerData = new LinkedHashMap();
			customerData.put("name", table.getValueAt(0, 0).toString());
			customerData.put("ssnNumber", table.getValueAt(0, 1).toString());
			JSONObject customerDataf = new JSONObject();
			customerDataf.putAll(customerData);
			customerData.put("name", table.getValueAt(0, 0).toString());
			customerData.put("ssnNumber", table.getValueAt(0, 1).toString());

			JSONArray items = new JSONArray();
			int count = table.getRowCount();
			for (int i = 0; i < count; i++) {
				JSONObject item = new JSONObject();
				item.put("name", table.getValueAt(i, 2).toString());
				item.put("quantity", table.getValueAt(i, 3).toString());
				item.put("unitPrice", table.getValueAt(i, 4).toString());
				item.put("amount", table.getValueAt(i, 5).toString());
				items.add(item);
			}

			JSONObject invoiceData = new JSONObject();
			invoiceData.put("seri", invSerNo);
			invoiceData.put("number", invNo);
			invoiceData.put("item", items);
			invoiceData.put("totalAmount", table.getValueAt(0, 6).toString());
			invoiceData.put("discount", table.getValueAt(0, 7).toString());
			invoiceData.put("amountToPay", table.getValueAt(0, 8).toString());
			JSONObject invoice = new JSONObject();
			invoice.put("invoice", invoiceData);

			uploadSystem.put("invoiceData", invoiceData);
			uploadSystem.put("customer", customerData);

			root.put("uploadSystem", uploadSystem);
			fileWriter = new FileWriter(jsonFile);
			fileWriter.write(root.toJSONString());

			JOptionPane.showMessageDialog(null, "inserted to new Json file");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
