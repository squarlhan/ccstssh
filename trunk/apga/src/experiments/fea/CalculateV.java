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
        double[] inputs = {1.13,1.00,1.17,1.07,1.13,1.71,1.19,2.38,1.19,1.35,1.18,1.18,1.13,1.12,1.74,1.82,1.26,1.15,1.18,1.14,1.15,1.23,1.14,1.12};
//        double[] inputs = {2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.0,2.0,2.0,2.0,2.0,2.0,2.0,2.0,1.5,1.5,1.5,1.5,1.5,1.5,1.5,1.5};
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
        System.out.println(cv.Calculate(inputs));
	}
}


