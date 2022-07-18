package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.sun.jdi.connect.spi.Connection;

import Helper.DbHelper;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import View.CreateInvoice;
import javax.swing.JScrollPane;

public class CustomerForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtCusName;
	private JTextField txtFoundCusName;
	private JFormattedTextField mskedTxtFoundCusSsý;
	private JFormattedTextField mskedTxtCusSsý;
	private JTable tableCustomer;
	static String customerName;
	static String customerssnNumber;
	DefaultTableModel dataModel;
	 
	
	
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerForm frame = new CustomerForm();
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
	public CustomerForm() {
		setTitle("Hugin Invoice System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1202, 678);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelFindCustomer = new JPanel();
		panelFindCustomer.setBackground(SystemColor.activeCaptionBorder);
		panelFindCustomer.setBounds(10, 28, 545, 412);
		contentPane.add(panelFindCustomer);
		panelFindCustomer.setLayout(null);
		
		txtFoundCusName = new JTextField();
		txtFoundCusName.setToolTipText("Customer Name");
		txtFoundCusName.setColumns(10);
		txtFoundCusName.setBounds(86, 36, 141, 32);
		panelFindCustomer.add(txtFoundCusName);
		
		
		try {
			mskedTxtFoundCusSsý = new JFormattedTextField(new MaskFormatter("###########"));
			mskedTxtFoundCusSsý.setToolTipText("Customer Social Id");
			mskedTxtFoundCusSsý.setBounds(394, 36, 141, 32);
			panelFindCustomer.add(mskedTxtFoundCusSsý);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JLabel lblCusName = new JLabel("Name : ");
		lblCusName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCusName.setBounds(10, 37, 94, 25);
		panelFindCustomer.add(lblCusName);
		
		JLabel lblCusSsý = new JLabel("SSI : ");
		lblCusSsý.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCusSsý.setBounds(288, 36, 76, 25);
		panelFindCustomer.add(lblCusSsý);
		
		
		JButton btnFindCustomer = new JButton("Find");
		btnFindCustomer.setToolTipText("Find Customer");
		btnFindCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataModel.setNumRows(0);
				PreparedStatement pst = null;
				ResultSet rs=null;
			  try {
				String query="SELECT * FROM customer WHERE name="+'"'+txtFoundCusName.getText()+'"'+" OR "+"ssnNumber="+'"'+mskedTxtFoundCusSsý.getText()+'"';
				pst = DbHelper.conn.prepareStatement(query);
				rs= pst.executeQuery();
				while(rs.next()) {
					Object dataObject[]= {
							rs.getString("name"),
							rs.getString("ssnNumber")
					};
					
					dataModel.addRow(dataObject);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				try {
					pst.close();
					rs.close();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
		});
		btnFindCustomer.setBackground(Color.GREEN);
		btnFindCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFindCustomer.setBounds(220, 96, 104, 32);
		panelFindCustomer.add(btnFindCustomer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 181, 509, 120);
		panelFindCustomer.add(scrollPane);
		
		tableCustomer = new JTable();
		scrollPane.setViewportView(tableCustomer);
		tableCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int sr=tableCustomer.getSelectedRow();
				String name=tableCustomer.getValueAt(sr, 0).toString();
				String ssnNumber=tableCustomer.getValueAt(sr, 1).toString();
				CreateInvoice.lblFoundedCusName.setText(name);
				CreateInvoice.lblFoundedCusSsý.setText(ssnNumber);
			}
		});
		dataModel=new DefaultTableModel();
		Object columns[]= {"name","ssnNumber"};
		dataModel.setColumnIdentifiers(columns);
		tableCustomer.setModel(dataModel);
		
		
		
		JButton btnChooseCustomer = new JButton("Choose");
		btnChooseCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnChooseCustomer.setToolTipText("Choose Customer");
		btnChooseCustomer.setBackground(Color.GREEN);
		btnChooseCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChooseCustomer.setBounds(209, 350, 155, 37);
		panelFindCustomer.add(btnChooseCustomer);
		
		JPanel panelInsertCustomer = new JPanel();
		panelInsertCustomer.setBackground(SystemColor.activeCaptionBorder);
		panelInsertCustomer.setBounds(643, 28, 535, 416);
		contentPane.add(panelInsertCustomer);
		panelInsertCustomer.setLayout(null);
		
		JButton btnSaveNewCustomer = new JButton("Save");
		btnSaveNewCustomer.addActionListener(new ActionListener() {
			PreparedStatement st=null;
			public void actionPerformed(ActionEvent e) {
				int returnValue = 0;
		    	returnValue = JOptionPane.showConfirmDialog(null, "Are you sure To Save Invoice", "Are you sure?", JOptionPane.YES_NO_OPTION);
		    	if (returnValue == JOptionPane.YES_OPTION) {
					if(txtCusName.getText().isEmpty() || mskedTxtCusSsý.getText().isEmpty()) {
						JOptionPane.showMessageDialog(btnSaveNewCustomer, "WARNING", "Please Enter All Area",JOptionPane.WARNING_MESSAGE);
					}else {
						String name=txtCusName.getText();
						String ssnNumber=mskedTxtCusSsý.getText();
						String query="INSERT INTO customer(name,ssnNumber) VALUES"+"("+"'"+name+"'"+","+"'"+ssnNumber+"'"+")";
						try {
							st=DbHelper.conn.prepareStatement(query);
							boolean state=st.execute();//true if the first result is a ResultSetobject; false if the first result is an updatecount or there is no result
							if(state) {
								JOptionPane.showMessageDialog(btnSaveNewCustomer, "WARNING", "NOT INSERTED",JOptionPane.WARNING_MESSAGE);
							}
							else {
								JOptionPane.showMessageDialog(btnSaveNewCustomer, "INFO", "INSERTED NEW CUSTOMER",JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}finally {
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
		    		JOptionPane.showMessageDialog(null, "Not Saved Customer");
		    	}

		    	clearInputs();
				
				
			}
			private void clearInputs() {
				txtCusName.setText("");
				mskedTxtCusSsý.setText("");
			}
		});
		btnSaveNewCustomer.setBackground(Color.GREEN);
		btnSaveNewCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveNewCustomer.setBounds(213, 298, 129, 32);
		panelInsertCustomer.add(btnSaveNewCustomer);
		
		
		try {
			mskedTxtCusSsý = new JFormattedTextField(new MaskFormatter("###########"));
			mskedTxtCusSsý.setBounds(231, 181, 141, 32);
			panelInsertCustomer.add(mskedTxtCusSsý);
			
			JLabel lblNewCusName = new JLabel("Name : ");
			lblNewCusName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewCusName.setBounds(106, 114, 94, 25);
			panelInsertCustomer.add(lblNewCusName);
			
			JLabel lblNewCusSsý = new JLabel("SSI : ");
			lblNewCusSsý.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewCusSsý.setBounds(107, 188, 76, 25);
			panelInsertCustomer.add(lblNewCusSsý);
			
			txtCusName = new JTextField();
			txtCusName.setBounds(231, 113, 141, 32);
			panelInsertCustomer.add(txtCusName);
			txtCusName.setColumns(10);
			
			JLabel lblNewLabel = new JLabel("Add New Customer");
			lblNewLabel.setForeground(Color.DARK_GRAY);
			lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 18));
			lblNewLabel.setBounds(177, 10, 183, 39);
			panelInsertCustomer.add(lblNewLabel);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}