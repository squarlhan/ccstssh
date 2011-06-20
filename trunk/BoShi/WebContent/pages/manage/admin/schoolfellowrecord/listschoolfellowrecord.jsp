<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校友录</title>
<s:head theme="simple" />
</head>
<body>

<table align="center" width="100%">
	<tr>
		<td align="center">
			<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
		</td>
	</tr>
	<tr>
		<td>
			<s:form theme="simple">
				<table align="center" width="100%">
					<tr bgcolor="#4A708B">
						<th width="10%">选择</th>
						<th width="10%">发布者</th>
						<th width="60%">内容</th>
						<th width="15%">发布日期</th>
						<th width="10%">操作</th>
					</tr>
					<s:iterator id="result" value="resultList" status="index">		
						<tr bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
							<td align="center">
								<s:checkbox name="resultList[%{#index.index}].haveSelected" theme="simple" />
							</td>
							<td align="center">
								<s:property value="#result.schoolfellowUser.name" />
							</td>
							<td align="center">
								<s:property value="#result.content" />
							</td>
							<td align="center">
								<s:date name="#result.date" format="yyyy年MM月dd日" />
							</td>
							<td align="right"> 
								<a href="<s:url action="ListSchoolfellowRecordAction!remove"><s:param name="id" value="#result.id"/></s:url>"></a>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td align="center">
							<s:submit action="ListSchoolfellowRecordAction!remove" value="删除所选" />
						</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
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
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="1"></s:param></s:url>">首页</a>
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="whichPage-1"></s:param></s:url>">上一页</a>
						
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="whichPage+1"></s:param></s:url>">下一页</a>
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="pagesAmount"></s:param></s:url>">尾页</a>
					</td>
					<td align="left">
						<FORM name="form" METHOD="POST" ACTION="">
							<script language="JavaScript">
								<!-- 
									function Jumping(){
										var url="ListSchoolfellowRecordAction!pagination.action?whichPage="+document.form.page.value;   
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