<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加知名校友</title>
	<link href="/BoShi/js/fckeditor/_samples/sample.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/BoShi/js/fckeditor/fckeditor.js"></script>
	<script type="text/javascript">	
		window.onload = function()
		{
			var oFCKeditor = new FCKeditor( 'content.content','900px','400px' ) ;
			oFCKeditor.BasePath	= '/BoShi/js/fckeditor/' ;
			oFCKeditor.ReplaceTextarea() ;
		}
	</script>
	<s:head theme="simple" />
</head>
<body>
<input type="file" name="file" size="40">
</body>
</html>