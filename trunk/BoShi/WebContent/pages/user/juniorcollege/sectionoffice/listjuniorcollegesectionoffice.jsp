<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科室介绍</title>
<s:head theme="simple" />
</head>
<body>

<table align="center" width="100%">
	<tr height="16"><td></td></tr>
	<tr><td class="borderbottom"><a href="/BoShi/pages/user/ShowUserJuniorCollegeCircumstanceAction.action"><span style="color: #8a0000;"><strong>沧州师范专科学校泊头分校</strong></span></a></td></tr>
	<tr height="300">
		<td valign="top">
			<s:form theme="simple">
				<table align="center" width="100%" cellpadding="0" cellspacing="0">
					<s:iterator id="result" value="resultList" status="index">
						<tr height="20">
							<td width="18%" align="right"><img src="/BoShi/image/shulan0.gif" /></td>
							<td></td>
							<td width="60%"></td>
						</tr>		
						<tr>
							<td width="18%" align="right"><img src="/BoShi/image/shulan.gif" /></td>
							<td class="redborder" align="center" bgcolor="#CDD397">
								<a target="top" href="<s:url action="ShowJuniorCollegeSectionOfficeAction"><s:param name="id" value="#result.id"/></s:url>"><span style="color: #8a0000;"> <s:property value="#result.title" /> </span></a>
							</td>
							<td width="60%"></td>
						</tr>
					</s:iterator>
				</table>
			</s:form>
		</td>
	</tr>
</table>

</body>
</html>