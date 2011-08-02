/**      
 * @{#} RunSimulate.java Create on 2009-12-18  
 *      
 * Copyright (c) 2009 by tgeosmart.      
 */
package server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import org.dom4j.DocumentException;

import simulate.data.BayesParamsInfo;
import simulate.data.MethodInfo;
import simulate.data.SimulatedUnParameter;
import simulate.data.TaskFeedbackInfo;
import LogManage.TaskLogFactory;
import LogManage.Data.TaskLogInfo;
import ProjectManage.IProjectWork;
import SimulateManage.CreateUnParameter.DesignType;
import SimulateManage.TreadPool.TaskRunInfo;
import SimulateManage.TreadPool.ThreadPoolManage;
import Tolerance.ReadReParamXML;
import Tolerance.ResponseValue;
import experiments.Matrix;
import experiments.bayes.bayes_lhs_uniform;

/**
 * 任务调度。调用算法生成不确定参数，添加任务�?
 * @author <a href="mailto:level_topain@sina.com">flf</a>
 * @version 1.0 
 */
public class RunSimulate
{
	/**
	 * 线程池管�?
	 */
	private ThreadPoolManage threadPool = new ThreadPoolManage();

	/**
	 * 设置模拟�?
	 * 
	 * @param type     模拟器类�?
	 * @param simulator模拟器路�?
	 */
	public void SetSimulator(String type, String simulator)
	{
		threadPool.SetSimulator(type, simulator);
	}

	/**
	 * 停止任务
	 * 
	 * @return
	 */
	public boolean StopTask(TaskFeedbackInfo taskInfo)
	{
		return threadPool.StopTask(taskInfo);
	}
	
	/**
	 * 添加新任�?
	 * @param taskInfo  任务信息
	 * @param projectManage 项目管理
	 */
	public void AddTask(TaskRunInfo taskInfo, IProjectWork projectManage)
	{
		System.out.println("添加新任�?);
		threadPool.SetProjectManage(projectManage);

		runMethod(taskInfo);
	}

	/**
	 * 调用算法，添加线程任务�?
	 * 
	 * @param taskInfo 任务信息
	 * @return
	 */
	@SuppressWarnings("static-access")
	private Vector<MethodInfo> runMethod(TaskRunInfo taskInfo)
	{
		Vector<ResponseValue> arrResponse = null;
		ReadReParamXML reader = new ReadReParamXML(
				taskInfo.sReaponseParameterFile);
		try
		{
			reader.resolvedResponseParameter();
			arrResponse = reader.getResponses();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}

		ArrayList<SimulatedUnParameter> orderParam = null;
		// 迭代优化时上次的结果数据
		Matrix lastPos = null, lastV = null, pBest = null, gBest = null, consValue = null, consStep = null, largest = null;

		Double dBestValue = null;

		Vector<MethodInfo> sMethod = taskInfo.ti.sMethod;

		Vector<ArrayList<SimulatedUnParameter>> parameterFile = null;
		Double[] globals = null;
		TaskLogInfo newTaskLog = TaskLogFactory.CreateTaskLog(
				taskInfo.ti.sUserName, taskInfo.ti.sProjectName,
				taskInfo.ti.sModelName, taskInfo.ti.sCaseName,
				taskInfo.ti.sTaskName, sMethod.get(0).getNum(), new Date());

		for (int i = 0; i < sMethod.size(); i++)
		{
			MethodInfo methodInfo = sMethod.get(i);

			// 任务并发限制
			Semaphore taskPopedom;
			if (methodInfo.getRunStyle().equals("串行") || methodInfo.getRunStyle().equals("Serial"))
			{
				taskPopedom = new Semaphore(1, true);
			}
			else
			{
				taskPopedom = new Semaphore(methodInfo.getParallelNum(), true);
			}

			if (methodInfo.getMethodName().equals("拉丁超立方体") || methodInfo.getMethodName().equals("Latin_Hypercube"))
			{
				if (!SimulateManage.getTaskWatch().hasTask(
						methodInfo.getMethodID()))
				{
					// 注册算法与任务日志编号关�?
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());
					// 新建实验设计方法
					int num = methodInfo.getNum();
					// 获取不确定参�?
					CreateUnParameter c = new CreateUnParameter();
					parameterFile = c.Create(taskInfo.sUncertainParam, num,taskInfo.ti.useInitFlag,
							DesignType.LATIN_SUPER_CUBE_DESIGN);

					SimulateAppraise appraise = new SimulateAppraise();
					appraise.setGlobals(new Double[parameterFile.size()]);
					// 添加任务，并等待任务运行完�?
					CountDownLatch event = threadPool.AddTask(newTaskLog,
							taskInfo, parameterFile, taskPopedom, appraise);
					try
					{
						event.await();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						return null;
					}
					globals = appraise.getGlobals();
				}
				else
				{
					// 获取原有实验设计方法
					parameterFile = new Vector<ArrayList<SimulatedUnParameter>>();
					Vector<String> Globals = new Vector<String>();
					// 获取不确定参数，Global
					Long oldTaskId = SimulateManage.getTaskWatch().getTaskId(
							methodInfo.getMethodID());
					SimulateManage.getTaskWatch().GetExperimentalDesignParam(taskInfo.ti.sUserName,
							oldTaskId, parameterFile, Globals);
					// 转换Global
					globals = new Double[Globals.size()];
					for (int index = 0; index < Globals.size(); index++)
					{
						globals[index] = new Double(1.0);
						if (Globals.get(index) != null)
						{
							globals[index] = Double.valueOf(Globals.get(index));
						}
					}
				}

				taskInfo.nStartIndex += parameterFile.size();
			}


			if (methodInfo.getMethodName().equals("Box-Behnken"))
			{
				if (!SimulateManage.getTaskWatch().hasTask(
						methodInfo.getMethodID()))
				{
					// 注册算法与任务日志编号关�?
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());

					// 新建实验设计方法
					int num = methodInfo.getNum();
					// 获取不确定参�?
					CreateUnParameter c = new CreateUnParameter();
					parameterFile = c.Create(taskInfo.sUncertainParam, num,
							taskInfo.ti.useInitFlag,DesignType.BOX_BEHNKEN_DESIGN);

					SimulateAppraise appraise = new SimulateAppraise();
					appraise.setGlobals(new Double[parameterFile.size()]);
					// 添加任务，并等待任务运行完�?
					CountDownLatch event = threadPool.AddTask(newTaskLog,
							taskInfo, parameterFile, taskPopedom, appraise);
					try
					{
						event.await();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						return null;
					}
					globals = appraise.getGlobals();
				}
				else
				{
					// 获取原有实验设计方法
					parameterFile = new Vector<ArrayList<SimulatedUnParameter>>();
					Vector<String> Globals = new Vector<String>();
					Long oldTaskId = SimulateManage.getTaskWatch().getTaskId(
							methodInfo.getMethodID());
					// 获取不确定参数，Global
					SimulateManage.getTaskWatch().GetExperimentalDesignParam(taskInfo.ti.sUserName,
							oldTaskId, parameterFile, Globals);
					// 转换Global
					globals = new Double[Globals.size()];
					for (int index = 0; index < Globals.size(); index++)
					{
						globals[index] = new Double(1.0);
						if (Globals.get(index) != null)
						{
							globals[index] = Double.valueOf(Globals.get(index));
						}
					}
				}

				taskInfo.nStartIndex += parameterFile.size();
			}

			if (methodInfo.getMethodName().equals("贝叶斯更�?) || methodInfo.getMethodName().equals("Bayesian"))
			{
				if (i == 1)
				{
					// 注册算法与任务日志编号关�?
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());
					int num = methodInfo.getNum();
					bayes_lhs_uniform opt = new bayes_lhs_uniform();
					SimulateAppraise appraise = new SimulateAppraise();
					int sumNum = parameterFile.size()
							+ ((BayesParamsInfo) methodInfo.getParamStruct())
									.getIntMost()
							+ ((BayesParamsInfo) methodInfo.getParamStruct())
									.getIntBest();
					// 获取先前算法模拟结果
					opt.paracoord = getLastSimulateValue(parameterFile,
							globals, sumNum);
					// 设置历史采样点数
					opt.setNhistorysam_sum(parameterFile.size());
					// 添加任务，并等待任务运行完�?
					CountDownLatch event = null;
					while (opt.check_state() == 1)
					{
						// 获取不确定参�?
						if (opt.ntype == 0)
						{// most informative runs
							opt.setproperty(1);
							opt.setIterative(((BayesParamsInfo) methodInfo
									.getParamStruct()).getIntMost());
						}
						else
						{
							opt.setproperty(2);
							opt.setIterative(((BayesParamsInfo) methodInfo
									.getParamStruct()).getIntBest());
						}

						CreateUnParameter c = new CreateUnParameter();
						parameterFile = c.Create(taskInfo.sUncertainParam, num,
								DesignType.BAYES, false,opt);
						appraise.setGlobals(new Double[parameterFile.size()]);
						if (parameterFile.size() != 0)
						{
							event = threadPool.AddTask(newTaskLog, taskInfo,
									parameterFile, taskPopedom, appraise);
							try
							{
								event.await();
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
								return null;
							}
							globals = appraise.getGlobals();
							opt.set_simulate_result(parameterFile, globals);
						}
					}
				}
				else
				{
					WriteXML wx = new WriteXML();
					int num = methodInfo.getNum();
					bayes_lhs_uniform opt = new bayes_lhs_uniform();
					SimulateAppraise appraise = new SimulateAppraise();
					double[][] lastValue = wx.readUnParameterValue(
							taskInfo.sModelFile, taskInfo.caseItem,
							taskInfo.ti.sTaskName);
					int sumSize = lastValue.length
							+ ((BayesParamsInfo) methodInfo.getParamStruct())
									.getIntMost()
							+ ((BayesParamsInfo) methodInfo.getParamStruct())
									.getIntBest();
					opt.paracoord = getLastSimulateValue(lastValue, sumSize);
					opt.setNhistorysam_sum(lastValue.length);
					// 添加任务，并等待任务运行完�?
					CountDownLatch event = null;
					while (opt.check_state() == 1)
					{
						// 获取不确定参�?
						if (opt.ntype == 0)
						{// most informative runs
							opt.setproperty(1);
							opt.setIterative(((BayesParamsInfo) methodInfo
									.getParamStruct()).getIntMost());
						}
						else
						{
							opt.setproperty(2);
							opt.setIterative(((BayesParamsInfo) methodInfo
									.getParamStruct()).getIntBest());
						}

						CreateUnParameter c = new CreateUnParameter();
						parameterFile = c.Create(taskInfo.sUncertainParam, num,
								DesignType.BAYES, false,opt);
						appraise.setGlobals(new Double[parameterFile.size()]);
						if (parameterFile.size() != 0)
						{
							event = threadPool.AddTask(newTaskLog, taskInfo,
									parameterFile, taskPopedom, appraise);
							try
							{
								event.await();
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
								return null;
							}
							globals = appraise.getGlobals();
							opt.set_simulate_result(parameterFile, globals);
						}
					}
				}
			}

			// 遗传算法
			if (methodInfo.getMethodName().equals("遗传算法") || methodInfo.getMethodName().equals("GA"))
			{
				if (i > 0)
				{
					// 注册算法与任务日志编号关�?
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());
					System.out.println("调用遗传算法");
					GAOptimize ga = null;

					// 算法内部迭代，需要将添加任务接口传入
					if (lastPos == null)
					{
						// 初次优化
						ga = new GAOptimize(threadPool, methodInfo, taskInfo,
								parameterFile, arrResponse,
								taskInfo.sUncertainParam, taskPopedom,
								newTaskLog, globals);
						System.out.println("初次优化");
					}
					else
					{
						System.out.println("迭代优化");
						// 迭代优化
						ga = new GAOptimize(lastPos, pBest, consValue,
								threadPool, methodInfo, taskInfo, orderParam,
								arrResponse, taskInfo.sUncertainParam,
								taskPopedom, newTaskLog);
					}
					taskInfo.nStartIndex += ga.GetIteratCount();
					lastPos = ga.GetLastPos();
					pBest = ga.GetPartBest();
					consValue = ga.GetConsValue();
					orderParam = ga.GetParamOrder();

				}
				else
				{

				}

			}

			if (methodInfo.getMethodName().equals("进化策略算法") || methodInfo.getMethodName().equals("ES"))
			{
				/**
				 * 以下是ES的代�?
				 */
				if (i > 0)
				{
					// 注册算法与任务日志编号关�?
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());
					System.out.println("调用ES算法");
					ESOptimize es = null;

					// 算法内部迭代，需要将添加任务接口传入
					if (lastPos == null)
					{
						// 初次优化
						es = new ESOptimize(threadPool, methodInfo, taskInfo,
								parameterFile, arrResponse,
								taskInfo.sUncertainParam, taskPopedom,
								newTaskLog, globals);
						System.out.println("初次优化");
					}
					else
					{
						System.out.println("迭代优化");
						// 迭代优化
						es = new ESOptimize(lastPos, pBest, consValue,largest,
								threadPool, methodInfo, taskInfo, orderParam,
								arrResponse, taskInfo.sUncertainParam,
								taskPopedom, newTaskLog);
					}
					taskInfo.nStartIndex += es.GetIteratCount();
					largest = es.GetLargest();
					lastPos = es.GetLastPos();
					pBest = es.GetPartBest();
					consValue = es.GetConsValue();
					orderParam = es.GetParamOrder();

				}
				else
				{

				}
			}
			
			//在这个地方加入新的优化算法判断
			if (//根据算法的名称判读，参照上面的ES算法)
			{
				//此处不用改
				if (i > 0)
				{
					// 注册算法与任务日志编号关联
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());
					
					//此处改为对应的新添加的算法管理类，此处添加一个类为OtherOptimize
					OtherOptimize es = null;

					// 算法内部迭代，需要将添加任务接口传入
					if (lastPos == null)
					{
						// 初次优化
						es = new OtherOptimize(threadPool, methodInfo, taskInfo,
								parameterFile, arrResponse,
								taskInfo.sUncertainParam, taskPopedom,
								newTaskLog, globals);
						System.out.println("初次优化");
					}
					else
					{
						System.out.println("迭代优化");
						// 迭代优化
						es = new OtherOptimize(lastPos, pBest, consValue,largest,
								threadPool, methodInfo, taskInfo, orderParam,
								arrResponse, taskInfo.sUncertainParam,
								taskPopedom, newTaskLog);
					}
					taskInfo.nStartIndex += es.GetIteratCount();
					largest = es.GetLargest();
					lastPos = es.GetLastPos();
					pBest = es.GetPartBest();
					consValue = es.GetConsValue();
					orderParam = es.GetParamOrder();

				}
				else
				{

				}
			}
			
			if (methodInfo.getMethodName().equals("粒子�?) || methodInfo.getMethodName().equals("PSO"))
			{
				if (i > 0)
				{
					// 注册算法与任务日志编号关�?
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());
					PSOOptimize pso = null;

					// 算法内部迭代，需要将添加任务接口传入
					if (lastPos == null)
					{
						// 初次优化
						pso = new PSOOptimize(threadPool, methodInfo, taskInfo,
								parameterFile, arrResponse,
								taskInfo.sUncertainParam, taskPopedom,
								newTaskLog, globals);

					}
					else
					{
						// 迭代优化
						pso = new PSOOptimize(lastPos, lastV, pBest, gBest,
								consValue, consStep, dBestValue, threadPool,
								methodInfo, taskInfo, orderParam, arrResponse,
								taskInfo.sUncertainParam, taskPopedom,
								newTaskLog);
					}
					taskInfo.nStartIndex += pso.GetIteratCount();
					lastPos = pso.GetLastPos();
					lastV = pso.GetLastVector();
					pBest = pso.GetPartBest();
					gBest = pso.GetGlobalBest();
					consValue = pso.GetConsValue();
					consStep = pso.GetConsStep();
					dBestValue = pso.GetBestValue();
					orderParam = pso.GetParamOrder();

				}
				else
				{

				}
			}
			
			//不确定参数独立运�?
			if(methodInfo.getMethodName().equals("along")){
				if (!SimulateManage.getTaskWatch().hasTask(
						methodInfo.getMethodID()))
				{
					// 注册算法与任务日志编号关�?
					SimulateManage.getTaskWatch().addTaskId(taskInfo.ti.sUserName,
							methodInfo.getMethodID(), newTaskLog.getId());
					// 新建实验设计方法
					int num = methodInfo.getNum();
					// 获取不确定参�?
					CreateUnParameter c = new CreateUnParameter();
					parameterFile = c.Create(taskInfo.sUncertainParam, num,taskInfo.ti.useInitFlag,
							DesignType.ALONG);

					SimulateAppraise appraise = new SimulateAppraise();
					appraise.setGlobals(new Double[parameterFile.size()]);
					// 添加任务，并等待任务运行完�?
					CountDownLatch event = threadPool.AddTask(newTaskLog,
							taskInfo, parameterFile, taskPopedom, appraise);
					try
					{
						event.await();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
						return null;
					}
					globals = appraise.getGlobals();
				}
				else
				{
					// 获取原有实验设计方法
					parameterFile = new Vector<ArrayList<SimulatedUnParameter>>();
					Vector<String> Globals = new Vector<String>();
					// 获取不确定参数，Global
					Long oldTaskId = SimulateManage.getTaskWatch().getTaskId(
							methodInfo.getMethodID());
					SimulateManage.getTaskWatch().GetExperimentalDesignParam(taskInfo.ti.sUserName,
							oldTaskId, parameterFile, Globals);
					// 转换Global
					globals = new Double[Globals.size()];
					for (int index = 0; index < Globals.size(); index++)
					{
						globals[index] = new Double(1.0);
						if (Globals.get(index) != null)
						{
							globals[index] = Double.valueOf(Globals.get(index));
						}
					}
				}
				taskInfo.nStartIndex += parameterFile.size();
			}
			
		}
		return null;

	}

	/**
	 * 获取先前算法的模拟结果，以数组形式存放，供bayes算法调用
	 * 
	 * @param parameterFile
	 *            先前算法生成不确定参数组结果
	 * @param globals
	 *            先前算法生产globals�?
	 * @return 返回�?��二维数组
	 */
	public double[][] getLastSimulateValue(
			Vector<ArrayList<SimulatedUnParameter>> parameterFile,
			Double[] globals, int sumSiz)
	{
		int size = parameterFile.get(0).size();
		double[][] lastSimulateValue = new double[sumSiz][size + 1];
		for (int i = 0; i < parameterFile.size(); i++)
		{
			List list = parameterFile.get(i);
			for (int j = 0; j < list.size(); j++)
			{
				lastSimulateValue[i][j] = ((SimulatedUnParameter) list.get(j))
						.getValue();
				lastSimulateValue[i][j + 1] = globals[i];
			}
		}
		return lastSimulateValue;
	}

	/**
	 * 获取先前算法的模拟结果，以数组形式存放，供bayes算法调用
	 * 
	 * @param lastValue
	 *            解析后的不确定参数组的�?
	 * @return 返回二维数组
	 */
	public double[][] getLastSimulateValue(double[][] lastValue, int sumSize)
	{
		int size = lastValue[0].length;
		double[][] lastSimulateValue = new double[sumSize][size];
		for (int i = 0; i < lastValue.length; i++)
		{
			for (int j = 0; j < size; j++)
			{
				lastSimulateValue[i][j] = lastValue[i][j];
			}
		}
		return lastSimulateValue;
	}
}
