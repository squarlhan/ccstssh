//--------------- 主导航条内容 ------------//
var mainLayer=new Array("首&nbsp;&nbsp;页","学校概况","新闻公告","招生信息","就业指南","学生工作","学习园地","教学科研","高中部","师芳中学");//主导航栏目
var mainLayerHttp=new Array("/BoShi/pages/user/ListWelcomePageNewsAction.action","/BoShi/pages/user/ShowUserJuniorCollegeCircumstanceAction.action","/BoShi/pages/user/ListJuniorCollegePolicyAfficheNewsAction.action","/BoShi/pages/user/ListJuniorCollegeRecruitStudentsNewsAction.action","/BoShi/pages/user/ShowUserJuniorCollegeOccupationCircumstanceAction.action","/BoShi/pages/user/ShowUserJuniorCollegePolicyRuleAction.action","/BoShi/pages/user/ShowUserJuniorCollegeCultureAction.action","/BoShi/pages/user/ListJuniorCollegeTeachingActivityNewsAction.action","/BoShi/pages/user/ShowUserSeniorHighSchoolCircumstanceAction.action","/BoShi/pages/user/ShowUserJuniorHighSchoolCircumstanceAction.action");//主导航栏目

//--------------- 次导航条内容 ------------//
var subLayer0=new Array(); //导航栏目一下的次级栏目
var subLayerHttp0=new Array();
var subLayer1=new Array("学校概况","校徽介绍","现任领导","院系设置","科室介绍","媒体报道","校园景色"); //导航栏目一下的次级栏目
var subLayerHttp1=new Array("/BoShi/pages/user/ShowUserJuniorCollegeCircumstanceAction.action","/BoShi/pages/user/ShowUserJuniorCollegeIntroduceSchoolBadgeAction.action","/BoShi/pages/user/ShowUserJuniorCollegeIncumbentLeaderAction.action","/BoShi/pages/user/ShowUserJuniorCollegeCollegeSetAction.action","/BoShi/pages/user/ListJuniorCollegeSectionOfficeAction.action","/BoShi/pages/user/ListCoalReportAction.action","/BoShi/pages/user/campusscene/campusscene.jsp");//主导航栏目一下的次级栏目连接地址
var subLayer2=new Array("政策公布","通知公告","学校新闻");//导航栏目二的次级栏目
var subLayerHttp2=new Array("/BoShi/pages/user/ListJuniorCollegePolicyAfficheNewsAction.action","/BoShi/pages/user/ListJuniorCollegeActivityNotifyAction.action","/BoShi/pages/user/ListCollegeNewsAction.action");//主导航栏目二的次级栏目连接地址
var subLayer3=new Array("招生新闻","招生简章","专业设置","网上报名","录取查询");
var subLayerHttp3=new Array("/BoShi/pages/user/ListJuniorCollegeRecruitStudentsNewsAction.action","/BoShi/pages/user/ListJuniorCollegeRecruitStudentsRulesAction.action","/BoShi/pages/user/ShowUserJuniorCollegeSpecializedSetAction.action","/BoShi/pages/user/ToAddJuniorCollegeOnlineSignUpNewsAction.action","/BoShi/pages/user/ToQueryJuniorCollegeOnlineSignUpNewsAction.action");
var subLayer4=new Array("招聘信息","就业情况","人才培养","对外交流");
var subLayerHttp4=new Array("/BoShi/pages/user/ListJuniorCollegeInviteNewsAction.action","/BoShi/pages/user/ShowUserJuniorCollegeOccupationCircumstanceAction.action","/BoShi/pages/user/ShowUserJuniorCollegePersonAbilityTrainAction.action","/BoShi/pages/user/ShowUserJuniorCollegeExternalCommunionAction.action");
var subLayer5=new Array("政策法规","学生管理","心理咨询","学生会工作","环境管理");
var subLayerHttp5=new Array("/BoShi/pages/user/ListPolicyRuleAction.action","/BoShi/pages/user/ListStudentManagement.action","/BoShi/pages/user/ToStudentLoginAndRegisterAction_login.action","/BoShi/pages/user/ListStudentsWorkAction.action","/BoShi/pages/user/ListEnvirManagementAction.action");
var subLayer6=new Array("校园文化","数字图书馆","成绩查询");
var subLayerHttp6=new Array("/BoShi/pages/user/ShowUserJuniorCollegeCultureAction.action","/BoShi/pages/user/ListLearningResourceShareAction.action","/csyweb");
var subLayer7=new Array("教学活动","教学管理","科研成果");
var subLayerHttp7=new Array("/BoShi/pages/user/ListJuniorCollegeTeachingActivityNewsAction.action","/BoShi/pages/user/ListJuniorCollegeTeachingManageNewsAction.action","/BoShi/pages/user/ListJuniorCollegeScientificResearchNewsAction.action");
var subLayer8=new Array("高中概况","新闻信息","通知公告","优秀教师","招生信息","家长专栏");
var subLayerHttp8=new Array("/BoShi/pages/user/ShowUserSeniorHighSchoolCircumstanceAction.action","/BoShi/pages/user/ListSeniorHighSchoolNewsAction.action","/BoShi/pages/user/ListSeniorNoticesAction.action","/BoShi/pages/user/ShowUserSeniorHighSchoolExcellenceTeacherAction.action","/BoShi/pages/user/ListSeniorHighSchoolRecruitStudentsNewsAction.action","/BoShi/pages/user/ToGenearchLoginAndRegisterAction_login.action");
var subLayer9=new Array("师芳概况","新闻信息","通知公告","优秀教师","招生信息","家长专栏");
var subLayerHttp9=new Array("/BoShi/pages/user/ShowUserJuniorHighSchoolCircumstanceAction.action","/BoShi/pages/user/ListJuniorHighSchoolNewsAction.action","/BoShi/pages/user/ListJuniorNoticesAction.action","/BoShi/pages/user/ShowUserJuniorHighSchoolExcellenceTeacherAction.action","/BoShi/pages/user/ListJuniorHighSchoolRecruitStudentsNewsAction.action","/BoShi/pages/user/ToGenearchLoginAndRegisterAction_login.action");



//--------------- 主导航条Table参数调整 ------------//
var mainTableTdWidth=90; //每个TD的宽度，调整主导航内容间距
var mainTableTdHeight=20;
var mainTableBorder=0; //调整主导航表格边框宽度
var mainTableCellspacing=0; //调整主导航表格Cellspacing
var mainTableCellpadding=0; //调整主导航表格Cellpadding
var mainTableBordercolor=""; //调整主导航表格编框颜色
var mainTableBackgroundImg=""; //调整主导航表格背景图片url地址

//--------------- 次导航条Table参数调整 ------------//
var subTableBorder=0; //调整次导航条表格边框宽度
var subTableCellspacing=0; //调整次导航条表格Cellspacing
var subTableCellpadding=1; //调整次导航条表格Cellpadding
var subTableBordercolor=""; //次导航条表格编框颜色
var subTableBackgroundImg=""; //次导航条表格背景图片url地址
var sbuTabbleTop=20; //次导航表格上下微调
var sbuTabbleLeft=0; //次导航表格左右微调

//--------------- 系统参数*请勿调整 ------------//
var layerMax=mainLayer.length+10;
var layerName="index"

//--------------- 生成下拉菜单 ------------//
function createMainLayer(){ 
document.write("<table align=center border=0 cellspacing=0 cellpadding=0><tr><td><div id='wall' onmouseout=layervib('visible','"+layerMax+"') style='position:relative; left:0px; top:0px; width:100%; z-index:1' ><table height="+mainTableTdHeight+" border='"+mainTableBorder+"' cellspacing='"+mainTableCellpadding+"' cellpadding='"+mainTableCellpadding+"'><tr>");
for(i=0;i<mainLayer.length;i++){
document.write("<td align=center width='"+mainTableTdWidth+"' height='"+mainTableTdHeight+"' onmouseover=layervib('visible','"+i+"')><a href='"+mainLayerHttp[i]+"' class='menutop'>"+mainLayer[i]+"</a></td>");
}
document.write("</tr></table>");

for(j=0;j<mainLayer.length-2;j++){
createSubLayer(j,110,0);
}
//for(j=mainLayer.length-2;j<mainLayer.length;j++)
//{
createSubLayer(mainLayer.length-2,110,0);
createSubLayer(mainLayer.length-1,110,0);
//}
document.write("</div></td></tr></table>");
}


//--------------- 生成每项下拉菜单内容 ------------//
function createSubLayer(num,divWidth,offset){
var subLayerName= layerName +num;
var subLayerLeft=(mainTableTdWidth*num)+mainTableCellpadding+mainTableBorder;
var subLayerList=eval("subLayer"+num);
var subLayerLeftMargin=subLayerLeft+sbuTabbleLeft-offset-15;
//var LayerID=eval("Layer"+num);
var subLayerHttpList=eval("subLayerHttp"+num);
document.write("<div id='"+subLayerName+"' style='position:absolute; left:"+subLayerLeftMargin+"px; top:"+((mainTableBorder+mainTableCellspacing+mainTableCellpadding)*2+sbuTabbleTop)+"px; visibility: hidden' onmouseover=layervib('visible','"+num+"') onmouseout=layervib('visible','"+layerMax+"')>");
if(subLayerList.length!=0){
document.write("<table width='"+divWidth+"' cellspacing='"+subTableCellpadding+"' cellpadding='"+subTableCellpadding+"' bgcolor=#FFFFCC ") 
for(h=0;h<subLayerList.length;h++){ 
//var itemID=eval('item_'+num+'_'+h);
document.write("<tr><td id='item"+num+"_"+h+"' align=center ><a href='"+ subLayerHttpList [h]+"' class='menu'>"+subLayerList[h]+"</a></td></tr>");
//document.write("<tr><td height='1' width='100%'></td></tr>");
//document.write("<tr><td width='100%'><a href='"+ subLayerHttpList [h]+"'>"+subLayerList[h]+"</a></td></tr>");
}
document.write("</table>");
}
document.write("</div>"); 
}

//Highlight function.  Change the specified object's class to the specified class name.  Class must be in main CSS file or in page itself.
function changelight(row, stylename) {
eval('document.all.' + row + '.className = "' + stylename + '"');

}


//------------------------------次菜单显隐控制--------------------------//
function layervib(type,num){
var H=type;
var temp=(H='visible'?'hidden':'visible')
for(var i=0;i<mainLayer.length;i++){
var E=eval('document.all.index'+i+'.style');
var H=eval(i);
if(i==num){E.visibility=type}else{E.visibility=temp};
}
}

function switchCell(n) {
	for(i=0;i<navcell.length;i++){
		navcell[i].className="tab-off";
		tb[i].style.display="none";
	}
	navcell[n].className="tab-on";
	tb[n].style.display="block";
	//tb_content.height=screen.availHeight-tb_header.clientHeight-tb_navbar.clientHeight-tb_footer.clientHeight-64;
}