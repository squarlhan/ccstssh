<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>老师登陆</title>
<s:head />
</head>
<body>

<table align="center">
	<tr>
		<td>
			<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
		</td>
	</tr>
	<tr>
		<td>
			<s:form action="TeacherLoginAction!login">
				<s:textfield label="用户名" name="user.name" />
				<s:password label="密码" name="user.password" />
				<s:submit value="确定" />
			</s:form>
		</td>
	</tr>
</table>

</body>
</html>
