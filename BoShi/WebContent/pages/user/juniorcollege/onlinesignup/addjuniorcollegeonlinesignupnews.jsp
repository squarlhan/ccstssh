<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上报名</title>
<s:head />
</head>
<body>

<table align="center">
	<tr>
		<td height="30">
		</td>
	</tr>
	<tr>
		<td><h1>泊头职业学院网上报名系统</h1>
		</td>
	</tr>
	<tr>
		<td>
			<s:form action="AddJuniorCollegeOnlineSignUpNewsAction" method = "POST" validate="true" enctype="multipart/form-data">
				<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
				<s:textfield label="姓名" name="content.name" onmouseover="divShow('必填,中文,2-4个汉字',this);" onmouseout="divHidden();" />
				<s:select label="性别"
				       name="content.sex"
				       list="#{'男':'男', 
				       '女':'女'}"
				       value="男"
				/>
				<!--<s:textfield label="年龄" name="content.age" onmouseover="divShow('必填,10-70之间的数字',this);" onmouseout="divHidden();" />-->
				<s:textfield label="考生号" name="content.examid" onmouseover="divShow('必填,数字考生号',this);" onmouseout="divHidden();" />
				<s:textfield label="身份证号" name="content.pid" onmouseover="divShow('必填,身份证号',this);" onmouseout="divHidden();" />
				<s:textfield label="手机电话" name="content.mobile" onmouseover="divShow('必填,最多14个字符',this);" onmouseout="divHidden();" />
				<s:textfield label="固定电话" name="content.phone" onmouseover="divShow('必填,最多12个字符',this);" onmouseout="divHidden();" />			
				<s:select label="报考专业"
				       name="content.profession"
				       list="#{'汉语':'汉语', 
				       '汉语（学前教育方向）':'汉语（学前教育方向）',
				       '汉语（语文教育方向）':'汉语（语文教育方向）',
				       '文秘':'文秘',
				       '应用英语':'应用英语',
				       '应用英语（学前教育方向）':'应用英语（学前教育方向）',
				       '商务英语':'商务英语',
				       '旅游管理':'旅游管理',
				       '市场营销':'市场营销',
				       '物流管理':'物流管理',
				       '会计':'会计',
				       '会计电算化':'会计电算化',
				       '艺术设计':'艺术设计',
				       '音乐表演':'音乐表演',
				       '音乐表演（学前教育方向）':'音乐表演（学前教育方向）',
				       '舞蹈表演':'舞蹈表演',
				       '模具设计与制造':'模具设计与制造',
				       '机械制造与自动化':'机械制造与自动化',
				       '连锁经营管理':'连锁经营管理'}"
				       value="汉语"
				/>
				<s:file name ="upphoto" label ="上传照片" onmouseover="divShow('必填,320*240 小于20K的照片',this);" onmouseout="divHidden();"/> 
				<s:textarea label="附加说明" name="content.content" onmouseover="divShow('最多300个字符',this);" onmouseout="divHidden();" />
				
				<s:submit value="确定" />
			</s:form>
		</td>
	</tr>
	<tr>
		<td height="10">
		</td>
	</tr>
	<tr>
		<td><h4>报考注意事项</h4>
		</td>
	</tr>
	<tr>
		<td height="10">
		</td>
	</tr>
	<tr>
		<td><h5>1. 考生号为14位，高考报名时考证上考生号。</h5>
		</td>
	</tr>
	<tr>
		<td><h5>2. 姓名用字必须和身份证一致。</h5>
		</td>
	</tr>
	<tr>
		<td><h5>3. 手机电话、固定电话在录取期间必须保持畅通。</h5>
		</td>
	</tr>
</table>

</body>
</html>
