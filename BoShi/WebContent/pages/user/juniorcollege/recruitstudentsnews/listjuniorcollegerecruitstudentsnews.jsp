<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>招生新闻</title>
<s:head theme="simple" />
</head>
<body>

<table align="center" width="100%">
	<tr height="16"><td></td></tr>
	<tr height="300">
		<td valign="top">
			<s:form theme="simple">
				<table align="center" width="100%" cellpadding="0" cellspacing="0">
					<s:iterator id="result" value="resultList" status="index">		
						<tr>
							<td width="10%"></td>
							<td width="65%" align="left">
								<a target="top" class="blackWord" href="<s:url action="ShowJuniorCollegeRecruitStudentsNewsAction"><s:param name="id" value="#result.id"/></s:url>"> <s:property value="#result.title" /> </a>
							</td>
							<td width="15%" align="right">
								<s:date name="#result.date" format="MM月dd日" />
							</td>
							<td width="10%"></td>
						</tr>
						<tr><td height="10"></td></tr>
					</s:iterator>
				</table>
			</s:form>
		</td>
	</tr>
	<tr>
		<td width="100%">
			<table width="100%">
				<tr>
					<td valign="top" align="right" width="74%">
						总共有<s:property value="resulthAmount" />个结果
						当前是第<s:property value="whichPage" />页
						<a href="<s:url action="ListJuniorCollegeRecruitStudentsNewsAction!pagination"><s:param name="whichPage" value="1"></s:param></s:url>">首页</a>
						<a href="<s:url action="ListJuniorCollegeRecruitStudentsNewsAction!pagination"><s:param name="whichPage" value="whichPage-1"></s:param></s:url>">上一页</a>
						
						<a href="<s:url action="ListJuniorCollegeRecruitStudentsNewsAction!pagination"><s:param name="whichPage" value="whichPage+1"></s:param></s:url>">下一页</a>
						<a href="<s:url action="ListJuniorCollegeRecruitStudentsNewsAction!pagination"><s:param name="whichPage" value="pagesAmount"></s:param></s:url>">尾页</a>
					</td>
					<td>
						<FORM name="form" METHOD="POST" ACTION="">
							<script language="JavaScript">
								<!-- 
									function Jumping(){
										var url="ListJuniorCollegeRecruitStudentsNewsAction!pagination.action?whichPage="+document.form.page.value;   
										window.location.href=url;
										return;
									}
								-->
							</script>
							<s:select name="page" list="pagesList" headerKey="-1" headerValue="跳转到" onchange="Jumping()" theme="simple" />									
						</FORM>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>