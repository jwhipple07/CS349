package mvc.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.models.DBModel;
import mvc.models.QuestionViewModel;

/**
 * Servlet implementation class showQuestions
 */
@WebServlet("/showQuestions")
public class showQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DBModel exerciseModel;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showQuestions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		 
		exerciseModel = new DBModel();
		
		QuestionViewModel result = new QuestionViewModel();
		result.questions = exerciseModel.selectAllQuestions();
		rd = request.getRequestDispatcher("/Views/QuestionView.jsp");
		request.setAttribute("viewModel", result);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
        String theQuestion = request.getParameter("theQuestion");
        if(theQuestion == null || theQuestion.isEmpty()){
        	rd = request.getRequestDispatcher("/Views/QuestionView.jsp");
    		request.setAttribute("error", "true");
        } else {
	        if(exerciseModel == null){
	        	exerciseModel = new DBModel();
	        }
	        exerciseModel.insertQuestion(theQuestion);
        }

        doGet(request, response);
	}

}
