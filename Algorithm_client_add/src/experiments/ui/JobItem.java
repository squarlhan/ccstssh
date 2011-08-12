package experiments.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.xml.sax.SAXException;

import ploting.EventFrequency;
import simulate.data.DirSeperator;
import simulate.data.MethodInfo;
import simulate.data.RequestSimList;
import simulate.data.SimulateResultItem;
import simulate.data.TaskInfo;
import simulate.data.simListInfo;
import simulate.data.simResultInfo;
import simulateclient.common.ListChartData;
import simulateclient.common.MapChartData;
import simulateclient.common.OutExcel;
import simulateclient.common.PairValue;
import simulateclient.common.SampleGetFileName;
import simulateclient.common.SampleIsNumeric;
import simulateclient.common.StringSplit;
import simulateclient.common.ThreeValue;
import simulateclient.dataresolve.ComputeVectors;
import simulateclient.dataresolve.DataResolve;
import simulateclient.dataresolve.LyrFileParse;
import simulateclient.dataresolve.LyrInfo;
import simulateclient.dataresolve.ObsInfo;
import simulateclient.network.NetWorkManage;
import simulateclient.ploting.dialog.ProfileFrame;
import simulateclient.ploting.dialog.RCFrameSwing;
import simulateclient.ploting.dialog.ResultContrast;
import simulateclient.projectmanager.dialog.ResultAnalyze;
import simulateclient.projectmanager.dialog.SimulateListDialog;
import simulateclient.projectmanager.dialog.WaitDlg;
import simulateclient.responseparam.ResolvedResponseParameter_Interface;
import simulateclient.sensitivity.CofficientSet;
import simulateclient.sensitivity.dialog.CofficientDialog;
import simulateclient.sensitivity.dialog.CofficientTable;
import simulateclient.sensitivity.dialog.ConfficientData;
import simulateclient.thirdparty.ResourceManager;
import simulateclient.tolerance.GetVector;
import simulateclient.tolerance.ReadReParamXML;
import simulateclient.tolerance.ResponseValue;
import simulateclient.tolerance.Tolerance;
import simulateclient.tolerance.dialog.QualitiesTable;
import simulateclient.uncertainparam.ReadSimulatedUnParamXML_Interface;
import simulateclient.uncertainparam.dialog.UncertainTable;
import experiments.ui.MethodSelectDlg;

class DrawParam
{
	String trueFile;
	String[] Sims;
	String[] Params;
}

/**
 * 任务节点类，处理任务级别上的操作，主要指一些菜单上面的响应操作
 * 
 * @author Administrator
 * 
 */
public class JobItem extends BaseItem
{

	/**
	 * infc解析后数据
	 */
	public HashMap<String, DataResolve> simdata_map = new HashMap<String, DataResolve>();

	/**
	 * lyr解析后数据
	 */
	public HashMap<String, LyrInfo> lyr_data = new HashMap<String, LyrInfo>();

	/**
	 * 任务参数信息
	 */
	protected TaskInfo ti = new TaskInfo();

	private HashMap<Integer, Double> map;

	private MapChartData<String, ListChartData<Integer, Double>> Data;
	private ListChartData<Integer, Double> list;
	private PairValue<String, ListChartData<Integer, Double>> test;
	private ListChartData<Integer, Double> list1;
	private PairValue<String, ListChartData<Integer, Double>> test1;
	/**
	 * 参数检测错误信息
	 */
	public String errMessage = ""; //$NON-NLS-1$

	public String startDate = ""; //设置起始时间 //$NON-NLS-1$

	private String method1;
	private String method2;

	/**
	 * 节点图标
	 */
	public String GetIcon()
	{

		return "job.png"; //$NON-NLS-1$
	}

	public JobItem(TreeItem item, Element info)
	{
		super(item, info);

	}

	public CaseItem GetCaseItem()
	{
		CaseItem caseItem = (CaseItem) m_treeItem.getParentItem().getData(
				"info"); //$NON-NLS-1$
		return caseItem;
	}

	/**
	 * 解析xml文件，构建对象层次
	 */
	public void ParseProjectInfo()
	{

		// 还原模拟数据称成对象
		FileItem[] files = ListSpecialAttachment("SimulateData"); //$NON-NLS-1$
		if (files == null || files.length == 0)
			return;

		for (FileItem fileItem : files)
		{

			fileItem = TransToAbsolutePath(fileItem);

			if (!(new File(fileItem.path)).exists())
				continue;

			try
			{
				String path = fileItem.path + ".bin"; //$NON-NLS-1$
				String ext = GetFileExt(fileItem.path);
				// 判断文件是否存在，否则解析
				File file = new File(path);
				if (!file.exists())
				{
					if (ext.equalsIgnoreCase("infc")) //$NON-NLS-1$
					{
						// 没被解析过
						DataResolve resolve = new DataResolve(fileItem.path);

						// 解析所有数据
						resolve.Resolve();
						simdata_map.put(fileItem.path, resolve);
						// 保存解析后的数据成二进制
						ObjectOutputStream out = null;
						try
						{
							out = new ObjectOutputStream(new FileOutputStream(
									path));
							out.writeObject(resolve);
							out.close();

						} catch (FileNotFoundException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else
					{
						LyrFileParse parse = new LyrFileParse();
						LyrInfo lyr = parse.ParseFile(path);
						lyr_data.put(fileItem.path, lyr);

						ObjectOutputStream out = null;
						try
						{
							out = new ObjectOutputStream(new FileOutputStream(
									path + ".bin")); //$NON-NLS-1$
							out.writeObject(lyr);
							out.close();

						} catch (FileNotFoundException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else
				{
					if (ext.equalsIgnoreCase("infc")) { //$NON-NLS-1$
						ObjectInputStream in = new ObjectInputStream(
								new FileInputStream(path));
						DataResolve resolve = (DataResolve) in.readObject();
						in.close();

						// 还原集合
						simdata_map.put(fileItem.path, resolve);

					} else
					{
						ObjectInputStream in = new ObjectInputStream(
								new FileInputStream(path));
						LyrInfo lyr = (LyrInfo) in.readObject();
						lyr_data.put(fileItem.path, lyr);
						in.close();
					}
				}

			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检测参数设置完全性,提交数据使用
	 */
	public boolean CheckParamAvalidate()
	{

		// 检测方法类型
		/*
		 * String type = m_info.attributeValue("type"); if(type.isEmpty())
		 * return false;
		 */
		// 检测算法
		String method = m_info.attributeValue("method"); //$NON-NLS-1$
		if (method == null || method.isEmpty())
		{

			errMessage = Messages.getString("JobItem.SelecAlgorithm"); //$NON-NLS-1$
			return false;
		}

		String times = m_info.attributeValue("times"); //$NON-NLS-1$
		if (times == null || times.isEmpty())
		{
			errMessage = Messages.getString("JobItem.AlgorithmCount"); //$NON-NLS-1$
			return false;
		}

		String style = m_info.attributeValue("style"); //$NON-NLS-1$
		if (style == null || style.isEmpty())
		{
			errMessage = Messages.getString("JobItem.AlgorithmType"); //$NON-NLS-1$
			return false;
		}

		String paraNum = m_info.attributeValue("paraNum"); //$NON-NLS-1$
		if (paraNum == null || paraNum.isEmpty())
		{
			errMessage = Messages.getString("JobItem.AlgorithmPara"); //$NON-NLS-1$
			return false;
		}

		return true;
	}

	/**
	 * 获取不确定参数列表
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public Vector<String> GetUncertainParams()
			throws ParserConfigurationException, SAXException, IOException,
			DocumentException
	{

		CaseItem caseItem = GetCaseItem();

		FileItem[] uncertainFiles = caseItem
				.ListSpecialAttachment("UncertainParam"); //$NON-NLS-1$

		// 不确定参数文件
		if (uncertainFiles.length == 0)
			return null;

		FileItem uncertainFile = uncertainFiles[0];

		uncertainFile = caseItem.TransToAbsolutePath(uncertainFile);

		// 解析不确定参数文件
		ReadSimulatedUnParamXML_Interface resolvedUncertainParameter = new ReadSimulatedUnParamXML_Interface(
				uncertainFile.path);
		resolvedUncertainParameter.resolvedUParameter();

		Vector<String> vecParams = resolvedUncertainParameter
				.getParameterName();

		return vecParams;
	}

	/**
	 * 获取响应参数列表
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public Vector<String> GetRespondParams()
			throws ParserConfigurationException, SAXException, IOException,
			DocumentException
	{

		CaseItem caseItem = GetCaseItem();

		FileItem[] respondFiles = caseItem
				.ListSpecialAttachment("RespondParam"); //$NON-NLS-1$

		// 响应参数文件
		if (respondFiles.length == 0)
			return null;

		FileItem repondFile = respondFiles[0];

		repondFile = caseItem.TransToAbsolutePath(repondFile);

		// 解析响应参数文件
		ResolvedResponseParameter_Interface resolvedResponseParameter = new ResolvedResponseParameter_Interface(
				repondFile.path);
		resolvedResponseParameter.resolvedRParameter();

		Vector<String> vecParams = resolvedResponseParameter.getParameterName();
		return vecParams;
	}

	/**
	 * 获取模拟结果和运行的不确定参数
	 * 
	 * @return
	 */
	public Vector<CofficientSet> GetSimData()
	{

		FileItem[] files = ListSpecialAttachment("SimulateData"); //$NON-NLS-1$
		if (files == null || files.length == 0)
			return null;

		Vector<CofficientSet> css = new Vector<CofficientSet>();

		for (FileItem fileItem : files)
		{
			CofficientSet data = new CofficientSet();
			fileItem = TransToAbsolutePath(fileItem);
			data.simPath = fileItem.path;
			String useDataString = fileItem.useData;
			Vector<Double> uncertainParam = TransStringToDoubles(useDataString);
			data.uncertainParam = uncertainParam;
			css.add(data);
		}

		return css;
	}

	/**
	 * 字符类型浮点数转换
	 * 
	 * @param str
	 * @return
	 */
	public static Vector<Double> TransStringToDoubles(String str)
	{

		String[] valStrings = str.split(","); //$NON-NLS-1$
		Vector<Double> vals = new Vector<Double>();
		for (String val : valStrings)
		{
			double dbv = Double.valueOf(val);
			vals.add(dbv);
		}

		return vals;
	}

	/**
	 * 解析所有的模拟结果文件(INFC、LYR)
	 * 
	 * @param filter
	 * @return
	 */
	public DrawParam DrawParamSelect(String filter)
	{
		// 获取数据
		Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		ResultAnalyze dlg = new ResultAnalyze(shell);

		CaseItem caseItem = GetCaseItem();
		FileItem[] AlltrueFiles = caseItem.ListSpecialAttachment("TrueData"); //$NON-NLS-1$
		// 去掉obs文件
		Vector<FileItem> tempTrueFile = new Vector<FileItem>();
		for (FileItem file : AlltrueFiles)
		{
			if (file.path.substring(file.path.lastIndexOf(".") + 1) //$NON-NLS-1$
					.equalsIgnoreCase("user")) //$NON-NLS-1$
			{
				tempTrueFile.add(file);
			}
		}

		FileItem[] trueFiles = new FileItem[tempTrueFile.size()];
		for (int i = 0; i < tempTrueFile.size(); i++)
		{
			trueFiles[i] = tempTrueFile.get(i);
		}

		FileItem[] simFiles = ListSpecialAttachment("SimulateData"); //$NON-NLS-1$

		if (trueFiles == null || simFiles == null)
			return null;

		Vector<String> vecSims = new Vector<String>();// 模拟结果INFC文件

		// 模拟结果文件
		for (FileItem fileItem : simFiles)
		{

			fileItem = TransToAbsolutePath(fileItem);

			// 过滤模拟结果
			if (!filter.isEmpty()
					&& GetFileExt(fileItem.path).equalsIgnoreCase(filter))
				vecSims.add(fileItem.path);
		}

		Vector<String> vecParams = null;
		// HashMap<String, Vector <ploting.ThreeValue<Double,Double,Double>>>
		// oldrcValue=new HashMap<String, Vector
		// <ploting.ThreeValue<Double,Double,Double>>>();//用来获取容差
		try
		{
			try
			{
				vecParams = GetRespondParams();
				// oldrcValue=GetRespondParamsRC();
			} catch (DocumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (vecParams == null)
			return null;

		// 根据功能过滤掉对应的响应参数
		Vector<String> userVectParams = new Vector<String>();
		for (String params : vecParams)
		{
			StringSplit stringSplit = new StringSplit(params);
			String[] param = stringSplit.Split();
			if (filter.equalsIgnoreCase("infc")) //$NON-NLS-1$
			{
				if (!SampleIsNumeric.isDouble(param[3]))
				{
					userVectParams.add(params);
				}
			} else
			{
				if (SampleIsNumeric.isDouble(param[3]))
				{
					userVectParams.add(params);
				}
			}
		}

		dlg.Init(userVectParams, vecSims);
		Object result = dlg.open();

		// 用户取消
		if (result.equals(0))
			return null;

		DrawParam retParam = new DrawParam();

		retParam.Params = dlg.retParams;
		retParam.Sims = dlg.retSims;

		if (trueFiles.length == 0)
			return null;

		FileItem trueFile = trueFiles[0];
		retParam.trueFile = trueFile.path;

		retParam.trueFile = TransToAbsolutePath(trueFile).path;

		return retParam;
	}

	/**
	 * 设置任务参数
	 */
	public void SetJobinfo(boolean flag, String strTask)
	{

		Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		if (!flag)
		{
			if (m_info.attribute("method") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("method")); //$NON-NLS-1$
			if (m_info.attribute("times") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("times")); //$NON-NLS-1$
			if (m_info.attribute("most_times") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("most_times")); //$NON-NLS-1$
			if (m_info.attribute("best_times") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("best_times")); //$NON-NLS-1$
			if (m_info.attribute("GA_crossRate1") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("GA_crossRate1")); //$NON-NLS-1$
			if (m_info.attribute("GA_crossRate2") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("GA_crossRate2")); //$NON-NLS-1$
			if (m_info.attribute("GA_temperature") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("GA_temperature")); //$NON-NLS-1$
			if (m_info.attribute("GA_insideIterator") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("GA_insideIterator")); //$NON-NLS-1$
			if (m_info.attribute("GA_variationRate") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("GA_variationRate")); //$NON-NLS-1$
			if (m_info.attribute("GA_cooling") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("GA_cooling")); //$NON-NLS-1$
			if (m_info.attribute("APGA_crossRate1") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("APGA_crossRate1")); //$NON-NLS-1$
			if (m_info.attribute("APGA_crossRate2") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("APGA_crossRate2")); //$NON-NLS-1$
			if (m_info.attribute("APGA_temperature") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("APGA_temperature")); //$NON-NLS-1$
			if (m_info.attribute("APGA_insideIterator") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("APGA_insideIterator")); //$NON-NLS-1$
			if (m_info.attribute("APGA_variationRate") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("APGA_variationRate")); //$NON-NLS-1$
			if (m_info.attribute("APGA_cooling") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("APGA_cooling")); //$NON-NLS-1$
			if (m_info.attribute("PSO_partStudyDivisor") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("PSO_partStudyDivisor")); //$NON-NLS-1$
			if (m_info.attribute("PSO_globalStudyDivisor") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("PSO_globalStudyDivisor")); //$NON-NLS-1$
			if (m_info.attribute("PSO_intertiaWeight") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("PSO_intertiaWeight")); //$NON-NLS-1$
			if (m_info.attribute("PSO_anneal") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("PSO_anneal")); //$NON-NLS-1$
			if (m_info.attribute("PSO_iterator") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("PSO_iterator")); //$NON-NLS-1$
			if (m_info.attribute("style") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("style")); //$NON-NLS-1$
			if (m_info.attribute("paraNum") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("paraNum")); //$NON-NLS-1$
			if (m_info.attribute("methodID") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("methodID")); //$NON-NLS-1$

			if (m_info.attribute("ES_ngInt") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("ES_ngInt")); //$NON-NLS-1$
			if (m_info.attribute("ES_chSigma") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("ES_chSigma")); //$NON-NLS-1$
			if (m_info.attribute("ES_OneFifth_gen") != null) //$NON-NLS-1$
				m_info.remove(m_info.attribute("ES_OneFifth_gen")); //$NON-NLS-1$
			
			//在这个后面根据新加的算法的属性信息先从配置文件中清除这些信息
			
			if(/*.....*/)
				//.....模仿上面的代码添加
		}
		MethodSelectDlg dlg = new MethodSelectDlg(shell, ""); //$NON-NLS-1$

		Vector<TaskInfo> jobsInfo = GetSubmitedTaskInfo();
		dlg.SetSubmitedjob(jobsInfo);
		dlg.setTaskName(strTask);
		if (!flag)
		{
			dlg.setTask(ti);
		}
		dlg.setFlag(flag);
		Object result = dlg.open();

		// 用户取消
		if (!flag)
		{
			if (result.equals(0))
				return;

			// 生成任务信息
			ti = dlg.getTask();
			int size = ti.sMethod.size();
			// methodName 取得算法名称，如果只有一个算法则取第一个算法，如果有多个算法，则取第2个算法的名称
			String methodName = size == 1 ? ti.sMethod.get(0).getMethodName()
					: ti.sMethod.get(1).getMethodName();
			StringBuffer strMethod = new StringBuffer();
			StringBuffer strTimes = new StringBuffer();
			StringBuffer strStyle = new StringBuffer();
			StringBuffer strParaNum = new StringBuffer();
			StringBuffer strMethodID = new StringBuffer();
			StringBuffer strBYMostTimes = new StringBuffer(); // most属性
			StringBuffer strBYBestTimes = new StringBuffer(); // best属性
			StringBuffer strGACrossRate1 = new StringBuffer(); // 交叉概率1
			StringBuffer strGACrossRate2 = new StringBuffer(); // 交叉概率2
			StringBuffer strGAInsideIterator = new StringBuffer(); // 内部迭代次数
			StringBuffer strGATemperature = new StringBuffer(); // 温度系数
			StringBuffer strGACooling = new StringBuffer(); // 降温系数
			StringBuffer strGAVariationRate = new StringBuffer(); // 变异概率
			StringBuffer strAPGACrossRate1 = new StringBuffer(); // 交叉概率1
			StringBuffer strAPGACrossRate2 = new StringBuffer(); // 交叉概率2
			StringBuffer strAPGAInsideIterator = new StringBuffer(); // 内部迭代次数
			StringBuffer strAPGATemperature = new StringBuffer(); // 温度系数
			StringBuffer strAPGACooling = new StringBuffer(); // 降温系数
			StringBuffer strAPGAVariationRate = new StringBuffer(); // 变异概率
			StringBuffer strPSOPartStudyDivisor = new StringBuffer(); // 局部学习因子
			StringBuffer strPSOGlobalStydyDivisor = new StringBuffer(); // 全局学习因子
			StringBuffer strPSOIntertiaWeight = new StringBuffer(); // 惯性权重
			StringBuffer strPSOTuiHuo = new StringBuffer(); // 退火系数
			StringBuffer strPOSIterator = new StringBuffer(); // 迭代次数

			StringBuffer strESngInt = new StringBuffer(); // 惯性权重
			StringBuffer strESchSigma = new StringBuffer(); // 退火系数
			StringBuffer strESOneFifth_gen = new StringBuffer(); // 迭代次数
			
			//这里根据实际的新加的算法属性添加对应的字符串类，如上面的ES算法的 惯性权重啊 退火系数等
			
			
			// 转换成xml保存信息
			TaskInfo.TransMethodData(ti.sMethod, strMethod, strTimes, strStyle,
					strParaNum, strMethodID, strBYMostTimes, strBYBestTimes,
					strGACrossRate1, strGACrossRate2, strGAInsideIterator,
					strGATemperature, strGACooling, strGAVariationRate,
					strAPGACrossRate1, strAPGACrossRate2, strAPGAInsideIterator,
					strAPGATemperature, strAPGACooling, strAPGAVariationRate,
					strPSOPartStudyDivisor, strPSOGlobalStydyDivisor,
					strPSOIntertiaWeight, strPSOTuiHuo, strPOSIterator,
					strESngInt, strESchSigma, strESOneFifth_gen, /*这里传上面新定义的算法字符串类，所以对应的该方法中的实现也要改变*/);

			// 记录
			m_info.addAttribute("method", strMethod.toString()); //$NON-NLS-1$
			m_info.addAttribute("times", strTimes.toString()); //$NON-NLS-1$
			if (methodName.equalsIgnoreCase("贝叶斯更新") || methodName.equalsIgnoreCase("Bayesian")) { //$NON-NLS-1$ //$NON-NLS-2$
				m_info.addAttribute("most_times", strBYMostTimes.toString()); //$NON-NLS-1$
				m_info.addAttribute("best_times", strBYBestTimes.toString()); //$NON-NLS-1$
			}
			if (methodName.equalsIgnoreCase("遗传算法") || methodName.equalsIgnoreCase("GA")) { //$NON-NLS-1$ //$NON-NLS-2$
				m_info
						.addAttribute(
								"GA_crossRate1", strGACrossRate1.toString()); //$NON-NLS-1$
				m_info
						.addAttribute(
								"GA_crossRate2", strGACrossRate2.toString()); //$NON-NLS-1$
				m_info.addAttribute(
						"GA_temperature", strGATemperature.toString()); //$NON-NLS-1$
				m_info.addAttribute(
						"GA_insideIterator", strGAInsideIterator.toString()); //$NON-NLS-1$
				m_info.addAttribute(
						"GA_variationRate", strGAVariationRate.toString()); //$NON-NLS-1$
				m_info.addAttribute("GA_cooling", strGACooling.toString()); //$NON-NLS-1$
			}
			if (methodName.equalsIgnoreCase("粒子群") || methodName.equalsIgnoreCase("PSO")) { //$NON-NLS-1$ //$NON-NLS-2$
				m_info
						.addAttribute(
								"PSO_partStudyDivisor", strPSOPartStudyDivisor.toString()); //$NON-NLS-1$
				m_info
						.addAttribute(
								"PSO_globalStudyDivisor", strPSOGlobalStydyDivisor.toString()); //$NON-NLS-1$
				m_info.addAttribute(
						"PSO_intertiaWeight", strPSOIntertiaWeight.toString()); //$NON-NLS-1$
				m_info.addAttribute("PSO_anneal", strPSOTuiHuo.toString()); //$NON-NLS-1$
				m_info.addAttribute("PSO_iterator", strPOSIterator.toString()); //$NON-NLS-1$
			}
			if (methodName.equalsIgnoreCase("进化策略算法") || methodName.equalsIgnoreCase("ES")) //$NON-NLS-1$ //$NON-NLS-2$
			{
				m_info.addAttribute("ES_ngInt", strESngInt.toString()); //$NON-NLS-1$
				m_info.addAttribute("ES_chSigma", strESchSigma.toString()); //$NON-NLS-1$
				m_info.addAttribute("ES_OneFifth_gen", strESOneFifth_gen //$NON-NLS-1$
						.toString());
			}
			//这里加入新算法的判断,同上面的添加代码
			if (methodName.equalsIgnoreCase("聚类遗传算法") || methodName.equalsIgnoreCase("APGA")) { //$NON-NLS-1$ //$NON-NLS-2$
				m_info
						.addAttribute(
								"APGA_crossRate1", strAPGACrossRate1.toString()); //$NON-NLS-1$
				m_info
						.addAttribute(
								"APGA_crossRate2", strAPGACrossRate2.toString()); //$NON-NLS-1$
				m_info.addAttribute(
						"APGA_temperature", strAPGATemperature.toString()); //$NON-NLS-1$
				m_info.addAttribute(
						"APGA_insideIterator", strAPGAInsideIterator.toString()); //$NON-NLS-1$
				m_info.addAttribute(
						"APGA_variationRate", strAPGAVariationRate.toString()); //$NON-NLS-1$
				m_info.addAttribute("APGA_cooling", strAPGACooling.toString()); //$NON-NLS-1$
			}
			
			m_info.addAttribute("style", strStyle.toString()); //$NON-NLS-1$
			m_info.addAttribute("paraNum", strParaNum.toString()); //$NON-NLS-1$
			m_info.addAttribute("methodID", strMethodID.toString()); //$NON-NLS-1$
			m_info.addAttribute("isPrePt", String.valueOf(dlg.isPrePt()));
		}
	}

	/**
	 * 右键菜单
	 */
	public Menu GetMenu(Shell shell)
	{
		if (m_menu == null)
		{
			m_menu = new Menu(shell, SWT.POP_UP);

			MenuItem mi = null;

			/**
			 * 菜单创建
			 */
			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.JobSet")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/setting.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{
					if (!checkUserLogin())
						return;

					// 查看任务是否已经已经提交过
					// if (IsCompleteSubmit())
					// {
					// // BaseItem
					// // .NotifyMsg(
					////										Messages.getString("JobItem.Tip1"), Messages.getString("JobItem.DontModifyParam")); //$NON-NLS-1$ //$NON-NLS-2$
					// // return;
					// WatchJobinfo();
					// }
					// else
					// {
					// System.out.println(e.item.toString());

					SetJobinfo(IsCompleteSubmit(), GetName());

					// 保存项目
					if (!IsCompleteSubmit())
						SaveProject();
					// }
				}
			});

			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.Submit")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/commit.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{
					if (!checkUserLogin())
						return;

					// 查看任务是否已经已经提交过
					if (IsCompleteSubmit())
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip19"), Messages.getString("JobItem.Submited")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}

					// 判断参数完整性
					if (!CheckParamAvalidate())
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip20"), Messages.getString("JobItem.ParamComplete")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}

					SubmitJob();

					// 保存项目
					SaveProject();
				}
			});

			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.DeleteJob")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/delete.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{
					if (!checkUserLogin())
						return;
					int iDel = BaseItem
							.XshowMessageDialog(
									Messages.getString("JobItem.32"), Messages.getString("JobItem.33")); //$NON-NLS-1$ //$NON-NLS-2$
					if (iDel == 1 || iDel == -1)
						return;
					// 删除当前任务
					RemoveItem(true);

					// 保存项目
					saveAll();
				}
			});

			// 分隔符
			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.SEPARATOR);
			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.12")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/along_run.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{
					if (!checkUserLogin())
						return;

					// 查看任务是否已经已经提交过
					if (IsCompleteSubmit())
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip19"), Messages.getString("JobItem.Submited")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}

					// // 判断参数完整性
					// if (!CheckParamAvalidate())
					// {
					// BaseItem
					// .NotifyMsg(
					//										Messages.getString("JobItem.Tip20"), Messages.getString("JobItem.ParamComplete")); //$NON-NLS-1$ //$NON-NLS-2$
					// return;
					// }

					initialParamSimulate();

					// 保存项目
					SaveProject();
				}
			});

			// 分隔符
			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.SEPARATOR);

			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.GetSim")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/getInfc.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{
					if (!checkUserLogin())
						return;

					RequestSimList info = new RequestSimList();
					info.sUserName = NetWorkManage.GetLoginInfo().User;
					CaseItem ci = GetCaseItem();
					if (ci == null)
						return;

					ModelItem mi = ci.GetModelItem();
					if (mi == null)
						return;

					ProjectItem pi = mi.GetProjectItem();
					if (pi == null)
						return;

					info.sProjectName = pi.GetName();
					info.sModelName = mi.GetName();
					info.sCaseName = ci.GetName();
					info.sTaskName = GetName();

					NetWorkManage.GetSendEvent().GetSimulateList(info);

				}
			});

			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.Sensitivity")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/sensitivity.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				@SuppressWarnings("static-access")
				public void widgetSelected(final SelectionEvent e)
				{

					if (!checkUserLogin())
						return;
					// 响应参数
					Vector<String> vecParams = null;
					try
					{
						try
						{
							vecParams = GetRespondParams();
						} catch (DocumentException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (ParserConfigurationException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SAXException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (vecParams == null)
						return;

					// 只添加.user文件中的响应参数
					Vector<String> userVecParams = new Vector<String>();
					for (int i = 0; i < vecParams.size(); i++)
					{
						StringSplit stringSplit = new StringSplit(vecParams
								.get(i));
						String[] tempParams = stringSplit.Split();
						if (!SampleIsNumeric.isDouble(tempParams[3]))
						{
							userVecParams.add(vecParams.get(i));
						}
					}

					// 模拟文件数据
					Vector<CofficientSet> simDataVector = GetSimData();
					if (simDataVector == null)
						return;
					// 过滤掉.lyr模拟文件
					Vector<CofficientSet> infcSimDataVector = new Vector<CofficientSet>();
					for (CofficientSet path : simDataVector)
					{
						if (path.simPath.substring(
								path.simPath.lastIndexOf(".") + 1) //$NON-NLS-1$
								.equalsIgnoreCase("infc")) //$NON-NLS-1$
						{
							infcSimDataVector.add(path);
						}
					}

					// 参数设置，数据计算
					Display display = Display.getDefault();
					final Shell shell = new Shell(display);
					if (simdata_map.size() < 1)
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip7"), Messages.getString("JobItem.NotSelectSimFile")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}
					CofficientDialog dlg = new CofficientDialog(shell,
							simdata_map);
					dlg.Init(GetName(), userVecParams, infcSimDataVector);
					// 测试相关性表格打开BUG!
					System.out.println("开始打开相关性表格……"); //$NON-NLS-1$
					Object result = dlg.open();
					System.out.println("相关性表格设置结果：" + "-----------" + result); //$NON-NLS-1$ //$NON-NLS-2$
					// 用户取消
					if (result.equals(0))
						return;

					// 获取响应参数名称
					Vector<String> vecUncertain = null;
					try
					{
						try
						{
							vecUncertain = GetUncertainParams();
						} catch (DocumentException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (ParserConfigurationException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SAXException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (vecUncertain == null)
						return;

					System.out.println("计算相关性……"); //$NON-NLS-1$
					// 开始计算相关性，显示相关性表格--------------------------------------------------------------------------

					int uncertenL = dlg.uncertenL;
					int uncertenC = dlg.uncertenC;
					int responseL = dlg.responseL;
					int responseC = dlg.responseC;
					int otherC = dlg.otherC;
					int otherL = dlg.otherL;
					int responseTyle = dlg.responseTyle;// 1，时间 2，均值 3，最大 4，最小
					int method = dlg.method;

					String[] uncertainName = new String[vecUncertain.size()];// 所有的不确定参数名称
					String[] responseName = new String[userVecParams.size()];// 所有的响应参数名称

					Vector<ConfficientData> datas = dlg.datas;
					HashMap<String, Vector<Double>> map2 = new HashMap<String, Vector<Double>>(); // 存储所有参数值
					vecUncertain.toArray(uncertainName);
					userVecParams.toArray(responseName);
					// 把数据加入到map中
					Vector<Double>[] paramUncertain = new Vector[uncertainName.length];// 行参数，每行是一个Vector
					// <Double>
					Vector<Double>[] paramResponse = new Vector[responseName.length];// 列参数，每列是一个Vector
					// <Double>
					for (int i22 = 0; i22 < uncertainName.length; i22++)
					{
						paramUncertain[i22] = new Vector();
					}

					for (int i33 = 0; i33 < responseName.length; i33++)
					{
						paramResponse[i33] = new Vector();
					}

					for (int i = 0; i < datas.size(); i++)
					{// 一次循环是一次模拟
						// 把不确定参数加入到map中
						for (int i2 = 0; i2 < uncertainName.length; i2++)
						{
							paramUncertain[i2]
									.add(datas.elementAt(i).uncertainParam
											.elementAt(i2));
						}
						// 把响应参数加入到map中
						for (int i3 = 0; i3 < responseName.length; i3++)
						{
							// paramResponse[i3]=new Vector();
							paramResponse[i3]
									.add(datas.elementAt(i).respondParam
											.elementAt(i3));
						}
					}// for i<datas.size()
					for (int i4 = 0; i4 < paramUncertain.length; i4++)
						map2.put(uncertainName[i4], paramUncertain[i4]);
					for (int i5 = 0; i5 < paramResponse.length; i5++)
						map2.put(responseName[i5], paramResponse[i5]);

					if (uncertenL + responseL + otherL < 1
							|| uncertenC + responseC + otherC < 1)
					{
						System.out.println("行或列没有选择参数！"); //$NON-NLS-1$
						return;
					}
					// 提取global值和容差最大值集合
					Vector<Double> gVector = new Vector<Double>();
					Vector<Double> rcVector = new Vector<Double>();
					CaseItem caseItem = GetCaseItem();
					FileItem[] tempSimFiles = ListSpecialAttachment("SimulateData"); //$NON-NLS-1$
					// 只取.INFC文件
					Vector<FileItem> simFiles = new Vector<FileItem>();
					for (FileItem fileItem : tempSimFiles)
					{
						// if (fileItem.path.substring(
						//								fileItem.path.lastIndexOf(".") + 1) //$NON-NLS-1$
						//								.equalsIgnoreCase("infc")) //$NON-NLS-1$
						// {
						// simFiles.add(fileItem);
						// }
						for (int i = 0; i < datas.size(); i++)
						{
							String simPath = datas.elementAt(i).getSimName();
							String[] strPath = simPath
									.split(DirSeperator.separator);
							int intPath = strPath.length;
							simPath = strPath[intPath - 2]
									+ DirSeperator.separator
									+ strPath[intPath - 1];
							if (simPath.equals(fileItem.path))
							{
								simFiles.add(fileItem);
							}
						}
					}

					Vector<String> vecSims = new Vector<String>();

					// Global值
					for (FileItem fileItem : simFiles)
					{
						gVector.add(fileItem.getGlobal());

						String GetResponseXML = caseItem.GetResponseXML();// 获取响应参数XML配置文件路径
						ReadReParamXML read = new ReadReParamXML(GetResponseXML);
						try
						{
							read.resolvedResponseParameter();
						} catch (DocumentException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Vector<ResponseValue> response = read.getRcResponses();
						fileItem = TransToAbsolutePath(fileItem);
						Tolerance t = new Tolerance();
						t.infcPath = fileItem.path;
						t.setResponses(response);
						HashMap<String, DataResolve> truedata_map = GetCaseItem().truedata_map;
						t.setUserResolve(truedata_map);
						t.setInfcResolve(simdata_map);

						GetVector getVector = new GetVector();
						Vector<String> reName = new Vector<String>();
						for (int id = 0; id < response.size(); id++)
						{
							reName.add(response.get(id).getResponseName());
						}
						getVector.split(reName);

						Vector<String> wells = getVector.GetWellName();
						Double x = 0.0;
						for (int i = 0; i < wells.size(); i++)
						{
							// 总量
							String style = Messages.getString("JobItem.0"); //$NON-NLS-1$
							double o = t.overallWellTolerance(wells.get(i),
									style);
							x += o;
						}
						rcVector.add(x);

					}

					// 添加Global和容差列数据
					map2.put(Messages.getString("JobItem.1"), gVector); //$NON-NLS-1$
					map2.put(Messages.getString("JobItem.2"), rcVector); //$NON-NLS-1$

					// 额外的数据列
					Vector<String> addition = new Vector<String>();
					addition.add(Messages.getString("JobItem.3")); //$NON-NLS-1$
					addition.add(Messages.getString("JobItem.4")); //$NON-NLS-1$
					System.out.println("（JobItem）打开相关性表格……"); //$NON-NLS-1$
					CofficientTable window = new CofficientTable(shell,
							SWT.NONE, uncertenL, uncertenC, responseL,
							responseC, otherC, otherL, responseTyle, method,
							uncertainName, responseName, map2, addition);
					window.open();

					// 敏感性分析

				}
			});

			/**
			 * 容差质量评估
			 */
			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.Tolerance")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/table.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{
					if (!checkUserLogin())
						return;
					Display display = Display.getDefault();
					Vector<String> infcFiles = new Vector<String>();// 得到所选择的模拟文件路径
					String[] Sims = new String[0], Params = new String[0];
					// 获取数据
					DrawParam papram = DrawParamSelect("infc"); //$NON-NLS-1$

					// 参数错误
					if (papram == null)
						return;
					if (papram.Sims.length < 1)
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip2"), Messages.getString("JobItem.Simpath")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}

					for (int i = 0; i < papram.Sims.length; i++)
					{

						String path = papram.Sims[i];
						infcFiles.add(path);
					}
					Shell shell = new Shell(display);
					// 获得已经解析的观测值MAP
					HashMap<String, DataResolve> truedata_map = GetCaseItem().truedata_map;
					// simdata_map,已经解析的模拟值MAP
					QualitiesTable window = new QualitiesTable(shell, SWT.NONE);
					// 获得响应参数xml配置文件
					CaseItem caseItem = GetCaseItem();
					String GetResponseXML = caseItem.GetResponseXML();// 获取响应参数XML配置文件路径
					try
					{
						// window.setUnParamPath("c:\\ReaponseParameter.xml");
						window.setUnParamPath(GetResponseXML);
					} catch (DocumentException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					window.setInfcResolve(simdata_map);
					window.setUserResolve(truedata_map);
					window.setInfcFiles(infcFiles, papram.Params);// 设置模拟值文件路径

					window.open();
				}
			});
			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.Quality")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/quxian.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{

					if (!checkUserLogin())
						return;
					// String[] Sims = new String[0], Params = new String[0];

					// 获取数据
					System.out.println("打开参数选择对话框"); //$NON-NLS-1$
					DrawParam papram = DrawParamSelect("infc"); //$NON-NLS-1$

					startDate = GetCaseItem().GetModelItem().startDate;

					if (papram == null)
					{

						System.out.println("观测值或模拟值文件 不存在！"); //$NON-NLS-1$
					}

					// 参数错误
					if (papram == null)
					{
						Display diaplay = Display.getCurrent();
						Shell shell = new Shell(diaplay);
						MessageBox msgBox = new MessageBox(shell,
								SWT.ICON_INFORMATION);
						msgBox.setText(Messages.getString("JobItem.Tip4")); //$NON-NLS-1$
						msgBox.setMessage(Messages
								.getString("JobItem.ParamError")); //$NON-NLS-1$
						msgBox.open();
						return;
					}
					System.out.println("参数选择完毕！"); //$NON-NLS-1$
					// 使用新的质量分析图，TSC 12月21日
					// AdjustScatter nullDemo = new AdjustScatter();
					// nullDemo.plot(papram.trueFile, papram.Sims,
					// papram.Params);
					// 获取容差设置
					// 测试，获取的容差
					Vector<ThreeValue<Double, Double, Double>> oldrcValue0 = new Vector<ThreeValue<Double, Double, Double>>();// 用来设置容差
					oldrcValue0.add(new ThreeValue<Double, Double, Double>(
							100.0, 20.0, 5.0));
					oldrcValue0.add(new ThreeValue(500.0, 50.0, 3.0));
					oldrcValue0.add(new ThreeValue(1500.0, 70.0, 6.0));

					// RCFrame window = new
					// RCFrame(papram.trueFile,papram.Sims,papram.Params,oldrcValue0);//userPath,infcPath,reponse
					// window.open();
					// 显示容差，为每个响应参数显示用户设置的容差
					HashMap<String, Vector<simulateclient.common.ThreeValue<Double, Double, Double>>> map = new HashMap<String, Vector<simulateclient.common.ThreeValue<Double, Double, Double>>>();
					HashMap<String, Vector<simulateclient.common.ThreeValue<Double, Double, Double>>> oldrcValue = new HashMap<String, Vector<simulateclient.common.ThreeValue<Double, Double, Double>>>();
					if (papram.Params == null || papram.Params.length < 1)
					{
						Display diaplay = Display.getCurrent();
						Shell shell = new Shell(diaplay);
						MessageBox msgBox = new MessageBox(shell,
								SWT.ICON_INFORMATION);
						msgBox.setText(Messages.getString("JobItem.Tip5")); //$NON-NLS-1$
						msgBox.setMessage(Messages
								.getString("JobItem.RespondError")); //$NON-NLS-1$
						msgBox.open();
						return;
					}
					String[] response = new String[papram.Params.length];
					response = papram.Params;// 所有的响应参数
					System.out.println("从响应参数配置文件得到容差设置！"); //$NON-NLS-1$
					CaseItem caseItem = GetCaseItem();

					oldrcValue = caseItem.GetRespondParamsRC();// 从响应参数配置文件得到容差设置
					map = caseItem.GetRespondParamsRC();// 从响应参数配置文件得到容差设置

					if (oldrcValue == null)
						return;
					/*
					 * if(response.length>0){//把容差值加入MAP中response.length==oldrcValue
					 * .size() for(int i=0;i<response.length;i++){//一个响应参数
					 * Vector<ploting.ThreeValue<Double, Double, Double>>
					 * oldrcValue1=new
					 * Vector<ploting.ThreeValue<Double,Double,Double>>();
					 * if(oldrcValue.elementAt(i)!=null)//响应参数容差不为空 for(int
					 * j=0;j<oldrcValue.elementAt(i).size();j++){
					 * oldrcValue1.add(oldrcValue.elementAt(i).elementAt(j)); }
					 * if(oldrcValue.elementAt(i)!=null){ map.put(response[i],
					 * oldrcValue1); System.out.println("加入一个"); }//if }//for
					 * }//if
					 */
					if (papram.Sims == null || papram.Sims.length < 1)
					{
						Display diaplay = Display.getCurrent();
						Shell shell = new Shell(diaplay);
						MessageBox msgBox = new MessageBox(shell,
								SWT.ICON_INFORMATION);
						msgBox.setText(Messages.getString("JobItem.Tip5")); //$NON-NLS-1$
						msgBox.setMessage(Messages
								.getString("JobItem.SimNotSelect")); //$NON-NLS-1$
						msgBox.open();
						return;
					}
					// String [] userpath=papram.Sims;
					HashMap<String, DataResolve> truedata_map = GetCaseItem().truedata_map;
					// System.out.println("truedata_map.size()="+truedata_map.size());
					System.err.println("(JObItem)容差map大小：" + map.size()); //$NON-NLS-1$
					if (simdata_map == null || simdata_map.size() < 1)
					{
						Display diaplay = Display.getCurrent();
						Shell shell = new Shell(diaplay);
						MessageBox msgBox = new MessageBox(shell,
								SWT.ICON_INFORMATION);
						msgBox.setText(Messages.getString("JobItem.Tip6")); //$NON-NLS-1$
						msgBox.setMessage(Messages
								.getString("JobItem.SmValueError")); //$NON-NLS-1$
						msgBox.open();
						return;
					}

					// 获取响应参数观测值对应关系
					HashMap<String, String> respond_file = caseItem
							.GetRespondTrueFile();
					if (respond_file == null)
						return;

					HashMap<String, HashMap<String, HashMap<String, Vector<String>>>> propertyMap = caseItem.file_typeMap;

					// 为剖面图初始化数据
					String[] lyrPath = new String[papram.Sims.length];
					for (int i = 0; i < lyrPath.length; i++)
					{
						String infcName = papram.Sims[i];
						int point = infcName.lastIndexOf("."); //$NON-NLS-1$
						String tempLyrPath = infcName.substring(0, point) + "." //$NON-NLS-1$
								+ "lyr"; //$NON-NLS-1$
						lyrPath[i] = tempLyrPath;
					}
					HashMap<String, Vector<ObsInfo>> obsMap = caseItem.obs_data;// 已经解析的obs观测值
					HashMap<String, LyrInfo> lyrSimuMap = lyr_data;// 已经解析的obs模拟值

					HashMap<String, HashMap<String, Double>> tempNncertainDataMap = getUncertainDataMap();
					// 传入不确定参数数值集合
					HashMap<String, HashMap<String, Double>> uncertainDataMap = changeFileToSimulateName(
							tempNncertainDataMap, "infc"); //$NON-NLS-1$
					// ProfileFrame window = new
					// ProfileFrame(null,lyrPath,null,obsMap,lyrSimuMap,caseItem.file_obs_typeMap);

					// HashMap<String,String> userPathMap = new
					// HashMap<String,String>();//
					// 响应参数对应观测值文件MAP；key-响应参数，value-观测值文件绝对路径
					// propertyMap 来自CaseItem.java 第642行，同 file_typeMap.
					// RCFrame window = new
					// RCFrame(papram.trueFile,papram.Sims,papram.Params,map,truedata_map,simdata_map,propertyMap);
					// 给userPathMap赋值
					// RCFrame window = new
					// RCFrame(respond_file,papram.Sims,papram.Params,map,truedata_map,simdata_map,propertyMap);
					RCFrameSwing window = new RCFrameSwing(respond_file,
							papram.Sims, papram.Params, map, truedata_map,
							simdata_map, propertyMap, lyrPath, obsMap,
							lyrSimuMap, caseItem.file_obs_typeMap);

					window.setStartDate(startDate);
					window.setUncertainDataMap(uncertainDataMap);
					// window.setDemoPanelProchartData(null,lyrPath,null,obsMap,lyrSimuMap,caseItem.file_obs_typeMap);
					window.open();
				}
			});

			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.Profile")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/zhuzhuangtu.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{

					if (!checkUserLogin())
						return;
					// String[] Sims = new String[0], Params = new String[0];

					startDate = GetCaseItem().GetModelItem().startDate;

					CaseItem caseItem = GetCaseItem();
					// 获取数据
					DrawParam papram = DrawParamSelect("lyr"); //$NON-NLS-1$

					// 参数错误
					if (papram == null)
						return;

					// 获取观测值文件
					FileItem[] files = caseItem
							.ListSpecialAttachment("TrueData"); //$NON-NLS-1$
					if (files == null || files.length == 0)
						return;

					String trueFile = null;
					for (FileItem fileItem : files)
					{
						// 文件路径是否存在
						String path = TransToAbsolutePath(fileItem).path;
						String ext = GetFileExt(path);
						if (ext.equalsIgnoreCase("obs")) { //$NON-NLS-1$

							trueFile = path;
							break;
						}
					}

					// 获取模拟测值文件
					files = ListSpecialAttachment("SimulateData"); //$NON-NLS-1$
					if (files == null || files.length == 0)
						return;

					Vector<String> simVector = new Vector<String>();
					for (FileItem fileItem : files)
					{
						// 文件路径是否存在
						String path = TransToAbsolutePath(fileItem).path;
						String ext = GetFileExt(path);
						if (ext.equalsIgnoreCase("lyr")) { //$NON-NLS-1$

							simVector.add(path);
						}
					}

					EventFrequency nullDemo = new EventFrequency();
					// nullDemo.plot(papram.trueFile, papram.Sims,
					// papram.Params);
					HashMap<String, DataResolve> truedata_map = GetCaseItem().truedata_map;
					if (truedata_map.size() < 1)
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip18"), Messages.getString("JobItem.TrueDataNotParse3")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}

					// 暂时屏蔽
					String obs = trueFile;// obs观测值
					String[] simObs = new String[simVector.size()];
					simVector.toArray(simObs);// obs模拟值
					String[] response = papram.Params;// 响应参数
					String[] lyrPath = papram.Sims;

					HashMap<String, Vector<ObsInfo>> obsMap = new HashMap<String, Vector<ObsInfo>>();
					HashMap<String, Vector<ObsInfo>> tempObsMap = caseItem.obs_data;// 已经解析的obs观测值
					obsMap.putAll(tempObsMap);
					HashMap<String, LyrInfo> lyrSimuMap = new HashMap<String, LyrInfo>();// 已经解析的obs模拟值
					lyrSimuMap.putAll(lyr_data);
					// 获取响应参数观测值对应关系
					HashMap<String, String> respond_file = new HashMap<String, String>();
					HashMap<String, String> tempRespond_file = caseItem
							.GetRespondTrueFile();
					respond_file.putAll(tempRespond_file);
					if (respond_file == null)
						return;

					HashMap<String, HashMap<String, HashMap<String, Vector<String>>>> file_obs_typeMap = new HashMap<String, HashMap<String, HashMap<String, Vector<String>>>>();
					file_obs_typeMap.putAll(caseItem.file_obs_typeMap);

					HashMap<String, HashMap<String, HashMap<String, Vector<String>>>> propertyMap = caseItem.file_typeMap;
					ProfileFrame window = new ProfileFrame(respond_file,
							lyrPath, response, obsMap, lyrSimuMap,
							file_obs_typeMap);

					window.setStartDate(startDate);

					window.init();
					// 标示剖面图是由任务节点菜单调用的，默认为false.
					window.setJob(true);
					// 选择多少个响应参数对应绘制多少个图
					window.plot(response.length);

					/*
					 * Histogram nullDemo1 = new
					 * Histogram("剖面对比图",obs,simObs,response,
					 * obsMap,obsSimuMap);
					 * nullDemo1.plotSwing("剖面对比图",obs,simObs,response,
					 * obsMap,obsSimuMap);
					 */
				}
			});

			// 拟合质量评估图
			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.5")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/sandian.gif"))); //$NON-NLS-1$
			Data = new MapChartData<String, ListChartData<Integer, Double>>();
			list = new ListChartData<Integer, Double>();
			test = new PairValue<String, ListChartData<Integer, Double>>(null,
					null);
			list1 = new ListChartData<Integer, Double>();
			test1 = new PairValue<String, ListChartData<Integer, Double>>(null,
					null);
			mi.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{
					if (!checkUserLogin())
						return;
					if (!Data.isEmpty())
						Data.clear();
					if (!list.isEmpty())
						list.clear();
					if (!list1.isEmpty())
						list1.clear();
					String baseFile = GetCaseItem().GetModelItem()
							.GetModelPath();
					File file = new File(baseFile);
					String[] str = file.list();
					String filename = null;
					for (String s : str)
					{
						if (s.endsWith("i.dat") || s.endsWith("r.dat")) { //$NON-NLS-1$ //$NON-NLS-2$
							filename = s.substring(0, s.length() - 5);
						}
					}
					Iterator modelIterator = m_info.elementIterator();
					String startTime = "1"; //$NON-NLS-1$
					String isInit = m_info.attributeValue("initMethod"); //$NON-NLS-1$
					String times = m_info.attributeValue("times"); //$NON-NLS-1$
					String timeArray[] = times.split(","); //$NON-NLS-1$
					String methods[] = m_info
							.attributeValue("method").split(","); //$NON-NLS-1$ //$NON-NLS-2$
					method1 = methods[0];
					if (methods.length >= 2)
						method2 = methods[1];
					int time = Integer.parseInt(timeArray[0]); //$NON-NLS-1$
					String isPrePt = m_info.attributeValue("isPrePt");
					if (isInit.equals("true")) //$NON-NLS-1$
					{
						time = Integer.parseInt(startTime) + time;
					} else
					{
						time = Integer.parseInt(startTime) + time - 1;
					}
					if (!Boolean.parseBoolean(isPrePt))
					{
						while (modelIterator.hasNext())
						{
							Element modelElement = (Element) modelIterator
									.next();
							Iterator modelIterator1 = modelElement
									.elementIterator();

							while (modelIterator1.hasNext())
							{
								Element File = (Element) modelIterator1.next();
								String path = File.attributeValue("path"); //$NON-NLS-1$
								if (path.endsWith(".infc")) { //$NON-NLS-1$
									String count = path.substring(path
											.indexOf(filename)
											+ filename.length(), path
											.indexOf(".")); //$NON-NLS-1$
									String global = File
											.attributeValue("global"); //$NON-NLS-1$
									if (Integer.valueOf(count) <= time)
										list.add(Integer.valueOf(count), Double
												.valueOf(global));
									else
									{
										list1.add(Integer.valueOf(count),
												Double.valueOf(global));
									}
								}
							}
						}
					} else
					{
						String methodIds = m_info.attributeValue("methodID");
						String methodId = methodIds.split(",")[0];
						Element case_info = m_info.getParent();
						String pTimes = m_info.attributeValue("times");
						int pTime = Integer.parseInt(pTimes.split(",")[0]);
						Iterator jobIterator = case_info.elementIterator();
						while (jobIterator.hasNext())
						{
							Element sub_element = (Element) jobIterator.next();
							if(sub_element.attributeValue("methodID") == null || sub_element
									.attributeValue("isPrePt") == null)
								continue;
							if (!sub_element.getName().equals("Job")
									&& !sub_element.attributeValue("methodID")
											.equals(methodId)
									&& Boolean.valueOf(sub_element
											.attributeValue("isPrePt")))
								continue;
							
							Element attachmentElement = (Element) sub_element.elementIterator()
							.next();
							
							Iterator modelIterator1 = attachmentElement
									.elementIterator();

							while (modelIterator1.hasNext())
							{
								Element File = (Element) modelIterator1.next();
								String path = File.attributeValue("path"); //$NON-NLS-1$
								if (path.endsWith(".infc")) { //$NON-NLS-1$
									String count = path.substring(path
											.indexOf(filename)
											+ filename.length(), path
											.indexOf(".")); //$NON-NLS-1$
									String global = File
											.attributeValue("global"); //$NON-NLS-1$
									if (Integer.valueOf(count) <= pTime)
										list.add(Integer.valueOf(count), Double
												.valueOf(global));
								}
							}
							break;
						}

						while (modelIterator.hasNext())
						{
							Element modelElement = (Element) modelIterator
									.next();
							Iterator modelIterator1 = modelElement
									.elementIterator();

							while (modelIterator1.hasNext())
							{
								Element File = (Element) modelIterator1.next();
								String path = File.attributeValue("path"); //$NON-NLS-1$
								if (path.endsWith(".infc")) { //$NON-NLS-1$
									String count = path.substring(path
											.indexOf(filename)
											+ filename.length(), path
											.indexOf(".")); //$NON-NLS-1$
									String global = File
											.attributeValue("global"); //$NON-NLS-1$
									list1.add(Integer.valueOf(count) + pTime, Double
											.valueOf(global));
								}
							}
						}

					}
					test.first = method1;
					test.second = list;
					Data.add(0, test);
					if (method2 != null)
					{
						test1.first = method2;
						test1.second = list1;
						Data.add(1, test1);
					}
					new ResultContrast(Data);
				}
			});

			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.ExportFile")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/export.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{
					if (!checkUserLogin())
						return;
					Display display = Display.getDefault();
					final Shell shell = new Shell(display);
					ResultAnalyze dlg = new ResultAnalyze(shell);

					{
						CaseItem caseItem = GetCaseItem();
						FileItem[] trueFiles = caseItem
								.ListSpecialAttachment("TrueData"); //$NON-NLS-1$
						FileItem[] simFiles = ListSpecialAttachment("SimulateData"); //$NON-NLS-1$

						Vector<String> vecSims = new Vector<String>();

						// 模拟结果文件
						for (FileItem fileItem : simFiles)
						{

							fileItem = TransToAbsolutePath(fileItem);

							vecSims.add(fileItem.path);
						}

						Vector<String> vecParams = null;
						try
						{
							try
							{
								vecParams = GetRespondParams();
							} catch (DocumentException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (ParserConfigurationException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SAXException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if (vecParams == null)
						{
							Display diaplay = Display.getCurrent();
							Shell shell2 = new Shell(diaplay);
							MessageBox msgBox = new MessageBox(shell2,
									SWT.ICON_INFORMATION);
							msgBox.setText(Messages.getString("JobItem.Tip8")); //$NON-NLS-1$
							msgBox.setMessage(Messages
									.getString("JobItem.SimFileEmpty")); //$NON-NLS-1$
							msgBox.open();
							return;
						}

						dlg.Init(vecParams, vecSims);
					}
					Object result = dlg.open();

					// 用户取消
					if (result.equals(0))
						return;

					if (dlg.retSims == null || dlg.retSims.length < 1)
					{
						Display diaplay = Display.getCurrent();
						Shell shell2 = new Shell(diaplay);
						MessageBox msgBox = new MessageBox(shell2,
								SWT.ICON_INFORMATION);
						msgBox.setText(Messages.getString("JobItem.Tip9")); //$NON-NLS-1$
						msgBox.setMessage(Messages
								.getString("JobItem.NotSelectSim")); //$NON-NLS-1$
						msgBox.open();
						return;
					}
					if (dlg.retParams == null || dlg.retParams.length < 1)
					{
						Display diaplay = Display.getCurrent();
						Shell shell2 = new Shell(diaplay);
						MessageBox msgBox = new MessageBox(shell2,
								SWT.ICON_INFORMATION);
						msgBox.setText(Messages.getString("JobItem.Tip10")); //$NON-NLS-1$
						msgBox.setMessage(Messages
								.getString("JobItem.RespondEmpty")); //$NON-NLS-1$
						msgBox.open();
						return;
					}
					String[] simulate = new String[dlg.retSims.length];
					simulate = dlg.retSims;// 选择的模拟
					String[] response = new String[dlg.retParams.length];
					response = dlg.retParams;// 选择的响应参数

					// 响应参数
					Vector<String> responseVector = new Vector<String>();// 选择的响应参数
					if (response != null && response.length < 1)
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip11"), Messages.getString("JobItem.NotSelectRespond")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}
					for (int i = 0; i < response.length; i++)
					{
						responseVector.add(response[i]);
					}
					// ---------创建窗口中的目录界选择面组件------------- 输出的Excel文件的路径
					DirectoryDialog dialog = new DirectoryDialog(shell);
					dialog.setText(Messages.getString("JobItem.Directory")); // 设置窗口标题 //$NON-NLS-1$
					dialog.setMessage(Messages
							.getString("JobItem.SelectDirectory")); // 设置提示文字 //$NON-NLS-1$
					dialog.setFilterPath("c:/windows"); // 设置初始目录 //$NON-NLS-1$
					String dir = dialog.open(); // 打开对话框并返回一个包含所选目录路径的字符串
					// 模拟文件路径与名称
					Vector<String> infcPath = new Vector<String>();// 选择的模拟（infcPath路径）
					if (simulate.length < 1)
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip12"), Messages.getString("JobItem.SmiFileNotSelct")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}
					for (int i = 0; i < simulate.length; i++)
					{
						infcPath.add(simulate[i]);
					}
					// 输出的Excel文件名称
					Vector<String> excelName = new Vector<String>();
					for (int i = 0; i < simulate.length; i++)
					{
						// String str =
						// SampleGetFileName.GetFileNmae(simulate[i]);
						String str = simulate[i].replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
						String[] strs = str.split("/"); //$NON-NLS-1$
						str = strs[strs.length - 1];
						str = str + ".xls"; //$NON-NLS-1$
						excelName.add(str);
					}
					if (excelName.size() < 1)
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip13"), Messages.getString("JobItem.ExcelError")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}
					// 得到观测值文件
					String userPath = ""; //$NON-NLS-1$
					CaseItem caseItem = GetCaseItem();
					FileItem[] trueFiles = caseItem
							.ListSpecialAttachment("TrueData"); //$NON-NLS-1$
					// 获取响应参数观测值对应关系
					HashMap<String, String> respond_file = caseItem
							.GetRespondTrueFile();
					HashMap<String, Vector<ObsInfo>> ObsMap = caseItem.obs_data;// 已经解析的obs观测值
					Vector<String> vecUser = new Vector<String>();
					// 观测值文件
					for (FileItem fileItem : trueFiles)
					{
						fileItem = TransToAbsolutePath(fileItem);
						vecUser.add(fileItem.path);
					}
					if (vecUser != null && vecUser.size() > 0)
						userPath = vecUser.elementAt(0);
					else
					{
						BaseItem
								.NotifyMsg(
										Messages.getString("JobItem.Tip14"), Messages.getString("JobItem.SelTrueFile")); //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}
					if (dir != null)
					{
						System.out.println("存储路径：" + dir); // 打印出所选择目录的绝对路径 //$NON-NLS-1$
						if (infcPath.size() != excelName.size())
						{
							BaseItem
									.NotifyMsg(
											Messages.getString("JobItem.Tip15"), Messages.getString("JobItem.ExcelCountErrot")); //$NON-NLS-1$ //$NON-NLS-2$
							return;
						}
						HashMap<String, DataResolve> truedata_map = GetCaseItem().truedata_map;
						if (truedata_map == null || truedata_map.size() < 1)
						{
							BaseItem
									.NotifyMsg(
											Messages.getString("JobItem.Tip16"), Messages.getString("JobItem.TrueDataNotParse")); //$NON-NLS-1$ //$NON-NLS-2$
							return;
						}
						// System.out.println("开始生成Excel表格！");
						OutExcel OutExcel = new OutExcel();
						OutExcel.OutputMultExcel(userPath, infcPath,
								responseVector, dir, excelName, truedata_map,
								simdata_map, respond_file, ObsMap, lyr_data);
						// 观测值文件(一) 模拟值文件（多） 响应参数（多） 目录（一）
						// Excel文件名（多）---------------------
					}
				}
			});

			/*
			 * mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			 * mi.setText(Messages.getString("JobItem.DrawScatter"));
			 * //$NON-NLS-1$ mi.addSelectionListener(new SelectionAdapter() {
			 * public void widgetSelected(final SelectionEvent e) {
			 * 
			 * String[] Sims = new String[0], Params = new String[0];
			 * 
			 * // 获取数据 DrawParam papram = DrawParamSelect("infc"); //$NON-NLS-1$
			 * 
			 * // 参数错误 if(papram == null) return;
			 * 
			 * EventFrequency nullDemo = new EventFrequency();
			 * //nullDemo.plot(papram.trueFile, papram.Sims, papram.Params);
			 * HashMap<String, DataResolve> truedata_map =
			 * GetCaseItem().truedata_map; if(truedata_map.size()<1){
			 * BaseItem.NotifyMsg(Messages.getString("JobItem.Tip17"),
			 * Messages.getString("JobItem.TrueDataNotParse2")); //$NON-NLS-1$
			 * //$NON-NLS-2$ return; } nullDemo.plotSwing(papram.trueFile,
			 * papram.Sims, papram.Params,truedata_map,simdata_map); } });
			 */

			/*
			 * mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			 * mi.setText(Messages.getString("JobItem.DrawCurve"));
			 * //$NON-NLS-1$ mi.addSelectionListener(new SelectionAdapter() {
			 * public void widgetSelected(final SelectionEvent e) {
			 * 
			 * } });
			 */

			mi = new org.eclipse.swt.widgets.MenuItem(m_menu, SWT.PUSH);
			mi.setText(Messages.getString("JobItem.LookupSim")); //$NON-NLS-1$
			mi.setImage(ResourceManager.getImage(AbstractUIPlugin
					.imageDescriptorFromPlugin("SimulateClient", //$NON-NLS-1$
							"icons/lookInfo.gif"))); //$NON-NLS-1$
			mi.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(final SelectionEvent e)
				{

					if (!checkUserLogin())
						return;
					HashMap<String, HashMap<String, Double>> uncertainDataMap = new HashMap<String, HashMap<String, Double>>();

					uncertainDataMap = getUncertainDataMap();

					if (uncertainDataMap == null)
					{
						return;
					}

					// 额外的数据列
					Vector<String> addition = new Vector<String>();

					Display display = Display.getDefault();
					Shell shell = new Shell(display);
					UncertainTable window = new UncertainTable(shell, SWT.NONE,
							addition, uncertainDataMap);
					window.open();
				}
			});
		}

		return m_menu;
	}

	/**
	 * 获取不确定参数数值
	 * 
	 * @return 模拟文件——不确定参数数值
	 */
	public HashMap<String, HashMap<String, Double>> getUncertainDataMap()
	{
		HashMap<String, HashMap<String, Double>> uncertainDataMap = new HashMap<String, HashMap<String, Double>>();
		Vector<String> unCertNameVector = null;
		// 获取不确定参数名字
		try
		{
			unCertNameVector = GetUncertainParams();
		} catch (ParserConfigurationException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (unCertNameVector == null)
			return null;

		// 获取所有模拟结果
		FileItem[] files = ListSpecialAttachment("SimulateData"); //$NON-NLS-1$
		if (files == null || files.length == 0)
			return null;

		Vector<String> simVector = new Vector<String>();
		for (FileItem fileItem : files)
		{

			fileItem = TransToAbsolutePath(fileItem);
			Vector<String> useDataVector = fileItem.GetUseData();

			if (useDataVector.size() != unCertNameVector.size())
			{
				// 不确定参数个数不匹配，方案可能修改，请重新提交方案
				BaseItem
						.NotifyMsg(
								Messages.getString("JobItem.0"), Messages.getString("JobItem.1")); //$NON-NLS-1$ //$NON-NLS-2$
				return null;
			}

			HashMap<String, Double> uncertainMap = new HashMap<String, Double>();

			Iterator<String> it_name = unCertNameVector.iterator();
			while (it_name.hasNext())
			{
				String colName = (String) it_name.next();
				int index = unCertNameVector.indexOf(colName);
				Double vDouble = Double.parseDouble(useDataVector.get(index));
				uncertainMap.put(colName, vDouble);
			}

			uncertainDataMap.put(fileItem.path, uncertainMap);
		}

		return uncertainDataMap;
	}

	/**
	 * 得到具体的曲线名称与不确定参数组的一一对应关系
	 * 
	 * @param uncertainDataMap
	 *            文件与不确定参数组的对应关系集合
	 * @param fileType
	 *            文件类型，传入"infc"或"lry"两种文件类型变量
	 * @return 具体的曲线名称与不确定参数组的一一对应关系
	 */
	public HashMap<String, HashMap<String, Double>> changeFileToSimulateName(
			HashMap<String, HashMap<String, Double>> uncertainDataMap,
			String fileType)
	{
		HashMap<String, HashMap<String, Double>> tempNncertainDataMap = new HashMap<String, HashMap<String, Double>>();
		Set keySet = uncertainDataMap.keySet();
		if (fileType.equalsIgnoreCase("infc")) //$NON-NLS-1$
		{
			for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
			{
				String tempKey = (String) iterator.next();
				if (tempKey.substring(tempKey.lastIndexOf(".") + 1) //$NON-NLS-1$
						.equalsIgnoreCase("infc")) //$NON-NLS-1$
				{
					HashMap tempMap = uncertainDataMap.get(tempKey);
					String newKey = SampleGetFileName.getInfcName(tempKey);
					tempNncertainDataMap.put(newKey, tempMap);
				}
			}
		} else
		{
			for (Iterator iterator = keySet.iterator(); iterator.hasNext();)
			{
				String tempKey = (String) iterator.next();
				if (tempKey.substring(tempKey.lastIndexOf(".") + 1) //$NON-NLS-1$
						.equalsIgnoreCase("lyr")) //$NON-NLS-1$
				{
					HashMap tempMap = uncertainDataMap.get(tempKey);
					String newKey = SampleGetFileName.getInfcName(tempKey);
					tempNncertainDataMap.put(newKey, tempMap);
				}
			}
		}
		return tempNncertainDataMap;
	}

	/**
	 * 将不确定参数初始值送往服务器端进行模拟</br> 该方法实现如下两个操作</br> 1.初始化必要的参数，包括模拟的次数，串并行</br>
	 * 2.提交任务到服务器端
	 */
	public void initialParamSimulate()
	{
		TreeItem itCase = m_treeItem.getParentItem();
		if (itCase == null)
		{
			System.out.println("Can't Find Parent Case Item!"); //$NON-NLS-1$
			return;
		}

		TreeItem itModel = itCase.getParentItem();
		if (itModel == null)
		{
			System.out.println("Can't Find Parent Model Item!"); //$NON-NLS-1$
			return;
		}

		TreeItem itProj = itModel.getParentItem();
		if (itProj == null)
		{
			System.out.println("Can't Find Parent Model Item!"); //$NON-NLS-1$
			return;
		}

		TaskInfo taskInfo = new TaskInfo();

		if (!NetWorkManage.IsLogin())
			return;

		taskInfo.sUserName = NetWorkManage.GetLoginInfo().User;
		taskInfo.sProjectName = itProj.getText();
		taskInfo.sModelName = itModel.getText();
		taskInfo.sCaseName = itCase.getText();
		taskInfo.sTaskName = m_treeItem.getText();
		taskInfo.setUseInitFlag(false);

		Vector<MethodInfo> methods = new Vector<MethodInfo>();
		MethodInfo methodInfo = new MethodInfo();
		methodInfo.setMethodID(UUID.randomUUID().toString());
		methodInfo.setMethodName("along"); //$NON-NLS-1$
		methodInfo.setNum(1);
		methodInfo.setParallelNum(1);
		methodInfo.setParamStruct(null);
		methodInfo.setRunStyle("串行"); //$NON-NLS-1$

		methods.add(methodInfo);

		taskInfo.sMethod = methods;

		NetWorkManage.GetSendEvent().SubmitTask(taskInfo);

		// 设置提交标志
		CompleteSubmit();
	}

	/**
	 * 提交任务
	 */
	public void SubmitJob()
	{

		TaskInfo taskInfo = GetTaskinfo();
		if (taskInfo == null)
			return;

		boolean useFlag = false;
		int a = BaseItem
				.XshowMessageDialog(
						Messages.getString("JobItem.6"), Messages.getString("JobItem.7")); //$NON-NLS-1$ //$NON-NLS-2$
		if (a == 1)
		{
			useFlag = false;
			m_info.addAttribute("initMethod", "false"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		else if (a == 0)
		{
			useFlag = true;
			m_info.addAttribute("initMethod", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		} else if (a == -1)
		{
			return;
		}
		taskInfo.setUseInitFlag(useFlag);
		NetWorkManage.GetSendEvent().SubmitTask(taskInfo);

		// 设置提交标志
		CompleteSubmit();
	}

	/**
	 * 获取任务的TaskInfo结构
	 */
	public TaskInfo GetTaskinfo()
	{

		TreeItem itCase = m_treeItem.getParentItem();
		if (itCase == null)
		{
			System.out.println("Can't Find Parent Case Item!"); //$NON-NLS-1$
			return null;
		}

		TreeItem itModel = itCase.getParentItem();
		if (itModel == null)
		{
			System.out.println("Can't Find Parent Model Item!"); //$NON-NLS-1$
			return null;
		}

		TreeItem itProj = itModel.getParentItem();
		if (itProj == null)
		{
			System.out.println("Can't Find Parent Model Item!"); //$NON-NLS-1$
			return null;
		}

		TaskInfo taskInfo = new TaskInfo();
		if (NetWorkManage.IsLogin())
			taskInfo.sUserName = NetWorkManage.GetLoginInfo().User;
		taskInfo.sProjectName = itProj.getText();
		taskInfo.sModelName = itModel.getText();
		taskInfo.sCaseName = itCase.getText();
		taskInfo.sTaskName = m_treeItem.getText();
		// taskInfo.sType = m_info.attributeValue("type");

		// 解析任务方法
		String strMethod = m_info.attributeValue("method"); //$NON-NLS-1$
		String strTimes = m_info.attributeValue("times"); //$NON-NLS-1$
		String strStyle = m_info.attributeValue("style"); //$NON-NLS-1$
		String strParaNum = m_info.attributeValue("paraNum"); //$NON-NLS-1$
		String strMethodID = m_info.attributeValue("methodID"); //$NON-NLS-1$
		String strMostTimes = m_info.attributeValue("most_times"); //$NON-NLS-1$
		String strBestTimes = m_info.attributeValue("best_times"); //$NON-NLS-1$

		String strGACroosRate1 = m_info.attributeValue("GA_crossRate1"); //$NON-NLS-1$
		String strGACroosRate2 = m_info.attributeValue("GA_crossRate2"); //$NON-NLS-1$
		String strGATemperature = m_info.attributeValue("GA_temperature"); //$NON-NLS-1$
		String strGAInsideIterator = m_info.attributeValue("GA_insideIterator"); //$NON-NLS-1$
		String strGAVariationRate = m_info.attributeValue("GA_variationRate"); //$NON-NLS-1$
		String strGACooling = m_info.attributeValue("GA_cooling"); //$NON-NLS-1$

		String strPSOPartStudyDivisor = m_info
				.attributeValue("PSO_partStudyDivisor"); //$NON-NLS-1$
		String strPSOGlobalStudyDivisor = m_info
				.attributeValue("PSO_globalStudyDivisor"); //$NON-NLS-1$
		String strPSOIntertiaWeight = m_info
				.attributeValue("PSO_intertiaWeight"); //$NON-NLS-1$
		String strPSOAnneal = m_info.attributeValue("PSO_anneal"); //$NON-NLS-1$
		String strPSOIterator = m_info.attributeValue("PSO_iterator"); //$NON-NLS-1$

		String strESngInt = m_info.attributeValue("ES_ngInt"); //$NON-NLS-1$
		String strESchSigma = m_info.attributeValue("ES_chSigma"); //$NON-NLS-1$
		String strESOneFifth_gen = m_info.attributeValue("ES_OneFifth_gen"); //$NON-NLS-1$
		
		//在这里根据新算法的参数名称加入与上面的这个ES类似的代码，这个里面的像“ES_ngInt”这些字符串是写入到配置文件中的节点，用于记录算法属性的值
		String strAPGACroosRate1 = m_info.attributeValue("APGA_crossRate1"); //$NON-NLS-1$
		String strAPGACroosRate2 = m_info.attributeValue("APGA_crossRate2"); //$NON-NLS-1$
		String strAPGATemperature = m_info.attributeValue("APGA_temperature"); //$NON-NLS-1$
		String strAPGAInsideIterator = m_info.attributeValue("APGA_insideIterator"); //$NON-NLS-1$
		String strAPGAVariationRate = m_info.attributeValue("APGA_variationRate"); //$NON-NLS-1$
		String strAPGACooling = m_info.attributeValue("APGA_cooling"); //$NON-NLS-1$
		
		Vector<MethodInfo> methods = new Vector<MethodInfo>();

		//这里传入从配置文件获取过来的字符串信息到这个TransMethodData方法中，所以这个方法的内部实现也要更改
		if (!TaskInfo.TransMethodData(strMethod, strTimes, strStyle,
				strParaNum, strMethodID, methods, strMostTimes, strBestTimes,
				strGACroosRate1, strGACroosRate2, strGATemperature,
				strGAInsideIterator, strGAVariationRate, strGACooling,
				strAPGACroosRate1, strAPGACroosRate2, strAPGATemperature,
				strAPGAInsideIterator, strAPGAVariationRate, strAPGACooling,
				strPSOPartStudyDivisor, strPSOGlobalStudyDivisor,
				strPSOIntertiaWeight, strPSOAnneal, strPSOIterator, strESngInt,
				strESchSigma, strESOneFifth_gen))
			return null;

		taskInfo.sMethod = methods;

		return taskInfo;
	}

	/**
	 * 删除任务
	 */
	public void RemoveItem(boolean bDelFile)
	{

		// 删除相关文件
		if (bDelFile)
		{
			String path = GetCaseItem().GetCasePath();
			path += DirSeperator.separator + GetName();
			System.out.println(path);
			// System.out.println(widget.getData());
			DeleteDirectoty(path);
			// FileItem[] files = ListAttachment();
			// if (files != null)
			// {
			//
			// for (FileItem fileItem : files)
			// {
			// fileItem = TransToAbsolutePath(fileItem);
			// DeleteFile(fileItem.path);
			// }
			// }
		}
		// 同步服务端信息

		SynDeleteJob();
		// 删除 对象
		DeleteThis();

	}

	/**
	 * 返回模拟结果列表
	 * 
	 * @param info
	 */
	// public void BackSimulateList(simListInfo info)
	// {
	//
	// // 列出所有模拟名，供用户选择
	// int nCount = info.simList.size();
	//
	// String[] targets = new String[nCount];
	// for (int i = 0; i < nCount; i++)
	// targets[i] = info.simList.get(i);
	//
	// Display display = Display.getDefault();
	// final Shell shell = new Shell(display);
	// ListSelect dlg = new ListSelect(shell);
	//		dlg.Init(targets, Messages.getString("JobItem.SimResultSel"), true); //$NON-NLS-1$
	// Object result = dlg.open();
	//
	// // 用户取消
	// if (result.equals(0))
	// return;
	//
	// String[] selItemss = dlg.strItems;
	//
	// // 请求模拟文件
	// Vector<String> vec_sel = new Vector<String>();
	// for (int i = 0; i < selItemss.length; i++)
	// {
	// vec_sel.add(selItemss[i]);
	// }
	//
	// info.simList = vec_sel;
	// NetWorkManage.GetSendEvent().GetSimulateData(info);
	// }
	/**
	 * 返回模拟结果列表
	 * 
	 * @param info
	 */
	public void BackSimulateList(simListInfo info)
	{

		// 列出所有模拟名，供用户选择
		int nCount = info.simList.size();

		String[] targets = new String[nCount];
		for (int i = 0; i < nCount; i++)
			targets[i] = info.simList.get(i);

		Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		// ListSelect dlg = new ListSelect(shell);
		SimulateListDialog dlg = new SimulateListDialog(shell);
		dlg.Init(targets, Messages.getString("JobItem.SimResultSel"), true); //$NON-NLS-1$
		Object result = dlg.open();

		// 用户取消
		if (result.equals(0))
			return;

		String[] selItemss = dlg.strItems;

		// 请求模拟文件
		Vector<String> vec_sel = new Vector<String>();
		for (int i = 0; i < selItemss.length; i++)
		{
			vec_sel.add(selItemss[i]);
		}

		info.simList = vec_sel;
		NetWorkManage.GetSendEvent().GetSimulateData(info);
	}

	/**
	 * 返回模拟结果
	 * 
	 * @param info
	 */
	public void BackSimulateData(simResultInfo info)
	{

		CaseItem caseItem = GetCaseItem();

		// 记录模拟结果到配置文件
		Iterator<SimulateResultItem> iterator = info.simResult.iterator();
		while (iterator.hasNext())
		{
			SimulateResultItem simItem = iterator.next();

			FileItem file = new FileItem(GetName() + DirSeperator.separator
					+ simItem.simFile, "rel", "SimulateData"); //$NON-NLS-1$ //$NON-NLS-2$

			// 附加不确定参数
			Iterator<String> it_unParam = simItem.uncertainParam.iterator();
			StringBuffer strUnParam = new StringBuffer();
			while (it_unParam.hasNext())
			{
				String string = it_unParam.next();
				strUnParam.append(string);
				strUnParam.append(","); //$NON-NLS-1$
			}

			if (!simItem.uncertainParam.isEmpty())
				strUnParam.deleteCharAt(strUnParam.length() - 1);

			file.SetUseData(strUnParam.toString());

			// 提取global值
			file.SetGlobal(simItem.global);

			AddAttachment(file);

			System.out.println("模拟结果对应的不确定参数" + strUnParam.toString()); //$NON-NLS-1$
		}

	}

	/**
	 * 返回模拟结果文件接收完成
	 */
	public void BackSimulateDataEnd(final simResultInfo info)
	{

		final CaseItem caseItem = GetCaseItem();

		// 记录模拟结果到配置文件
		Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		WaitDlg waitDlg = new WaitDlg(shell);
		waitDlg.SetTitle(Messages.getString("JobItem.Parsing2")); //$NON-NLS-1$

		Thread parseThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				// obs_data.clear();
				Iterator<SimulateResultItem> iterator = info.simResult
						.iterator();

				m_info.getParent().setAttributeValue("fileNum", //$NON-NLS-1$
						info.fileNumString);
				//m_info.addAttribute("fileNumStart", info.fileNumStart); //$NON-NLS-1$
				while (iterator.hasNext())
				{
					SimulateResultItem simItem = iterator.next();

					FileItem file = new FileItem(GetName()
							+ DirSeperator.separator + simItem.simFile,
							"rel", "SimulateData"); //$NON-NLS-1$ //$NON-NLS-2$

					// 解析模拟结果文件
					file = TransToAbsolutePath(file);

					String path = file.path;
					ComputeVectors computeVectors = new ComputeVectors(path);
					computeVectors.ComputeAllVector();// 计算文件中有多少属性
					Vector<String> newWellVector = new Vector<String>();
					newWellVector = computeVectors.GetAllVector();
					// DataResolve resolve=new DataResolve(path);
					// 支持不同属性的infc文件解析
					// 根据不同扩展名调用不同模拟器解析数据
					String ext = GetFileExt(path);
					if (ext == null)
						continue;

					if (ext.equalsIgnoreCase("infc")) { //$NON-NLS-1$

						DataResolve resolve = new DataResolve(path,
								newWellVector);

						if (resolve.isFileExist == false)
							return;

						// 解析所有数据
						resolve.Resolve();

						// 记录数据解析对象
						simdata_map.put(path, resolve);

						// 保存解析后的数据成二进制
						ObjectOutputStream out = null;
						try
						{
							out = new ObjectOutputStream(new FileOutputStream(
									path + ".bin")); //$NON-NLS-1$
							out.writeObject(resolve);
							out.close();

						} catch (FileNotFoundException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if (ext.equalsIgnoreCase("lyr")) { //$NON-NLS-1$

						// 暂时屏蔽
						/*
						 * Vector<ObsInfo> data = new Vector<ObsInfo>();
						 * ObsFileParse obsParse = new ObsFileParse(); data =
						 * obsParse.Parse(path); obs_data.put(path, data);
						 */

						LyrFileParse parse = new LyrFileParse();
						LyrInfo lyr = parse.ParseFile(path);
						lyr_data.put(path, lyr);

						ObjectOutputStream out = null;
						try
						{
							out = new ObjectOutputStream(new FileOutputStream(
									path + ".bin")); //$NON-NLS-1$
							out.writeObject(lyr);
							out.close();

						} catch (FileNotFoundException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

				Display.getDefault().syncExec(new Runnable()
				{

					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						shell.dispose();
					}

				});
			}

		});
		parseThread.start();

		waitDlg.open();

		try
		{
			parseThread.join();
		} catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SaveProject();
	}

	/**
	 * 通知任务提交完成
	 * 
	 * @param info
	 */
	public void NotifyJobComplete(TaskInfo info)
	{
		System.err.println("NotifyJobComplete"); //$NON-NLS-1$
	}

	/**
	 * 对象拷贝
	 */
	public BaseItem Copy(BaseItem parent)
	{

		// 创建xml项
		Element info = parent.GetInfo().addElement("Job"); //$NON-NLS-1$

		// 保证不重名
		String name = m_info.attributeValue("name"); //$NON-NLS-1$
		/*
		 * String name = "copy " + m_info.attributeValue("name"); ModelItem
		 * modelItem = GetModelItem();
		 * while(!modelItem.CheckCaseNameUnique(name)){ name = "copy " + name; }
		 */

		// 创建树项
		TreeItem item = new TreeItem(parent.GetTreeItem(), SWT.NONE);
		item.setText(name);

		// 创建节点
		BaseItem copy = new JobItem(item, info);

		copy.GetInfo().addAttribute("name", name); //$NON-NLS-1$

		String method = m_info.attributeValue("method"); //$NON-NLS-1$
		if (method != null)
			copy.GetInfo().addAttribute("method", method); //$NON-NLS-1$

		String times = m_info.attributeValue("times"); //$NON-NLS-1$
		if (times != null)
			copy.GetInfo().addAttribute("times", times); //$NON-NLS-1$

		String style = m_info.attributeValue("style"); //$NON-NLS-1$
		if (style != null)
			copy.GetInfo().addAttribute("style", style); //$NON-NLS-1$

		String paraNum = m_info.attributeValue("paraNum"); //$NON-NLS-1$
		if (paraNum != null)
			copy.GetInfo().addAttribute("paraNum", paraNum); //$NON-NLS-1$

		String methodID = m_info.attributeValue("methodID"); //$NON-NLS-1$
		if (methodID != null)
			copy.GetInfo().addAttribute("methodID", methodID); //$NON-NLS-1$

		// 拷贝相关附件信息
		copy.CopyAttachment(this, true);

		return copy;
	}

	/**
	 * 同步删除任务
	 */
	public void SynDeleteJob()
	{
		String name = GetName();

		// 发送服务端
		TreeItem itCase = m_treeItem.getParentItem();
		if (itCase == null)
		{
			System.out.println("Can't Find Parent Case Item!"); //$NON-NLS-1$
			return;
		}

		TreeItem itModel = itCase.getParentItem();
		if (itModel == null)
		{
			System.out.println("Can't Find Parent Model Item!"); //$NON-NLS-1$
			return;
		}

		TreeItem itProj = itModel.getParentItem();
		if (itProj == null)
		{
			System.out.println("Can't Find Parent Project Item!"); //$NON-NLS-1$
			return;
		}

		TaskInfo taskInfo = new TaskInfo();
		taskInfo.sProjectName = itProj.getText();
		taskInfo.sUserName = NetWorkManage.GetLoginInfo().User;
		taskInfo.sModelName = itModel.getText();
		taskInfo.sCaseName = itCase.getText();
		taskInfo.sTaskName = name;

		NetWorkManage.GetSendEvent().DeleteTask(taskInfo);
	}

	/**
	 * 同步创建任务
	 */
	public void SynCreateJob()
	{

		String name = GetName();

		// 发送服务端
		TreeItem itCase = m_treeItem.getParentItem();
		if (itCase == null)
		{
			System.out.println("Can't Find Parent Case Item!"); //$NON-NLS-1$
			return;
		}

		TreeItem itModel = itCase.getParentItem();
		if (itModel == null)
		{
			System.out.println("Can't Find Parent Model Item!"); //$NON-NLS-1$
			return;
		}

		TreeItem itProj = itModel.getParentItem();
		if (itProj == null)
		{
			System.out.println("Can't Find Parent Project Item!"); //$NON-NLS-1$
			return;
		}

		TaskInfo taskInfo = new TaskInfo();
		taskInfo.sProjectName = itProj.getText();
		taskInfo.sUserName = NetWorkManage.GetLoginInfo().User;
		taskInfo.sModelName = itModel.getText();
		taskInfo.sCaseName = itCase.getText();
		taskInfo.sTaskName = name;
		NetWorkManage.GetSendEvent().NewTask(taskInfo);
	}

	/**
	 * 获取已经提交的任务
	 */
	public Vector<TaskInfo> GetSubmitedTaskInfo()
	{

		Vector<TaskInfo> tasksInfo = new Vector<TaskInfo>();

		Vector<JobItem> jobs = GetCaseItem().GetAllJob();
		Iterator<JobItem> it_job = jobs.iterator();
		while (it_job.hasNext())
		{
			JobItem jobItem = (JobItem) it_job.next();

			if (jobItem.IsCompleteSubmit())
			{

				TaskInfo taskInfo = jobItem.GetTaskinfo();
				if (taskInfo == null)
					continue;

				tasksInfo.add(taskInfo);
			}
		}

		return tasksInfo;
	}

	/**
	 * 保存项目
	 */
	public void SaveProject()
	{

		GetCaseItem().GetModelItem().GetProjectItem().SaveProject();
	}
}
