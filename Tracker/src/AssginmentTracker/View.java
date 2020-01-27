package AssginmentTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class View
 */
@WebServlet("/View")
public class View extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String bname=req.getParameter("bid");
		int aid=Integer.parseInt(req.getParameter("aid"));
		int sid=Integer.parseInt(req.getParameter("sid"));
		int n=0;
		int m=0;
		PrintWriter out=res.getWriter();
		int status=0;
		HttpSession http=req.getSession(true);
		http.setAttribute("sid",sid);
		http.setAttribute("aid",aid);
		http.setAttribute("bname",bname);
		String tid=(String) http.getAttribute("Tid");
		String tname=(String) http.getAttribute("Tname");
		
		System.out.println("Teacher name:" + tname);
		
		Student s=new Student();
		try {
			Boolean ans=TrackerDao.Batch(bname);
			if(ans)
			{
				
				s=TrackerDao.CheckBatch(bname,sid);
				res.setContentType("text/html");
	out.println("<form action='Submit'><br><br>");
	out.println("<table border='1'><tr><th>Student_id</th><th>Student_name</th><th>Batch_Name</th><th>Assignment_id</th></tr>");
	out.println("<tr><td>"+s.getSid()+"</td>");
	out.println("<td> "+s.getSname()+"</td>");
	out.println("<td>"+s.getBname()+"</td>");
	out.println("<td> "+aid+"</td></tr>");
	out.println("</table>");
	
	out.println("<table border='1'><tr><th>Qid</th><th>Question Name</th><th>Completed</th><th>Started</th></tr>");
	Set<String> ss=new LinkedHashSet<>();
	 n=1;
	ss=TrackerDao.getAssignment(aid);
	status=TrackerDao.chk(sid,aid,bname);
	if(status==1)
	{
		HashMap<Integer,String> hm=new HashMap<>();
		hm=TrackerDao.getResult(sid,aid,bname);
		for(String s1:ss)
		{
			if(hm.containsKey(n))
			{
		out.println("<tr><td>"+n+"</td>");
		out.println("<td>"+s1+"</td>");
		out.println("<td><input type='radio' name='"+n+"' value='c' checked='checked'></td>");
		out.println("<td><input type='radio' name='"+n+"' value='n'></td></tr>");
		n++;
		 m=n;
		}
		else
		{
		out.println("<tr><td>"+n+"</td>");
		out.println("<td>"+s1+"</td>");
		out.println("<td><input type='radio' name='"+n+"' value='c'></td>");
		out.println("<td><input type='radio' name='"+n+"' value='n'></td></tr>");
		n++;
		 m=n;
		}
		}
		http.setAttribute("m",m);
		out.println("</table>");
		out.println("<input type='Submit' value='Submit' target='Akash'><br><br>");
		out.println("</form>");
		
	}
	else
	{
	for(String s1:ss)
	{
	out.println("<tr><td>"+n+"</td>");
	out.println("<td>"+s1+"</td>");
	out.println("<td><input type='radio' name='"+n+"' value='c'></td>");
	out.println("<td><input type='radio' name='"+n+"' value='n'></td></tr>");
	n++;
	 m=n;
	}
	http.setAttribute("m",m);
	out.println("</table>");
	out.println("<input type='Submit' value='Submit' target='Akash'><br><br>");
	out.println("</form>");
			}
			}
			else
			{
				res.setContentType("text/html");
				out.print("Entered Wrong Batch Name");
				out.println("<body>");
				out.println("<form action='View'>");
				out.println("Welcome Teacher: " + tname+"<br><br>");
				out.println("Enter Batch : ");
				out.println("<input type='text' name='bid' placeholder='Batch_name'><br><br>");
				out.println("Enter Assignment Between 1 to 5 : ");
				out.println("<input type='text' name='aid' placeholder='Assignment No'><br><br>");
				out.println("Enter Student Roll No : ");
				out.println("<input type='text' name='sid' placeholder='Student Roll No'><br><br>");
				out.println("<input type='Submit' value='View' target='Akash'><br><br>");
				out.println("<iframe name='Akash' height='70%' width='100%'>");
				out.println("</iframe></form></body>");	
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
