package experiments.fea;
import java.util.*;


public class Exectute {
private static List OcdList,MemList;
private static List OutputData;
private static final int NUMEG=1, NLCASE=1,MODEX=1,LL=1,IDIRN=3,NPAR1=1,NPAR3=5,KG=0;
private static final double E_N=207.0e9,AREA_N=120e-6;
private static final double FLOAD=1000;
private static final double[]  E=new double[NPAR3], AREA=new double[NPAR3];
private static int MTYP=0;
private static void Init(){
	for(int i=0;i<NPAR3;i++){
		E[i]=E_N;
		AREA[i]=AREA_N-i*12e-6;
	}
}
public  void NewArea(double[] area){
	for(int i=0;i<AREA.length;i++){
		AREA[i]=Math.pow(area[i]*0.01, 2)*Math.PI;
	}
}
 public  void FirstCallFrotran(){
//	 double max=0;
	 String[] strings={"*.txt","*.txt"};
	 InputStructureData[] Isd=new InputStructureData[2];
	 Isd[0]=new InputStructureData("NODES.txt");
	 Isd[1]=new InputStructureData("ELEMENTS.txt");
	 
	 OcdList=Isd[0].GetInputData();
	 MemList=Isd[1].GetInputData();
	 Init();
//	 max=CaculateOutputData();
//	 return max;
 }
 private double cac(){
	 double max=0;
	 OutputInFortran OF=new OutputInFortran(OutputData);
	 OF.OutputtoFile();
	 
	 boolean flag=false;
	 ProcessFortranExe PF=new ProcessFortranExe();
	 flag=PF.Fortran();
	 if(!flag){
		 System.out.println("Fortran Error!");
		 
	 }
	 max=GetResult();
	 return max;
	 }
 public   double GetResult(){
	 
	 List RList=new ArrayList();
	 List RList1=new ArrayList();
	 List RList2=new ArrayList();
	 InputResultData ISD=new InputResultData("stap90.out");
	 List ResultList=ISD.GetInputData( " D I S P L A C E M E N T S");
	 Iterator itor=ResultList.iterator();
	 double max=0;
	 int i=0;
	 while(itor.hasNext()){
		 List list=(List)itor.next();
		 Iterator it=list.iterator();
		 
		 it.hasNext();
		 it.next();
		 if(i++<OcdList.size()){
			 double temp=0;
			 while(it.hasNext()){
				 double d=Double.parseDouble((String)it.next());
				 temp+=d*d;
			 }
			 if(max<temp){
				 max=temp;
			 }
			 RList1.add(list);
//			 System.out.println(list);
		 }
		 else{
			 RList2.add(list);
//			 System.out.println(list);
		 }
	 
	 }
	 max=Math.sqrt(max);
	 RList1.add(max);
	 RList.add(RList1);
	 RList.add(RList2);
//	 System.out.println(max);
	 return max;
	 
 }
 private  double CaculateOutputData(){
	 double max=0;
	 List  list=new ArrayList();
	 list.add(OcdList.size());
	 list.add(NUMEG);
	 list.add(NLCASE);
	 list.add(MODEX);

	 OutputData=new ArrayList();
	 OutputData.add(list);

	 
	 Iterator itor=OcdList.iterator();
	 System.out.println(OcdList.size());
     while(itor.hasNext()){
    	 List  SubList=(List)itor.next();
    	 double length=SubList.size()+1;
    	 Iterator sitor=SubList.iterator();
    	 List iList=new ArrayList();
    	 for(int i=0;i<length;i++){
    		 if((i>=1&&i<=3)||i==7){
    			 iList.add(0);
    		 }
    		 else if(i!=0){
    			 iList.add(Double.parseDouble((String)sitor.next()));
    		 } 
    		 else{
    			 iList.add(Integer.parseInt((String)sitor.next()));
    		 }
    	 }
//    	 need replace
    	 int num=(Integer)iList.get(0);
    	 for(int j=0;j<6;j++){
    		 if((num==j*8)||(num==j*8+384)){
    			 iList.set(1, 1);
    			 iList.set(2, 1);
    			 iList.set(3, 1);
    		 }
    	 }
    	 OutputData.add(iList); 
     }
     
   List SubList1=new ArrayList();
   SubList1.add(LL);
   SubList1.add(OcdList.size());
   OutputData.add(SubList1);
   
   for(int i=0;i<OcdList.size();i++){
   List SubList2=new ArrayList();
   SubList2.add(i+1);
   SubList2.add(IDIRN);
   SubList2.add(FLOAD);
   OutputData.add(SubList2);
   }
   
 List SubList3=new ArrayList();
 SubList3.add(NPAR1);
 SubList3.add(MemList.size());
 SubList3.add(NPAR3);
 OutputData.add(SubList3);
 

 for(int i=0;i<NPAR3;i++){
	 List SubList4=new ArrayList();
	 SubList4.add(i+1);
	 SubList4.add(E[i]);
	 SubList4.add(AREA[i]);
	 OutputData.add(SubList4);
 }
 
 Iterator Eitor= MemList.iterator();
 int i=0;
 while(Eitor.hasNext()){
	 List subList5=(List)Eitor.next();
	 List EList=new ArrayList();
	 EList.add(Integer.parseInt((String)subList5.get(0)));
	 EList.add(Integer.parseInt((String)subList5.get(6)));
	 EList.add(Integer.parseInt((String)subList5.get(7)));
//	 need replace
	 switch(++i){
	 case 1:           MTYP=1;break;
	 case 385:       MTYP=2;break;
	 case 1105:     MTYP=3;break;
	 case 1489:     MTYP=4;break;
	 case 2209:     MTYP=5;break;
	 default:         break;
	 }
	 EList.add(MTYP);
	 EList.add(KG);
	 OutputData.add(EList); 
 }
 max=cac();
 return max;
 }
 
}
