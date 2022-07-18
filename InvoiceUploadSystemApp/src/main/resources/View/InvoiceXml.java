package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.DbHelper;
import Helper.XmlHelper;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class InvoiceXml extends JFrame {

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
					InvoiceXml frame = new InvoiceXml();
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
	public InvoiceXml() {
		setTitle("Hugin Invoice System");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 904, 558);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInvSerNo = new JLabel("Inv Seri No : ");
		lblInvSerNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvSerNo.setToolTipText("Inv Seri No : ");
		lblInvSerNo.setBounds(48, 39, 94, 13);
		contentPane.add(lblInvSerNo);
		
		JLabel lblInvNo = new JLabel("Inv No :");
		lblInvNo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvNo.setBounds(48, 88, 94, 13);
		contentPane.add(lblInvNo);
		
		txtInvSerNo = new JTextField();
		txtInvSerNo.setBounds(174, 36, 96, 19);
		contentPane.add(txtInvSerNo);
		txtInvSerNo.setColumns(10);
		
		txtInvNo = new JTextField();
		txtInvNo.setBounds(174, 85, 96, 19);
		contentPane.add(txtInvNo);
		txtInvNo.setColumns(10);
		
		JButton btnFindInv = new JButton("F\u0131nd");
		btnFindInv.addActionListener(new ActionListener() {
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
		btnFindInv.setBackground(Color.GREEN);
		btnFindInv.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFindInv.setBounds(386, 54, 104, 27);
		contentPane.add(btnFindInv);
		tableModel=new DefaultTableModel();
		Object columns[]= {"Customer Name","Customer SSN Number","Item Name","Quantity","Unit Price","Amount","Total Amount","Discount","Amount To Pay"};
		tableModel.setColumnIdentifiers(columns);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 163, 870, 254);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		
		JButton btnSaveXmlService = new JButton("Save XML Service");
		btnSaveXmlService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count=table.getRowCount();
				String customerName=null;
				String customerSsnNumber=null;
				Object items[][]=new Object[count][4];
				String totalAmountValue=null;
				String discountValue=null;
				String amountToPayValue=null;
				customerName=table.getValueAt(0, 0).toString();
				customerSsnNumber=table.getValueAt(0, 1).toString();
				
				
				
				
				for(int i=0;i<items.length;i++) {
					items[i][0]=table.getValueAt(i, 2).toString();
					items[i][1]=table.getValueAt(i, 3).toString();
					items[i][2]=table.getValueAt(i, 4).toString();
					items[i][3]=table.getValueAt(i, 5).toString();
				}
				   discountValue=table.getValueAt(0, 6).toString();
            	   totalAmountValue=table.getValueAt(0, 7).toString();
            	   amountToPayValue+=table.getValueAt(0, 8).toString();
				
				
				
				XmlHelper.xmlConn(customerName,customerSsnNumber,items, totalAmountValue, discountValue, amountToPayValue);
			}
		});
		btnSaveXmlService.setBackground(Color.GREEN);
		btnSaveXmlService.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSaveXmlService.setBounds(178, 458, 151, 33);
		contentPane.add(btnSaveXmlService);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBackground(Color.RED);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCancel.setBounds(447, 458, 171, 33);
		contentPane.add(btnCancel);
	}
}