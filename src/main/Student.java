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

public class Student extends JFrame implements ActionListener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student frame = new Student();
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
	    System.out.println("���� CONNECTION SUCCESFUL\nREADY TO USE PROGRAM");
	  } catch(SQLException e1) {
	      e1.printStackTrace();
	    }
	}
	
	JButton btnOk1,btnOk2,btnOk3,btnOk4,btnOk5,btnOk6, btn_insert, btnReset,btnreset_table, btn_insert_data;
	JTextField txt_value1, txt_value2, txt_value3, txt_name;
	JTextArea txtResult, txtStatus;
	JPanel pn1, insert_pn;
	JButton btn;
	JButton backbtn,btn_stuname;
	String stu_name=null;

	JRadioButton rd1,rd2,rd3,rd4,rd5;
	int flag=0;
	int stu_id;
		
	public Student() {
		setTitle("����");
		conDB();
		layInit();
	    setBounds(200, 200, 1000, 600); //������ġ,������ġ,���α���,���α���
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    backbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		backbtn.setBackground(Color.gray);
		backbtn.setBounds(0, 0, 103, 23);
		
	}
	
	public void layInit() {
	      btnOk1 = new JButton("��������(�Է�)");
	      btnOk2 = new JButton("�ð�ǥ");
	      btnOk3 = new JButton("���Ƹ�");
	      btnOk4 = new JButton("����ǥ");
	      backbtn = new JButton("���");
	      btn_insert = new JButton("insert");
	      btnreset_table=new JButton("table ����");
	      btnReset = new JButton("ȭ���ʱ�ȭ");
	      
	      txtResult = new JTextArea(); //���� ��
	      txtStatus = new JTextArea(5,30);
	      
	      pn1 = new JPanel();

	      
	      insert_pn = new JPanel();
	      insert_pn.setLayout(new GridLayout(10,5));
	      
	      txt_value1 = new JTextField(20);
	      txt_value2 = new JTextField(20);
	      txt_name=new JTextField(20);
	      btn_insert_data = new JButton("�Է�â");
	      btn_stuname=new JButton("Ȯ��");
	      
	      insert_pn.add(new JLabel("query�� �Է�(insert ���� ������ ���� �Է����ּ���)"));
	      insert_pn.add(txt_name);
	      insert_pn.add(btn_stuname);
	      
	      btn_insert_data.addActionListener(this);
	      
	      
	      rd1 = new JRadioButton("Doctors");
	      rd2 = new JRadioButton("Treatments");
	      rd3 = new JRadioButton("Patients");
	      rd4 = new JRadioButton("Nures");
	      rd5 = new JRadioButton("Charts");
	      ButtonGroup bg = new ButtonGroup();
	      bg.add(rd1);
	      bg.add(rd2);
	      bg.add(rd3);
	      bg.add(rd4);
	      bg.add(rd5);
	      
	      
	      pn1.add(backbtn);
	      pn1.add(rd1);
	      pn1.add(rd2);
	      pn1.add(rd3);
	      pn1.add(rd4);
	      pn1.add(rd5);
	      
	      txtResult.setEditable(false);
	      txtStatus.setEditable(false);
	      JScrollPane scrollPane = new JScrollPane(txtResult);
	      JScrollPane scrollPane2 = new JScrollPane(txtStatus);
	      
	      add("North", pn1);
	      add("Center", scrollPane);
	      add("South", scrollPane2);
	      add("East",insert_pn);

	      
	      btn_stuname.addActionListener(this);
	      
	   }

	public void returnresult(){
		/*try {
			listarea.setText("ķ��īID \t ���� \t ������ȣ \t �����ο��� \t ����ȸ�� \t �������� \t ��������Ÿ� \t �뿩��� \tķ��ī������� \t �뿩ȸ��ID \n");
			stmt = con.createStatement();
			String query="SELECT * FROM rentcar_list;";
			rs = stmt.executeQuery(query);
             while(rs.next()) {
                String str = rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
                +  "\t" + rs.getString(5)+ "\t" + rs.getString(6)+"\t"+ rs.getString(7)+"\t"+ rs.getString(8)+"����\t"+ rs.getString(9)+"\t"+ rs.getString(10)+"\n";
                listarea.append(str);
             }
		}catch(Exception e1) {
			System.out.println(e1);
		}*/ 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			stmt = con.createStatement();
			
			if(e.getSource() == btn_stuname) {
				
				if(rd1.isSelected()) {
	    			  flag=1;
	    			}
	    		  else if(rd2.isSelected()) {
	    			  flag=2;
	    		  }
	    		  else if(rd3.isSelected()) {
	    			  flag=3;
	    		  }
	    		  else if(rd4.isSelected()) {
	    			  flag=4;
	    		  }
	    		  else if(rd5.isSelected()) {
	    			  flag=5;
	    		  }
				
				String query;
				
				if(flag==0) {
					JOptionPane.showMessageDialog(btnOk1, "��ư�� ����ּ���");
				}
				
				else if(flag == 1) {
					 query = txt_name.getText();
		             stmt.executeUpdate(query);
		             JOptionPane.showMessageDialog(btn_stuname, "�Է� �Ϸ�!");
				}
				
				else if(flag == 2) {
					query = txt_name.getText();
		             stmt.executeUpdate(query);
		             JOptionPane.showMessageDialog(btn_stuname, "�Է� �Ϸ�!");
				}
				
				else if(flag == 3) {
					query = txt_name.getText();
		             stmt.executeUpdate(query);
		             JOptionPane.showMessageDialog(btn_stuname, "�Է� �Ϸ�!");	
				}
							
				else if(flag == 4) {
					query = txt_name.getText();
		             stmt.executeUpdate(query);
		             JOptionPane.showMessageDialog(btn_stuname, "�Է� �Ϸ�!");
				}
							
				else if(flag == 5) {
					query = txt_name.getText();
		             stmt.executeUpdate(query);
		             JOptionPane.showMessageDialog(btn_stuname, "�Է� �Ϸ�!");
				}
			
			}
	
		}
		
		catch (Exception e2) {
			JOptionPane.showMessageDialog(btn_stuname,"����!!" + e2);
		}
		
	      
	
		
		
	}
}
