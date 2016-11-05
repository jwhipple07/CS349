import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Answers")
public class ShowAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String questionID;

	private final DBHelper DB;
    public ShowAnswers() {
        super();
        DB = new DBHelper();
    }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		questionID = request.getParameter("id");
		String error = request.getParameter("error");
	        
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Answers for question " + questionID + "</title>");
	        out.println("<link rel='stylesheet' type='text/css' href='//cdn.datatables.net/1.10.12/css/jquery.dataTables.css'>");
	        
	        out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>");
	        out.println("<script type='text/javascript' charset='utf8' src='//cdn.datatables.net/1.10.12/js/jquery.dataTables.js'></script>");
	        out.println("<script type='text/javascript' language='javascript'>");
	        out.println("$(document).ready( function () {$('#table_id').DataTable();} );");
	        out.println("</script>");
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<div style='width: 600px;  display: block;  margin-left: auto;  margin-right: auto;'>");
	        out.println("<h1>Answers</h1>");
	        out.println("<h3>" + DB.getQuestion(Integer.parseInt(questionID)) + "</h3>");
	        
	        out.println("<table id='table_id' class='display'><thead>");
	        out.println("<tr><th width='50'>Number</th><th>Question</th></tr></thead>");
	        out.println("<tbody>");
	        List<Answer> answers = DB.selectAllAnswers(Integer.parseInt(questionID));
	        int count = 0;
	        for (Answer a : answers) {
	        	count++;
		        out.print("<tr><td align='center' width='50'>" + count + "</td><td>" + a.answer + "</td>");	        	
	        } 
	        out.println("</tbody></table>");
	        out.println("<form method=\"POST\" action=\"Answers\">");
	        out.println("<p><input type=\"text\" name=\"theAnswer\" size=\"50\"> <input type=\"submit\" value=\"Add Answer\"></p>");
	        if(error != null){
	        	out.println("<div style='color:red'>You can not submit an empty answer</div>");
	        }
	        out.println("</form>");
	        out.println("<a href='Questions'>Go Back to Questions</a>");
	        out.println("</div>");
	        out.println("</body></html>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        
	        String theAnswer = request.getParameter("theAnswer");
	        if(theAnswer == null || theAnswer.isEmpty()){
		        response.sendRedirect("Answers?id="+questionID+"&error=true");
		        return;
	        }
	        DB.insertAnswer(Integer.parseInt(questionID), theAnswer);
	       
	        response.sendRedirect("Answers?id="+questionID);
	}
}
