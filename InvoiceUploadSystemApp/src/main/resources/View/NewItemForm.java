package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DbHelper;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class NewItemForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtItemName;
	private JTextField txtItemPrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewItemForm frame = new NewItemForm();
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
	public NewItemForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 743, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelNewItem = new JPanel();
		panelNewItem.setLayout(null);
		panelNewItem.setBackground(Color.LIGHT_GRAY);
		panelNewItem.setBounds(126, 39, 464, 336);
		contentPane.add(panelNewItem);

		JLabel lblItemName = new JLabel("Item Name : ");
		lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblItemName.setBounds(77, 66, 113, 13);
		panelNewItem.add(lblItemName);

		JLabel lblItemPrice = new JLabel("Item Price : ");
		lblItemPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblItemPrice.setBounds(77, 158, 113, 13);
		panelNewItem.add(lblItemPrice);

		txtItemName = new JTextField();
		txtItemName.setToolTipText("Item Name");
		txtItemName.setColumns(10);
		txtItemName.setBounds(217, 65, 103, 19);
		panelNewItem.add(txtItemName);

		JButton btnSaveItem = new JButton("Save");
		btnSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewItemToDatabase();
				loadItemTable();
			}

			private void addNewItemToDatabase() {
				PreparedStatement pst = null;
				int returnValue = 0;
				returnValue = JOptionPane.showConfirmDialog(null, "Are you sure To Save Invoice", "Are you sure?",
						JOptionPane.YES_NO_OPTION);
				if (returnValue == JOptionPane.YES_OPTION) {
					if (txtItemName.getText().isEmpty() || txtItemPrice.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnSaveItem, "Please Enter All Area", "WARNING",
								JOptionPane.WARNING_MESSAGE);
					} else {
						String itemName = txtItemName.getText();
						String itemPrice = txtItemPrice.getText();
						String query = "INSERT INTO item(name,unitPrice) VALUES(" + "'" + itemName + "'" + "," + "'"
								+ itemPrice + "'" + ")";
						try {
							pst = DbHelper.conn.prepareStatement(query);
							boolean state = pst.execute();
							if (state) {
								JOptionPane.showMessageDialog(btnSaveItem, "NOT INSERTED", "WARNING",
										JOptionPane.WARNING_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(btnSaveItem, "INSERTED NEW Item", "INFO",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							try {
								pst.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}

				else if (returnValue == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(btnSaveItem, "Not Saved Item", "Warning",
							JOptionPane.WARNING_MESSAGE);
				}

				clearInputs();
			}

			private void clearInputs() {
				txtItemName.setText("");
				txtItemPrice.setText("");
			}

			private void loadItemTable() {
				ItemForm.tableModel.setNumRows(0);
				Statement pst = null;
				ResultSet rs = null;
				String query = "SELECT * FROM item";
				try {
					pst = DbHelper.conn.createStatement();
					rs = pst.executeQuery(query);
					while (rs.next()) {
						Object data[] = { rs.getString(2), rs.getString(3) };
						ItemForm.tableModel.addRow(data);
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
		});
		btnSaveItem.setToolTipText("Save New Item");
		btnSaveItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveItem.setBackground(Color.GREEN);
		btnSaveItem.setBounds(178, 224, 113, 37);
		panelNewItem.add(btnSaveItem);

		txtItemPrice = new JTextField();
		txtItemPrice.setToolTipText("Item Price");
		txtItemPrice.setColumns(10);
		txtItemPrice.setBounds(217, 157, 96, 19);
		panelNewItem.add(txtItemPrice);
	}
}
