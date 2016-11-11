package mvc;

public class Answer {
	public String answer;
	public int id, question_id;
	public Answer(int id, int question_id, String answer) {
		this.answer = answer;
		this.id = id;
		this.question_id = question_id;
	}
}
