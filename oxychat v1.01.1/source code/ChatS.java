import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.*;
class ChatServer extends Thread
{
   ServerSocket server;
   int max,port;
   int messagecount=0;
   String sname;
   public static Vector Messages=new Vector();

   public static void addMessage(String s)
   {
      Messages.addElement(s);
   }

   public static int countMessages()
   {
      return Messages.size();
   }

   public static String readMessage(int i)
   {
      return((String)Messages.elementAt(i));
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

   public static Vector clients=new Vector();
   public static int NOC()
   {
      return clients.size();
   }

   public static void addClient(Client c)
   {
      clients.addElement(c);
   }

   public static void removeClient(Client c)
   {
      clients.removeElement(c);
   }

   public static Client findClient(String un)
   {
      int i,n=clients.size();
      Client c;
      for(i=0;i<n;i++)
      {
          c=(Client)clients.elementAt(i);
          if(c.uname.equals(un))
             break;
      }
      if(i<n)
          return (Client)clients.elementAt(i);
      else
          return null;
   }

   public ChatServer(String n,int p)
   {
      sname=n;
      port=p;
      try
      {
         server=new ServerSocket(port);
      }
      catch(IOException e)
      {
         System.out.println("Error Starting Chat Server..."+e);
      }
      this.start();
      System.out.println("Chat Server "+sname+" Started....");

   }

   public void run()
   {
      Socket s;
      try
      {
			while(true)
			{
				s=server.accept();
				new ClientConnection(s);
			}
      }
      catch(IOException e)
      {
        System.out.println("Server "+sname+" Not Listening....");
      }
   }
}

class Client
{
   String uname;
   Socket skt;
   public Client(String un,Socket s)
   {
     uname=un;
     skt=s;
   }
}

class ClientConnection extends Thread
{
   String sname,uname,pw,mn,msg,p1,p2,p3,msg1,id,hintq,hanswr,hq;
   PrintStream out;
   BufferedReader in;
   Client c1,c2;
   Socket skt;
   int flag,found=0,i,ent;
   Connection con,con1;
   PreparedStatement pstat,stat;
   Statement stat1;
   ResultSet result;
   public ClientConnection(Socket s)
   {
     try
     {
        skt=s;
        out=new PrintStream(s.getOutputStream());
		in=new BufferedReader(new InputStreamReader(s.getInputStream()));
     }
     catch(IOException e)
     {
        
		try
		{
			skt.close();
        }
		catch(IOException e1)
        {
          
		   return;
        }
     }
     this.start();
   }//end of client connection constructor

  public void run()
  {
     boolean exit=false;
     int count=1;
     try
     {     
        outer: while(!exit)
        {
            try
            {
               
		        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                con=DriverManager.getConnection("jdbc:odbc:chat","scott","tiger");
            }
            catch(Exception e)
            {               
                System.out.println("Error: "+e);
            }
            String str=in.readLine();
          
	        if(str==null)
		        break;	        
	       
	        flag=Integer.parseInt(str);
	        System.out.println("flag="+flag);
	        ent=flag;
            while(flag==1) //while1
            {
 		       while(flag==1) //while 2
		       {
                   System.out.println("in while loop");
		           try
		           {
                      msg1=in.readLine();
		              flag=Integer.parseInt(msg1);
		           }
		           catch(NumberFormatException e)
		           {
			           
		           }
		          if(flag==0)
		          {
		             ent=flag;
			           break;
			       }
		          try
		          {
			         stat=con.prepareStatement("Insert into Reg values(?,?,?,?,?,?,?,?)");
			         try
			         {
			              stat.setString(1,in.readLine());
			              stat.setString(2,in.readLine());
			              stat.setString(3,in.readLine());
			              stat.setString(4,in.readLine());
			              stat.setString(5,in.readLine());
			              stat.setString(6,in.readLine());
			              stat.setString(7,in.readLine());
			              stat.setString(8,in.readLine());
			              flag=Integer.parseInt(in.readLine());
			              stat.executeUpdate();
  			        } 
			        catch(Exception e)
			        {
			           
			            count=0;
			        }
                    if(count==0)
			        {
			          out.println(0);
			        }
			        else
			        {
			             out.println(1);
			             count++;
			        }
		        }	
		        catch(Exception e)
                {
                   
			        
		        }
              }//end of inner while
		     flag=Integer.parseInt(in.readLine()); 
		     if(flag==1) 
				 break outer;
             if(flag==12)
				continue;
			 if(flag==2) 
				break;
           }//end of first while
           
        uname=in.readLine();      
          
	    if(uname.equals("forgot"))
	    {	
	       while(ent==0)
	       {
	            
				try
				{					
					i=Integer.parseInt(in.readLine());					
				   
			    }
		        catch(NumberFormatException e)
		        {
			      e.printStackTrace();    
		        }
		   
		        if(i==15)
		        {
					try
					{
						
						id=in.readLine();
						pstat=con.prepareStatement("select * from Reg where uname=?");
						pstat.setString(1,id);
						result=pstat.executeQuery();
						if(result.next())
						{
						    hintq=result.getString(7);
						    hanswr=result.getString(8);
					         found=1;
					       if(found==1)
					       {
							  out.println(1);					
							 
						   }
						   else
							  out.println(0);
					   }
					   else
							out.println(0);
					}
					catch(Exception e)
					{ 
						System.out.println("Exception:"+e); 
					}
				}
				if(i==16)
				{
					System.out.println("out");
				 	try
				    {
					    String hq=in.readLine();
					    String ha=in.readLine();
					   
					    if(hq.equals(hintq))
					    {
					      
							if(ha.equals(hanswr))
							{
								    out.println(6);							    
							}		
							 else
					         {							      
					               out.println(0);
					         }					
					    }
					    else
					    {
							
					        out.println(0);
					    }
					  
			        }
		            catch(NumberFormatException e)
		           {
			           
		            }
		        }
		        		        
		        if(i==17)
		        {
					try
					{
						
						String npwd=in.readLine();
						pstat=con.prepareStatement("update Reg set password=? where uname=?");
						pstat.setString(1,npwd);
						pstat.setString(2,id);
						pstat.executeUpdate();
						out.println(7);
					}
					catch(Exception ex)
					{
						
						out.println(0);
					}
				}       
						
				else if(i==0)
				{
					ent=1;				
				}
	       }  	              
	    }  
	    if(ent==1)
	    {
	      
	      continue;
	    }	   
           if(uname.equals("Continue"))
	    	   continue;
	       pw=in.readLine();
           System.out.println("user is"+uname);
	       Client temp=ChatServer.findClient(uname);
           
	       if(temp!=null)
	       {
              
 		       out.println(3);
		       continue;
	       }
	       try
	       {           
                pstat=con.prepareStatement("select * from Reg where uname=?");		        
		        pstat.setString(1,uname);		       
                result=pstat.executeQuery();                
                if(result.next())
                {
                   String kr=result.getString(2);              
                    boolean stv=pw.equals(kr);                  

                   if(pw.equals(kr))
		           {
                     c1=new Client(uname,skt);
		             ChatServer.addClient(c1);
		             out.println(1);
				   } 
		           else
		          {
		             out.println(2);
		             continue;
		          }
		        }
		        else
		        {
		             out.println(2);
		             continue;
		        }
	        }
	        catch(Exception e)
	        {                             
	            continue;
	        }
	        int s=ChatServer.NOC();
            for(int i=0;i<s;i++)
	        {
				Client ccc;
				PrintStream xout;
				ccc=(Client)ChatServer.clients.elementAt(i);
				xout=new PrintStream(ccc.skt.getOutputStream());
				xout.println("xyz#" +c1.uname);
				xout.println("user#"+c1.uname);
				if(!c1.uname.equals(ccc.uname))
				out.println("user#" +ccc.uname);
			}
			while(true)
			{
				try
				{
					msg=in.readLine();					
				}
				catch(IOException e)
				{					
					continue;
				}
                if(msg.equals(""))
					continue;
				StringTokenizer st=new StringTokenizer(msg,"#");
				p1=st.nextToken();
				if(p1.equals("xyz"))
				{
					p2=st.nextToken();
					out.println("xyz#"+p2);
				}
				else if(p1.equals("close"))
				{
					p2=st.nextToken();
					Client closeclient=(Client)ChatServer.findClient(p2);
					System.out.println("closing "+closeclient.uname);
					ChatServer.removeClient(closeclient);
					s=ChatServer.NOC();
					for(int i=0;i<s;i++)
					{
						Client ccc;
						PrintStream xout;
						ccc=(Client)ChatServer.clients.elementAt(i);
						xout=new PrintStream(ccc.skt.getOutputStream());
						xout.println("removeuser#" +p2);
					}
					break;
				}
				else if(p1.equals("request"))
				{
					p2=st.nextToken();
					c2=(Client)ChatServer.findClient(p2);
					if(c2!=null)
					{
						PrintStream c2out;
						c2out=new PrintStream(c2.skt.getOutputStream());
						c2out.println("requested#" +st.nextToken());
					}
				}
				else if(p1.equals("rejected"))
				{
					p2=st.nextToken();
					c2=(Client)ChatServer.findClient(p2);
					if(c2!=null)
					{
						PrintStream c2out;
						c2out=new PrintStream(c2.skt.getOutputStream());
						c2out.println("rejected#" +c1.uname);
					}
					//if null send client not responding
				}
				else if(p1.equals("start1"))
				{
					p2=st.nextToken();
					PrintStream c2out;
					c2=(Client)ChatServer.findClient(p2);
					c2out=new PrintStream(c2.skt.getOutputStream());
					new Communication(c2,c1.uname);
   					c2out.println("agreed#" +c1.uname);		
				}
				else if(p1.equals("start2"))
				{
					p2=st.nextToken();
					c2=(Client)ChatServer.findClient(p2);
					new Communication(c2,c1.uname);		
				}
				else if(p1.equals("message"))
				{
					ChatServer.addMessage(msg);
				}
           }//end of recent while
        }//end of outer block
     }
     catch(IOException e2)
     {
		
     }
  }// end run
} //end clientconnection class

class Communication extends Thread
{
   PrintStream out;
   String sname,msg1,p1,p2,p3,p4;
   StringTokenizer msg2;
   Client c;
   
   public Communication(Client x,String s)
   {
		c=x;
		sname=s;
		System.out.println(c.uname+sname);
		try
		{
			out=new PrintStream(c.skt.getOutputStream());
		}
		catch(IOException e){}
		this.start();
   }
   public void run()
   {
		while(true)
		{
			int i=ChatServer.countMessages();
			for(int j=0;j<i;j++)
			{
				try
				{
					msg1=ChatServer.readMessage(j);
				}
  				catch(ArrayIndexOutOfBoundsException e)
				{
					
					break;
				}
				msg2=new StringTokenizer(msg1,"#");
				p1=msg2.nextToken();
				p2=msg2.nextToken();
				p3=msg2.nextToken();
				if(p2.equals(c.uname) && p3.equals(sname))
				{
					p4=msg2.nextToken();
					ChatServer.removeMessage(msg1);
					System.out.println("element"+p1+"#"+sname+"#"+p3+"send");
					out.println(msg1);
				}
			}// end for
		} //end while
    } //end run
}//end communication class

public class ChatS
{
	public static void main(String a[])
	{
		ChatServer cs=new ChatServer("HelloServer",5555);
	}
}
