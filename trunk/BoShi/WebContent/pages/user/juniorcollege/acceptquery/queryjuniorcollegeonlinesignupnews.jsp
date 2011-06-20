<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录取查询</title>
<s:head />
</head>
<body>

<table align="center">
	<tr>
		<td height="70">
		</td>
	</tr>
	<tr>
		<td>
			<s:form action="QueryJuniorCollegeOnlineSignUpNewsAction" validate="true">
				<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
				
				<s:textfield label="考号" name="content.examid" onmouseover="divShow('必填,数字考生号',this);" onmouseout="divHidden();" />
				<s:textfield label="身份证" name="content.pid" onmouseover="divShow('必填,身份证号',this);" onmouseout="divHidden();" />
				
				<s:submit value="确定" />
			</s:form>
		</td>
	</tr>
	
</table>

</body>
</html>
