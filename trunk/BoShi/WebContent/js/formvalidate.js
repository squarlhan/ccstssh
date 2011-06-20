// 检查是否为电话号码  
function fucCheckTEL()    
{    
	var i,j,strTemp;    
	strTemp="0123456789-()# ";    
	for (i=0;i<tel.length;i++)    
	{    
		j=strTemp.indexOf(tel.charAt(i));    
		if (j==-1)    
		{   
			return false;    
		}    
	}   
	return true;    
}

// 判断输入是否为中文的函数      
function ischinese(s){  
	var ret=true;  
	for(var i=0;i<s.length;i++)  
	ret=ret && (s.charCodeAt(i)>=10000);  
	return ret;  
}

// 两次输入密码是否相同
function passwordequate()
{
	with(document.all){
		if(passwordA.value!=passwordB.value)
		{
			alert("两次输入的密码不相等")
			passwordA.value = "";
			passwordB.value = "";
			return  false;
		}
		return true;
	}
}

// 判断年龄
function age()
{
	if  (age.value<10 && 100<age)
	{ 
		alert("年龄必须在10-100之间");
		age.value="";
		return  false;
	}
	return true;
}