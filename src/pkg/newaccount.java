package pkg;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.mysql.cj.protocol.Resultset;

public class newaccount {

	//public static void main(String[] args) {
		// TODO Auto-generated method stub
	public newaccount(){
		Test l = new Test();
		l.display();	
	}
}

class Test extends JFrame implements ActionListener{
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  //jdbc ����̹� �ּ�
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/java";
								//jdbc:mysql://IP:��Ʈ                   /DB�̸�

	static final String USERNAME = "admin";
	static final String PASSWORD = "root";
	
	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement st = null;//?�����̰�
	
	JLabel l1, l2;
	JTextField id, pw;
	JButton go;
	
	Test(){
		l1 = new JLabel("ID : ");
		l2 = new JLabel("PW : ");
		
		id = new JTextField(6);
		pw = new JTextField(6);
		
		go = new JButton("����");
		
		go.addActionListener(this);
	}
	void display(){
		setLayout(new FlowLayout());
		setTitle("ȸ������");
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,150);
		setFont(new Font("SansSerif", Font.BOLD, 48));
		
		add(l1);add(id);
		add(l2);add(pw);
		add(go);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getSource()==go)
		{
			try{
				Class.forName(JDBC_DRIVER);//JDBC����̹� �ε�
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);//DB����
				stmt = conn.createStatement();
				
				
				String sql;
				
				//���� �Էµ� ���̵�� ������ ���̵� �ִ��� �˻�
				sql = "SELECT * FROM user WHERE user_ID ='" + id.getText() + "'";
				ResultSet rs1 = stmt.executeQuery(sql);
				
				int i=0;
				
				while(rs1.next())
				{
					i++;
				}		
				rs1.close();
				
				//i�� 0�̸� �ߺ��� ���� ����
				if(i==0)
				{	
					if(id.getText().equals("")||pw.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "���̵�� �н����带 �Է����ּ���!");
					}
					else
					{
					String sql1;
					sql1 = "INSERT INTO user VALUES(?,?)";
					st = conn.prepareStatement(sql1);
					st.setString(1, id.getText());
					st.setString(2, pw.getText());
					st.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "ȸ������ �Ϸ�!! ȯ���մϴ�~!");
					this.dispose();
					}
				}
				//i�� 1�̻��̸�(1�̸�)�ߺ��� ���� �ִ°��̱� ������ ���� x
				
				if(i!=0)
				{
					JOptionPane.showMessageDialog(null, "�ߺ��� ���̵� �Դϴ�.");
				}
				stmt.close();
				conn.close();
			}catch(SQLException se1){
				se1.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				try{
					if(stmt!=null)
						stmt.close();
				}catch(SQLException se2){
				}
				try{
					if(conn!=null)
						conn.close();
				}catch(SQLException se){
					se.printStackTrace();
				}
			}

		}
	}
}

