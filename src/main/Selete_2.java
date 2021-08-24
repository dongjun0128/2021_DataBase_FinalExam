package main;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class Selete_2 extends JFrame implements ActionListener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Selete_2 frame = new Selete_2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//SQL ����
	Connection con;// Ŭ���� booklist�� �����Ѵ�. java.sql�� Connection ��ü con�� �����Ѵ�.
	PreparedStatement psmt;
	Statement stmt,stmt1;
	ResultSet rs;
	ResultSet rs2;
	String Driver="";
	String url="jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false"; 
	String userid="madang";
	String pwd="madang";
	private JTextField torepair; // �ʿ������ �����
	private JTextField tofix;
	public void conDB() { 
	  try {
	    Class.forName("com.mysql.cj.jdbc.Driver");   
	    //System.out.println("����̹� �ε� ����");
	  } catch(ClassNotFoundException e1) {
	      e1.printStackTrace();
	  }
	try {
	    //System.out.println("�����ͺ��̽� ���� �غ�...");
	    con=DriverManager.getConnection(url, userid, pwd); 
	    System.out.println("�˻�2 CONNECTION SUCCESFUL\nREADY TO USE PROGRAM");
	  } catch(SQLException e1) {
	      e1.printStackTrace();
		    }
	}
	
	JButton btnOk1,backbtn;
	JTextArea txtResult, txtStatus;
	JPanel pn1;
	
	public Selete_2() {
		setTitle("Selete_2");
		conDB();
		layInit();
	    setBounds(200, 200, 1000, 600); //������ġ,������ġ,���α���,���α���
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    backbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		backbtn.setBackground(Color.gray);
		backbtn.setBounds(0, 0, 103, 23);
		
	}
	
	public void layInit() {
		backbtn = new JButton("���");
		btnOk1=new JButton("Ȯ��");
		
		txtResult = new JTextArea(); //���� ��
	    txtStatus = new JTextArea(5,30);
	      
	    pn1 = new JPanel();
	    
	    btnOk1.addActionListener(this);
	   
	    
	    pn1.add(backbtn);
	    pn1.add(btnOk1);
	    
	    txtResult.setEditable(false);
	    txtStatus.setEditable(false);
	    JScrollPane scrollPane = new JScrollPane(txtResult);
	    JScrollPane scrollPane2 = new JScrollPane(txtStatus);
	
	    add("North", pn1);
	    add("Center", scrollPane);
	    add("South", scrollPane2);	      
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
		if(e.getSource() == btnOk1) {
			txtResult.setText("'������' ��ȣ��� ���� ������ �ǻ���� �������� count\n");
			String query ="select major_treat, count(major_treat) from Doctors where doc_gen = (select nur_gen from Nurses where nur_name = '������') group by major_treat;";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			txtResult.append("����	��\n");
			while (rs.next()) {
                String str = rs.getString(1) + "\t" + rs.getString(2) +"\n";
                txtResult.append(str);
             }
            
		}	
		}

		 catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
