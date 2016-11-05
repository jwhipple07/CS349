

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DBHelper {
	
    private final static String mysqluserID    = "jmwcn7";
    private final static String mysqlpassword  = "V8jPNBP3NR";
	private final static String dbURL = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/"
			+ mysqluserID;
    private final static String driver = "com.mysql.jdbc.Driver";
    private Connection con;
    private Statement stmt;
    
    public DBHelper() {
    	try {
    		initConnection();
			if (!tableExists("Questions")){
				createQuestionTable();
			} 
			if(!tableExists("Answers")){
				createAnswerTable();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
       
    private void initConnection() throws Exception {
        Class.forName(driver);
        con = DriverManager.getConnection(dbURL,mysqluserID,mysqlpassword);
        stmt = con.createStatement();
    }

    private void deleteTables() throws SQLException {
        String sqlCmd = "DROP TABLE Questions";
        stmt.executeUpdate(sqlCmd);
        sqlCmd = "DROP TABLE Answers";
        stmt.executeUpdate(sqlCmd);
	}
    
	private void createQuestionTable() throws SQLException {
        String createQuestionSQL = "create table Questions (question_id INT NOT NULL AUTO_INCREMENT, question VARCHAR(240), PRIMARY KEY (question_id))";
        stmt.executeUpdate(createQuestionSQL);
	}
	private void createAnswerTable() throws SQLException {
        String createAnswerSQL = "create table Answers (id INT NOT NULL AUTO_INCREMENT, Question_id INT NOT NULL, Answer VARCHAR(240), PRIMARY KEY (id))";
        stmt.executeUpdate(createAnswerSQL);
	}

	private boolean tableExists(String tableName) throws SQLException {
		DatabaseMetaData dbmd;
		ResultSet rs;
		dbmd = con.getMetaData();
		rs = dbmd.getTables(null, null, tableName, null);
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public List<Question> selectAllQuestions(){
		List<Question> questions = new ArrayList<Question>();
		try{
			ResultSet rs = stmt.executeQuery("select * from Questions");
			Question row;
			while(rs.next()){
				String question;
				Integer id;
				id = rs.getInt("question_id"); 
				question = rs.getString("Question");
				row = new Question(id, question);	
				questions.add(row);
			}
		} catch( SQLException e){
			
		}
		
		return questions;
	}
	 
	public boolean insertQuestion(String question){
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into questions (question) values (?)");
			pstmt.setString(1, question);
			pstmt.executeUpdate(); // execute prepared statement
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<Answer> selectAllAnswers(Integer QuestionID){
		List<Answer> answers = new ArrayList<Answer>();
		try{
			ResultSet rs = stmt.executeQuery("select * from Answers where question_id ='"+QuestionID+"'");
			Answer row;
			while(rs.next()){
				String answer;
				Integer id, q_id;
				id = rs.getInt("id");
				q_id = rs.getInt("Question_id");
				answer = rs.getString("Answer");
				row = new Answer(id, q_id,answer);	
				answers.add(row);
			}
		} catch( SQLException e){
			
		}
		
		return answers;
	}
	public boolean insertAnswer(Integer question_ID, String answer){
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into answers (question_id, answer) values (?, ?)");
			pstmt.setInt(1, question_ID);
			pstmt.setString(2, answer);
			pstmt.executeUpdate(); // execute prepared statement
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public String getQuestion(Integer q_id){
		try{
			ResultSet rs = stmt.executeQuery("select * from Questions where question_id ='"+ q_id +"'");
			while(rs.next()){
				return rs.getString("Question");
			}
		} catch( SQLException e){
			
		}
		
		return "";
	}

}