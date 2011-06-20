function divShow()
{
   if(arguments.length < 2)
     return;
     
   var msg,e,msgWidth,leftPosition,topPosition,backColor;
   msg = arguments[0];
   e   = arguments[1];
   //获取信息显示宽度
   msgWidth = msg.length*15;
 	//获取信息显示与对象横向位移
   leftPosition = 10;
//获取信息显示与对象纵向位移
   topPosition = 20;
   //获取信息显示的颜色
   backColor = "#7B865E";

   var divObj = document.getElementById("showMyMessage");
   divObj.style.background = backColor;
   divObj.innerHTML = msg;
   divObj.style.width = msgWidth;
   divObj.style.display = ""; 
   divObj.style.top = e.getBoundingClientRect().top + topPosition + document.body.scrollTop;
   divObj.style.left= e.getBoundingClientRect().left + leftPosition + document.body.scrollLeft;
}

function divHidden()
{
   document.getElementById("showMyMessage").style.display = 'none';
}