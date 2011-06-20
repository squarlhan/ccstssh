<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>家长登陆</title>
<s:head />
</head>
<body>

<table align="center">
	<tr>
		<td align="right">
			<a href="ToGenearchLoginAndRegisterAction_register.action">注册</a>
		</td>
	</tr>
	<tr>
		<td class="redborder">
		&nbsp;&nbsp;&nbsp;&nbsp;注重发挥家庭的教育功能。教育子女的责任，要由父母共同承担，哪一位都不能放弃自己的责任；同时，父亲和母亲要为对方承担教育责任创造条件，在教育孩子上有分工有合作；只有在父母共同承担教育责任、对子女的教育影响力和谐互补的情况下，才有可能全方位发挥家庭的教育功能。
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;一个孩子的健康、健全成长，仅靠学校或仅靠家庭都是不够的：教师观察不到孩子在家的情况，家长也很难看到孩子在校的表现，需要的是两者之间的合力，教育才会有针对性和连贯性。应该说，这是校园人际关系中难度较大的一种关系。因为家长的职业不同、层次不同，教育孩子的观念也不同，要让他们都能与学校“步调一致”，真的很不容易。为培养创造性人才提供一个良好的大教育环境。教师与家长必须做到互相配合，和谐施教，共育新人。
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;任何教师，无论他具有多么丰富的实践经验和深厚的理论修养，都不可能把复杂的教育工作做得十全十美、不出差错。加之“旁观者清”，有时家长比教师更容易发现教育过程中的问题。因此，经常向家长征求意见，虚心听取他们的批评和建议，以改进自己的工作。这样做，也会使家长觉得教师可亲可信，从而诚心诚意地支持和配合教师的工作，维护教师的威信。
		</td>
	</tr>
	<tr>
		<td align="center">
			<s:form action="GenearchLoginAndRegisterAction!login">
				<div style="color: red"><s:fielderror /> <s:actionmessage /></div>
				<s:textfield label="用户名" name="user.name" onmouseover="divShow('必填',this);" onmouseout="divHidden();" />
				<s:password label="密码" name="user.password" onmouseover="divShow('必填',this);" onmouseout="divHidden();" />
				<s:submit value="确定" />
			</s:form>
		</td>
	</tr>
</table>			

</body>
</html>
