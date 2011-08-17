package server;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import org.dom4j.DocumentException;

import simulate.data.ESParamsInfo;
import simulate.data.MethodInfo;
import simulate.data.SimulatedUnParameter;
import LogManage.Data.TaskLogInfo;
import SimulateManage.TreadPool.ITreadTask;
import SimulateManage.TreadPool.TaskRunInfo;
import Tolerance.ResponseValue;
import experiments.Matrix;
import experiments.apga.APGA;
import experiments.apga.APGAFunction;


/**
 * 该类里面不用改什么大的东西，算法接口都不用改,构造函数可根据对应的算法参数做适当调整，构造方法里面的代码要适当修改，已注释标明
 * @author 
 *
 */
public class APGAOptimize
{
	/**
	 * 线程池管理
	 */
	private ITreadTask taskManage = null; 

	/**
	 * 任务运行信息
	 */
	private TaskRunInfo tri = null; 

	/**
	 * 编号
	 */
	private int nIterateCount = 1;

	/**
	 * 不确定参数信息
	 */
	private ArrayList<SimulatedUnParameter> orderParam = new ArrayList<SimulatedUnParameter>();

	/**
	 * 参数范围矩阵
	 */
	private Matrix consValue = null;

	/**
	 * 不确定参数组矩阵
	 */
	private Matrix lastPos = null;

	/**
	 * 目标函数值矩阵
	 */
	private Matrix pBest = null;

	/**
	 * 聚类遗传算法优化
	 * @param taskManage 线程池管理
	 * @param methodInfo 方法信息
	 * @param tri 任务运行信息
	 * @param arrParam 参数列表
	 * @param arrResponse 响应参数列表
	 * @param rangeFile 参数范围文件
	 * @param taskPopedom 任务通道信号量
	 * @param tasklog 任务日志信息
	 * @param globals 目标函数值
	 */
	public APGAOptimize(ITreadTask taskManage,MethodInfo methodInfo, TaskRunInfo tri,
			Vector<ArrayList<SimulatedUnParameter>> arrParam,
			Vector<ResponseValue> arrResponse, String rangeFile,
			Semaphore taskPopedom, TaskLogInfo tasklog, Double[] globals)
	{
		this.taskManage = taskManage;
		this.tri = tri;
		this.orderParam.clear();
		
		//注意：此处需要改为记录新方法的类的数据载体
		APGAParamsInfo apgaParamesInfo = (APGAParamsInfo)methodInfo.getParamStruct();
		
		
		// 解析待优化数据文件
		Matrix[] data = ParseParamValue(arrParam, rangeFile, orderParam,
				globals);
		this.lastPos = data[0];
		this.pBest = data[1];
		this.consValue = data[2];// 取值范围
		

		// fitness 要优化的函数  此处
		OptimizeFunction fun = new OptimizeFunction(orderParam, taskManage,
				tri, arrResponse, tasklog);
		fun.SetTaskPopedom(taskPopedom);
		//GATest ga = new GATest();
		// Matrix lastV = null;
		
		//此处的ES算法改为新建的Other算法类
	     APGA apga = new APGA();
		
		Object[] result = apga.Calculate(fun, apgaParamesInfo.getPc1(),
				apgaParamesInfo.getPc2(),apgaParamesInfo.getPm(),
				apgaParamesInfo.getK(), apgaParamesInfo.getPt(),
				consValue, lastPos, pBest, apgaParamesInfo.getNG());

		this.lastPos = (Matrix) result[0];
		this.pBest = (Matrix) result[1];
		this.consValue = (Matrix) result[2];
	}

	/**
	 * 聚类遗传算法优化
	 * @param lastPos    不确定参数组矩阵
	 * @param pBest      目标函数值矩阵
	 * @param consValue  参数范围矩阵
	 * @param taskManage 线程池管理
	 * @param methodInfo 方法信息
	 * @param tri 任务完成信息
	 * @param orderParam  参数顺序
	 * @param arrResponse 响应参数列表
	 * @param rangeFile 参数范围日志
	 * @param taskPopedom 任务通道信号量
	 * @param tasklog 任务日志信息
	 */
	public APGAOptimize(Matrix lastPos, Matrix pBest, Matrix consValue,
			ITreadTask taskManage,MethodInfo methodInfo,TaskRunInfo tri,
			ArrayList<SimulatedUnParameter> orderParam,
			Vector<ResponseValue> arrResponse, String rangeFile,
			Semaphore taskPopedom, TaskLogInfo tasklog)
	{
		// TODO Auto-generated constructor stub
		this.taskManage = taskManage;
		this.tri = tri;
		this.orderParam = orderParam;
		this.lastPos = lastPos;
		this.pBest = pBest;
		this.consValue = consValue;
		APGAParamsInfo apgaParamesInfo = (APGAParamsInfo)methodInfo.getParamStruct();
		
		OptimizeFunction fun = new OptimizeFunction(orderParam, taskManage,
				tri, arrResponse, tasklog);
		fun.SetTaskPopedom(taskPopedom);
		 APGA apga = new APGA();
			
			Object[] result = apga.Calculate(fun, apgaParamesInfo.getPc1(),
					apgaParamesInfo.getPc2(),apgaParamesInfo.getPm(),
					apgaParamesInfo.getK(), apgaParamesInfo.getPt(),
					consValue, lastPos, pBest, apgaParamesInfo.getNG());
		// GATest ga = new GATest();
		// Object[] result = ga.Calculate(fun, gaParamesInfo.getPc1(),
		// gaParamesInfo.getPc2(), gaParamesInfo.getPm(), /**0.1, 0.01,*/
		// gaParamesInfo.getK(),
//				gaParamesInfo.getPt(), consValue, lastPos, pBest,gaParamesInfo.getNG());

		nIterateCount = apga.getnIterateCount();
		this.consValue = (Matrix) result[2];
		this.lastPos = (Matrix) result[0];
		this.pBest = (Matrix) result[1];
	}

	/**
	 * @return 参数顺序
	 */
	public ArrayList<SimulatedUnParameter> GetParamOrder()
	{
		return orderParam;
	}

	
	/**
	 * 解析得到取值范围
	 * @param rangeFile 参数范围文件
	 * @return 参数范围矩阵
	 */
	private Matrix ParseRangeFile(String rangeFile)
	{
		// 读取值范围
		Matrix valueRange = null;

		ReadUnParamXML rangeReader = new ReadUnParamXML(rangeFile);

		try
		{
			rangeReader.resolvedUParameter();// 解析

			Vector<String> arrParamActive = rangeReader.getParameterActive();

			Double[] init = rangeReader.getInit().toArray(new Double[] {});
			Double[] min = rangeReader.getMin().toArray(new Double[] {});
			Double[] max = rangeReader.getMax().toArray(new Double[] {});
			if (min.length != max.length || min.length != arrParamActive.size()
					|| min.length != init.length)
			{
				System.out.println("GA Input Value Range MisMatch!");
				return null;
			}

			valueRange = new Matrix(2, min.length);// 初始化
			double[][] rangeData = valueRange.getData();// 得到取值范围

			for (int col = 0; col < min.length; ++col)
			{
				// 考虑参数是否活动，若不活动则将范围限制在初值
				if (arrParamActive.elementAt(col).equals("true"))
				{
					rangeData[0][col] = min[col]; // 最小值
					rangeData[1][col] = max[col];// 最大值
				}
				else
				{
					rangeData[0][col] = init[col];
					rangeData[1][col] = init[col];
				}
			}

		}
		catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Range Value File:");
		valueRange.show();
		return valueRange;
	}

	/**
	 * 获取参数信息
	 * @param arrParam 参数列表
	 * @param rangeFile参数范围文件
	 * @param orderParam参数顺序
	 * @param globals 目标函数值
	 * @return 不确定参数组矩阵，目标函数值矩阵，不确定参数范围矩阵 
	 */
	private Matrix[] ParseParamValue(
			Vector<ArrayList<SimulatedUnParameter>> arrParam, String rangeFile,
			ArrayList<SimulatedUnParameter> orderParam, Double[] globals)
	{
		Matrix[] value = ParseValueData(arrParam, orderParam, globals);

		Matrix valueRange = ParseRangeFile(rangeFile);// 取值范围

		return new Matrix[] { value[0], value[1], valueRange };
	}

    /**
     * 解析数据
     * @param arrParam  参数列表
     * @param orderParam不确定参数信息列表
     * @param globals   目标函数值
     * @return 参数组矩阵，对应目标函数值矩阵
     */
	private Matrix[] ParseValueData(
			Vector<ArrayList<SimulatedUnParameter>> arrParam,
			ArrayList<SimulatedUnParameter> orderParam, Double[] globals)
	{

		if (globals.length != arrParam.size())
		{
			System.out.println("Global Value Size MisMatch!");
			return null;
		}

		Vector<String> arrName = new Vector<String>();
		Vector<Vector<Double>> arrValue = new Vector<Vector<Double>>();
		Vector<String> arrSource = new Vector<String>();

		Vector<String> arrFiles = new Vector<String>();

		// 读取数据值(默认一个文件一行数据)
		for (int row = 0; row < arrParam.size(); ++row)
		{

			if (arrParam.get(row).size() == 0)
			{
				continue;
			}
			// 不确定参数
			ArrayList<SimulatedUnParameter> factor = arrParam.get(row);// Vector(row)
			if (row == 0 || arrName.isEmpty())
			{// Vector(0)
				for (int col = 0; col < factor.size(); ++col)
				{// List(col)
					SimulatedUnParameter param = factor.get(col);// 以下是给参数添加属性
					arrName.add(param.getUnParamName());
					arrSource.add(param.getSource());
					arrFiles.add(param.getFiles());
					//
					orderParam.add(new SimulatedUnParameter(param
							.getUnParamName(), 0, param.getSource(), param
							.getFiles(), param.getStep()));
					orderParam.get(col).setDigits(param.getDigits());
				}
			}
			else if (factor.size() != arrName.size())
			{// 参数不匹配
				System.out.println(String.format(
						"GA Value: %d Param Num MisMatch!", row));
				return null;
			}

			Vector<Double> factorValue = new Vector<Double>(); // 因子
			for (int col = 0; col < factor.size(); ++col)
			{
				factorValue.add(factor.get(col).getValue());// 放入因子
			}
			arrValue.add(factorValue);
		}
		// 初始值
		Matrix pInit = new Matrix(1, arrValue.size());// pInit[1][arrValue.size]
		double[][] pData = pInit.getData();
		// 赋值
		Matrix value = new Matrix(arrValue.size(), arrName.size());
		double[][] valueData = value.getData();
		for (int row = 0; row < arrValue.size(); ++row)
		{
			Vector<Double> rowValue = arrValue.get(row);
			for (int col = 0; col < arrName.size(); ++col)
			{
				valueData[row][col] = rowValue.get(col);// set valueData;
			}

			pData[0][row] = globals[row];// 获取globals目标函数
		}

		return new Matrix[] { value, pInit };
	}

	
	/**
	 * 获取不确定参数组矩阵
	 * @return 不确定参数组矩阵
	 */
	public Matrix GetLastPos()
	{
		// TODO Auto-generated method stub
		return lastPos;
	}

	/**
	 * 获取评价值矩阵
	 * @return 评价值矩阵
	 */
	public Matrix GetPartBest()
	{
		// TODO Auto-generated method stub
		return pBest;
	}

	/**
	 * 获得模拟序号
	 * @return 模拟序号
	 */
	public int GetIteratCount()
	{
		// TODO Auto-generated method stub
		return nIterateCount;
	}

	/**
	 * 获得不确定参数范围矩阵
	 * @return 不确定参数取值范围矩阵
	 */
	public Matrix GetConsValue()
	{
		// TODO Auto-generated method stub
		return consValue;
	}
}
