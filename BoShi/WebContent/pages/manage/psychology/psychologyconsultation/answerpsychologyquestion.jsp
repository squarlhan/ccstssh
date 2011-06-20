<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>回答学生心理问题</title>
<script type="text/javascript" src="/BoShi/js/fieldprompt_div.js" ></script>
<script type="text/javascript" src="/BoShi/js/fieldprompt_code.js" ></script>
<s:head />
</head>
<body>

<s:form action="AnswerPsychologyQuestionAction" validate="true">
	<s:hidden name="answer.id" value="%{answer.id}" />
	<s:hidden name="answer.qContent" value="%{answer.qContent}" />
	<table align="center">
		<tr>
			<td>
				<s:property value="answer.qContent"/>
			</td>
		</tr>
	</table>
	<table align="center">
		<tr>
			<td>
				<s:textarea cssStyle="border:1px; width:300px; height:100px;" label="回答" name="answer.aContent" onmouseover="divShow('必填,最多1000个字符',this);" onmouseout="divHidden();" />
			</td>
		</tr>
		<tr>
			<td>
				<s:submit value="确定" />
			</td>
		</tr>
	</table>
</s:form>

</body>
</html>
