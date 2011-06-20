<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎来到沧州师范专科学校泊头分校</title>
<s:head />
</head>
<body>

<table width="100%">
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td>
		<table bgcolor="#CDD397" width="100%">
			<tr>
				<td width="1%"></td>
				<td height="130">
					<div style='width: 100%; overflow: hidden;' id=marquee onmouseover=clearInterval(repeat) onmouseout=repeat=setInterval(scrollMarquee,1)>
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td><a href="/BoShi/pages/user/topimage_1.jsp"><img border=0 src="/BoShi/image/1.jpg"></a></td>
							<td><a href="/BoShi/pages/user/topimage_2.jsp"><img border=0 src="/BoShi/image/2.jpg"></a></td>
							<td><a href="/BoShi/pages/user/topimage_3.jsp"><img border=0 src="/BoShi/image/3.jpg"></a></td>
							<td><a href="/BoShi/pages/user/topimage_4.jsp"><img border=0 src="/BoShi/image/4.jpg"></a></td>
						</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td><a href="/BoShi/pages/user/topimage_1.jsp"><img border=0 src="/BoShi/image/1.jpg"></a></td>
							<td><a href="/BoShi/pages/user/topimage_2.jsp"><img border=0 src="/BoShi/image/2.jpg"></a></td>
							<td><a href="/BoShi/pages/user/topimage_3.jsp"><img border=0 src="/BoShi/image/3.jpg"></a></td>
							<td><a href="/BoShi/pages/user/topimage_4.jsp"><img border=0 src="/BoShi/image/4.jpg"></a></td>
						</tr>
					</table>
					</div>
					<SCRIPT language=JavaScript>marquee2(); </SCRIPT>
				</td>
				<td width="1%"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td valign="top" width="49%">
						<table width="100%">
							<tr height="25">
								<td>
									<table width="100%">
										<tr>
											<td><a class="STYLE5" href="/BoShi/pages/user/ListCollegeNewsAction.action">学校新闻</a></td>
											<td align="right"><a class="blackWord" href="/BoShi/pages/user/ListCollegeNewsAction.action">>>More</a></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>	
								<td>
									
										<table align="center" width="100%" cellpadding="0" cellspacing="0">
											<s:iterator id="result" value="collegeNewsResultList" status="index">		
												<tr>
													<td width="3%"></td>
													<td width="75%" align="left">
														※&nbsp;<a target="top" class="blackWord" href="<s:url action="ShowCollegeNewsAction"><s:param name="id" value="#result.id"/></s:url>"> <s:property value="#result.title" />&nbsp;&nbsp;<s:date name="#result.date" format="[MM-dd]" /></a>
													</td>
													<td width="2%"></td>
												</tr>
												<tr><td height="7"></td></tr>
											</s:iterator>
										</table>
									
								</td>
							</tr>
						</table>
					</td>
					<td valign="top" width="49%">
						<table width="100%">
							<tr height="25">
								<td>
									<table width="100%">
										<tr>
											<td><a class="STYLE5" href="/BoShi/pages/user/ListJuniorCollegeActivityNotifyAction.action">通知公告</a></td>
											<td align="right"><a class="blackWord" href="/BoShi/pages/user/ListJuniorCollegeActivityNotifyAction.action">>>More</a></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>	
								<td>
									
										<table align="center" width="100%" cellpadding="0" cellspacing="0">
											<s:iterator id="result" value="activityNotifyResultList" status="index">		
												<tr>
													<td width="3%"></td>
													<td width="75%" align="left">
														※&nbsp;<a target="top" class="blackWord" href="<s:url action="ShowJuniorCollegeActivityNotifyAction"><s:param name="id" value="#result.id"/></s:url>"> <s:property value="#result.title" />&nbsp;&nbsp;<s:date name="#result.date" format="[MM-dd]" /> </a>
													</td>
													<td width="2%"></td>
												</tr>
												<tr><td height="7"></td></tr>
											</s:iterator>
										</table>
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>	
</table>

</body>
</html>