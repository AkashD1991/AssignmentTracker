package AssginmentTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		int tid=Integer.parseInt(req.getParameter("id"));
		String pass=req.getParameter("Pass");
		HttpSession http=req.getSession();
		req.setAttribute("Tid",tid);
		PrintWriter out=res.getWriter();
		res.setContentType("text/html");
		Teacher t=new Teacher();
		t.setId(tid);
		t.setPassword(pass);
		try {
			boolean ans=TrackerDao.Login(t);
			if(ans)
			{
				
			String tname=TrackerDao.getTeacher(tid);
					t.setName(tname);
					req.setAttribute("Tname",tname);
					res.setContentType("text/html");
					out.println("<body>");
					out.println("<form action='View'>");
					out.println("Welcome Teacher: " + t.getName()+"<br><br>");
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
			else
			{
				res.setContentType("text/html");
				out.print("Invalid Login_id and Password");
				RequestDispatcher rd=req.getRequestDispatcher("Login.html");
				rd.include(req,res);
			}
		} catch (ClassNotFoundException | SQLException e) {
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
