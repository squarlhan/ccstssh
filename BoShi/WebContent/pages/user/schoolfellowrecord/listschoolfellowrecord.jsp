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
		<td align="right">
			<a href="ToSchoolfellowLoginAndRegisterAction_login.action">登陆</a>
			<a href="ToSchoolfellowLoginAndRegisterAction_register.action">注册</a>
		</td>
	</tr>
	<tr>
		<td>
			<table align="center" width="100%" cellpadding="0" cellspacing="0">
				<s:iterator id="result" value="resultList" status="index">
					<tr>
    					<td width="25%">
    						<table align="center" width="100%" bgcolor="#CDD397" class="psychologylisttable" cellpadding="0" cellspacing="0">
    							<tr>
    								<td width="30%" align="right">ID:</td>
    								<td align="center"><s:property value="#result.schoolfellowUser.name" /></td>
    							</tr>
    							<tr>
    								<td width="30%" align="right">姓名:</td>
    								<td align="center"><s:property value="#result.schoolfellowUser.chineseName" /></td>
    							</tr>
    							<tr>
    								<td width="30%" align="right">年级:</td>
    								<td align="center"><s:property value="#result.schoolfellowUser.grade" /></td>
    							</tr>
    							<tr>
    								<td width="30%" align="right">班级:</td>
    								<td align="center"><s:property value="#result.schoolfellowUser.className" /></td>
    							</tr>
    							<tr>
    								<td width="30%" align="right">日期:</td>
    								<td align="center"><s:date name="#result.date" format="MM月dd日HH:mm" /></td>
    							</tr>
    						</table>
    					</td>
						<td align="center" class="psychologylisttable">
							<s:property value="#result.content" />
						</td>
					</tr>
				</s:iterator>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<s:form action="AddSchoolfellowRecordAction" validate="true">
				<s:textarea label="发言" name="content.content" onmouseover="divShow('必填,最多50个字',this);" onmouseout="divHidden();" />
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
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="1"></s:param></s:url>">首页</a>
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="whichPage-1"></s:param></s:url>">上一页</a>
						
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="whichPage+1"></s:param></s:url>">下一页</a>
						<a href="<s:url action="ListSchoolfellowRecordAction!pagination"><s:param name="whichPage" value="pagesAmount"></s:param></s:url>">尾页</a>
					</td>
					<td>
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