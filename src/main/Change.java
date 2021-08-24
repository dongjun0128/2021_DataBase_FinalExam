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

import java.awt.EventQueue;

public class Change extends JFrame implements ActionListener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Change frame = new Change();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//SQL 연결
	Connection con;// 클래스 booklist를 선언한다. java.sql의 Connection 객체 con을 선언한다.
	PreparedStatement psmt;
	Statement stmt,stmt1;
	ResultSet rs;
	ResultSet rs2;
	String Driver="";
	String url="jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false"; 
	String userid="madang";
	String pwd="madang";
	private JTextField torepair; // 필요없으면 지우기
	private JTextField tofix;
	public void conDB() { 
	  try {
	    Class.forName("com.mysql.cj.jdbc.Driver");   
	    //System.out.println("드라이버 로드 성공");
	  } catch(ClassNotFoundException e1) {
	      e1.printStackTrace();
	  }
	try {
	    //System.out.println("데이터베이스 연결 준비...");
	    con=DriverManager.getConnection(url, userid, pwd); 
	    System.out.println("변경 CONNECTION SUCCESFUL\nREADY TO USE PROGRAM");
	  } catch(SQLException e1) {
	      e1.printStackTrace();
		    }
	}
	
	JButton btnOk1,backbtn;
	JRadioButton rd1,rd2,rd3,rd4,rd5;
	JTextArea txtResult, txtStatus;
	JPanel pn1, insert_pn;
	JTextField txt_value1, txt_value2;
	int flag=0;
	
	public Change() {
		setTitle("변경");
		conDB();
		layInit();
	    setBounds(200, 200, 1000, 600); //가로위치,세로위치,가로길이,세로길이
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    backbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		backbtn.setBackground(Color.gray);
		backbtn.setBounds(0, 0, 103, 23);
		
	}
	
	public void layInit() {
		backbtn = new JButton("취소");
		btnOk1=new JButton("확인");
		
		txtResult = new JTextArea(); //수정 ㄴ
	    txtStatus = new JTextArea(5,30);
	      
	    pn1 = new JPanel();
		
		insert_pn = new JPanel();
		insert_pn.setLayout(new GridLayout(10,5));
		
		txt_value1 = new JTextField(20);
		txt_value2 = new JTextField(20);
		
		insert_pn.add(new JLabel("set 조건식 입력"));
	    insert_pn.add(txt_value1);
	    insert_pn.add(new JLabel("where 조건식 입력"));
	    insert_pn.add(txt_value2); 
	    insert_pn.add(btnOk1);
	    
	    btnOk1.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent e) {
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
	    		  
	    		  System.out.println(flag);
	    	  }
	      
	      });
	    btnOk1.addActionListener(this);
	    
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
	      
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		try {
			stmt = con.createStatement();
			
			if(e.getSource() == btnOk1) {
				
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
				
				String query = null;
				String set_q;
				String where_q;
				
				if(flag==0) {
					JOptionPane.showMessageDialog(btnOk1, "버튼을 골라주세요");
				}
				
				else if(flag == 1) {
					 query = "update Doctors set ";
					 set_q = txt_value1.getText();
					 where_q = txt_value2.getText();
					 query = query + set_q + " where " + where_q +";";
					 stmt.executeUpdate(query);
					 JOptionPane.showMessageDialog(btnOk1, "입력되었습니다" );
				}
				
				else if(flag == 2) {
					 query = "update Treatments set ";
					 set_q = txt_value1.getText();
					 where_q = txt_value2.getText();
					 query = query + set_q + " where " + where_q +";";
					 stmt.executeUpdate(query);
					 JOptionPane.showMessageDialog(btnOk1, "입력되었습니다" );
				}
				
				else if(flag == 3) {
					 query = "update Patients set ";
					 set_q = txt_value1.getText();
					 where_q = txt_value2.getText();
					 query = query + set_q + " where " + where_q +";";
					 stmt.executeUpdate(query);
					 JOptionPane.showMessageDialog(btnOk1, "입력되었습니다" );
				}
							
				else if(flag == 4) {
					 query = "update Nurses set ";
					 set_q = txt_value1.getText();
					 where_q = txt_value2.getText();
					 query = query + set_q + " where " + where_q +";";
					 stmt.executeUpdate(query);
					 JOptionPane.showMessageDialog(btnOk1, "입력되었습니다" );
				}
							
				else if(flag == 5) {
					 query = "update Charts set ";
					 set_q = txt_value1.getText();
					 where_q = txt_value2.getText();
					 query = query + set_q + " where " + where_q +";";
					 stmt.executeUpdate(query);
					 JOptionPane.showMessageDialog(btnOk1, "입력되었습니다" );
				}
			
				System.out.println(query);
			}
	
		}
		
		catch (Exception e2) {
			JOptionPane.showMessageDialog(btnOk1, "오류!!" + e2);
		}
		
	}
	
	
}
