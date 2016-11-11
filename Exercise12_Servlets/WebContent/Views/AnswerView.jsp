<%@ page import="mvc.models.AnswerViewModel, mvc.Answer" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Answers</title>
	<link rel='stylesheet' type='text/css' href='//cdn.datatables.net/1.10.12/css/jquery.dataTables.css'>
	<script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script type='text/javascript' charset='utf8' src='//cdn.datatables.net/1.10.12/js/jquery.dataTables.js'></script>
	<script type='text/javascript' language='javascript'>
		$(document).ready(function() {
			$('#table_id').DataTable();
		});
	</script>
</head>
<body>
	<div style="width: 600px; display: block; margin-left: auto; margin-right: auto;">
		<h1>Answers</h1>
		<%
			out.println("<h3>" + request.getAttribute("currentQuestion") + "</h3>");
		%>
		<table id="table_id" class="display">
			<thead>
				<tr>
					<th width="50">Number</th>
					<th>Answer</th>
				</tr>
			</thead>
			<tbody>
			<%
			        AnswerViewModel viewModel = (AnswerViewModel)request.getAttribute("viewModel");
			        if (viewModel != null) {
			        	int i = 0;
			        	for(Answer a : viewModel.answers){
			        		out.println("<tr><td>" + ++i + " </td><td>"+a.answer +"</td>");
			        	}
			        }
			%>
			</tbody>
		</table>
			<%
				out.println("<form method='POST' action='showAnswers?id="+request.getParameter("id")+"'>");
			%>
		        <p>
		        	<input type="text" name="theAnswer" size="50"> 
		        	<input type="submit" value="Add Answer"></p>
		     <%   
				String errorFound = (String)request.getAttribute("error");
				if(errorFound != null){
		        	out.println("<div style='color:red'>You can not submit an empty answer</div>");
				}
				out.println("</form>");
			%>
		<a href='/Exercise12_Servlets/showQuestions'>Go Back to Questions</a>
	</div>


</body>
</html>