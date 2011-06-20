<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校友专栏</title>
<s:head theme="simple" />
</head>
<body>

<table align="center" width="100%">
	<tr height="16"><td></td></tr>
	<tr>
		<td valign="top" align="left" width="25%">
			<a href="/BoShi/pages/user/ListFSFellowAction.action" target="_blank" >知名校友:</a>
		</td>
		<td width="65%"></td>
		<td width="10%"></td>
	</tr>
	<tr height="8"><td></td></tr>
	<tr>
		<td valign="top">
			<s:form theme="simple">
				<table align="center" width="100%" cellpadding="0" cellspacing="0">
					<s:iterator id="result" value="resultList" status="index">		
						<tr>
							<td width="10%"></td>
							<td width="15%" align="left" valign="top">
								<a target="top" class="blackWord" > <s:property value="#result.title" /> </a>
							</td>
							<td width="65%" align="left">
								<s:property value="#result.content" escape="false" />
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
				<td width="65%"></td>
				<td valign="top" align="right" width="25%">
				<a href="/BoShi/pages/user/ListFSFellowAction.action" target="_blank" >详细信息>></a>
				</td>
				<td width="10%"></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="16"><td></td></tr>
	<tr>
		<td valign="top" align="left" width="25%">
			<a href="/BoShi/pages/user/ListSchoolfellowRecordAction.action" target="_blank" >校友留言:</a>
		</td>
		<td width="65%"></td>
		<td width="10%"></td>
	</tr>
	<tr>
	<td width="100%">
			<table width="100%">
				<tr>
				<td width="10%"></td>
				<td width="80%">
					这里是校友留言的地方，欢迎您在这里留言，作为泊师校友，在校友留言板留下您想对泊师说的话，这里是您跟泊师交流的平台，是你我感情的寄托。
				</td>
				<td width="10%"></td>
				</tr>
			</table>
		</td>
		
	</tr>
	<tr>
		<td width="100%">
			<table width="100%">
				<tr>
				<td width="65%"></td>
				<td valign="top" align="right" width="25%">
				<a href="/BoShi/pages/user/ListSchoolfellowRecordAction.action" target="_blank" >进入留言板>></a>
				</td>
				<td width="10%"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>