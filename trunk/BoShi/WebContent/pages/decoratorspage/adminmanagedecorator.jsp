<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title default="管理员" /></title>
<link href="/BoShi/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/BoShi/js/adminmenu.js"></script>
<decorator:head />
</head>
<body>

<table width="900" align="center" cellpadding="0" cellspacing="0" border="1">
	<tr>
		<td align="right">
			<a href="/BoShi/pages/manage/admin/AdminLoginAction!logout.action" >退出</a>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" align="center">					
				<tr>
					<td width="2%"></td>
					<td width="100%" align="center">
						<SCRIPT language=JavaScript type=text/JavaScript>
							createMainLayer();
						</SCRIPT>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<decorator:body />
		</td>
	</tr>
</table>

</body>
</html>
