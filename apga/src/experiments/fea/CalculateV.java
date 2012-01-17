package experiments.fea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import experiments.fea.CalculateV.Bar;
import experiments.fea.CalculateV.Point;

public class CalculateV {
	
	class Point{
		double x;
		double y;
		double z;
	}
	class Bar{
		int point1;
		int point2;
		int type;
	}
	
	@SuppressWarnings("null")
	public double Calculate(double[] inputs){
//		double[] inputs={2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.0,2.0,2.0,2.0,2.0,2.0,2.0,2.0,1.5,1.5,1.5,1.5,1.5,1.5,1.5,1.5};
	    for(int i=0;i<inputs.length;i++){
	    	int max=i;
	    	for(int j=i+1;j<inputs.length;j++){
	    		if(inputs[max]<inputs[j]){
	    			max=j;
	    		}
	    	}
	    	if(max!=i){
	    	double temp=inputs[max];
	    	inputs[max]=inputs[i];
	    	inputs[i]=temp;
	    	}
	    }
	    	
		Exectute exe=new Exectute();
		exe.FirstCallFrotran();
		exe.NewArea(inputs);
		exe.CaculateOutputData();
		List<Point> points = null;
		List<Bar> bars = null;
		List<Double> areas = null;
		
		double v = 0.0;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		File file = new File("stap90.in");
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				
				if (flag1) {				 
					double x = Double.parseDouble(line.substring(20, 30).trim());
					double y = Double.parseDouble(line.substring(30, 40).trim());
					double z = Double.parseDouble(line.substring(40, 50).trim());
					Point temp = new Point();
					temp.x = x;
					temp.y = y;
					temp.z = z;
					points.add(temp);
				}
				if(flag2){
					double temp = Double.parseDouble(line.substring(15).trim());
					areas.add(temp);
				}
                if(flag3){
                	int point1 = Integer.parseInt(line.substring(5,10).trim());
            		int point2 = Integer.parseInt(line.substring(10,15).trim());
            		int type = Integer.parseInt(line.substring(15,20).trim());
            		Bar bar = new Bar();
            		bar.point1 = point1-1;
            		bar.point2 = point2-1;
            		bar.type = type-1;
            		bars.add(bar);
				}
				if(line.equals("  770    1    1    1")){
					flag1 = true;
					points = new ArrayList<Point>();
				}
				if(points!=null&&points.size()==770){
					flag1 = false;
				}
				if(line.equals("    1 2849   24")){
					flag2 = true;
					areas = new ArrayList<Double>();
				}
				if(flag2 == true&&flag3 == false&&areas!=null&&areas.size()==24){
					flag2 = false;
					flag3 = true;
					bars = new ArrayList<Bar>();
				}
				if(bars!=null&&bars.size()==2849){
					flag3 = false;
				}
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Bar bar:bars){
			Point p1 =points.get(bar.point1);
			Point p2 =points.get(bar.point2);
			double t = areas.get(bar.type);
			double l = Math.sqrt(Math.pow(p1.x-p2.x, 2.0)+Math.pow(p1.y-p2.y, 2.0)+Math.pow(p1.z-p2.z, 2.0));
			v = v+l*t;
		}
		return v;
	}
    
	
	public static void main(String[] args){
        
//最新的ap的平均宽度
//        double[] apm  =  {1.02850, 1.05521666666667, 1.05616666666667, 1.08621666666667, 1.07186666666667, 1.10690, 1.08546666666667, 1.06050, 1.08033333333333, 1.03441666666667, 1.06001666666667, 1.07948333333333, 1.05291666666667, 1.07648333333333, 1.04280, 1.06656666666667, 1.04225, 1.06423333333333, 1.10775, 1.08720, 1.06626666666667, 1.06146666666667, 1.09063333333333, 1.04393333333333};
//        double[] gam = {1.05896666666667, 1.04701666666667, 1.03698333333333, 1.0295, 1.03186666666667, 1.0341, 1.04675, 1.03391666666667, 1.04078333333333, 1.02241666666667, 1.02395, 1.0296, 1.02138333333333, 1.0695, 1.02483333333333, 1.0632, 1.03393333333333, 1.04251666666667, 1.03941666666667, 1.03265, 1.05318333333333, 1.03548333333333, 1.02773333333333, 1.01255};
       double[] inputs = {1.08, 1.02, 1.03, 1.03, 1.03, 1.04, 1.03, 1.01, 1.03, 1.02, 1.03, 1.04, 1.04, 1.01, 1.03, 1.11, 1.04, 1.03, 1.02, 1.01, 1.02, 1.03, 1.02, 1.02};
		//		String str= "   26    0    0    0-1.983e+01-2.611e+00 1.500e+01    0";
//		System.out.println(str.indexOf("-"));
//		System.out.println(str.substring(20, 30).trim());
//		System.out.println(str.substring(30, 40).trim());
//		System.out.println(str.substring(40, 50).trim());
//		String str2 = "    1 2.070e+11 1.776e-03";
//		System.out.println(str2.indexOf("1.7"));
//		System.out.println(str2.substring(15).trim());
//		String str3 = " 2849  715  283   24    0";
//		System.out.println(str3.substring(5, 10).trim());
//		System.out.println(str3.substring(10, 15).trim());
//		System.out.println(str3.substring(15, 20).trim());
        CalculateV cv = new CalculateV();
        System.out.println("origin: "+cv.Calculate(inputs));
//        System.out.println("ap4: "+cv.Calculate(apm));
//        System.out.println("ga4: "+cv.Calculate(gam));
	}
}


