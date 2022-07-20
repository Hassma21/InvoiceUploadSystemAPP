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
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import View.CreateInvoiceForm;
import javax.swing.JScrollPane;

public class CustomerForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtFoundCusName;
	private JTable tableCustomer;
	static String customerName;
	static String customerssnNumber;
	static DefaultTableModel dataModel;
	private JTextField txtFoundCusSocId;

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
		panelFindCustomer.setBounds(10, 27, 545, 412);
		contentPane.add(panelFindCustomer);
		panelFindCustomer.setLayout(null);

		txtFoundCusName = new JTextField();
		txtFoundCusName.setToolTipText("Customer Name");
		txtFoundCusName.setColumns(10);
		txtFoundCusName.setBounds(86, 36, 141, 32);
		panelFindCustomer.add(txtFoundCusName);

		

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
				ResultSet rs = null;
				try {
					if(txtFoundCusName.getText().isEmpty()) {
						txtFoundCusName.setText(" ");
					}
					if(txtFoundCusSocId.getText().isEmpty()) {
						txtFoundCusSocId.setText(" ");
					}
					String query = "SELECT * FROM customer WHERE name LIKE " + '"' + txtFoundCusName.getText() +"%"+ '"' + " OR "
							+ "ssnNumber LIKE " + '"' + txtFoundCusSocId.getText() +"%"+ '"';
					System.out.println(query);
					pst = DbHelper.conn.prepareStatement(query);
					rs = pst.executeQuery();
					while (rs.next()) {
						Object dataObject[] = { rs.getString("name"), rs.getString("ssnNumber") };

						dataModel.addRow(dataObject);
					}
					txtFoundCusName.setText("");
					txtFoundCusSocId.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
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
		dataModel = new DefaultTableModel();
		Object columns[] = { "name", "ssnNumber" };
		dataModel.setColumnIdentifiers(columns);
		tableCustomer.setModel(dataModel);
		loadCustomerToTable();

		JButton btnChooseCustomer = new JButton("Choose");
		btnChooseCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sr = tableCustomer.getSelectedRow();
				String name = tableCustomer.getValueAt(sr, 0).toString();
				String ssnNumber = tableCustomer.getValueAt(sr, 1).toString();
				int returnValue = 0;
				returnValue = JOptionPane.showConfirmDialog(null,
						"Your Selection is \n" + "Name :" + name + "\n" + "Social Id :" + ssnNumber, "Are you sure?",
						JOptionPane.YES_NO_OPTION);
				if (returnValue == JOptionPane.YES_OPTION) {
					CreateInvoiceForm.lblFoundedCusName.setText(name);
					CreateInvoiceForm.lblFoundedCusSsý.setText(ssnNumber);
				}
			}
		});
		btnChooseCustomer.setToolTipText("Choose Customer");
		btnChooseCustomer.setBackground(Color.GREEN);
		btnChooseCustomer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnChooseCustomer.setBounds(209, 350, 155, 37);
		panelFindCustomer.add(btnChooseCustomer);

		JButton btnGetAllCus = new JButton("Get All Customer");
		btnGetAllCus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataModel.setNumRows(0);
				loadCustomerToTable();
			}
		});
		btnGetAllCus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGetAllCus.setBackground(Color.GREEN);
		btnGetAllCus.setBounds(19, 311, 155, 32);
		panelFindCustomer.add(btnGetAllCus);
		
		txtFoundCusSocId = new JTextField();
		txtFoundCusSocId.setBounds(356, 36, 126, 32);
		panelFindCustomer.add(txtFoundCusSocId);
		txtFoundCusSocId.setColumns(10);

		JButton btnSaveNewCustomer = new JButton("New Customer");
		btnSaveNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewCustomerForm newCustomerForm = new NewCustomerForm();
				newCustomerForm.setVisible(true);
				newCustomerForm.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnSaveNewCustomer.setBackground(Color.GREEN);
		btnSaveNewCustomer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSaveNewCustomer.setBounds(660, 156, 309, 71);
		contentPane.add(btnSaveNewCustomer);
	}

	private void loadCustomerToTable() {
		Statement pst = null;
		ResultSet rs = null;
		String query = "SELECT * FROM customer";
		try {
			pst = DbHelper.conn.createStatement();
			rs = pst.executeQuery(query);
			while (rs.next()) {
				Object data[] = { rs.getString(2), rs.getString(3) };
				dataModel.addRow(data);
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