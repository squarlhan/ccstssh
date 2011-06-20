<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生登陆</title>
<s:head />
</head>
<body>

<table align="center">
	<tr>
		<td align="right">
			<a href="ToStudentLoginAndRegisterAction_register.action">注册</a>
		</td>
	</tr>
	<tr>
		<td class="redborder">
		&nbsp;&nbsp;&nbsp;&nbsp;心理咨询是由专业人员即心理咨询师运用心理学以及相关知识，遵循心理学原则，通过各种技术和方法，帮助求助者解决心理问题。“帮助求助者解决心理问题”的含义有二：一、咨询关系是“求”和“帮”的关系，这种关系在心理咨询中有普遍意义。二、帮助解决的问题，只能是心理问题，或有心理问题引发的行为问题，除此以外，咨询师不帮助求助者解决任何生活中的具体问题。心理咨询这一概念有广义和狭义之分，广义概念，它涵盖了临床干预的各种方法或手段；狭义概念主要是指非标准化的临床干预措施。也就是说，广义的“心理咨询”这一概念，包括了“狭义的心理咨询”和“心理治疗”这两类临床技术手段。
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;心理咨询借助语言、文字等媒介，给咨询对象以帮助、启发、暗示和教育的过程。心理咨询可以使咨询对象在认识、情感和态度上有所变化，解决其在学习、工作、生活、疾病和康复等方面出现的心理问题/障碍，促使咨询对象的自我调整，从而能够更好地适应环境，保持身心健康。为了达到交互的及时性，真实性，常常要求求询者和咨询工作者的相互交谈。咨询工作者是求询者的朋友和心理话的倾听者，而求询者是一个访问者。
		</td>
	</tr>
	<tr>
		<td align="center">
			<s:form action="StudentLoginAndRegisterAction!login.action">
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
