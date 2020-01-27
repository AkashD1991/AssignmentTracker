package AssginmentTracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class TrackerDao 
{
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Connection con=null;
		
			Class.forName("com.mysql.cj.jdbc.Driver");
		
			con=DriverManager.getConnection
					("jdbc:mysql://localhost:3306/assginmenttracker","root","root");
		
		return con;
	}
	public static Boolean Login(Teacher t) throws ClassNotFoundException, SQLException 
	{
		Connection con=TrackerDao.getConnection();
		
			PreparedStatement pt=con.prepareStatement("select Teacher_id,Password from teacher where teacher_id=?");
			pt.setInt(1,t.getId());
			ResultSet rs=pt.executeQuery();
			
			while(rs.next())
			{
				int id=rs.getInt(1);
				String pass=rs.getString(2);
			if(id==t.getId() && pass.equals(t.getPassword()))
			{
				return true;
			}
			}
			return false;

	}
	public static Boolean Batch(String bname) throws ClassNotFoundException, SQLException
	{
		Connection con=TrackerDao.getConnection();
		
		PreparedStatement pt=con.prepareStatement("select * from batch");
		ResultSet rs=pt.executeQuery();
		
		while(rs.next())
		{
			String name=rs.getString(2);
			if(bname.equals(name))
			{
				return true;
			}
		}
		return false;
	}
	public static Student CheckBatch(String bname, int sid) throws ClassNotFoundException, SQLException
	{
		Connection con=TrackerDao.getConnection();
		
		PreparedStatement pt=con.prepareStatement("select * from batch");
		ResultSet rs=pt.executeQuery();
		Student s=new Student();
		while(rs.next())
		{
			int id=rs.getInt(1);
			String name=rs.getString(2);
			if(bname.equals(name))
			{	
				PreparedStatement pt1=con.prepareStatement("select * from Student");
				ResultSet rs1=pt1.executeQuery();
				while(rs1.next())
				{
					String sname=rs1.getString(2);
					int Batch_id=rs1.getInt(3);
					if(Batch_id==id)
					{
						s.setSid(sid);
						s.setSname(sname);
						s.setBid(id);
						s.setBname(bname);
					}
				}
			}
		}
		return s;
	}
	public static String getTeacher(int tid) throws ClassNotFoundException, SQLException
	{
		Connection con=TrackerDao.getConnection();
		
		PreparedStatement pt=con.prepareStatement("select * from teacher where teacher_id=?");
		pt.setInt(1,tid);
		ResultSet rs=pt.executeQuery();
		String tname = null;
		while(rs.next())
		{
			int id=rs.getInt(1);
			 tname=rs.getString(2);
		if(tid==id)
		{
			return tname;
		}
		}
		return tname;		
	}
	public static Set<String> getAssignment(int aid) throws IOException
	{
		String s=Integer.toString(aid);
		File file = new File("C:\\Users\\Aaksj\\Desktop\\Bank\\"+s+".xlsx");   //creating a new file instance  
		FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
		//creating Workbook instance that refers to .xlsx file  
		XSSFWorkbook wb = new XSSFWorkbook(fis);   
		XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
		Iterator<Row> itr = sheet.iterator(); //iterating over excel file
				
				Set<String> ss=new LinkedHashSet<>();
				
		while (itr.hasNext())                 
		{  
		Row row = itr.next();  
		Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
		while (cellIterator.hasNext())   
		{  
		Cell cell = cellIterator.next();  
		switch (cell.getCellType())               
		{  
		case Cell.CELL_TYPE_STRING:    //field that represents string cell type  
		String value=cell.getStringCellValue();
		ss.add(value);
		break;  
		case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type  
		int key=(int) cell.getNumericCellValue();  
		break;  
		default:  
		}  
		}  
		System.out.println("");  
		}
		

		
		return ss;   
	}
	public static int Update(int sid, int aid, String bname, HashMap<Integer, String> hm) throws ClassNotFoundException, SQLException
	{
		Connection con=TrackerDao.getConnection();
		HashMap<Integer,String> hm1=new HashMap<>();
		PreparedStatement pt1=con.prepareStatement("select Qid,Status from studentresult where Sid=? and Aid=? and Bname=?");
		pt1.setInt(1,sid);
		pt1.setInt(2,aid);
		pt1.setString(3,bname);
		ResultSet rs=pt1.executeQuery();
		while(rs.next())
		{
			hm1.put(rs.getInt(1),rs.getString(2));
		}
		Set<Entry<Integer,String>> s=hm.entrySet();
		int status=0;
		for(Entry<Integer,String> e:s)
		{
			if(! hm1.containsKey(e.getKey()))
			{
			PreparedStatement pt=con.prepareStatement("INSERT INTO studentresult(Sid,Aid,Bname,Qid,Status) VALUES (?,?,?,?,?)");
			pt.setInt(1,sid);
			pt.setInt(2,aid);
			pt.setString(3,bname);
			pt.setInt(4,e.getKey());
			pt.setString(5,e.getValue());
			if(e.getValue().compareTo("c")==0)
			{
			status=pt.executeUpdate();
			}
			}
		}
		
		
		return status;
	}
	public static int chk(int sid, int aid, String bname) throws ClassNotFoundException, SQLException 
	{
		Connection con=TrackerDao.getConnection();
		PreparedStatement pt=con.prepareStatement("select * from studentresult");

		ResultSet rs=pt.executeQuery();
		int c=0;
		while(rs.next())
		{
			if(rs.getInt(1)==sid && rs.getInt(2)==aid && rs.getString(3).compareTo(bname)==0)
			{
				c++;
				break;
			}
			
		}
		return c;
		
	}
	public static HashMap<Integer, String> getResult(int sid, int aid, String bname) throws ClassNotFoundException, SQLException 
	{
		Connection con=TrackerDao.getConnection();
		PreparedStatement pt=con.prepareStatement("select Qid,Status from studentresult where Sid=? and Aid=? and Bname=?");
		pt.setInt(1,sid);
		pt.setInt(2,aid);
		pt.setString(3,bname);
		HashMap<Integer,String> hm=new HashMap<>();
		ResultSet rs=pt.executeQuery();
		int c=0;
		while(rs.next())
		{
			hm.put(rs.getInt(1),rs.getString(2));
		}
		return hm;
	}
	
}
