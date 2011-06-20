<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报名人：<s:property value="content.name" /></title>
<s:head />
</head>
<body>

<table>
	<tr>
		<td><s:label label="报名时间" name="content.date"  /></td>
	</tr>
	<tr>
		<td><s:label label="姓名" name="content.name" /></td>
	</tr>
	<tr>
		<td><s:label label="性别" name="content.sex" /></td>
	</tr>
	<tr>
		<td><s:label label="考生号" name="content.examid" /></td>
	</tr>
	<tr>
		<td><s:label label="身份证号" name="content.pid" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="报考专业" name="content.profession" /></td>
	</tr>
	<tr>
		<td><s:label label="录取信息" name="content.isaccept" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="手机电话" name="content.mobile" /></td>
	</tr>
	<tr align="center">
		<td><s:label label="固定电话" name="content.phone" /></td>
	</tr>
	<tr>
		<td><s:label label="附加说明" name="content.content" /></td>
	</tr>
	<tr>
	    <td>
		    <img src='/BoShi/UserFiles/<s:property value ="content.photo"/>' />
		</td>
	</tr>
	
</table>

</body>
</html>