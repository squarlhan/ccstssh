<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>录取信息</title>

<s:head />

</head>
<body>
<table width="100%" align="center">
    <tr>
		<td height="100"></td>
	</tr>
	<tr align="center">
		<td><s:label label="报名时间" name="content.date" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="姓名" name="content.name" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="性别" name="content.sex" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="考号" name="content.examid" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="身份证" name="content.pid" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="报考专业" name="content.profession" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="录取信息" name="content.isaccept" cssStyle="color:red;font-size:14"/></td>
	</tr>
	<tr align="center">
		<td><s:label label="手机" name="content.mobile" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="固定电话" name="content.phone" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="附加说明" name="content.content" /></td>
	</tr>
</table>

</body>
</html>