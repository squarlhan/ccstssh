<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>您的问题与答复</title>
<s:head theme="simple" />
</head>
<body>

<table align="center" width="100%">
	<tr>
		<td align="right">
			<a href="GenearchLoginAndRegisterAction!logout.action">退出</a>
		</td>
	</tr>
	<tr>
		<td>
			<table align="center" width="100%" class="psychologylisttable" cellpadding="0" cellspacing="0">
				<s:iterator id="result" value="resultList" status="index">		
					<tr>
    					<td class="qbackc"><strong>&nbsp;&nbsp;问题:</strong></td>
  					</tr>
					<tr>
						<td align="center" height="70">
							<s:property value="#result.qContent" />
						</td>
					</tr>
					<tr>
    					<td class="abackc">回答:</td>
  					</tr>
					<tr>
						<td align="center" height="70" class="aborderbottom">
							<s:property value="#result.aContent" />
						</td>
					</tr>
				</s:iterator>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
			<s:form action="AddGenearchQuestionAction" validate="true">
				<s:textarea label="问题" name="question.qContent" onmouseover="divShow('必填,最多300个字',this);" onmouseout="divHidden();" />
				<s:submit value="确定" />
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
						<a href="<s:url action="ListGenearchQuestionAndAnswerAction!pagination"><s:param name="whichPage" value="1"></s:param></s:url>">首页</a>
						<a href="<s:url action="ListGenearchQuestionAndAnswerAction!pagination"><s:param name="whichPage" value="whichPage-1"></s:param></s:url>">上一页</a>
						
						<a href="<s:url action="ListGenearchQuestionAndAnswerAction!pagination"><s:param name="whichPage" value="whichPage+1"></s:param></s:url>">下一页</a>
						<a href="<s:url action="ListGenearchQuestionAndAnswerAction!pagination"><s:param name="whichPage" value="pagesAmount"></s:param></s:url>">尾页</a>
					</td>
					<td>
						<FORM name="form" METHOD="POST" ACTION="">
							<script language="JavaScript">
								<!-- 
									function Jumping(){
										var url="ListGenearchQuestionAndAnswerAction!pagination.action?whichPage="+document.form.page.value;   
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