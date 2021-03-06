<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><decorator:title default="高中管理员" /></title>
<link href="/BoShi/css/css.css" rel="stylesheet" type="text/css" />
<decorator:head />
</head>
<body>

<table width="900" align="center" cellpadding="0" cellspacing="0" border="1">
	<tr>
		<td align="right">
			<a href="/BoShi/pages/manage/seniorhighschool/SeniorHighSchoolLoginAction!logout.action" >退出</a>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" align="center">					
				<tr>
					<td align="center"><a class="menutop" href="/BoShi/pages/manage/seniorhighschool/ShowSeniorHighSchoolCircumstanceAction.action">高中概况</a></td>
					<td align="center"><a class="menutop" href="/BoShi/pages/manage/seniorhighschool/ListSeniorHighSchoolNewsAction.action">新闻信息</a></td>
					<td align="center"><a class="menutop" href="/BoShi/pages/manage/seniorhighschool/ListSeniorNoticesAction.action">通知公告</a></td>
					<td align="center"><a class="menutop" href="/BoShi/pages/manage/seniorhighschool/ShowSeniorHighSchoolExcellenceTeacherAction.action">优秀教师</a></td>
					<td align="center"><a class="menutop" href="/BoShi/pages/manage/seniorhighschool/ListSeniorHighSchoolRecruitStudentsNewsAction.action">招生信息</a></td>
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
