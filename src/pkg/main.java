package pkg;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

import javax.jws.soap.InitParam;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import static pkg.Login_C.nowid;


public class main{
      
   //public static void main(String[] args) {
      // TODO Auto-generated method stub
	public main(){
      Core c = new Core(); 
   }
}
class Core extends JFrame implements FocusListener{
	Game g = new Game();
	Login_C lc = new Login_C();

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  //jdbc ����̹� �ּ�
	static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/java";
                        //jdbc:mysql://IP:��Ʈ                   /DB�̸�

	static final String USERNAME = "admin";
	static final String PASSWORD = "root";
   
	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement st = null;
	   
	int count = 0;
    int up=0,down=0,left=0,right=0;
    int temp3 = -1, temp4 = -1;
    int x=0;
    boolean move = true;
    String[] save = new String[16];
    
    
	Core(){
		
		for(int i=0; i < 16; i++)
		{
			g.btn[i].addFocusListener(this);
		}
		if(lc.load){
			int temp2 = 0;      
		    
		    g.btn[random()].setText("2");
		    for(int i = 0; i < 16; i++)
		    {
		       if(g.btn[i].getText().equals("2"))
		       {
		          temp2 = i;
		       }
		    }
		    while(true)
		    {
		       int a = random();
		       if(a!=temp2)
		       {
		          g.btn[a].setText("2");
		          break;
		       }
		    }
		}
	    g.requestFocus();

	    g.addKeyListener(new KeyListener() {
	       
	       @Override
	       public void keyTyped(KeyEvent e) {
	          // TODO Auto-generated method stub
	       }
	       
	       @Override
	       public void keyReleased(KeyEvent e) {
	          // TODO Auto-generated method stub
	       }
	       
	       @Override
	       public void keyPressed(KeyEvent e) {
	          // TODO Auto-generated method stub
	       
	          if(up+down+left+right==4) 
	          {
	          	try{
	          	Class.forName(JDBC_DRIVER);//JDBC����̹� �ε�
	            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);//DB����
	            stmt = conn.createStatement();
	          	
	            String gameover;
	            gameover = "INSERT INTO rank VALUES(?,?)";
	            
				st = conn.prepareStatement(gameover);
				st.setString(1, nowid);
				st.setInt(2, Integer.parseInt(g.jumsu.getText()));
				st.executeUpdate();
					
				String savecheck;
				savecheck = "SELECT * FROM save WHERE user_ID ='" + nowid + "'";
				
				ResultSet rs = stmt.executeQuery(savecheck);	 
				int s=0;
				while(rs.next())
				{
					s++;
				}		
				rs.close();
				
				if(s==1){
					String set;
					set = "DELETE FROM save WHERE user_ID='"+ nowid + "'";
					st = conn.prepareStatement(set);
					st.executeUpdate();
				}
	            stmt.close();
	            conn.close();
	            
	            JOptionPane.showMessageDialog(null,"���� ����! ���� : "+g.jumsu.getText());
	            Rankview rv = new Rankview();
	            rv.display();
	            g.dispose();
	            
	            
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
	          
	          for(int i = 0; i<16; i++)
	          {
	             save[i]=g.btn[i].getText();
	          }
	          int keycode = e.getKeyCode();
	          
	          System.out.println(keycode);
	             switch(keycode)
	             {
	             case KeyEvent.VK_UP: 
	             //0  1  2  3
	             //4  5  6  7
	             //8  9  10 11
	             //12 13 14 15
	                
	                for(int j =4; j <= 7; j++)
	                {
	                  for(int i = j; i <= j+8; i=i+4)
	                  {
	                     if(!g.btn[i].getText().equals(""))
	                     {
	                        if(g.btn[i-4].getText().equals(""))
	                        {
	                           g.btn[i-4].setText(g.btn[i].getText());
	                           g.btn[i].setText("");
	                           i = j-4;
	                        }
	                        else
	                        {
	                           if(g.btn[i-4].getText().equals(g.btn[i].getText()))
	                           {
	                              g.btn[i-4].setText(Integer.toString(Integer.parseInt(g.btn[i-4].getText())*2));
	                              g.jumsu.setText(Integer.toString(Integer.parseInt(g.jumsu.getText())+Integer.parseInt(g.btn[i-4].getText())));
	                              g.btn[i].setText("");
	                              i = i-4;
	                           }
	                        }
	                     }
	                  }
	                }
	                
	                //��
	                x=0;
	                for(int i=0; i<16;i++)
	                {
	                   if(!save[i].equals(g.btn[i].getText()))
	                   {
	                      up=0;
	                      down=0;
	                      left=0;
	                      right=0;
	                      move=true;
	                      break;
	                   }
	                   x++;
	                }
	                if(x==16)
	                {
	                   up=1;
	                   move=false;
	                }
	                
	                
	                break;
	                
	             case KeyEvent.VK_DOWN:  
	                //0  1  2  3
	             //4  5  6  7
	             //8  9  10 11
	             //12 13 14 15
	                
	                for(int j = 8; j <=11 ; j++)
	               {
	                  for(int i = j; i >=j-8; i=i-4)
	                  {
	                     if(!g.btn[i].getText().equals(""))
	                     {
	                        if(g.btn[i+4].getText().equals(""))
	                        {
	                           g.btn[i+4].setText(g.btn[i].getText());
	                           g.btn[i].setText("");
	                           i = j+4;
	                        }
	                        else
	                        {
	                           if(g.btn[i+4].getText().equals(g.btn[i].getText()))
	                           {
	                              g.btn[i+4].setText(Integer.toString(Integer.parseInt(g.btn[i+4].getText())*2));
	                              g.jumsu.setText(Integer.toString(Integer.parseInt(g.jumsu.getText())+Integer.parseInt(g.btn[i+4].getText())));
	                              g.btn[i].setText("");
	                              i = i+4;
	                           }
	                        }
	                     }
	                  }
	               }
	                x=0;
	                for(int i=0; i<16;i++)
	                {
	                   if(!save[i].equals(g.btn[i].getText()))
	                   {
	                  	 up=0;
	                       down=0;
	                       left=0;
	                       right=0;
	                      move=true;
	                      break;
	                   }
	                   x++;
	                }
	                if(x==16)
	                {
	                   down=1;
	                   move=false;
	                }
	                break;
	                
	             case KeyEvent.VK_LEFT:  
	                //0  1  2  3
	             //4  5  6  7
	             //8  9  10 11
	             //12 13 14 15
	                
	                for(int i = 1; i <= 13; i=i+4)      //�Ʒ��� �̿� ����� ��Ʈ�� ����ϸ�� 
	               {
	                  for(int j = i; j <= i+2; j++)
	                  {   //0�� �ƴҶ��� �˻�
	                     if(g.btn[j-1].getText().equals(""))
	                     {   //����ĭ�� 0���� �˻�
	                        if(!g.btn[j].getText().equals(""))
	                        {
	                           g.btn[j-1].setText(g.btn[j].getText());
	                           g.btn[j].setText("");
	                           j=i-1;
	                        }
	                     }
	                     
	                     else 
	                     {
	                        if(g.btn[j-1].getText().equals(g.btn[j].getText()))
	                        {
	                           g.btn[j-1].setText(Integer.toString(Integer.parseInt(g.btn[j-1].getText())*2));
	                           g.jumsu.setText(Integer.toString(Integer.parseInt(g.jumsu.getText())+Integer.parseInt(g.btn[j-1].getText())));
	                           g.btn[j].setText("");
	                           j = j-1;
	                        }
	                     }
	                  }
	               }
	                x=0;
	                for(int i=0; i<16;i++)
	                {
	                   if(!save[i].equals(g.btn[i].getText()))
	                   {
	                  	 up=0;
	                       down=0;
	                       left=0;
	                       right=0;
	                      move=true;
	                      break;
	                   }
	                   x++;
	                }
	                if(x==16)
	                {
	                   left=1;
	                   move=false;
	                }
	                break;
	                
	             case KeyEvent.VK_RIGHT: 
	                //0  1  2  3
	             //4  5  6  7
	             //8  9  10 11
	             //12 13 14 15
	                for(int i = 2; i <= 14; i=i+4)
	               {
	                  for(int j = i; j >= i-2; j--)
	                  {
	                    //System.out.println("1)i��° j�� : "+i+"|"+j);
	                     if(g.btn[j+1].getText().equals(""))
	                     {   //����ĭ�� 0���� �˻�
	                        if(!g.btn[j].getText().equals(""))
	                        {
	                           g.btn[j+1].setText(g.btn[j].getText());
	                           g.btn[j].setText("");
	                           //System.out.println("2)i��° j�� : "+i+"|"+j);
	                           j=i+1;
	                        }
	                     }
	                     else 
	                     {
	                        if(g.btn[j+1].getText().equals(g.btn[j].getText()))
	                        {
	                           g.btn[j+1].setText(Integer.toString(Integer.parseInt(g.btn[j+1].getText())*2));
	                           g.jumsu.setText(Integer.toString(Integer.parseInt(g.jumsu.getText())+Integer.parseInt(g.btn[j+1].getText())));
	                           g.btn[j].setText("");
	                           //System.out.println("3)i��° j�� : "+i+"|"+j);
	                           j = j+1;
	                        }
	                     }
	                  }
	               }
	                x=0;
	                for(int i=0; i<16;i++)
	                {
	                   if(!save[i].equals(g.btn[i].getText()))
	                   {
	                  	 up=0;
	                       down=0;
	                       left=0;
	                       right=0;
	                      move=true;
	                      break;
	                   }
	                   x++;
	                }
	                if(x==16)
	                {
	                   right=1;
	                   move=false;
	                }
	             }
	             //����ġ ��
	             if(move==true)
	             {
	                int count = 0;
	                for(int i = 0; i < 16; i++)
	                {
	                   if(g.btn[i].getText().equals(""))
	                   {
	                      
	                      count++;
	                   }
	                }
	                int[] temp = new int[count];
	                count = 0;
	                for(int i = 0; i < 16; i++)
	                {
	                   
	                   if(g.btn[i].getText().equals(""))
	                   {
	                      temp[count] = i;
	                      count++;
	                   }
	                }
	                if(keycode==KeyEvent.VK_UP||keycode==KeyEvent.VK_DOWN||keycode==KeyEvent.VK_LEFT||keycode==KeyEvent.VK_RIGHT){
		                temp3 = (int)(Math.random() * count);
		                temp4 = temp[temp3];
		                g.btn[temp4].setText("2");
	                }
	             }
	       }
	    });
	    //nowjumsu();
	    g.display();
	}
	public static int random() {
	       return (int)(Math.random() * 16); 
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		g.requestFocus();
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

class Game extends JFrame implements ActionListener{
   Login_C lc = new Login_C();
   
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  //jdbc ����̹� �ּ�
   static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/java";
                        //jdbc:mysql://IP:��Ʈ                   /DB�̸�

   static final String USERNAME = "admin";
   static final String PASSWORD = "root";
   
   static Connection conn = null;
   static Statement stmt = null;
   static PreparedStatement st = null;
   
   JMenuBar jmb;
   JMenu mFile, mRank;
   JMenuItem iSave, iRank;   
   
   JButton[] btn = new JButton[16];
   //JButton test;
   JLabel l1,king,l2,jumsu;
   JPanel p,score;
   Font s,f;
   Random rnd = new Random();
   int a,b;
   
   Game(){
      jmb = new JMenuBar();
      mFile = new JMenu("����");  
      mRank = new JMenu("����");
      iSave = new JMenuItem("����");
      iRank = new JMenuItem("��ŷ");
      iSave.addActionListener(this);
      iRank.addActionListener(this);
      
      //test = new JButton("��β����ư");
      l1 = new JLabel("��ŷ 1�� : ");
      king = new JLabel("0");
      l2 = new JLabel("���� ���� : ");
      jumsu = new JLabel("0");
      s = new Font("SansSerif", Font.BOLD, 30);
      f = new Font("SansSerif", Font.BOLD, 48);
      p = new JPanel(new GridLayout(4,5,5,5));
      score = new JPanel(new GridLayout(1,4,5,5));
      
      king.setText(lc.kingjumsu);
      //��ư�� ���ڸ� �������ִ°�
      if(lc.savenum==0){
         for(int i=0;i<=15;i++) {
            btn[i]=new JButton("");
            btn[i].setFont(f);
            p.add(btn[i]);
         }
         System.out.println("����:0");
      }
      else if(lc.savenum==1)
      {
         for(int i=0;i<=15;i++) {
            btn[i]=new JButton(lc.btnnum[i]);
            btn[i].setFont(f);
            p.add(btn[i]);
         }
         jumsu.setText(lc.jumsu);
         System.out.println("����:1");
      }
   }
   void display(){
      setLayout(new BorderLayout(5,20));
      setTitle("�÷��̾� : "+ lc.nowid);
      setVisible(true);
      //setDefaultCloseOperation(EXIT_ON_CLOSE);
      setSize(700,700);
      setFont(new Font("SansSerif", Font.BOLD, 48));
      
      //test.setFont(f);
      //test.addActionListener(this);
      l1.setFont(s);king.setFont(s);
      l2.setFont(s);jumsu.setFont(s);
      score.add(l1);
      score.add(king);
      score.add(l2);
      score.add(jumsu);
      
      mFile.add(iSave);mRank.add(iRank);
      jmb.add(mFile);jmb.add(mRank);
      add(jmb);
      setJMenuBar(jmb);
      
      add(p, BorderLayout.CENTER);
      //add(test, BorderLayout.SOUTH);
      add(score, BorderLayout.NORTH);
   }

   public void actionPerformed(ActionEvent e) {
      Login_C lc = new Login_C();
      
      //�����ư
      /*
      if(e.getSource()==test){
         System.exit(0);
         //this.dispose();   
      }
      */
      //��ŷ��ư
      if(e.getSource()==iRank){
    	  Rankview rv = new Rankview();
    	  rv.display();
      }
      //�����ư
      if(e.getSource()==iSave){
         try{
         Class.forName(JDBC_DRIVER);//JDBC����̹� �ε�
         conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);//DB����
         stmt = conn.createStatement();

         //����� ������ ���� �ʿ�
         String checksql;
         checksql = "SELECT * FROM save WHERE user_ID='"+nowid+"'";
         ResultSet rs = stmt.executeQuery(checksql);
         int j=0;
         while(rs.next())
         {
            j++;
         }
         //����ID�� �������� ã�ƿ� ���� 0�� �ƴϸ� �����
         
         if(j!=0)
         {
            int result = 0;
            result = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�? ������ִ� �����ʹ� �����˴ϴ�.","����",JOptionPane.YES_NO_OPTION);
            
            if(result==JOptionPane.YES_OPTION)
            {
               String set;
               set = "DELETE FROM save WHERE user_ID='"+ nowid + "'";
               st = conn.prepareStatement(set);
               st.executeUpdate();
               
               String sql1;
               sql1 = "INSERT INTO save VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
               st = conn.prepareStatement(sql1);
               
               st.setString(1, lc.nowid);
               st.setString(2, jumsu.getText());
               
               int x=3;//3������ ��ư[0]~[15]����
               for(int i=0;i<=15;i++){
                  st.setString(x, btn[i].getText());
                  x++;
               }
               st.executeUpdate();
               
               JOptionPane.showMessageDialog(null, "���� ����");
            }
            if(result==JOptionPane.NO_OPTION)
            {   
            }
         }
         //ã�ƿ°��� 0�̸� �ٷ�����
         else if(j==0)
         {
            String sql1;
            sql1 = "INSERT INTO save VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            st = conn.prepareStatement(sql1);
            st.setString(1, lc.nowid);
            st.setString(2, jumsu.getText());
            
            int x=3;//3������ ��ư[0]~[15]����
            for(int i=0;i<=15;i++){
               st.setString(x, btn[i].getText());
               x++;
            }
            st.executeUpdate();
            System.out.println(lc.nowid);
            JOptionPane.showMessageDialog(null, "���� ����");
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