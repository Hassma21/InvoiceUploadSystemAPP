package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.DbHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;

public class CreateInvoiceForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtInvSerNo;
	private JTextField txtInvoiceNo;
	static JTable tableItems;
	private JTextField txtDiscountAmount;
	private JLabel lblSumOfInvoice;
	private JLabel lblAmountToBePaid;
	static JLabel lblFoundedCusName;
	static JLabel lblFoundedCusSsý;
	static DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateInvoiceForm frame = new CreateInvoiceForm();
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
	public CreateInvoiceForm() {
		setTitle("Hugin Invoice System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 925, 978);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInvSerialNo = new JLabel("Invoice Serial No : ");
		lblInvSerialNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvSerialNo.setBounds(284, 10, 128, 13);
		contentPane.add(lblInvSerialNo);

		JLabel lblInvoiceNo = new JLabel("Invoice No : ");
		lblInvoiceNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvoiceNo.setBounds(290, 48, 122, 13);
		contentPane.add(lblInvoiceNo);

		txtInvSerNo = new JTextField();
		txtInvSerNo.setToolTipText("Invoice Serial No");
		txtInvSerNo.setBounds(452, 9, 96, 19);
		contentPane.add(txtInvSerNo);
		txtInvSerNo.setColumns(10);

		txtInvoiceNo = new JTextField();
		txtInvoiceNo.setToolTipText("Invoice No");
		txtInvoiceNo.setBounds(452, 47, 96, 19);
		contentPane.add(txtInvoiceNo);
		txtInvoiceNo.setColumns(10);

		JButton btnChooseCustomer = new JButton("Choose Customer");
		btnChooseCustomer.setToolTipText("Choose Customer");
		btnChooseCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerForm cf = new CustomerForm();
				cf.setVisible(true);
				cf.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnChooseCustomer.setBackground(Color.GREEN);
		btnChooseCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChooseCustomer.setBounds(344, 100, 159, 27);
		contentPane.add(btnChooseCustomer);

		JLabel lblSelectedCustomer = new JLabel("Selected Customer : ");
		lblSelectedCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSelectedCustomer.setBounds(284, 149, 147, 27);
		contentPane.add(lblSelectedCustomer);

		JPanel panelSelectedCustomer = new JPanel();
		panelSelectedCustomer.setBounds(284, 186, 296, 95);
		contentPane.add(panelSelectedCustomer);
		panelSelectedCustomer.setLayout(null);

		JLabel lblCustomerName = new JLabel("Name : ");
		lblCustomerName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCustomerName.setBounds(10, 10, 82, 13);
		panelSelectedCustomer.add(lblCustomerName);

		JLabel lblCustomerSsý = new JLabel("SSI : ");
		lblCustomerSsý.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCustomerSsý.setBounds(10, 56, 82, 13);
		panelSelectedCustomer.add(lblCustomerSsý);

		lblFoundedCusName = new JLabel("");
		lblFoundedCusName.setBounds(102, 10, 184, 24);
		panelSelectedCustomer.add(lblFoundedCusName);

		lblFoundedCusSsý = new JLabel("");
		lblFoundedCusSsý.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFoundedCusSsý.setBounds(102, 56, 184, 29);
		panelSelectedCustomer.add(lblFoundedCusSsý);

		JLabel lblItemsBasket = new JLabel("Items In Basket : ");
		lblItemsBasket.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblItemsBasket.setBounds(284, 314, 122, 17);
		contentPane.add(lblItemsBasket);

		JButton btnNewButton = new JButton("Add Item");
		btnNewButton.setToolTipText("Add New Item");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ItemForm itemForm = new ItemForm();
				itemForm.setVisible(true);
				itemForm.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(464, 312, 116, 21);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 359, 831, 132);
		contentPane.add(scrollPane);

		tableItems = new JTable();
		scrollPane.setViewportView(tableItems);
		tableModel = new DefaultTableModel();
		Object columns[] = { "Item Name", "Item Price", "Amount", "Total Price" };
		tableModel.setColumnIdentifiers(columns);
		tableItems.setModel(tableModel);

		JLabel lblDiscount = new JLabel("Discount Amount : ");
		lblDiscount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDiscount.setBounds(272, 525, 159, 13);
		contentPane.add(lblDiscount);

		txtDiscountAmount = new JTextField();
		txtDiscountAmount.setToolTipText("Discount Price");
		txtDiscountAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtDiscountAmount.setBounds(452, 515, 128, 34);
		contentPane.add(txtDiscountAmount);
		txtDiscountAmount.setColumns(10);

		JButton btnAccountInvoice = new JButton("Account Invoice");
		btnAccountInvoice.setToolTipText("Account to be Paid Invoice");
		btnAccountInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int countRow = tableItems.getRowCount();
				float sumOfItems = 0;
				if (countRow != 0) {
					for (int i = 0; i < countRow; i++) {

						sumOfItems += Integer.parseInt(tableItems.getModel().getValueAt(i, 3).toString());
					}
				}
				System.out.println(countRow);
				float discount = 0;
				if (!(txtDiscountAmount.getText().isEmpty())) {
					discount = Integer.parseInt(txtDiscountAmount.getText());
				}
				float toBePaid = sumOfItems - discount;
				lblSumOfInvoice.setText(Float.toString(sumOfItems));
				lblAmountToBePaid.setText(Float.toString(toBePaid));
			}
		});
		btnAccountInvoice.setBackground(Color.GREEN);
		btnAccountInvoice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAccountInvoice.setBounds(424, 574, 187, 36);
		contentPane.add(btnAccountInvoice);

		JLabel lblInvSummary = new JLabel("Invoice Summary :");
		lblInvSummary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvSummary.setBounds(272, 635, 128, 20);
		contentPane.add(lblInvSummary);

		JPanel panelInvoiceSummary = new JPanel();
		panelInvoiceSummary.setBounds(424, 635, 377, 82);
		contentPane.add(panelInvoiceSummary);
		panelInvoiceSummary.setLayout(null);

		JLabel lblSum = new JLabel("Sum Of Invoice : ");
		lblSum.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSum.setBounds(24, 10, 133, 25);
		panelInvoiceSummary.add(lblSum);

		lblSumOfInvoice = new JLabel("");
		lblSumOfInvoice.setBounds(191, 10, 142, 25);
		panelInvoiceSummary.add(lblSumOfInvoice);

		JLabel lblPaid = new JLabel("amount to be paid :");
		lblPaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPaid.setBounds(10, 45, 147, 27);
		panelInvoiceSummary.add(lblPaid);

		lblAmountToBePaid = new JLabel("");
		lblAmountToBePaid.setBounds(191, 45, 142, 27);
		panelInvoiceSummary.add(lblAmountToBePaid);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Cancel Form");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBackground(Color.RED);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.setBounds(228, 722, 116, 38);
		contentPane.add(btnCancel);

		JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Save New Invoice");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = 0;
				returnValue = JOptionPane.showConfirmDialog(null, "Are you sure To Save Invoice", "Are you sure?",
						JOptionPane.YES_NO_OPTION);
				if (returnValue == JOptionPane.YES_OPTION) {
					boolean stateOfInvoiceTable = loadDataToInvoiceTable();
					boolean stateOfInvoiceItemsTable = loadDataToInvoiceItemsTable();
					if (stateOfInvoiceTable == false && stateOfInvoiceItemsTable == false) {
						JOptionPane.showMessageDialog(btnSave, "Saved Invoice", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(btnSave, "Not Saved Invoice", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}
					clearInputs();

				}

				else if (returnValue == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Not Saved Invoice");
				}
			}

			private void clearInputs() {
				// TODO Auto-generated method stub
				txtInvSerNo.setText("");
				txtInvoiceNo.setText("");
				lblFoundedCusName.setText("");
				lblFoundedCusSsý.setText("");
				lblFoundedCusSsý.setText("");
				tableModel.setNumRows(0);
				txtDiscountAmount.setText("");
				lblSumOfInvoice.setText("");
				lblAmountToBePaid.setText("");
			}

			private boolean loadDataToInvoiceItemsTable() {
				boolean state = true;
				if (txtInvSerNo.getText().isEmpty() || txtInvoiceNo.getText().isEmpty()
						|| lblFoundedCusName.getText().isEmpty() || lblFoundedCusSsý.getText().isEmpty()
						|| txtDiscountAmount.getText().isEmpty() || lblAmountToBePaid.getText().isEmpty()
						|| lblSumOfInvoice.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnSave, "Warning", "Fill All Area", JOptionPane.WARNING_MESSAGE);
				} else {
					int choosenItemsCount = tableItems.getRowCount();
					Statement st = null;
					ResultSet rs = null;
					PreparedStatement pst = null;
					int itemId = 0;
					int invoiceId = 0;
					for (int i = 0; i < choosenItemsCount; i++) {
						String query = "SELECT id FROM item WHERE name=" + "'" + tableItems.getValueAt(i, 0).toString()
								+ "'" + " AND " + "unitPrice=" + "'" + tableItems.getValueAt(i, 1).toString() + "'";
						String query2 = "SELECT id FROM invoice WHERE seri=" + "'" + txtInvSerNo.getText() + "'"
								+ " AND " + "number=" + "'" + txtInvoiceNo.getText() + "'";
						try {
							st = DbHelper.conn.createStatement();
							rs = st.executeQuery(query);
							while (rs.next()) {
								itemId = rs.getInt(1);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							try {
								st.close();
								rs.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
						try {
							st = DbHelper.conn.createStatement();
							rs = st.executeQuery(query2);
							while (rs.next()) {
								invoiceId = rs.getInt(1);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							try {
								st.close();
								rs.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						int quantity = Integer.parseInt(tableItems.getValueAt(i, 2).toString());
						int amount = Integer.parseInt(tableItems.getValueAt(i, 3).toString());
						String query3 = "INSERT INTO invoiceItems(invoiceId,itemId,quantity,amount) VALUES(" + invoiceId
								+ "," + itemId + "," + quantity + "," + amount + ")";
						try {
							pst = DbHelper.conn.prepareStatement(query3);
							state = pst.execute();
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
				return state;

			}

			private boolean loadDataToInvoiceTable() {
				PreparedStatement pst = null;
				Statement st = null;
				boolean state = true;
				if (txtInvSerNo.getText().isEmpty() || txtInvoiceNo.getText().isEmpty()
						|| lblFoundedCusName.getText().isEmpty() || lblFoundedCusSsý.getText().isEmpty()
						|| txtDiscountAmount.getText().isEmpty() || lblAmountToBePaid.getText().isEmpty()
						|| lblSumOfInvoice.getText().isEmpty()) {
					JOptionPane.showMessageDialog(btnSave, "Warning", "Fill All Area", JOptionPane.WARNING_MESSAGE);
				} else {

					ResultSet rs = null;
					try {
						int customerId = 0;
						String query1 = "SELECT id FROM customer WHERE name=" + "'" + lblFoundedCusName.getText() + "'"
								+ " AND " + "ssnNumber=" + "'" + lblFoundedCusSsý.getText() + "'";
						st = DbHelper.conn.createStatement();
						rs = st.executeQuery(query1);
						while (rs.next()) {
							customerId = rs.getInt(1);
						}
						String query2 = "INSERT INTO invoice(seri,number,totalAmount,discount,amountToPay,customerId) VALUES("
								+ "'" + txtInvSerNo.getText() + "'" + "," + "'" + txtInvoiceNo.getText() + "'" + ","
								+ "'" + lblSumOfInvoice.getText() + "'" + "," + "'" + txtDiscountAmount.getText() + "'"
								+ "," + "'" + lblAmountToBePaid.getText() + "'" + "," + customerId + ")";

						pst = DbHelper.conn.prepareStatement(query2);
						state = pst.execute();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
						try {
							pst.close();
							rs.close();
							st.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					// TODO Auto-generated method stub

				}
				return state;

			}
		});
		btnSave.setBackground(Color.GREEN);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.setBounds(631, 727, 122, 38);
		contentPane.add(btnSave);

	}
}
