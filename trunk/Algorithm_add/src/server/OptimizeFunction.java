  /**      
 * @{#} OptimizeFunction.java Create on 2009-12-31  
 *      
 * Copyright (c) 2009 by tgeosmart.      
 */    
package server;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import simulate.data.SimulatedUnParameter;
import LogManage.Data.TaskLogInfo;
import SimulateManage.TreadPool.ITreadTask;
import SimulateManage.TreadPool.TaskRunInfo;
import Tolerance.ResponseValue;
import experiments.Matrix;
import experiments.PSOFunction;
import experiments.apga.APGAFunction;
import experiments.es.ESFunction;
import experiments.ga.GAFunction;
/**
 * 优化功能实现类 
 * @author <a href="mailto:huangliang@tgeosmart.cn">Huang.Liang</a>     
 * @version 1.0      
 */
// 此处要添加OtherFunction接口即implements PSOFunction,GAFunction,ESFunction,OtherFunction  添加完了就可以了，不需要其它修改了
public class OptimizeFunction implements PSOFunction,GAFunction,ESFunction,APGAFunction
{
	/**
	 *  不确定参数顺序
	 */
	private ArrayList<SimulatedUnParameter> orderParam = null;
	/**
	 *  线程池管理
	 */
	private ITreadTask taskManage = null;
	/**
	 * 任务运行信息
	 */
	private TaskRunInfo tri = null;
	/**
	 * 响应参数 
	 */
	private Vector<ResponseValue> arrResponse = null;
	/**
	 * 模拟器生成文件路径 
	 */
	private String resultFilePath = "";
	/**
	 * 随机值
	 */
	private Random random = new Random();
	
	/**
	 *任务权限 
	 */
	private Semaphore taskPopedom;
	
	/**
	 * 设置任务权限
	 * @param taskPopedom 任务模拟信号量
	 */
	public void SetTaskPopedom(Semaphore taskPopedom)
	{
		this.taskPopedom = taskPopedom;
	}
	/**
	 * 用于用户任务操作记录
	 */
	TaskLogInfo tasklog;
	/**
	 * 构造函数
	 * @param orderParam 不确定参数顺序
	 * @param taskManage 线程池管理
	 * @param tri		 任务运行信息
	 * @param simulate   模拟器路径
	 * @param arrResponse 响应参数
	 */
	public OptimizeFunction (ArrayList<SimulatedUnParameter> orderParam, ITreadTask taskManage, TaskRunInfo tri, Vector<ResponseValue> arrResponse,TaskLogInfo tasklog) {
		this.orderParam = orderParam;//执行顺序
		this.taskManage = taskManage;
		this.tri = tri;
		this.resultFilePath = tri.sModelFile + File.separator + tri.ti.sModelName;
		this.arrResponse = arrResponse; 
		this.tasklog = tasklog;
	}
	
	/* (non-Javadoc)
	 * @see experiments.PSOFunction#excute(experiments.Matrix, int)
	 */
	public Double[] excute(Matrix data, int nIterateCount)
	{
		System.out.println("Matrix Data:");
		data.show();//打印数据
		
		if (orderParam.size() != data.getN()) {
			System.out.println("Param Size MisMatch");
		}
		
		Vector<ArrayList<SimulatedUnParameter> > parameterFile = new Vector< ArrayList<SimulatedUnParameter> >(data.getM());
		double[][] value = data.getData(); 
		for (int row = 0; row < data.getM(); ++row) {
			
			ArrayList<SimulatedUnParameter> lstParam = new ArrayList<SimulatedUnParameter>(data.getN());
			
			for (int col = 0; col < data.getN(); ++col) {
				SimulatedUnParameter old = orderParam.get(col);
				SimulatedUnParameter param = new SimulatedUnParameter(old.getUnParamName(), 0, old.getSource(), old.getFiles(), old.getStep());//不确定参数
				param.setDigits(old.getDigits());
				param.setValue(value[row][col]);
				lstParam.add(param);
			}
			
			parameterFile.add(lstParam);
		}
		System.out.println("Uncertain Value:");
		for (int k = 0; k < parameterFile.size(); ++k) {
			ArrayList<SimulatedUnParameter> lst = parameterFile.get(k);
			for (int j = 0; j < lst.size(); ++j) {
				System.out.println(lst.get(j).getValue());
			}
		}
		
		tri.nStartIndex += nIterateCount;
		// 任务执行是否失败
		boolean bFailed;
		// 任务失败计数
		int nCount = 0;
		SimulateAppraise appraise = new SimulateAppraise();//解析
		appraise.setGlobals(new Double[data.getM()]);//设置global值
		do {
			
			bFailed = false;
			
			CountDownLatch eventId = taskManage.AddTask(tasklog, tri, parameterFile, taskPopedom, appraise);
			try {
				eventId.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				bFailed = true;
				nCount++;
				e.printStackTrace();
			}
		} while(bFailed && nCount < 5);
		
		// 模拟计算等待失败
		if (bFailed == true) {
			return null;
		}
				
		return appraise.getGlobals();
	}
}
