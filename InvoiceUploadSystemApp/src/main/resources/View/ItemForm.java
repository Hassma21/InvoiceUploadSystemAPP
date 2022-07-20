package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import Helper.DbHelper;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;

public class ItemForm extends JFrame {

	private JPanel contentPane;
	private JTable tableItems;
	static DefaultTableModel tableModel;
	private JTextField txtAmount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItemForm frame = new ItemForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ItemForm() {
		setTitle("Hugin Invoice System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1032, 625);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelAllItems = new JPanel();
		panelAllItems.setBackground(Color.LIGHT_GRAY);
		panelAllItems.setBounds(10, 40, 519, 404);
		contentPane.add(panelAllItems);
		panelAllItems.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 499, 199);
		panelAllItems.add(scrollPane);

		tableItems = new JTable();
		scrollPane.setViewportView(tableItems);
		tableModel = new DefaultTableModel();
		Object columns[] = { "Name", "Price" };
		tableModel.setColumnIdentifiers(columns);
		tableItems.setModel(tableModel);
		loadItemsToTable();

		JLabel lblItems = new JLabel("All Items");
		lblItems.setFont(new Font("Tahoma", Font.ITALIC, 18));
		lblItems.setHorizontalAlignment(SwingConstants.CENTER);
		lblItems.setBounds(154, 10, 186, 30);
		panelAllItems.add(lblItems);

		JLabel lblItemAmount = new JLabel("Amount : ");
		lblItemAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblItemAmount.setBounds(20, 286, 97, 21);
		panelAllItems.add(lblItemAmount);

		JButton btnTakeItem = new JButton("Take");
		btnTakeItem.setToolTipText("Take");
		btnTakeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableItems.getSelectedRow();
				String itemName = tableItems.getValueAt(selectedRow, 0).toString();
				int itemPrice = Integer.parseInt(tableItems.getValueAt(selectedRow, 1).toString());
				int amount = Integer.parseInt(txtAmount.getText());// format tipte olduðu için girilmeyen karakterler
																	// boþluk olrak alýnýr bu yüzden
																	// NumberFormatException hatasý alýrýz
				// boþluklarý silmek için replace methodu kullanýlýr
				int totalPrice = itemPrice * amount;
				Object item[] = { itemName, itemPrice, amount, totalPrice };
				CreateInvoiceForm.tableModel.addRow(item);
			}
		});
		btnTakeItem.setBackground(Color.GREEN);
		btnTakeItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnTakeItem.setBounds(224, 357, 116, 37);
		panelAllItems.add(btnTakeItem);

		txtAmount = new JTextField();
		txtAmount.setToolTipText("Amount Of Item");
		txtAmount.setBounds(102, 289, 96, 19);
		panelAllItems.add(txtAmount);
		txtAmount.setColumns(10);

		JButton btnSaveNewItem = new JButton("New Item");
		btnSaveNewItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewItemForm newItemForm = new NewItemForm();
				newItemForm.setVisible(true);
				newItemForm.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnSaveNewItem.setBackground(Color.GREEN);
		btnSaveNewItem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSaveNewItem.setBounds(595, 170, 314, 77);
		contentPane.add(btnSaveNewItem);

	}

	private void loadItemsToTable() {
		Statement pst = null;
		ResultSet rs = null;
		String query = "SELECT * FROM item";
		try {
			pst = DbHelper.conn.createStatement();
			rs = pst.executeQuery(query);
			while (rs.next()) {
				Object data[] = { rs.getString(2), rs.getString(3) };
				tableModel.addRow(data);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pst.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
