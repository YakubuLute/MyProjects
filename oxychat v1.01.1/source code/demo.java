import java.sql.*;

class demo{

	public static void main(String args[])
	{
	try
         { Connection con;            
		        		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                	con=DriverManager.getConnection("jdbc:odbc:chat","scott","tiger");
	if(con!=null)
		System.out.println("Connection Suceed");
            
	else System.out.println("Connection Failed"); 
           }catch(Exception e)
            {               
                System.out.println("Error: "+e);
            }
}}