package experiments.fea;
import java.util.*;
import org.jgap.*;


public class Exectute {
private static List OcdList,MemList;
private static List OutputData;
private static final int NUMEG=1, NLCASE=1,MODEX=1,LL=1,IDIRN=3,NPAR1=1,NPAR3=24,KG=0;
private static final double[] E_N={207.0e9,207.0e9,207.0e9},AREA_N={196.25e-5,125.6e-5,70.65e-5};
private static final double R=Math.pow(625.0, 1.0/3),BALLRAEA=2*Math.PI*(R)*(R),SNOW=600*0.5,FLOAD=(BALLRAEA*SNOW*9.8),MAX_FORCE=2.35e8;
private static final double[]  E=new double[NPAR3], AREA=new double[NPAR3];
private static int MTYP=0;
private static double SumArea=0.0;
private IChromosome IChrom=null;
public Exectute(IChromosome a_subject){
	this.IChrom=a_subject;
}
public Exectute(){
}
private static void Init(){
	for(int i=0;i<NPAR3;i++){
		E[i]=E_N[i/8];
		AREA[i]=AREA_N[i/8]+5*1.0e-5*(i%8);
	}
}
public  void NewArea(double[] area){
	try{
		int length=E_N.length;
//		System.out.println(area.length);
	for(int i=0;i<AREA.length/length;i++){
		for(int j=0;j<length;j++){
		AREA[j*(AREA.length/length)+i]=area[i*length+j]*area[i*length+j]*3.14*0.0001;
		}
	}
	}catch(Exception e){
		System.err.println("the nember of areas is not enough! please check input !");
		e.printStackTrace();
		
	}
//	for(int i=0;i<AREA.length;i++){
//		if(i%8==0){
//			System.out.println();
//		}
//		System.out.print(AREA[i]+" ");
//	
//	
//	}
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
 private   double GetResult(){
	 
	 List RList=new ArrayList();
	 List RList1=new ArrayList();
	 List RList2=new ArrayList();
	 InputResultData ISD=new InputResultData("stap90.out");
	 List ResultList=ISD.GetInputData( " D I S P L A C E M E N T S");
	 Iterator itor=ResultList.iterator();
	 double max1=0,max2=0,min2=Double.MAX_VALUE ,average=0.0;
	 int i=0,num=0;
	 
	 while(itor.hasNext()){
		 List list=(List)itor.next();
		 Iterator it=list.iterator();
		 
		 
		 it.hasNext();
		 it.next();
		 if(i++<OcdList.size()){
			 double temp=0;
			 while(it.hasNext()){
				 double d=Double.parseDouble((String)it.next());
				 d=Math.abs(d);
				 temp+=d*d;
			 }
			 if(max1<temp){
				 max1=temp;
			 }
			 RList1.add(temp);
//			 System.out.println(list);
		 }
		 else{
			 double temp=0;
			 it.hasNext();
			 it.next();
		
			 if(it.hasNext())
			 temp=Double.parseDouble((String)it.next());
			 temp=Math.abs(temp);
			 average+=temp;
			 ++num;
			 if(max2<temp){
				 max2=temp;
			 }
			 if(min2>temp&&temp!=0){
				 min2=temp;
//				 System.out.println(temp);
			 }
				 
			 RList2.add(temp);
//			 System.out.println(R+"  "+BALLRAEA+"   "+FLOAD);
//			 System.out.println(list);
		 }
	 
	 }
	 average/=num;
//	 System.out.println(num);
	 if(max2>MAX_FORCE){
		 average=-1;
	 }
	 max1=Math.sqrt(max1);
	 RList1.add(max1);
	 RList2.add(max2);
	 RList2.add(average);
	 RList2.add(min2);
	 RList.add(RList1);
	 RList.add(RList2);
	 if(IChrom!=null) IChrom.SetFitnessList(RList);

//	 System.out.println(RList1.size());
//	 System.out.println(RList2.size());
//	 System.out.println(max1+"    "+max2+" "+average);
//	 return average;
	 return max2;
	 
 }
 public  double CaculateOutputData(){
	 double max=0;
	 List  list=new ArrayList();
	 list.add(OcdList.size());
	 list.add(NUMEG);
	 list.add(NLCASE);
	 list.add(MODEX);

	 OutputData=new ArrayList();
	 OutputData.add(list);

	 
	 Iterator itor=OcdList.iterator();
//	 System.out.println(OcdList.size());
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
   if((i<OcdList.size()/2-1)||(i==OcdList.size()-2)){
   SubList2.add(FLOAD/OcdList.size());
   }
   else{
	   SubList2.add(0);
   }
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
//	 System.out.println(SubList4.size());
 }
 
 Iterator Eitor= MemList.iterator();
 
 SumArea=0.0;
 while(Eitor.hasNext()){
	 List subList5=(List)Eitor.next();
	 List EList=new ArrayList();
	 EList.add(Integer.parseInt((String)subList5.get(0)));
	 EList.add(Integer.parseInt((String)subList5.get(6)));
	 EList.add(Integer.parseInt((String)subList5.get(7)));
	 
	 MTYP= Integer.parseInt((String)subList5.get(3));
	 SumArea+=(Math.sqrt(AREA[MTYP-1]/3.14));
//	 System.out.println(Integer.parseInt((String)subList5.get(0))+"---->"+MTYP);
	
	 EList.add(MTYP);
	 EList.add(KG);
	 OutputData.add(EList); 
 }
 max=cac();
 return max;
 }
 
}
