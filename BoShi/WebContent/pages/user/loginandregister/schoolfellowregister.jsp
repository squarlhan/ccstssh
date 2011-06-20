<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校友注册</title>
<s:head />
</head>
<body>

<table align="center">
	<tr>
		<td class="redborder">
		&nbsp;&nbsp;&nbsp;&nbsp;用户单独承担发布内容的责任。用户对服务的使用是根据所有适用于服务的地方法律、国家法律和国际法律标准的。用户承诺在网页上发布信息或者利用服务时必须符合中国有关法规(部分法规请见附录)，不得在网页上或者利用服务制作、复制、发布、传播以下信息：
　  		<br/>(a) 违反宪法确定的基本原则的；
　  		<br/>(b) 危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；
		<br/>(c) 损害国家荣誉和利益的；
		<br/>(d) 煽动民族仇恨、民族歧视，破坏民族团结的；
		<br/>(e) 破坏国家宗教政策，宣扬邪教和封建迷信的；
		<br/>(f) 散布谣言，扰乱社会秩序，破坏社会稳定的；
		<br/>(g) 散布淫秽、色情、赌博、暴力、恐怖或者教唆犯罪的；
		<br/>(h) 侮辱或者诽谤他人，侵害他人合法权益的；
		<br/>(i) 煽动非法集会、结社、游行、示威、聚众扰乱社会秩序的；
		<br/>(j) 以非法民间组织名义活动的；
		<br/>(k) 含有法律、行政法规禁止的其他内容的。
		<br/>如出现违反以上条款行为，本网站有权删除用户信息。一旦您点击“确定”，即表明您遵守以上条款。
		</td>
	</tr>
	<tr>
		<td align="center">
			<s:form action="SchoolfellowLoginAndRegisterAction!register" validate="true">
				<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
				<s:textfield label="用户名" name="user.name" onmouseover="divShow('必填,1-8个字符',this);" onmouseout="divHidden();" />
				<s:password label="密码" name="user.password" onmouseover="divShow('必填,4-10个字符',this);" onmouseout="divHidden();" />
				<s:textfield label="姓名" name="user.chineseName" onmouseover="divShow('必填,中文,2-4个汉字',this);" onmouseout="divHidden();" />
				<s:textfield label="年级" name="user.grade" onmouseover="divShow('必填,中文,1-16个汉字',this);" onmouseout="divHidden();" />
				<s:textfield label="班级" name="user.className" onmouseover="divShow('必填,中文,1-16个汉字',this);" onmouseout="divHidden();" />
				<s:submit value="确定" />
			</s:form>
		</td>
	</tr>
</table>			

</body>
</html>
