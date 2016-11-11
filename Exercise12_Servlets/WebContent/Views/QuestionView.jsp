<%@ page import="mvc.models.QuestionViewModel, mvc.Question" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Questions</title>
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
		<h1>Questions</h1>
		<table id="table_id" class="display">
			<thead>
				<tr>
					<th width="50">Number</th>
					<th>Question</th>
				</tr>
			</thead>
			<tbody>
			<%
			        QuestionViewModel viewModel = (QuestionViewModel)request.getAttribute("viewModel");
			        if (viewModel != null) {
			        	for(Question q : viewModel.questions){
			        		out.println("<tr><td>" + q.id + " </td><td><a href='/Exercise12_Servlets/showAnswers?id="+q.id+"'>"+q.question +"</a></td>");
			        	}
			        }
			%>
			</tbody>
		</table>
		<form method="POST" action="showQuestions">
	        <p>
	        	<input type="text" name="theQuestion" size="50"> 
	        	<input type="submit" value="Add Question"></p>
	        
			<%
			String errorFound = (String)request.getAttribute("error");
			if(errorFound != null){
	        	out.println("<div style='color:red'>You can not submit an empty question</div>");
			}
			%>
	        </form>
	</div>


</body>
</html>