<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校友登陆</title>
<s:head />
</head>
<body>

<table align="center">
	<tr>
		<td align="right">
			<a href="ToSchoolfellowLoginAndRegisterAction_register.action">注册</a>
		</td>
	</tr>
	<tr>
		<td>
		</td>
	</tr>
	<tr>
		<td align="center">
			<s:form action="SchoolfellowLoginAndRegisterAction!login">
				<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
				<s:textfield label="用户名" name="user.name" onmouseover="divShow('必填',this);" onmouseout="divHidden();" />
				<s:password label="密码" name="user.password" onmouseover="divShow('必填',this);" onmouseout="divHidden();" />
				<s:submit value="确定" />
			</s:form>
		</td>
	</tr>
</table>			

</body>
</html>
