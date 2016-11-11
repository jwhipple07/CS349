import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.Question;
import mvc.models.DBModel;

@WebServlet("/APIGetQuestions")
public class APIGetQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBModel DB;

	public APIGetQuestions() {
		super();
		DB = new DBModel();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream 
		PrintWriter out = response.getWriter();
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);

		JsonBuilderFactory factory = Json.createBuilderFactory(config);
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (Question q : DB.selectAllQuestions()) {
			JsonObject question = factory.createObjectBuilder()
					.add("question", q.question)
					.add("id", q.id)
				.build();
			builder.add(question);
		}
		
		JsonObject value = factory.createObjectBuilder()
				.add("questions", builder.build())
			.build();
		out.print(value);
		out.flush();
	}
}