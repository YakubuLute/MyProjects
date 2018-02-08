import javax.swing.tree.*;
import java.io.*;
import javax.swing.border.*;
import java.net.*;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

class MyClient extends JFrame implements ActionListener
{
	int port,p,f,dbrv;
	Socket c;
	BufferedReader in;
	PrintStream out;
	String ip,sname,msg1,msg2,msgu,msgp,sn,flag;
	JPanel mp,haveidp,getidp,actionp,mp2,mp3,sp,xp,pa;
	JLabel lbl1,lbl2,lbl3;
	JTextField txt,txt1,txt3;
	JPasswordField txt2;
	JTextArea ta1,ta2;
	JButton getidb,llog,lcan,fpwd,lhelp,b5,b6,b7,yes,no;
	GridBagLayout g1;
	BorderLayout b1;
    BoxLayout x1,x2;
	GridBagConstraints gc;
	TitledBorder tb1,tb2;

	Border lb1;
	JPanel dialogp;
	JButton dialogok,dialogcan;

	Vector veclist,conlist;
	boolean logflag=false;
	JTree t;
	DefaultMutableTreeNode users,online;
	xyz abc;

	JScrollPane jsp;
	JMenuBar jmb;
	JMenu jcon;
	JMenuItem con,discon,exit;

	MyClient(String i,int p1)
	{
		super("OxyChat");
		ip=i;
		p=p1;
		veclist=new Vector();
		conlist=new Vector();

		mp=new JPanel();
		x1=new BoxLayout(mp,BoxLayout.Y_AXIS);
		mp.setLayout(x1);
		lb1=BorderFactory.createBevelBorder(BevelBorder.RAISED);

		getidp=new JPanel();
		tb1=new TitledBorder("New User");
		tb1.setTitleColor(new Color(107,35,142));
		tb1.setTitleFont(new Font("Arial",Font.BOLD,15));		
		getidb=new JButton("Get Your Id");
		getidb.setBorder(lb1);
		getidb.addActionListener(this);
		getidp.add(getidb);
		getidp.setBackground(new Color(220,230,255));
		getidp.setBorder(tb1);

		tb2=new TitledBorder("Already Have a ID");
		tb2.setTitleColor(new Color(107,35,142));
		tb2.setTitleFont(new Font("Arial",Font.BOLD,15));
		g1=new GridBagLayout();
		gc=new GridBagConstraints();
		lbl1=new JLabel("Login");
		lbl1.setForeground(new Color(107,35,142));
		txt1=new JTextField(20);
		lbl2=new JLabel("Password");
		lbl2.setForeground(new Color(107,35,142));
		txt2=new JPasswordField(20);
		haveidp=new JPanel();
		haveidp.setLayout(g1);
		haveidp.setBorder(tb2);
		haveidp.setBackground(new Color(220,230,255));
		gc.anchor=GridBagConstraints.WEST;
		gc.gridx=1;
		gc.gridy=1;
		g1.setConstraints(lbl1,gc);
		haveidp.add(lbl1);
		gc.gridx=2;
		gc.gridy=1;
		g1.setConstraints(txt1,gc);
		haveidp.add(txt1);

		gc.gridx=1;
		gc.gridy=2;
		g1.setConstraints(lbl2,gc);
		haveidp.add(lbl2);
		gc.gridx=2;
		gc.gridy=2;
		g1.setConstraints(txt2,gc);
		haveidp.add(txt2);

		actionp=new JPanel();
		Border lb=BorderFactory.createEtchedBorder();
		actionp.setBorder(lb);
		llog=new JButton("Login");
		llog.setBorder(lb1);
		llog.addActionListener(this);
		lcan=new JButton("Cancel");
		lcan.setBorder(lb1);
		lcan.addActionListener(this);
		lhelp=new JButton("Help");
		lhelp.setBorder(lb1);
		lhelp.addActionListener(this);
		fpwd=new JButton("Forgot Password");
		fpwd.setBorder(lb1);			
		fpwd.addActionListener(this);		
		actionp.add(llog);
		actionp.add(lcan);
		actionp.add(fpwd);			
		actionp.add(lhelp);

		mp.add(getidp);
		mp.add(haveidp);
		mp.add(actionp);
		this.getContentPane().add(mp);
		this.setResizable(false);
		this.addWindowListener(new WinClose(this));
		this.setSize(300,300);
		this.show();

		b6=new JButton("Connect");
		b6.setBorder(lb1);
		b6.addActionListener(this);
        b7=new JButton("Sign Out");
		b7.setBorder(lb1);
		b7.addActionListener(this);
		mp3=new JPanel();
		mp3.add(b6);
		mp3.add(b7);

		online=new DefaultMutableTreeNode("Online Users");
		online.add(new DefaultMutableTreeNode("None"));
		t=new JTree(online);
		jsp=new JScrollPane(t);
		sp=new JPanel();
		b1=new BorderLayout();
		sp.setBackground(new Color(220,230,255));
		sp.setLayout(b1);
		sp.add("Center",jsp);
		sp.add("South",mp3);
	}//end of myclient constructor

	public void actionPerformed(ActionEvent action)
	{
		String src=action.getActionCommand();
        if(src.equals("Get Your Id"))
		{
			if(c==null)
                 new Connection(ip,p);
            out.println(1);
			RegForm a=new RegForm(c);
			a.setSize(400,400);
			a.show();
		}

		if(src.equals("Help"))
		{
			JOptionPane.showMessageDialog(new JButton(),"Please refer to powerpoint presentattion on OxyChat");
		}

	    if(src.equals("Forgot Password"))	
		{

			if(c==null)
                   new Connection(ip,p);
		    out.println(0);
            out.println("forgot");
			Forgotform f=new Forgotform(c);
			f.setSize(300,300);
			f.show();
		}

        if(src.equals("Login"))
		{

            
			if(c==null)
			new Connection(ip,p);
			out.println(2);

			msgu=txt1.getText();
			msgp=txt2.getText();
            if((msgu.equals(""))||(msgp.equals("")))
			{
				out.println("Continue");
				JOptionPane.showMessageDialog(new JButton(),"login,password must be entered");
			}
			else
			{
				out.println(msgu);
				sname=msgu;
				out.println(msgp);
				try
				{
					msg1=in.readLine();
				}
				catch (IOException e)
				{
					System.out.println(e);
				}
				f=Integer.parseInt(msg1);
				if(f==1)
				{
					logflag=true;
                                      
					abc=new xyz();
                    abc.start();                                      
					this.getContentPane().remove(mp);
					this.getContentPane().add(sp);                                       
					this.setSize(400,400);
					this.show();
					this.setResizable(false);
					this.setTitle(sname);
				}
				else if(f==2)
				{
					JOptionPane.showMessageDialog(new JButton("Ok"),"Login Incorrect");
				}
				else if(f==3)
				{
					JOptionPane.showMessageDialog(new JButton("Ok"),"Username already existed");
				}
				else 
				{
					JOptionPane.showMessageDialog(new JButton("Ok"),"Invalid Username");
				}
			}
          }// end login if
                
		  else if(src.equals("Cancel"))
		  {
			  if(c!=null)
			  {
				  try
				  {
					 out.println(12);
					 c.close();
					 in.close();
					 out.close();
					 System.exit(0);				
				  }
				  catch (Exception e)
                  {
					 System.out.println("Closing"+e);
					 System.exit(0);
				  }
			  }
              try
              {
                 System.exit(0);
              }
              catch(Exception e)
              {
              }
          }//end of cancel if

          else if(src=="Connect")
		  {
				TreePath tp=t.getSelectionPath();
				if(tp!=null)
				{
					String s=tp.getLastPathComponent().toString();
					int n=conlist.size();
                    System.out.println("in 255 "+s+" "+sname);
					int flag=0;
					for(int i=0;i<n;i++)
					{
						String p=(String)conlist.elementAt(i);
                        if(p.equals(s))
						{
							flag=1;
						}
					}
                    if((flag==0)&&(!s.equals("Online Users")) && (!s.equals(sname)))
                        out.println("request#"+s+"#"+sname);
					else
					{
                        if(!s.equals(sname))
							JOptionPane.showMessageDialog(new JButton("fhg"),"Already connected to you");
                    }              
				}
			}// end of connect

            else if(src=="Sign Out")
			{
				try
				{
					out.println("close#"+sname);
					c.close();
					in.close();
					out.close();
					System.exit(0);	
				}
				catch (Exception e)
				{
					System.out.println("Closing"+e);
				}
			}// end of signout if     

          else if(src=="Connect")
		  {
				TreePath tp=t.getSelectionPath();
				if(tp!=null)
				{
					String s=tp.getLastPathComponent().toString();
					int n=conlist.size();
                    System.out.println("in 255 "+s+" "+sname);
					int flag=0;
					for(int i=0;i<n;i++)
					{
						String p=(String)conlist.elementAt(i);
                        if(p.equals(s))
						{
							flag=1;
						}
					}
                    if((flag==0)&&(!s.equals("Online Users")) && (!s.equals(sname)))
                        out.println("request#"+s+"#"+sname);
					else
					{
                        if(!s.equals(sname))
							JOptionPane.showMessageDialog(new JButton("fhg"),"Already connected to you");
                    }              
				}
			}// end of connect

            else if(src=="Sign Out")
			{
				try
				{
					out.println("close#"+sname);
					c.close();
					in.close();
					out.close();
					System.exit(0);	
				}
				catch (Exception e)
				{
					System.out.println("Closing"+e);
				}
			}// end of signout if
       }//action performed closing
  // }
	class xyz extends Thread
	{
		String msg,p1,p2;
		public void run()
		{                        
           	while(true)
			{                                                                                         
				try
				{
					msg=in.readLine();
                    System.out.println(msg);
				}
				catch (IOException e)
				{					
					continue;
				}
				StringTokenizer st=new StringTokenizer(msg,"#");
				p1=st.nextToken();
				p2=st.nextToken();                
                if(p1.equals("xyz"))
				{
                    conlist.removeElement(p2);
				}
                else if(p1.equals("user"))
				{
                    veclist.addElement(p2);
                    update1();
				}
                else if(p1.equals("removeuser"))
				{
					veclist.removeElement(p2);
					conlist.removeElement(p2);
					addMessage("message#"+sname+"#"+p2+"#close");
					update2(p2);
				}
                else if(p1.equals("message"))
				{
					addMessage(msg);
				}
                if(p1.equals("rejected"))
				{
					JOptionPane.showMessageDialog(new JButton("Ok"),p2+"Rejected");
				}
                else if(p1.equals("agreed"))
				{
					out.println("start2#"+p2);
					conlist.addElement(p2);
					new ChatDemo(out,sname,p2);
				}
                else if(p1.equals("requested"))
				{
					int x=JOptionPane.showConfirmDialog(new JPanel(),"Do u want to connect with  "+p2,"java",2);
					if(x==0)
					{
						out.println("start1#"+p2);
						conlist.addElement(p2);
						new ChatDemo(out,sname,p2);
					}
					else
					{
						out.println("rejected#"+p2);
					}
				}
            }//end of while
		}// end of run

		void update1()
		{
			online=new DefaultMutableTreeNode("Online Users");
			for(int i=0;i<veclist.size();i++)
			{
				online.add(new DefaultMutableTreeNode(veclist.elementAt(i)));
			}
			sp.remove(jsp);
			t=new JTree(online);
			jsp=new JScrollPane(t);
			jsp.setForeground(new Color(200,35,35));
			sp.add("Center",jsp);
			show();
			repaint();
		}

		void update2(String s1)
		{
            int n=online.getChildCount();
			for(int i=0;i<n;i++)
			{
				String string=online.getChildAt(i).toString();
                if(string.equals(s1))
					online.remove(i);
			}
			sp.remove(jsp);
			t=new JTree(online);
			jsp=new JScrollPane(t);
			sp.add("Center",jsp);
			show();
			repaint();
		}
	}// end of xyz class

	class Connection
	{
		Connection(String ip,int p)
		{
			try
			{
				c=new Socket(ip,p);
			
			}
			catch (Exception e)
            {       
				System.out.println("hai");
                System.out.println("Error"+e);
			}
			try
			{
				in=new BufferedReader(new InputStreamReader(c.getInputStream()));
				out=new PrintStream(c.getOutputStream());
			}
			catch (IOException e)
			{
				System.out.println(e.getMessage());
				try
				{
					c.close();
				}
                catch (Exception e1)
				{
                    System.out.println(e1.getMessage());
				}
			}
		 }
    }//connection class

    public static Vector Messages=new Vector();

	public static void addMessage(String s)
	{
		 Messages.addElement(s);
		 System.out.println("Message added");
	}

	public static int countMessages()
	{
		return Messages.size();
	}

	public static String readMessage(int i)
	{
		return ((String)Messages.elementAt(i));
	}

    public static void removeMessage(String s)
	{
		Messages.removeElement(s);
	}

	public static int findMessage(String s)
	{
		int i,n=Messages.size();
		String c;
		for(i=0;i<n;i++)
		{
			c=(String)Messages.elementAt(i);
            if(c.equals(s))
				break;
		}
		if(i<n)
			return i;
		else
			return 0;
	}

	class WinClose extends WindowAdapter
	{
		JFrame f;
		WinClose(JFrame f1)
		{
			f=f1;
		}
		public void windowClosing(WindowEvent w)
		{
			if(logflag==false)
			{
				if(c!=null)
				{
					try
					{
						out.println(12);
						c.close();
						in.close();
						out.close();
						System.exit(0);	
					}
					catch (Exception e)
					{
								
					}
					System.exit(0);	
				}
			}
			else
			{
				try
				{
					out.println("close#"+sname);
					c.close();
					in.close();
					out.close();
					System.exit(0);	
				}
				catch (Exception e)
				{
					
				}
			}
         }//window closing                
    }//WinClose adapter class
} // end of myclient class

class ChatDemo extends Thread implements ActionListener
{
	boolean flag=true,togb1,togb2;
	JFrame f;
	JLabel to,from;
	JPanel xp1,mp2,xp,xp2;
	BoxLayout x2;
	JButton b3,b4,b,i,u;
	JToggleButton jtb1,jtb2;
	JTextArea ta1,ta2;
	JComboBox jc,jc1;
	JScrollPane jsp,jsp1;
	JViewport jvp,jvp1;
	Border lb1;
	JTextField totxt,fromtxt;
	PrintStream out;
	String fname,tname,msg1,p1,p2,p3,p4,name="Telugu Lipi";
	StringTokenizer msg2;
	int p=0,q=0,r=0,style=Font.PLAIN,size=15,p5,p6;
	ChatDemo(PrintStream out,String s1,String s2)
	{
		f=new JFrame();
		this.out=out;
		fname=s1;
		tname=s2;
		lb1=BorderFactory.createBevelBorder(BevelBorder.RAISED);
		mp2=new JPanel();
		x2=new BoxLayout(mp2,BoxLayout.Y_AXIS);
		mp2.setLayout(x2);
		xp=new JPanel();
		b3=new JButton("Send");
		b3.setBorder(lb1);
		b3.setMnemonic('S');
		b3.addActionListener(this);
		ta2=new JTextArea(10,10);		
		ta2.setForeground(new Color(107,35,142));		
		ta1=new JTextArea(10,10);		
		ta1.setForeground(new Color(107,35,142));
		b4=new JButton("Close");
		b4.setBorder(lb1);
		b4.setMnemonic('C');
		b4.addActionListener(this);
		xp1=new JPanel();
		JLabel to=new JLabel("To");
		JLabel from=new JLabel("From");
		totxt=new JTextField(10);
		totxt.setText(tname);
		totxt.setEnabled(false);
		fromtxt=new JTextField(10);
            fromtxt.setText(fname);
		fromtxt.setEnabled(false);
		xp1.add(to);
		xp1.add(totxt);
		xp1.add(from);
		xp1.add(fromtxt);
		
		xp2=new JPanel();
		jtb1=new JToggleButton("B",true);
		togb1=true;
		jtb1.addActionListener(this);
		jtb2=new JToggleButton("I",false);
		jtb2.addActionListener(this);	
		xp2.add(jtb1);
		xp2.add(jtb2);	
		
		jc1=new JComboBox();
		jc1.addItem("10");
		jc1.addItem("12");
		jc1.addItem("14");
		jc1.addItem("16");
		jc1.addItem("18");
		jc1.addItem("20");
		jc1.addItem("28");
		jc1.addItem("32");
		jc1.addItem("40");
		xp2.add(jc1);
		jc1.addItemListener(new ItemClass());

		mp2.add(xp1);
		ta1.setEnabled(false);
		ta1.setText("An approach to implement Telugu Chat");
		jsp=new JScrollPane(ta1);
		
		mp2.add(jsp);
		mp2.add(xp2);
		xp.add(b3);
		xp.add(b4);
		jsp1=new JScrollPane(ta2);
		
		mp2.add(jsp1);		
		mp2.add(xp);
		f.getContentPane().add(mp2);
		f.addWindowListener(new WinClose(f));	
		f.setSize(350,450);
		f.show();
		this.start();
		
	}//chatdemo constructor
	
	public void actionPerformed(ActionEvent e)
	{
		String s=e.getActionCommand();
		togb1=jtb1.isSelected();
		togb2=jtb2.isSelected();
		if(togb1 && togb2)
			style=Font.BOLD + Font.ITALIC;		
		else if(togb1 && (!(togb2)))
			style=Font.BOLD;
		else if((!(togb1)) && togb2)
			style=Font.ITALIC;
		else if((!(togb1)) && (!(togb2)))
			style=Font.PLAIN;
		System.out.println("Font="+name);
		ta2.setFont(new Font(name,style,size));
		if(s=="Send")
		{
			out.println("message#"+tname+"#"+fname+"#"+ta2.getText()+"#"+name+"#"+style+"#"+size);
			ta1.setFont(new Font(name,style,size));
			ta1.setText(ta1.getText()+"\n"+fname+":"+ta2.getText());
			ta2.setText("");
		}
		if(s=="Close")
		{
			out.println("message#"+tname+"#"+fname+"#"+"close");
			out.println("xyz#"+tname);
			f.dispose();			
		}
		
	}// end of actionperformed

	public void run() //receiveMessages
	{
		while(true)
		{
			int i=MyClient.countMessages();
			for(int j=0;j<i;j++)
			{
				msg1=MyClient.readMessage(j);
				System.out.println(msg1);
				msg2=new StringTokenizer(msg1,"#");
				p1=msg2.nextToken();
				p2=msg2.nextToken();
				p3=msg2.nextToken();
				String temp=msg2.nextToken();
				
				
                if(p1.equals("message") && p2.equals(fname) && p3.equals(tname))
				{
					MyClient.removeMessage(msg1);
										
                    if(temp.equals("close"))
					{
						
						JOptionPane.showMessageDialog(new JButton("Ok"),tname+"  is Disconnecting.....");
						out.println("xyz#"+tname);
						flag=false;
						f.dispose();
						break;
					}
					else
					{		
						p4=msg2.nextToken();
						p5=Integer.parseInt(msg2.nextToken());						
						p6=Integer.parseInt(msg2.nextToken());
						System.out.println("name="+p4+"\t"+"style="+p5+"\t"+"size="+p6);
						ta1.setFont(new Font(p4,p5,p6));
						ta1.append("\n"+tname+":"+temp);			
						//ta1.setText(ta1.getText()+"\n"+tname+":"+temp);
					}
				}
			}// end for
		}//end while
	}//end run
		class WinClose extends WindowAdapter
	{
		JFrame f;
		WinClose(JFrame f1)
		{
			f=f1;
		}
		public void windowClosing(WindowEvent w)
		{
			out.println("message#"+tname+"#"+fname+"#"+"close");
                      
		}
	}

	class ItemClass implements ItemListener
	{
		public void itemStateChanged(ItemEvent ie)
		{
			String x=(String)ie.getItem();
			if((x=="10") || (x=="12") || (x=="14") || (x=="16") || (x=="18")|| (x=="20")|| (x=="28")|| (x=="32")|| (x=="40"))
				size=Integer.parseInt(x);
			ta2.setFont(new Font(name,style,size));
		}
	}//end itemclass
}//end chatdemo class

class Forgotform extends JFrame implements ActionListener
{
	JPanel pan1,pan2,pan3,pan4;
	JButton submt,cancl,nex,can,fsubmt,fcan;
	JLabel entid,selhq,enthans,npwd,cnpwd;
	BufferedReader in;
	PrintStream out;
	JTextField jtid,jthans;
	JPasswordField jtnpwd,jtcnpwd;
	JComboBox jhq;
	BorderLayout b1;
	int flag=0,i;
	Socket c2;
	Forgotform(Socket c)
	{
		super("Forgot Password");
		c2=c;	
		
		pan1=new JPanel();
		pan2=new JPanel();
		pan3=new JPanel();
		pan4=new JPanel();
		//enter id
		entid=new JLabel("Enter Login id :",JLabel.LEFT);
		entid.setForeground(new Color(255,28,174));

		jtid=new JTextField(20);
		jtid.setBackground(new Color(255,255,255));

		Border lb1=BorderFactory.createBevelBorder(BevelBorder.RAISED);
		submt=new JButton("Submit.");
		submt.setBorder(lb1);	
		submt.addActionListener(this);
		
		cancl=new JButton("Cancel.");
		cancl.setBorder(lb1);	
		cancl.addActionListener(this);

		pan1.add(entid);
		pan1.add(jtid);
		pan1.add(submt);
		pan1.add(cancl);

		//ask for hint ques
		selhq=new JLabel("Select Hint Question :",JLabel.LEFT);
		selhq.setForeground(new Color(255,28,174));

		jhq=new JComboBox();
		jhq.addItem("What is ur pet name?");
		jhq.addItem("What is ur favorite color?");
		jhq.addItem("What is ur favorite holiday spot?");
		
		enthans=new JLabel("Enter Hint Answer :",JLabel.LEFT);
		enthans.setForeground(new Color(255,28,174));

		jthans=new JTextField(20);
		jthans.setBackground(new Color(255,255,255));

		nex=new JButton("Next..");
		nex.setBorder(lb1);	
		nex.addActionListener(this);

		can=new JButton("Cancel..");
		can.setBorder(lb1);	
		can.addActionListener(this);

		pan2.add(selhq);
		pan2.add(jhq);
		pan2.add(enthans);
		pan2.add(jthans);
		pan2.add(nex);
		pan2.add(can);

		npwd=new JLabel("Enter new password :",JLabel.LEFT);
		npwd.setForeground(new Color(255,28,174));
		jtnpwd=new JPasswordField(20);
		jtnpwd.setBackground(new Color(255,255,255));
		cnpwd=new JLabel("Enter Confirm new password :",JLabel.LEFT);
		cnpwd.setForeground(new Color(255,28,174));
		jtcnpwd=new JPasswordField(20);
		jtcnpwd.setBackground(new Color(255,255,255));	

		fsubmt=new JButton("Submit...");
		fsubmt.setBorder(lb1);	
		fsubmt.addActionListener(this);		

		fcan=new JButton("Cancel...");
		fcan.setBorder(lb1);
		fcan.addActionListener(this);

		pan3.add(npwd);
		pan3.add(jtnpwd);
		pan3.add(cnpwd);
		pan3.add(jtcnpwd);
		pan3.add(fsubmt);
		pan3.add(fcan);
		
		pan2.setVisible(false);
		pan3.setVisible(false);
		pan4.add(pan1);
		pan4.add(pan2);
		pan4.add(pan3);		
		this.getContentPane().add(pan4); 	
	
		try
		{
			in=new BufferedReader(new InputStreamReader(c2.getInputStream()));
			out=new PrintStream(c2.getOutputStream());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());			
		}
	}

	public void actionPerformed(ActionEvent ae)
	{
                String src=ae.getActionCommand();
                if(src.equals("Submit."))
				{
					if(jtid.getText().equals(""))
					{
						JOptionPane.showMessageDialog(new JButton("Ok"),"Please Enter login id");
					}
					else
					{
						try
						{                          
                            out.println(15);
							out.println(jtid.getText());											    
						}
						catch (Exception e)
						{
							System.out.println("Error"+e);
						}
												
						try
						{
							flag=Integer.parseInt(in.readLine());
						}
						catch (Exception e1)
						{
							System.out.println("Exception occured"+e1);
						}
						if(flag==1)	//valid user					
						{															
							pan1.setVisible(false);
							pan2.setVisible(true);       
						}
						else if(flag==0) //invalid user
						{
							jtid.setText("");
							JOptionPane.showMessageDialog(new JButton("Ok"),"Invalid login id");
						}
					}
				}
				
			    else if(src.equals("Cancel."))
				{
					System.out.println("Cancel.");
					out.println(0);
					this.dispose();
				}
				
				else if(src.equals("Next.."))
				{
					if(enthans.getText().equals(""))
					{
						JOptionPane.showMessageDialog(new JButton("Ok"),"Enter Hint Answer");						
					}
					else
					{
						try
						{                                                    
                            out.println(16);
                             out.println((String)jhq.getSelectedItem());
							 out.println(jthans.getText());	
																    
						}
						catch (Exception e)
						{
							System.out.println("Error"+e);
						}
						
						try
						{
							i=Integer.parseInt(in.readLine());
						}
						catch (Exception e1)
						{
							System.out.println("Exception occured"+e1);
						}
						
						if(i==6)
						{							
							 pan2.setVisible(false);
							 pan3.setVisible(true);                       
						}
						else if(i==0) //incorrect ansr
						{
							jtid.setText("");
							JOptionPane.showMessageDialog(new JButton("Ok"),"Incorrect answer");
						}
					}
				}
				
				else if(src.equals("Cancel.."))
				{
						out.println(0);
						this.dispose();
				}
				
				else if(src.equals("Submit..."))
				{
					if(jtnpwd.getText().equals("") || (jtcnpwd.getText().equals("")))
					{
						JOptionPane.showMessageDialog(new JButton("Ok"),"Enter all the fields");						
					}
					
					if(jtnpwd.getText().equals(jtcnpwd.getText()))
					{ 	
						  try
						  {                           
                               out.println(17);
							   out.println(jtnpwd.getText());								   									
						  }
						 catch (Exception e)
						 {
							 
						 }
						 try
						{
							i=Integer.parseInt(in.readLine());
						}
						catch (Exception e1)
						{
							
						}
						if(i==7)
						{
							JOptionPane.showMessageDialog(new JButton("Ok"),"Password has been sccessfully changed");
							out.println(0);
							this.dispose();							
						}
						else if(i==0) //incorrect ansr
						{
							jtid.setText("");
							JOptionPane.showMessageDialog(new JButton("Ok"),"Invalid login id");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(new JButton("Ok"),"Incorrect Confirm password");
					}
				}
				
				else if(src.equals("Cancel..."))
				{
					out.println(0);
					this.dispose();
				}
	 } //end of action performed

} //end of forgot pwd

class RegForm extends JFrame implements ActionListener
{
	JPanel rp,sp,mp,rf;
	int flag=0;
	static int count=0;
	JLabel he,un,pw,cp,fn,ln,des,city,hq,ha;
	JTextField uta,fta,lta,dta,cita,hans;	
	BufferedReader in,cin;
	PrintStream out;
	JPasswordField pwd,cpwd;
	JButton breg,bcl,bca;
	JComboBox h;
	BoxLayout b;
	GridBagLayout gb1;
	GridBagConstraints gbc;
	Socket c1;
	RegForm(Socket c)
	{
		super("Register Me");
		c1=c;
		rp=new JPanel();
		sp=new JPanel();
		rf=new JPanel();
		mp=new JPanel();
		he=new JLabel("REGISTRATION FORM",JLabel.CENTER);
		he.setFont(new Font("Arial",Font.ITALIC+Font.BOLD,15));
		he.setForeground(new Color(200,35,35));
		un=new JLabel("USER NAME",JLabel.LEFT);
		un.setForeground(new Color(255,28,174));
		pw=new JLabel("PASSWORD",JLabel.LEFT);
		cp=new JLabel("CONFIRM PASSWORD",JLabel.LEFT);
		cp.setForeground(new Color(35,107,142));
		fn=new JLabel("FIRST NAME",JLabel.LEFT);
		fn.setForeground(new Color(35,142,104));
		ln=new JLabel("LAST NAME",JLabel.LEFT);
		ln.setForeground(new Color(147,47,179));
		des=new JLabel("Designation",JLabel.LEFT);
		des.setForeground(new Color(207,181,59));
		city=new JLabel("City",JLabel.LEFT);
		city.setForeground(new Color(107,35,142));
		hq=new JLabel("Hint Question",JLabel.LEFT);
		hq.setForeground(new Color(35,142,35));
		ha=new JLabel("Hint Answer",JLabel.LEFT);
		ha.setForeground(new Color(200,100,250));
		uta=new JTextField(20);
		uta.setBackground(new Color(255,255,255));
		pwd=new JPasswordField(20);
		cpwd=new JPasswordField(20);
		fta=new JTextField(20);
		lta=new JTextField(20);
		dta=new JTextField(20);
		cita=new JTextField(20);
		hans=new JTextField(20);
		Border lb1=BorderFactory.createBevelBorder(BevelBorder.RAISED);
		breg=new JButton("Register me");
		breg.setBorder(lb1);
		breg.addActionListener(this);
		bcl=new JButton("Clear");
		bcl.setBorder(lb1);
		bcl.addActionListener(this);
		bca=new JButton("Cancel");
		bca.setBorder(lb1);
		bca.addActionListener(this);
		h=new JComboBox();
		h.setBackground(new Color(255,255,255));
		h.setForeground(new Color(23,132,42));
		h.addItem("What is ur pet name?");
		h.addItem("What is ur favorite color?");
		h.addItem("What is ur favorite holiday spot?");
		Border lb=BorderFactory.createEtchedBorder();
		rp.setBorder(lb);
		rp.setBackground(new Color(220,230,255));
		sp.setBorder(lb);
		b=new BoxLayout(mp,BoxLayout.Y_AXIS);
		Border lb2=BorderFactory.createLineBorder(new Color(92,51,23),3);
		mp.setLayout(b);
		mp.setBorder(lb2);
		mp.add(rf);
		mp.add(rp);
		mp.add(sp);
		  gb1=new GridBagLayout();
		  gbc=new GridBagConstraints();
		  gbc.fill=GridBagConstraints.BOTH;
		  rp.setLayout(gb1);
		rf.add(he);
		  gbc.gridx=0;
		  gbc.gridy=1;
		  gb1.setConstraints(un,gbc);
		rp.add(un);
		  gbc.gridx=1;
		  gbc.gridy=1;
		  gb1.setConstraints(uta,gbc);
		rp.add(uta);
 	          gbc.gridx=0;
		  gbc.gridy=2;
		  gb1.setConstraints(pw,gbc);
		rp.add(pw);
		  gbc.gridx=1;
		  gbc.gridy=2;
		  gb1.setConstraints(pwd,gbc);
		rp.add(pwd);
		  gbc.gridx=0;
		  gbc.gridy=3;
		  gb1.setConstraints(cp,gbc);
		rp.add(cp);
		  gbc.gridx=1;
		  gbc.gridy=3;
		  gb1.setConstraints(cpwd,gbc);
		rp.add(cpwd);
		  gbc.gridx=0;
		  gbc.gridy=4;
		  gb1.setConstraints(fn,gbc);
		rp.add(fn);
		  gbc.gridx=1;
		  gbc.gridy=4;
		  gb1.setConstraints(fta,gbc);
		rp.add(fta);
		  gbc.gridx=0;
		  gbc.gridy=5;
		  gb1.setConstraints(ln,gbc);
		rp.add(ln);
		  gbc.gridx=1;
		  gbc.gridy=5;
		  gb1.setConstraints(lta,gbc);
		rp.add(lta);
		  gbc.gridx=0;
		  gbc.gridy=6;
		  gb1.setConstraints(des,gbc);
		rp.add(des);
		  gbc.gridx=1;
		  gbc.gridy=6;
		  gb1.setConstraints(dta,gbc);
		rp.add(dta);
		  gbc.gridx=0;
		  gbc.gridy=7;
		  gb1.setConstraints(city,gbc);
		rp.add(city);
		  gbc.gridx=1;
		  gbc.gridy=7;
		  gb1.setConstraints(cita,gbc);
		rp.add(cita);
		  gbc.gridx=0;
		  gbc.gridy=8;
		  gb1.setConstraints(hq,gbc);
		rp.add(hq);
		  gbc.gridx=1;
		  gbc.gridy=8;
		  gb1.setConstraints(h,gbc);
		rp.add(h);
		  gbc.gridx=0;
		  gbc.gridy=9;
		  gb1.setConstraints(ha,gbc);
		rp.add(ha);
		  gbc.gridx=1;
		  gbc.gridy=9;
		  gb1.setConstraints(hans,gbc);
		rp.add(hans);
		
		sp.add(breg);
		sp.add(bcl);
		sp.add(bca);
		mp.add(rp);
		mp.add(sp);
		this.getContentPane().add(mp);
		try
		{
			in=new BufferedReader(new InputStreamReader(c1.getInputStream()));
			out=new PrintStream(c1.getOutputStream());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());			
		}
	}//end reform constructor

	public void actionPerformed(ActionEvent ae)
	{
        String src=ae.getActionCommand();
        int max=20;
        if(src.equals("Register me"))
		{
            if((uta.getText().equals("")) || (pwd.getText().equals("")) || (fta.getText().equals("")) || (lta.getText().equals("")) || (dta.getText().equals("")) || (city.getText().equals("")) || (hans.getText().equals("")))
			{
				JOptionPane.showMessageDialog(new JButton("Ok"),"All fields must be enetered");
			}
			else
			{
			   if((uta.getText().length()<=max) && (pwd.getText().length()<=max) && (fta.getText().length()<=max) && (lta.getText().length()<=max) && (dta.getText().length()<=max) && (city.getText().length()<=max) && (hans.getText().length()<=max))
               {
                if(pwd.getText().equals(cpwd.getText()))
				{
					try
					{
                        out.println(10);
                        System.out.println("Sending 10");
						out.println(uta.getText());
						out.println(pwd.getText());
						out.println(fta.getText());
						out.println(lta.getText());
						out.println(dta.getText());
						out.println(cita.getText());						
						out.println((String)h.getSelectedItem());
						out.println(hans.getText());
						out.println(1);					
					}
					catch (Exception e)
					{
						System.out.println("Error"+e);
					}
					try
					{
						flag=Integer.parseInt(in.readLine());
					}
					catch (Exception e)
					{
						System.out.println("Exception occured"+e);
					}
					if(flag==1)						
					{
						JOptionPane.showMessageDialog(new JButton("Ok"),"Registration completed successfully");
                        out.println(0);
                        this.dispose();
					}
                    else if(flag==0)                                      
					{
						JOptionPane.showMessageDialog(new JButton("Ok"),"User Already Existed");
					}
				} // pwd are equal
				else
				{					
					JOptionPane.showMessageDialog(new JButton("Ok"),"Invalid Confirm Password");
				}
			   }
			   else
			   {
			      JOptionPane.showMessageDialog(new JButton("Ok"),"Maximum characters allowed is 20"); 
			   }
			}
         }//end of register me
         else if(src.equals("Clear"))
		 {
				uta.setText("");
				pwd.setText("");
				cpwd.setText("");
				fta.setText("");
				lta.setText("");
				dta.setText("");
				cita.setText("");
				hans.setText("");
		 }
         else if(src.equals("Cancel"))
		 {
				out.println(0);
                this.dispose();
		 }
     }//end of action performed
}//end of regform class

class ChatC
{
     public static void main(String[] a)
     {
         MyClient c=new MyClient("192.168.0.5",5555);
     }
}



	
