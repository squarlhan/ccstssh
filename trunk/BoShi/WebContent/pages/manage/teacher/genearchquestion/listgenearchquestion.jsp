<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>家长问题</title>
<s:head theme="simple" />
</head>
<body>

<table align="center" width="800">
	<tr>
		<td align="center">
			<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
		</td>
	</tr>
	<tr>
		<td>
			<table align="center" width="100%">
				<tr bgcolor="#4A708B">
					<th width="10%">用户名</th>
					<th width="30%">来自班级</th>
					<th width="60%">问题</th>
				</tr>
				<s:iterator id="result" value="resultList" status="index">
					<tr bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
						<td align="center">
							<s:property value="#result.genearchUser.name" />
						</td>
						<td align="center">
							<s:property value="#result.genearchUser.theClass.name" />
						</td>
						<td align="center">
							<a href="<s:url action="AnswerGenearchQuestionAction!prepareAnswer"><s:param name="id" value="#result.id"/></s:url>"> <s:property value="#result.qContent" /> </a>
						</td>
					</tr>
				</s:iterator>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<s:form action="ListNotAnswerGenearchQuestionAction!addClass">
				<s:textfield label="添加班级名称" name="theClass.name" />
				<s:submit value="确定" />
			</s:form>
		</td>
	</tr>
	<tr>
		<td width="100%">
			<table width="100%">
				<tr>
					<td valign="top" align="right" width="68%">
						总共有<s:property value="resulthAmount" />个结果
						当前是第<s:property value="whichPage" />页
						<a href="<s:url action="ListNotAnswerGenearchQuestionAction!pagination"><s:param name="whichPage" value="1"></s:param></s:url>">首页</a>
						<a href="<s:url action="ListNotAnswerGenearchQuestionAction!pagination"><s:param name="whichPage" value="whichPage-1"></s:param></s:url>">上一页</a>
						
						<a href="<s:url action="ListNotAnswerGenearchQuestionAction!pagination"><s:param name="whichPage" value="whichPage+1"></s:param></s:url>">下一页</a>
						<a href="<s:url action="ListNotAnswerGenearchQuestionAction!pagination"><s:param name="whichPage" value="pagesAmount"></s:param></s:url>">尾页</a>
					</td>
					<td align="left">
						<FORM name="form" METHOD="POST" ACTION="">
							<script language="JavaScript">
								<!-- 
									function Jumping(){
										var url="ListNotAnswerGenearchQuestionAction!pagination.action?whichPage="+document.form.page.value;   
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