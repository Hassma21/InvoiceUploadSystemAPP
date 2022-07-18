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

public class ItemForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtItemName;
	private JTable tableItems;
	private DefaultTableModel tableModel;
	private JTextField txtAmount;
	private JTextField textItemPrice;

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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelNewItem = new JPanel();
		panelNewItem.setBackground(Color.LIGHT_GRAY);
		panelNewItem.setBounds(544, 40, 464, 336);
		contentPane.add(panelNewItem);
		panelNewItem.setLayout(null);
		
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
		txtItemName.setBounds(217, 65, 103, 19);
		panelNewItem.add(txtItemName);
		txtItemName.setColumns(10);
		
		
		
		
		
		
		JButton btnSaveItem = new JButton("Save");
		btnSaveItem.setToolTipText("Save New Item");
		btnSaveItem.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveItem.addActionListener(new ActionListener() {
			PreparedStatement pst=null;
			public void actionPerformed(ActionEvent e) {
				int returnValue = 0;
		    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure To Save Invoice", "Are you sure?", JOptionPane.YES_NO_OPTION);
		    	if (returnValue == JOptionPane.YES_OPTION) {
		    		if(txtItemName.getText().isEmpty() || textItemPrice.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnSaveItem, "Please Enter All Area", "WARNING",JOptionPane.WARNING_MESSAGE);
					}else {
						String itemName=txtItemName.getText();
						String itemPrice=textItemPrice.getText();
						String query="INSERT INTO item(name,unitPrice) VALUES("+"'"+itemName+"'"+","+"'"+itemPrice+"'"+")";
						try {
							pst=DbHelper.conn.prepareStatement(query);
							boolean state=pst.execute();
							if(state) {
								JOptionPane.showMessageDialog(btnSaveItem, "NOT INSERTED", "WARNING",JOptionPane.WARNING_MESSAGE);
							}
							else {
								JOptionPane.showMessageDialog(btnSaveItem, "INSERTED NEW Item", "INFO",JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}finally {
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
		    		JOptionPane.showMessageDialog(null, "Not Saved Item");
		    	}
		
				clearInputs();
			}
			private void clearInputs() {
				txtItemName.setText("");
				textItemPrice.setText("");
			}
		});
		btnSaveItem.setBackground(Color.GREEN);
		btnSaveItem.setBounds(178, 224, 113, 37);
		panelNewItem.add(btnSaveItem);
		
		textItemPrice = new JTextField();
		textItemPrice.setToolTipText("Item Price");
		textItemPrice.setBounds(217, 157, 96, 19);
		panelNewItem.add(textItemPrice);
		textItemPrice.setColumns(10);
		
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
		tableModel=new DefaultTableModel();
		Object columns[]= {"Name","Price"};
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
				int selectedRow=tableItems.getSelectedRow();
				String itemName=tableItems.getValueAt(selectedRow, 0).toString();
				int itemPrice=Integer.parseInt(tableItems.getValueAt(selectedRow, 1).toString());
				int amount=Integer.parseInt(txtAmount.getText());//format tipte olduðu için girilmeyen karakterler boþluk olrak alýnýr bu yüzden NumberFormatException hatasý alýrýz
				//boþluklarý silmek için replace methodu kullanýlýr
				int totalPrice=itemPrice*amount;
				Object item[]= {itemName,itemPrice,amount,totalPrice};
				CreateInvoice.tableModel.addRow(item);
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
		
		
	}

	private void loadItemsToTable() {
		Statement pst=null;
		ResultSet rs=null;
		String query="SELECT * FROM item";
		try {
			pst=DbHelper.conn.createStatement();
			rs=pst.executeQuery(query);
			while(rs.next()) {
				Object data[]= {rs.getString(2),rs.getString(3)};
				tableModel.addRow(data);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
