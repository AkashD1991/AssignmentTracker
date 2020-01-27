package AssginmentTracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Submit
 */
@WebServlet("/Submit")
public class Submit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Submit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		PrintWriter out=res.getWriter();
		HttpSession http=req.getSession(true);
		int ac=(int) http.getAttribute("m");
		int status=0;
		int sid=(int) http.getAttribute("sid");
		int aid=(int) http.getAttribute("aid");
		String bname=(String) http.getAttribute("bname");
		HashMap<Integer,String> hm=new HashMap<>();
		for(int i=1;i<ac;i++)
		{
			String s=req.getParameter(""+i);
			System.out.println("string s "+s);
			if(s.compareToIgnoreCase("c")==0)
			{
			hm.put(i,s);
			}
		}
			try {
				status=TrackerDao.Update(sid,aid,bname,hm);
				if(status>0)
				{
					out.println("Thank You Successfully Updated");
					RequestDispatcher rd=req.getRequestDispatcher("View");
					rd.include(req, res);
				}
				else
				{
					out.println("Sorry Not Successfully Updated");
					RequestDispatcher rd=req.getRequestDispatcher("View");
					rd.include(req, res);
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
