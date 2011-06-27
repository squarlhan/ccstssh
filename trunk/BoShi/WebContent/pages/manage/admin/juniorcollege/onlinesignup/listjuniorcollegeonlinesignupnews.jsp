<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上报名</title>

<script language = "javascript" >
function mycheck(){
    var upurl = document.getElementById('upexl').value;
    if(upurl==null)
        document.getElementById('upbtn').disabled=true;
    else
        document.getElementById('upbtn').disabled=false;
}
</script>

<s:head />
</head>
<body>

<table align="center" width="100%">
	<tr>
		<td align="center">
			<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
		</td>
	</tr>
	<tr align="left">
	    <td align="left">
	    <table align="center" width="100%">
	    <tr align="left">
	    <td align="left">
	    <a href="<s:url action="ListJuniorCollegeOnlineSignUpNewsAction!downpic"/>">导出照片</a>
	    &nbsp;&nbsp;&nbsp;&nbsp;
	    <a href="<s:url action="ListJuniorCollegeOnlineSignUpNewsAction!downexl" />">导出报名表</a>
	    &nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
	    <td align="left">
	    <s:form theme = "simple" action="ListJuniorCollegeOnlineSignUpNewsAction!upexl" method = "POST" validate="true" enctype="multipart/form-data">
	    <s:label value = "上传录取信息: "/>
	    <s:file name ="upexl" id="upexl" label ="上传录取信息" onchange="mycheck();"/> 
	    <s:submit id = "upbtn" value = "上传" disabled="true"/>
	    </s:form>
	    </td>
	    <tr align="left">
	    </table>
	    </td>
	</tr>
	<tr>
		<td>
			<s:form theme="simple">
				<table align="center" width="100%">
					<tr bgcolor="#4A708B">
						<th width="5%">选择</th>
						<th width="15%">报名时间</th>
						<th width="10%">姓名</th>
						<th width="10%">性别</th>
						<th width="10%">考生号</th>
						<th width="10%">报考专业</th>
						<th width="10%">录取情况</th>
						<th width="10%">手机电话</th>
						<th width="10%">固定电话</th>
						<th width="10%">操作</th>
					</tr>
					<s:iterator id="result" value="resultList" status="index">		
						<tr bgcolor="<s:if test="#index.odd == true">#ffffff</s:if><s:else>#EDEDED</s:else>">
							<td align="center">
								<s:checkbox name="resultList[%{#index.index}].haveSelected" theme="simple" />
							</td>
							<td align="center">
								<s:date name="#result.date" format="yyyy年MM月dd日" />
							</td>
							<td align="center">
								<a href="<s:url action="ShowJuniorCollegeOnlineSignUpNewsAction"><s:param name="id" value="#result.id"/></s:url>"> <s:property value="#result.name" /> </a>
							</td>
							<td align="center">
								<s:property value="#result.sex" />
							</td>
							<td align="center">
								<s:property value="#result.examid" />
							</td>
							<td align="center">
								<s:property value="#result.profession" />
							</td>
							<td align="center">
								<s:property value="#result.isaccept" />
							</td>
							<td align="center">
								<s:property value="#result.mobile" />
							</td>
							<td align="center">
								<s:property value="#result.phone" />
							</td>
							<td align="center"> 
								<a href="<s:url action="ListJuniorCollegeOnlineSignUpNewsAction!remove"><s:param name="id" value="#result.id"/></s:url>">删除</a>
							</td>
						</tr>
					</s:iterator>
					<tr>
						<td align="center">
							<s:submit action="ListJuniorCollegeOnlineSignUpNewsAction!remove" value="删除所选" />
						</td>
						<td></td>
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
						<a href="<s:url action="ListJuniorCollegeOnlineSignUpNewsAction!pagination"><s:param name="whichPage" value="1"></s:param></s:url>">首页</a>
						<a href="<s:url action="ListJuniorCollegeOnlineSignUpNewsAction!pagination"><s:param name="whichPage" value="whichPage-1"></s:param></s:url>">上一页</a>
						
						<a href="<s:url action="ListJuniorCollegeOnlineSignUpNewsAction!pagination"><s:param name="whichPage" value="whichPage+1"></s:param></s:url>">下一页</a>
						<a href="<s:url action="ListJuniorCollegeOnlineSignUpNewsAction!pagination"><s:param name="whichPage" value="pagesAmount"></s:param></s:url>">尾页</a>
					</td>
					<td align="left">
						<FORM name="form" METHOD="POST" ACTION="">
							<script language="JavaScript">
								<!-- 
									function Jumping(){
										var url="ListJuniorCollegeOnlineSignUpNewsAction!pagination.action?whichPage="+document.form.page.value;   
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