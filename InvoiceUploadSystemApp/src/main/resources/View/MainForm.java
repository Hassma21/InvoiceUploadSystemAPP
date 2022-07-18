package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DbHelper;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class MainForm extends JFrame {

	private JPanel contentPane;
	private PreparedStatement stm;
	Connection connect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//bir frame bu þekilde icon verilebilir
					frame.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setBackground(Color.GRAY);
        connect=DbHelper.dbConnect();
		setTitle("Hugin Invoice System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 525);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnCreateInv = new JButton("Create Invoice");
		btnCreateInv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateInvoice createInvoice=new CreateInvoice();
				createInvoice.setVisible(true);
				createInvoice.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnCreateInv.setToolTipText("Create New Invoice For Customer");
		btnCreateInv.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCreateInv.setBackground(Color.GREEN);
		btnCreateInv.setBounds(382, 50, 201, 44);
		contentPane.add(btnCreateInv);
		
		JButton btnXmlInv = new JButton("Invoice Xml Operations");
		btnXmlInv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InvoiceXml xmlInvoice=new InvoiceXml();
				xmlInvoice.setVisible(true);
				xmlInvoice.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnXmlInv.setToolTipText("Xml Operations");
		btnXmlInv.setBackground(Color.GREEN);
		btnXmlInv.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnXmlInv.setBounds(382, 115, 201, 44);
		contentPane.add(btnXmlInv);
		
		JButton btnJsonInv = new JButton("Invoice Json Operations");
		btnJsonInv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InvoiceJson invJson=new InvoiceJson();
				invJson.setVisible(true);
				invJson.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnJsonInv.setToolTipText("Json Operations");
		btnJsonInv.setBackground(Color.GREEN);
		btnJsonInv.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnJsonInv.setBounds(382, 184, 201, 44);
		contentPane.add(btnJsonInv);
		
		JButton btnDeleteInv = new JButton("Delete Invoice");
		btnDeleteInv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteInvoice dltInvoice=new DeleteInvoice();
				dltInvoice.setVisible(true);
				dltInvoice.setIconImage(new ImageIcon(getClass().getResource("/Images/post.jpg")).getImage());
			}
		});
		btnDeleteInv.setToolTipText("Delete Old Invoice");
		btnDeleteInv.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDeleteInv.setBackground(Color.GREEN);
		btnDeleteInv.setBounds(382, 258, 201, 39);
		contentPane.add(btnDeleteInv);
		
		JButton btnExitApp = new JButton("Exit");
		btnExitApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					connect.close();
					System.out.println("closed connect");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		btnExitApp.setBackground(Color.RED);
		btnExitApp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExitApp.setToolTipText("Exit App");
		btnExitApp.setBounds(345, 370, 263, 39);
		contentPane.add(btnExitApp);
	}
	
}
