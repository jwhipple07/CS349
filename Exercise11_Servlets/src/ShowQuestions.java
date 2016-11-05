import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Questions")
public class ShowQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DBHelper DB;
	
    public ShowQuestions() {
        super();
        DB = new DBHelper();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       response.setContentType("text/html");
	        PrintWriter out = response.getWriter();

			String error = request.getParameter("error");
			
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Questions</title>");
	        out.println("<link rel='stylesheet' type='text/css' href='//cdn.datatables.net/1.10.12/css/jquery.dataTables.css'>");
	        
	        out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>");
	        out.println("<script type='text/javascript' charset='utf8' src='//cdn.datatables.net/1.10.12/js/jquery.dataTables.js'></script>");
	        out.println("<script type='text/javascript' language='javascript'>");
	        out.println("$(document).ready( function () {$('#table_id').DataTable();} );");
	        out.println("</script>");
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<div style='width: 600px;  display: block;  margin-left: auto;  margin-right: auto;'>");
	        out.println("<h1>Questions</h1>");
	        
	        out.println("<table id='table_id' class='display'><thead>");
	        out.println("<tr><th width='50'>Number</th><th>Question</th></tr></thead>");
	        out.println("<tbody>");
	        List<Question> questions = DB.selectAllQuestions();
	        int count = 0;
	        for (Question q : questions) {
	        	count++;
		        out.print("<tr><td align='center' width='50'>" + count + "</td><td> <a href=\"Answers?id="+q.id+"\">" + q.question + "</a></td></tr>");	        	
	        }
	        out.println("</tbody></table>");
	        out.println("<form method=\"POST\" action=\"Questions\">");
	        out.println("<p><input type=\"text\" name=\"theQuestion\" size=\"50\"> <input type=\"submit\" value=\"Add Question\"></p>");
	        if(error != null){
	        	out.println("<div style='color:red'>You can not submit an empty question</div>");
	        }
	        out.println("</form>");
	        out.println("</div>");
	        out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        
	        String theQuestion = request.getParameter("theQuestion");
	        if(theQuestion == null || theQuestion.isEmpty()){
		        response.sendRedirect("Questions?error=true");
		        return;
	        }
	        
	        DB.insertQuestion(theQuestion);
	       
	        response.sendRedirect("Questions");
	}

}
