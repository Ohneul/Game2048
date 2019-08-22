package pkg;

import static pkg.Login_C.nowid;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class login{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login_C lc = new Login_C();
		lc.display();
	}

}

class Login_C extends JFrame implements ActionListener{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  //jdbc ����̹� �ּ�
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/java";
								//jdbc:mysql://IP:��Ʈ                   /DB�̸�

	static final String USERNAME = "admin";
	static final String PASSWORD = "root";
	
	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement st = null;//?�����̰�
	
	public static boolean load = true;
	public static String nowid;
	public static String[] btnnum = new String[16];
	public static int savenum = 0;
	public static String jumsu;
	public static String kingjumsu;
	
	JLabel l1, l2;
	JTextField id, pw;
	JButton go,signup;
	
	Login_C() {
		l1 = new JLabel("ID : ");
		l2 = new JLabel("PW : ");
		
		id = new JTextField(6);
		pw = new JTextField(6);
		
		go = new JButton("����");
		go.addActionListener(this);
		signup = new JButton("ȸ������");
		signup.addActionListener(this);
	
	}
	
	void display(){
		setLayout(new FlowLayout());
		setTitle("�α���");
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,150);
		setFont(new Font("SansSerif", Font.BOLD, 48));
		
		add(l1);add(id);
		add(l2);add(pw);
		add(go);add(signup);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==signup)
		{
			newaccount l = new newaccount();			
		}
		if(e.getSource()==go)
		{
			try{
				Class.forName(JDBC_DRIVER);//JDBC����̹� �ε�
				conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);//DB����
				System.out.println("\n- DB���� ����");
				stmt = conn.createStatement();//
				
				String king;
				king = "SELECT * FROM rank ORDER BY jumsu DESC LIMIT 1";
				ResultSet rsking = stmt.executeQuery(king);
				while(rsking.next())
				{
					kingjumsu=rsking.getString(2);
				}	
				
				String sql;
				//���� �Էµ� ���̵�� ������ ���̵� �ִ��� �˻�
				sql = "SELECT * FROM user WHERE user_ID ='" + id.getText() +
										"' AND  user_PW ='" + pw.getText() +"'";
				ResultSet rs1 = stmt.executeQuery(sql);
				
				int i=0;
				while(rs1.next())
				{
					i++;
				}		
				rs1.close();
				//i�� 1�̻��̸�(1�̸�)�ߺ��� ���� �ִ°��̱� ������ ���� o
				if(i!=0)
				{
					nowid = id.getText();//�Ѱ��ֱ� ���� ����
					
					//���� �Էµ� ���̵��� ���� �����Ͱ� �ִ��� �˻�
					String sql2;
					sql2 = "SELECT * FROM save WHERE user_ID ='" + nowid + "'";
					ResultSet rs2 = stmt.executeQuery(sql2);	 
					
					int j=0;
					while(rs2.next())
					{
						j++;
					}		
					rs2.close();
					
					//j�� 1 �̻��̸� �ߺ��� ���� �ִ°��̱� ������ ����� ���� o
					if(j!=0)
					{
						
						int result = 0;
						result = JOptionPane.showConfirmDialog(null, "����� �����Ͱ� �ֽ��ϴ�. �ҷ����ðڽ��ϱ�?\n(�ذ�� : �ҷ����� ������ ������ִ� �����ʹ� �����˴ϴ�.)"
								+ "									 ","�ҷ�����",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						// (result ���ϰ�)
						// JOptionPane.YES_OPTION, JOptionPane.NO_OPTION,
						// JOptionPane.CANCEL_OPTION ��
						
						//����� ������ ����
						if(result==JOptionPane.YES_OPTION){
							load=false;
							String sql3;
							sql3 = "SELECT * FROM save WHERE user_ID ='" + id.getText() + "'";
							//������̵�� ����� save�����͸� �޾Ƽ� ��ư�迭�� ����		
							ResultSet rs3 = stmt.executeQuery(sql3);
							
							int x=0;
							while(rs3.next())
							{
								for(int y=0;y<=15;y++)
								{
									btnnum[x]=rs3.getString(x+3);
									x++;
								}
								jumsu = rs3.getString(2);
							}		
							savenum=1;
							rs3.close();
							Core c = new Core();
						}//ó������ ����(���̺� ������ �ʱ�ȭ��)
						if(result==JOptionPane.NO_OPTION){
							String set;
							set = "DELETE FROM save WHERE user_ID='"+ nowid + "'";
							st = conn.prepareStatement(set);
							st.executeUpdate();
							
							Core c = new Core();
						}
					}
					//�ƴ϶�� �׳� ó������ ����
					else if(j==0)
					{
						JOptionPane.showMessageDialog(null,"�α��� ����");
						main main = new main();
					}
					
				}
				//i�� 0�̸� ������ ���⶧���� ���� x
				if(i==0)
				{
					JOptionPane.showMessageDialog(null, "���̵�, ��й�ȣ�� Ȯ�����ּ���");
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