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

public class Selete_1 extends JFrame implements ActionListener {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Selete_1 frame = new Selete_1();
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
	    System.out.println("검색1 CONNECTION SUCCESFUL\nREADY TO USE PROGRAM");
	  } catch(SQLException e1) {
	      e1.printStackTrace();
		    }
	}
	
	JButton btnOk1,backbtn;
	JRadioButton rd1,rd2,rd3,rd4,rd5;
	JTextArea txtResult, txtStatus;
	JPanel pn1;
	JTextField txt_value1;
	int flag=0;
	
	public Selete_1() {
		setTitle("Selete_1");
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
	    
	    /*btnOk1.addActionListener(new ActionListener() {
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
	    		  
	    		  //System.out.println(flag);
	    	  }
	      
	      });*/
	    
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
					
					String query;
					
					if(flag==0) {
						JOptionPane.showMessageDialog(btnOk1, "버튼을 골라주세요");
					}
					
					else if(flag == 1) {
						 query = "select * from Doctors;";
						 txtResult.setText("");
			             txtResult.setText("");
			        	 txtResult.append("Doctors Table\n");
			        	 txtResult.append("doc_id	major_treat	doc_name	doc_gen	doc_phone	doc_email	doc_position\n");
			             rs = stmt.executeQuery(query);
			             while (rs.next()) {
			                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7)+"\n";
			                txtResult.append(str);
			             }
					}
					
					else if(flag == 2) {
						query = "select * from Treatments;";
						txtResult.setText("");
			             txtResult.append("\nTreatments\n");
			        	 txtResult.append("treat_id	pat_id	doc_id	treat_contents	treat_date\n");
			        	 rs = stmt.executeQuery(query);
			             while (rs.next()) {
			                String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) +"\n";
			                txtResult.append(str);
			             }
					}
					
					else if(flag == 3) {
						query = "select * from Patients;";
						txtResult.setText("");
			             txtResult.append("\nPatients Table\n");
			        	 txtResult.append("pat_id	nut_id	doc_id	pat_name	pat_gen	pat_jumin	par_addr	pat_phone	pat_email	pat_job\n");
			        	 rs = stmt.executeQuery(query);
			             while (rs.next()) {
			                String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) + "\t" + rs.getString(9) + "\t" + rs.getString(10) + "\n";
			                txtResult.append(str);
			             }			
					}
								
					else if(flag == 4) {
						query = "select * from Nurses;";
						txtResult.setText("");
			             txtResult.append("\nNurses Table\n");
			        	 txtResult.append("nur_id	major_id	nur_name	nur_gen	nur_phone	nur_email	nur_position\n");
			        	 rs = stmt.executeQuery(query);
			             while (rs.next()) {
			                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\n";
			                txtResult.append(str);
			             }
					}
								
					else if(flag == 5) {
						query = "select * from Charts;";
						txtResult.setText("");
			             txtResult.append("\nCharts\n");
			        	 txtResult.append("chart_id	treat_id	doc_id	pat_id	nur_id	chart_contents\n");
			        	 rs = stmt.executeQuery(query);
			             while (rs.next()) {
			                String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getInt(5) + "\t" + rs.getString(6) +"\n";
			                txtResult.append(str);
			             }
					}
				
				}
		
			}
			
			catch (Exception e2) {
		         System.out.println("쿼리 읽기 실패 :" + e2);
			}
		
		
	}

}
