package mvc.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.models.AnswerViewModel;
import mvc.models.DBModel;
import mvc.models.QuestionViewModel;

/**
 * Servlet implementation class showAnswers
 */
@WebServlet("/showAnswers")
public class showAnswers extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DBModel exerciseModel;
    private Integer questionID;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showAnswers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			questionID = Integer.parseInt(request.getParameter("id"));
		} catch(Exception e){
			questionID = 0;
		}
		RequestDispatcher rd = null;
		 
		exerciseModel = new DBModel();
		
		String question = exerciseModel.getQuestion(questionID);
		AnswerViewModel result = new AnswerViewModel();
		result.answers = exerciseModel.selectAllAnswers(questionID);
		rd = request.getRequestDispatcher("/Views/AnswerView.jsp");
		request.setAttribute("viewModel", result);
		request.setAttribute("currentQuestion", question);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		
        String theAnswer = request.getParameter("theAnswer");
        Integer questionID = Integer.parseInt(request.getParameter("id"));
        if(theAnswer == null || theAnswer.isEmpty()){
        	rd = request.getRequestDispatcher("/Views/AnswerView.jsp");
    		request.setAttribute("error", "true");
        } else {
	        if(exerciseModel == null){
	        	exerciseModel = new DBModel();
	        }
	        exerciseModel.insertAnswer(questionID, theAnswer);
        }

        doGet(request, response);
	}

}
