<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加高中新闻</title>
	<link href="/BoShi/js/fckeditor/_samples/sample.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/BoShi/js/fckeditor/fckeditor.js"></script>
	<s:head theme="simple" />
</head>
<body>

<s:form action="AddSeniorHighSchoolNewsAction">
	<s:textfield label="标题" name="content.title" />
	<table>
		<tr>
			<td align="center">
				<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
			</td>
		</tr>
		<tr>
			<td>
				<FCK:editor id="content.content" basePath="/BoShi/js/fckeditor/" width="900"  height="400"
				    imageBrowserURL="/BoShi/js/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector"
				    linkBrowserURL="/BoShi/js/fckeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector"
				    flashBrowserURL="/BoShi/js/fckeditor/editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector"
				    imageUploadURL="/BoShi/js/fckeditor/editor/filemanager/upload/simpleuploader?Type=Image"
				    linkUploadURL="/BoShi/js/fckeditor/editor/filemanager/upload/simpleuploader?Type=File"
				    flashUploadURL="/BoShi/js/fckeditor/editor/filemanager/upload/simpleuploader?Type=Flash">
				</FCK:editor>
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
