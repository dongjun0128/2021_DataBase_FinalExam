package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.ComponentOrientation;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JSlider;
import java.awt.List;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Window.Type;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

public class admin extends JFrame implements ActionListener {

	
	JTextArea returnresulttxt=new JTextArea();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					admin frame = new admin();
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
	    System.out.println("초기화 CONNECTION SUCCESFUL\nREADY TO USE PROGRAM");
	  } catch(SQLException e1) {
	      e1.printStackTrace();
	    }
	}

	JButton btnOk1,btnOk2,btnOk3,btnOk4,btnOk5,btnOk6, btn_insert, btnReset,btnreset_table, btn_insert_data;
	JButton btn_delete_data,btn_change_data;
	JTextField txt_value1, txt_value2, txt_value3;
	JTextArea txtResult, txtStatus;
	JPanel pn1, insert_pn;
	JButton btn;
	JButton backbtn;
	JRadioButton rd1;
	
	public admin() {
		setTitle("초기화");
		conDB();
		layInit();
	    setBounds(200, 200, 1000, 600); //가로위치,세로위치,가로길이,세로길이
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    backbtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		backbtn.setBackground(Color.gray);
		backbtn.setBounds(0, 0, 103, 23);
		
	}
	
	public void layInit() {
		  btnOk1 = new JButton("확인");
	      btnOk2 = new JButton("입력/삭제/변경");
	      btnOk3 = new JButton("전체 테이블 보기");
	      backbtn = new JButton("취소");
	      btn_insert = new JButton("insert");
	      btnreset_table=new JButton("table 리셋");
	      btnReset = new JButton("화면초기화");
	      rd1=new JRadioButton("초기화");
	      
	      txtResult = new JTextArea(); //수정 ㄴ
	      txtStatus = new JTextArea(5,30);
	      
	      pn1 = new JPanel();

	      
	      pn1.add(backbtn);
	      //pn1.add(rd1);
	      pn1.add(btnOk1);
	      pn1.add(btnOk3);
	      
	      txtResult.setEditable(false);
	      txtStatus.setEditable(false);
	      JScrollPane scrollPane = new JScrollPane(txtResult);
	      JScrollPane scrollPane2 = new JScrollPane(txtStatus);
	      
	      add("North", pn1);
	      add("Center", scrollPane);
	      add("South", scrollPane2);
	      
	      
	      rd1.addItemListener(null);
	      btnOk1.addActionListener(this);
	      btnOk2.addActionListener(this);
	      btnOk3.addActionListener(this);
	      btnOk1.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent e) {
	    		  if(rd1.isSelected()) {
	    			  System.out.println("입력됐지롱");
	    		  }
	    	  }
	      
	      });
	      
	   }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
	         stmt = con.createStatement();
	         
	         
	         if (e.getSource() == btnOk1) {
	        	

	     		try {
	     			stmt1 = con.createStatement();
	     			String query="DROP SCHEMA IF EXISTS `madang` ;";
	     			stmt1.executeUpdate(query);
	     			
	     			stmt1 = con.createStatement();
	     			query="CREATE SCHEMA IF NOT EXISTS `madang` DEFAULT CHARACTER SET utf8 ;";
	     			stmt1.executeUpdate(query);
	     			
	     			stmt1 = con.createStatement();
	     			query="USE `madang`;";
	     			stmt1.executeUpdate(query);
	     			
	     			query="SET FOREIGN_KEY_CHECKS=0;"; 
	                 stmt1.executeUpdate(query);
	     			
	     			stmt1 = con.createStatement();
	                 query="DROP TABLE IF EXISTS `Doctors`;";
	                  stmt1.executeUpdate(query);
	                  query="DROP TABLE IF EXISTS `Nurses`;";
	                  stmt1.executeUpdate(query);
	                  query="DROP TABLE IF EXISTS `Patients`;";
	                  stmt1.executeUpdate(query);
	                  query="DROP TABLE IF EXISTS `Treatments`;";
	                  stmt1.executeUpdate(query);
	                  query="DROP TABLE IF EXISTS `Charts`;";
	                  stmt1.executeUpdate(query);
	                 
	                  System.out.println("삭제완료!");
	                  
	                  
	                     
	                query="CREATE TABLE IF NOT EXISTS `madang`.`Doctors` (\r\n"
	                		+ "  doc_id	integer not null,\r\n"
	                		+ "  major_treat	varchar(25) not null,\r\n"
	                		+ "  doc_name	varchar(20) not null,\r\n"
	                		+ "  doc_gen char(1) not null,\r\n"
	                		+ "  doc_phone	varchar(15) null,\r\n"
	                		+ "  doc_email	varchar(50) unique,\r\n"
	                		+ "  doc_position varchar(20) not null\r\n"
	                		+ "  );";
	                stmt1.executeUpdate(query);
	                
	                query=" alter table Doctors\r\n"
	                		+ "	add constraint doc_id_pk primary key(doc_id);";
	                stmt1.executeUpdate(query);
	                
	                System.out.println("Doctors 생성완료!");
	                
	                
	                query="CREATE TABLE IF NOT EXISTS `madang`.`Nurses` (\r\n"
	                		+ "	nur_id integer not null,\r\n"
	                		+ "    major_job	varchar(25) not null,\r\n"
	                		+ "    nur_name	varchar(20) not null,\r\n"
	                		+ "    nur_gen	char(1) not null,\r\n"
	                		+ "    nur_phone	varchar(15) null,\r\n"
	                		+ "    nur_email	varchar(50) unique,\r\n"
	                		+ "    nur_position	varchar(20) not null\r\n"
	                		+ "  );";
	                stmt1.executeUpdate(query);
	                
	                query=" alter table Nurses\r\n"
	                		+ "	add constraint nur_id_pk primary key (nur_id);";
	                stmt1.executeUpdate(query);
	               
	                System.out.println("Nurses 생성완료!");
	                
	                
	                query="CREATE TABLE IF NOT EXISTS `madang`.`Patients` (\r\n"
	                		+ "	pat_id	integer not null,\r\n"
	                		+ "    nur_id	integer not null,\r\n"
	                		+ "    doc_id	integer not null,\r\n"
	                		+ "    pat_name	varchar(20) not null,\r\n"
	                		+ "    pat_gen	char(1) not null,\r\n"
	                		+ "    pat_jumin	varchar(14) not null,\r\n"
	                		+ "    par_addr	varchar(100) not null,\r\n"
	                		+ "    pat_phone	varchar(15) null,\r\n"
	                		+ "    pat_email	varchar(50) unique,\r\n"
	                		+ "    pat_job	varchar(20) not null\r\n"
	                		+ ");";
	                stmt1.executeUpdate(query);
	                
	                query="alter table Patients\r\n"
	                		+ "	add constraint pat_id_pk primary key(pat_id);";
	                stmt1.executeUpdate(query);
	                  
	                query="alter table Patients\r\n"
	                		+ "	add (constraint R_2 foreign key (doc_id) references Doctors (doc_id));";
	                stmt1.executeUpdate(query);
	                     
	                query="alter table Patients\r\n"
	                		+ "	add (constraint R_3 foreign key (nur_id) references Nurses (nur_id));";
	                stmt1.executeUpdate(query);
	                        
	                
	                System.out.println("Patients 생성완료!");
	                
	                
	                query="CREATE TABLE IF NOT EXISTS `madang`.`Treatments` (\r\n"
	                		+ "	treat_id	integer not null,\r\n"
	                		+ "    pat_id	integer not null,\r\n"
	                		+ "    doc_id	integer not null,\r\n"
	                		+ "    treat_contents	varchar(1000) not null,\r\n"
	                		+ "    treat_date	date not null  \r\n"
	                		+ "  );";
	                stmt1.executeUpdate(query);
	                
	                query="alter table Treatments\r\n"
	                		+ "	add constraint treat_pat_doc_id_pk primary key(treat_id, pat_id, doc_id);";
	                stmt1.executeUpdate(query);
	                     
	                query="alter table Treatments\r\n"
	                   	+ "	add (constraint R_5 foreign key (pat_id) references Patients (pat_id));";
	                stmt1.executeUpdate(query);
	                        
	                query="alter table Treatments\r\n"
	                   	+ "	add (constraint R_6 foreign key (doc_id) references Patients (doc_id));";
	                stmt1.executeUpdate(query);
	                
	                System.out.println("Treatments 생성완료!");
	                
	                
	                query="CREATE TABLE IF NOT EXISTS `madang`.`Charts` (\r\n"
	                		+ "	chart_id varchar(20) not null,\r\n"
	                		+ "    treat_id	integer not null,\r\n"
	                		+ "    doc_id	integer not null,\r\n"
	                		+ "    pat_id	integer not null,\r\n"
	                		+ "    nur_id	integer not null,\r\n"
	                		+ "    chart_contents	varchar(1000) not null\r\n"
	                		+ "  );";
	                stmt1.executeUpdate(query);
	                
	                query="alter table Charts\r\n"
	                		+ "	add constraint chart_treat_doc_pat_id_pk primary key (chart_id, treat_id, doc_id, pat_id);";
	                stmt1.executeUpdate(query);
	                        
	                query="alter table Charts\r\n"
	             		+ "	add (constraint R_4 foreign key(nur_id) references Nurses (nur_id));";
	                stmt1.executeUpdate(query);
	                           
	                query="alter table Charts\r\n"
	                		+ "	add(constraint R_7 foreign key(treat_id, pat_id, doc_id) references Treatments (treat_id, pat_id, doc_id));";
	                stmt1.executeUpdate(query);
	                
	                System.out.println("Charts 생성완료!");
	                
	                System.out.println("테이블 초기화 완료");
	                 
	                query="INSERT INTO Doctors VALUES(980312, '소아과', '이태정', 'M', '010-333-1340','ltj@hanbh.com','과장');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(000601, '내과', '안성기', 'M', '011-222-0987','ask@hanbh.com','과장');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(001208, '외과', '김민종', 'M', '010-333-8743','kmj@hanbh.com','과장');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(020403, '피부과', '이태서', 'M', '019-777-3764','lts@hanbh.com','과장');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(050900, '소아과', '김연아', 'F', '010-555-3746','kya@hanbh.com','전문의');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(050101, '내과', '차태현', 'M', '011-222-7643','cth@hanbh.com','전문의');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(062019, '소아과', '전지현', 'F', '010-999-1265','jjh@hanbh.com','전문의');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(070576, '피부과', '홍길동', 'M', '016-333-7263','hgd@hanbh.com','전문의');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(080543, '방사선과', '유재석', 'M', '010-222-1263','yjs@hanbh.com','과장');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Doctors VALUES(091001, '외과', '김병만', 'M', '010-555-3542','kbm@hanbh.com','전문의');";
	                stmt1.executeUpdate(query);
	                
	                
	                System.out.println("Doctors 입력완료!");
	                
	                query="INSERT INTO Nurses VALUES(050302, '소아과', '김은영', 'F', '010-555-8751', 'key@hanbh.com', '수간호사');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(050021, '내과', '윤성애', 'F', '016-333-8745', 'ysa@hanbh.com', '수간호사');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(040089, '피부과', '신지원', 'M', '010-666-7646', 'sjw@hanbh.com', '주임');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(070605, '방사선과', '유정화', 'F', '010-333-4588', 'yjh@hanbh.com', '주임');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(070804, '내과', '라하나', 'F', '010-222-1340', 'nhn@hanbh.com', '주임');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(071018, '소아과', '김화경', 'F', '019-888-4116', 'khk@hanbh.com', '주임');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(100356, '소아과', '이선용', 'M', '010-777-1234', 'lsy@hanbh.com', '간호사');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(104145, '외과', '김현', 'M', '010-999-8520', 'kh@hanbh.com', '간호사');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(120309, '피부과', '박성완', 'M', '010-777-4996', 'psw@hanbh.com', '간호사');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Nurses VALUES(130211, '외과', '이서연', 'F', '010-222-3214', 'lsy2@hanbh.com', '간호사');";
	                stmt1.executeUpdate(query);
	                System.out.println("Nurses 입력완료!");
	                
	                
	                query="INSERT INTO Patients VALUES(2345, 050302, 980312, '안상건', 'M', '232345', '서울', '010-555-7845', 'ask@ab.com', '회사원');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(3545, 040089, 020403, '김성룡', 'M', '543545', '서울', '010-333-7812', 'ksr@bb.com', '자영업');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(3424, 070605, 080543, '이종진', 'M', '433424', '부산', '019-888-4859', 'ljj@ab.com', '회사원');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(7675, 100356, 050900, '최광석', 'M', '677675', '당진', '010-222-4847', 'cks@cc.com', '회사원');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(4533, 070804, 000601, '정한경', 'M', '744533', '강릉', '010-777-0214', 'jhk@ab.com', '교수');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(5546, 120309, 070576, '유원현', 'M', '765546', '대구', '016-777-0214', 'ywh@cc.com', '자영업');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(4543, 070804, 050101, '최재정', 'M', '454543', '부산', '010-555-4187', 'cjj@bb.com', '회사원');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(9768, 130211, 091001, '이진희', 'F', '119768', '서울', '010-888-3675', 'ljh@ab.com', '교수');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(4234, 130211, 091001, '오나미', 'F', '234234', '속초', '010-999-6541', 'onm@cc.com', '학생');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Patients VALUES(7643, 071018, 062019, '송성묵', 'M', '987643', '서울', '010-222-5874', 'ssm@bb.com', '학생');";
	                stmt1.executeUpdate(query);
	                System.out.println("Patients 입력완료!");
	                
	                query="INSERT INTO Treatments VALUES(130516023, 2345, 980312, '감기, 몸살', str_to_date('2013-05-16','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(130628100, 3545, 020403, '피부 트러블 치료', str_to_date('2013-06-28','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(131205056, 3424, 080543, '목 디스크로 MRI 촬영', str_to_date('2013-12-05','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(131218024, 7675, 050900, '중이염', str_to_date('2013-12-18','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(131224012, 4533, 000601, '장염', str_to_date('2013-12-24','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(140103001, 5546, 070576, '여드름 치료', str_to_date('2014-01-03','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(140109026, 4543, 050101, '위염', str_to_date('2014-01-09','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(140226102, 9768, 091001, '화상치료', str_to_date('2014-02-26','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(140303003, 4234, 091001, '교통사고 외상치료', str_to_date('2014-03-03','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Treatments VALUES(140308087, 7643, 062019, '장염', str_to_date('2014-03-08','%Y-%m-%d'));";
	                stmt1.executeUpdate(query);
	                System.out.println("Treatments 입력완료!");
	                
	                query="INSERT INTO Charts VALUES('000001',130516023, 980312, 2345, 71018, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000002',130628100, 20403, 3545, 40089, '레이저치료');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000003',131205056, 80543, 3424, 70605, '물리치료');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000004',131218024, 50900, 7675, 50302, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000005',131224012, 601, 4533, 50021, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000006',140103001, 70576, 5546, 120309, '레이저치료');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000007',140109026, 50101, 4543, 50021, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000008',140226102, 91001, 9768, 104145, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000009',140303003, 91001, 4234, 104145, '수술');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000010',140308087, 62019, 7643, 71018, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000011',130628100, 20403, 3545, 40089, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000012',131205056, 80543, 3424, 70605, '도수치료');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000013',140103001, 70576, 5546, 120309, '약처방');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000014',140303003, 91001, 4234, 104145, '재활치료');";
	                stmt1.executeUpdate(query);
	                query="INSERT INTO Charts VALUES('000015',140303003, 91001, 4234, 104145, '작업치료');";
	                stmt1.executeUpdate(query);
	                System.out.println("Treatments 입력완료!");
	                
	                System.out.println("초기화 완료!");
	                
	                JOptionPane.showMessageDialog(btnOk1,"Table 초기화 완료!");
	                
	             }
	     		
	     		catch(Exception Insert) {
	                System.out.println("데이터 입력에 오류 발생!\n"+Insert);
	             }
	             
	     	
	        		
	     	}
	        	 
	         
	         else if (e.getSource() == btnOk3) {
	        	 
	        	 String query = "select * from Doctors;";
	             txtResult.setText("");
	        	 txtResult.append("Doctors Table\n");
	        	 txtResult.append("doc_id	major_treat	doc_name	doc_gen	doc_phone	doc_email	doc_position\n");
	             rs = stmt.executeQuery(query);
	             while (rs.next()) {
	                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7)+"\n";
	                txtResult.append(str);
	             }
	             
	             query = "select * from Nurses;";
	             
	             txtResult.append("\nNurses Table\n");
	        	 txtResult.append("nur_id	major_job	nur_name	nur_gen	nur_phone	nur_email	nur_position\n");
	        	 rs = stmt.executeQuery(query);
	             while (rs.next()) {
	                String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\n";
	                txtResult.append(str);
	             }
	             
	             
	             query = "select * from Patients;";
	             
	             txtResult.append("\nPatients Table\n");
	        	 txtResult.append("pat_id	nut_id	doc_id	pat_name	pat_gen	pat_jumin	par_addr	pat_phone	pat_email	pat_job\n");
	        	 rs = stmt.executeQuery(query);
	             while (rs.next()) {
	                String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t" + rs.getString(8) + "\t" + rs.getString(9) + "\t" + rs.getString(10) + "\n";
	                txtResult.append(str);
	             }
	             
	             
	             query = "select * from Treatments;";
	             
	             txtResult.append("\nTreatments\n");
	        	 txtResult.append("treat_id	pat_id	doc_id	treat_contents	treat_date\n");
	        	 rs = stmt.executeQuery(query);
	             while (rs.next()) {
	                String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5) +"\n";
	                txtResult.append(str);
	             }
	             
	        
	             query = "select * from Charts;";
	             
	             txtResult.append("\nCharts\n");
	        	 txtResult.append("chart_id	treat_id	doc_id	pat_id	nur_id	chart_contents\n");
	        	 rs = stmt.executeQuery(query);
	             while (rs.next()) {
	                String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getInt(5) + "\t" + rs.getString(6) +"\n";
	                txtResult.append(str);
	             }
	             
	          }
	         
	         else if (e.getSource() == btnReset) {
	            txtResult.setText("");
	         }
	         
	        
	         
	         
	      } catch (Exception e2) {
	         System.out.println("쿼리 읽기 실패 :" + e2);
	 }  
	}
}
