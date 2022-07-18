package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import Helper.DbHelper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.SystemColor;

public class InvoiceJson extends JFrame {

	private JPanel contentPane;
	private JTextField txtInvSerNo;
	private JTextField txtInvNo;
	private JTable table;
	DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvoiceJson frame = new InvoiceJson();
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
	public InvoiceJson() {
		setTitle("Hugin Invoice System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 875, 507);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSaveJsonFile = new JButton("Save To Json Service");
		btnSaveJsonFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  File jsonFile = new File("sampleoutput.json");
				  FileWriter fileWriter=null;
				  JSONObject root=new JSONObject();
				  JSONArray uploadSystem=new JSONArray();
					  try {
							
							    System.out.println("File created: " + jsonFile.getName());
							    
							    JSONObject customerData=new JSONObject();
								customerData.put("name",table.getValueAt(0, 0).toString());
								customerData.put("ssnNumber",table.getValueAt(0, 1).toString());
								JSONObject customer=new JSONObject();
								customer.put("customer", customerData);
								
								JSONArray items=new JSONArray();
								int count=table.getRowCount();
								for(int i=0;i<count;i++) {
									JSONObject item=new JSONObject();
									item.put("name",table.getValueAt(i, 2).toString());
									item.put("quantity",table.getValueAt(i,3).toString());
									item.put("unitPrice",table.getValueAt(i,4).toString());
									item.put("amount",table.getValueAt(i,5).toString());
									items.add(item);
								}
								
								JSONObject invoiceData=new JSONObject();
								invoiceData.put("seri",txtInvSerNo.getText());
								invoiceData.put("number",txtInvNo.getText());
								invoiceData.put("item",items);
								invoiceData.put("totalAmount",table.getValueAt(0, 6).toString());
								invoiceData.put("discount",table.getValueAt(0, 7).toString());
								invoiceData.put("amountToPay",table.getValueAt(0, 8).toString());
								JSONObject invoice=new JSONObject();
								invoice.put("invoice",invoiceData);
								
								
								JSONArray customerInvoice=new JSONArray();
								
							    customerInvoice.add(customer);
							    customerInvoice.add(invoice);
								
								uploadSystem.add(customerInvoice);

                                root.put("uploadSystem", uploadSystem);
                                fileWriter=new FileWriter(jsonFile);
								fileWriter.write(root.toJSONString());
								
								JOptionPane.showMessageDialog(btnSaveJsonFile, "inserted to new Json file");

							  
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}finally {
							try {
								fileWriter.close();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
				  
			    
			}
		});
		btnSaveJsonFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveJsonFile.setBackground(Color.GREEN);
		btnSaveJsonFile.setBounds(196, 379, 181, 38);
		contentPane.add(btnSaveJsonFile);
		
		JLabel lblInvSerNo = new JLabel("Inv Seri No : ");
		lblInvSerNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvSerNo.setBounds(67, 29, 105, 13);
		contentPane.add(lblInvSerNo);
		
		JLabel lblInvNo = new JLabel("Inv No :");
		lblInvNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvNo.setBounds(67, 85, 105, 13);
		contentPane.add(lblInvNo);
		
		txtInvSerNo = new JTextField();
		txtInvSerNo.setBounds(182, 28, 96, 19);
		contentPane.add(txtInvSerNo);
		txtInvSerNo.setColumns(10);
		
		txtInvNo = new JTextField();
		txtInvNo.setBounds(182, 84, 96, 19);
		contentPane.add(txtInvNo);
		txtInvNo.setColumns(10);
		
		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.setNumRows(0);
				String query="SELECT c.name,c.ssnNumber,it.name,invit.quantity,it.unitPrice,invit.amount,i.totalAmount,i.discount,i.amountToPay FROM invoice AS i "
						+ "INNER JOIN customer AS c ON i.customerId=c.id "
						+ "INNER JOIN invoiceItems AS invit ON i.id=invit.invoiceId "
						+ "INNER JOIN item AS it ON it.id=invit.itemId WHERE seri="+"'"+txtInvSerNo.getText()+"'"+" AND "+"number="+"'"+txtInvNo.getText()+"'";
				System.out.println(query);
				Statement st=null;
				ResultSet rs=null;
				Object data[];
			   try {
					st=DbHelper.conn.createStatement();
					rs=st.executeQuery(query);
     				while(rs.next()) {
						data=new Object[]{rs.getString(1).toString(),rs.getString(2).toString(),rs.getString(3).toString(),rs.getInt(4),rs.getString(5).toString(),rs.getInt(6),rs.getString(7),rs.getString(8),rs.getString(9)};
						tableModel.addRow(data);
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
     			}finally {
					try {
						st.close();
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
					e1.printStackTrace();
					}
					
				}
			}
		});
		btnFind.setBackground(Color.GREEN);
		btnFind.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFind.setBounds(353, 48, 85, 21);
		contentPane.add(btnFind);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 142, 806, 214);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		tableModel=new DefaultTableModel();
		Object columns[]= {"Customer Name","Customer SSN Number","Item Name","Quantity","Unit Price","Amount","Total Amount","Discount","Amount To Pay"};
		tableModel.setColumnIdentifiers(columns);
		table.setModel(tableModel);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBackground(Color.RED);
		btnCancel.setBounds(474, 379, 188, 38);
		contentPane.add(btnCancel);
	}

}
