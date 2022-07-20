package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DbHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class NewCustomerForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtCusName;
	private JTextField txtCusSocId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewCustomerForm frame = new NewCustomerForm();
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
	public NewCustomerForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 642, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(99, 63, 438, 261);
		contentPane.add(panel);
		panel.setLayout(null);

		txtCusName = new JTextField();
		txtCusName.setBounds(164, 37, 96, 19);
		panel.add(txtCusName);
		txtCusName.setColumns(10);

		JLabel lblCusName = new JLabel("Name : ");
		lblCusName.setBounds(56, 38, 85, 13);
		panel.add(lblCusName);
		lblCusName.setFont(new Font("Tahoma", Font.PLAIN, 15));

		txtCusSocId = new JTextField();
		txtCusSocId.setBounds(164, 100, 96, 19);
		panel.add(txtCusSocId);
		txtCusSocId.setColumns(10);

		JLabel lblCusSocId = new JLabel("Social Id: ");
		lblCusSocId.setBounds(56, 101, 85, 13);
		panel.add(lblCusSocId);
		lblCusSocId.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JButton btnSaveNewCus = new JButton("Save");
		btnSaveNewCus.setBounds(164, 179, 96, 27);
		panel.add(btnSaveNewCus);
		btnSaveNewCus.addActionListener(new ActionListener() {
			PreparedStatement st = null;

			public void actionPerformed(ActionEvent e) {
				addNewCustomerToDatabase();
				loadCustomerTable();
			}

			private void loadCustomerTable() {
				CustomerForm.dataModel.setNumRows(0);
				Statement pst = null;
				ResultSet rs = null;
				String query = "SELECT * FROM customer";
				try {
					pst = DbHelper.conn.createStatement();
					rs = pst.executeQuery(query);
					while (rs.next()) {
						Object data[] = { rs.getString(2), rs.getString(3) };
						CustomerForm.dataModel.addRow(data);
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

			private void addNewCustomerToDatabase() {
				int returnValue = 0;
				returnValue = JOptionPane.showConfirmDialog(btnSaveNewCus, "Are you sure To Save Invoice",
						"Are you sure?", JOptionPane.YES_NO_OPTION);
				if (returnValue == JOptionPane.YES_OPTION) {
					if (txtCusName.getText().isEmpty() || txtCusSocId.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnSaveNewCus, "WARNING", "Please Enter All Area",
								JOptionPane.WARNING_MESSAGE);
					} else {
						String name = txtCusName.getText();
						String ssnNumber = txtCusSocId.getText();
						String query = "INSERT INTO customer(name,ssnNumber) VALUES" + "(" + "'" + name + "'" + ","
								+ "'" + ssnNumber + "'" + ")";
						try {
							st = DbHelper.conn.prepareStatement(query);
							boolean state = st.execute();// true if the first result is a ResultSetobject; false if the
															// first result is an updatecount or there is no result
							if (state) {
								JOptionPane.showMessageDialog(btnSaveNewCus, "NOT INSERTED", "WARNING",
										JOptionPane.WARNING_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(btnSaveNewCus, "INSERTED NEW CUSTOMER", "INFO",
										JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							try {
								st.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}

				else if (returnValue == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(btnSaveNewCus, "Not Saved Customer", "WARNING",
							JOptionPane.WARNING_MESSAGE);
				}

				clearInputs();

			}

			private void clearInputs() {
				txtCusName.setText("");
				txtCusSocId.setText("");
			}
		});
		btnSaveNewCus.setBackground(Color.GREEN);
		btnSaveNewCus.setFont(new Font("Tahoma", Font.PLAIN, 15));
	}
}
