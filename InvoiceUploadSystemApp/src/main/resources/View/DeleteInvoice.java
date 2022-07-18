package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.DbHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class DeleteInvoice extends JFrame {

	private JPanel contentPane;
	private JTextField txtInvSerNo;
	private JTextField txtInvNo;
	private JTable tableInvoice;
	private JButton btnFýndInvoice;
	DefaultTableModel tableModel;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteInvoice frame = new DeleteInvoice();
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
	public DeleteInvoice() {
		setTitle("Hugin Invoice System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 775, 458);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInvSerNo = new JLabel("Invoice Seri No :");
		lblInvSerNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvSerNo.setBounds(106, 46, 138, 13);
		contentPane.add(lblInvSerNo);
		
		JLabel lblInvNo = new JLabel("Invoice No : ");
		lblInvNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvNo.setBounds(106, 94, 108, 13);
		contentPane.add(lblInvNo);
		
		txtInvSerNo = new JTextField();
		txtInvSerNo.setBounds(254, 45, 96, 19);
		contentPane.add(txtInvSerNo);
		txtInvSerNo.setColumns(10);
		
		txtInvNo = new JTextField();
		txtInvNo.setBounds(254, 93, 96, 19);
		contentPane.add(txtInvNo);
		txtInvNo.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 144, 514, 138);
		contentPane.add(scrollPane);
		
		tableInvoice = new JTable();
		scrollPane.setViewportView(tableInvoice);
		tableModel=new DefaultTableModel();
		Object columns[]= {"Customer Name","Customer ssnNumber","Invoice Amount"};
		tableModel.setColumnIdentifiers(columns);
		tableInvoice.setModel(tableModel);
		
		
		JButton btnDeleteInv = new JButton("Delete");
		btnDeleteInv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = JOptionPane.showConfirmDialog(null, 
		                "Are You Sure To Delete Invoice", "Select an Option",JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (returnValue == JOptionPane.YES_OPTION) {
					PreparedStatement pst=null;
					Statement st=null;
					ResultSet rs = null;
					int id = 0;
					String query1="SELECT id FROM invoice WHERE seri="+"'"+txtInvSerNo.getText()+"'"+" AND "+"number="+"'"+txtInvNo.getText()+"'";
					
					try {
						st=DbHelper.conn.createStatement();
						rs=st.executeQuery(query1);
						while(rs.next()) {
							id=rs.getInt(1);
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
					
					String query2="DELETE FROM invoice WHERE seri="+"'"+txtInvSerNo.getText()+"'"+" AND "+"number="+"'"+txtInvNo.getText()+"'"+";"+"\n"
							+"DELETE FROM invoiceItems WHERE invoiceId="+id;
					System.out.println(query2);
					try {
						pst=DbHelper.conn.prepareStatement(query2);
						boolean state=pst.execute();
						if(state) {
							JOptionPane.showMessageDialog( btnDeleteInv,"Not Deleted Invoice", "Warning",JOptionPane.WARNING_MESSAGE );
						}
						else {
							JOptionPane.showMessageDialog(btnDeleteInv, "Deleted Invoice", "Info",JOptionPane.INFORMATION_MESSAGE );
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
		    	else if (returnValue == JOptionPane.NO_OPTION) {
		    		JOptionPane.showMessageDialog(null, "Not Deleted Invoice","Info",JOptionPane.INFORMATION_MESSAGE);
		    	}
		    		
			}
		});
		btnDeleteInv.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDeleteInv.setBackground(Color.GREEN);
		btnDeleteInv.setBounds(155, 332, 108, 34);
		contentPane.add(btnDeleteInv);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.setBackground(Color.RED);
		btnCancel.setBounds(376, 332, 108, 34);
		contentPane.add(btnCancel);
		
		btnFýndInvoice = new JButton("F\u0131nd");
		btnFýndInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statement st=null;
				ResultSet rs=null;
				String query="SELECT c.name,c.ssnNumber,i.amountToPay FROM invoice AS i INNER JOIN customer AS c ON i.customerId=c.id WHERE seri="+"'"+txtInvSerNo.getText()+"'"+" AND "+"number="+"'"+txtInvNo.getText()+"'";
				System.out.println(query);
				try {
					st=DbHelper.conn.createStatement();
					rs=st.executeQuery(query);
					Object data[];
					while(rs.next()) {
						data=new Object[]{rs.getString(1).toString(),rs.getString(2).toString(),rs.getString(3).toString()};
//						tableInvoice.setValueAt(rs.getString(1), 0, 0);
//						tableInvoice.setValueAt(rs.getString(2), 0, 1);
//						tableInvoice.setValueAt(rs.getString(3), 0, 2);
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
		btnFýndInvoice.setBackground(Color.GREEN);
		btnFýndInvoice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFýndInvoice.setBounds(489, 64, 85, 21);
		contentPane.add(btnFýndInvoice);
	}
}
