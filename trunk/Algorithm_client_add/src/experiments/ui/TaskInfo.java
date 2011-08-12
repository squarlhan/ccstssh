package experiments.ui;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Vector;

/**
 * 任务节点信息结构
 * @author wxp
 *
 */
public class TaskInfo implements Serializable {

	private static final long serialVersionUID = -7520890631449387298L;

	/**
	 * 表示是否将初始值作为一次模拟
	 */
	public boolean useInitFlag = false;
	/**
	 * 工程名称
	 */
	public String sProjectName = "";
	/**
	 * 用户名称
	 */
	public String sUserName = "";
	/**
	 * 模型名称
	 */
	public String sModelName = "";
	/**
	 * 方案名称
	 */
	public String sCaseName = "";
	/**
	 * 任务名称
	 */
	public String sTaskName = "";
	
	/**
	 * 重命名
	 */
	public String reName = "";
	// public String sType = "";
	/**
	 * 算法集合
	 */
	public Vector<MethodInfo> sMethod;
	/**
	 * 任务路径
	 */
	public String sTaskPath = "";

	public void setUseInitFlag(boolean useInitFlag)
	{
		this.useInitFlag = useInitFlag;
	}
	public boolean getUseInitFlag()
	{
		return useInitFlag;
	}
	public TaskInfo() {
		sMethod = new Vector<MethodInfo>();
	}

	public TaskInfo(String sProjectName, String sUserName, String sModelName,
			String sCaseName, String sTaskName) {
		this.sProjectName = sProjectName;
		this.sUserName = sUserName;
		this.sModelName = sModelName;
		this.sCaseName = sCaseName;
		this.sTaskName = sTaskName;
	}

	public TaskInfo(String sProjectName, String sUserName, String sModelName,
			String sCaseName, String sTaskName, String sType,
			Vector<MethodInfo> sMethod) {
		this.sProjectName = sProjectName;
		this.sUserName = sUserName;
		this.sModelName = sModelName;
		this.sCaseName = sCaseName;
		this.sTaskName = sTaskName;
		// this.sType = sType;
		this.sMethod = sMethod;
	}

	public Vector<MethodInfo> getSMethod() {
		return sMethod;
	}

	public void setSMethod(Vector<MethodInfo> method) {
		sMethod = method;
	}

	
	/**
	 * 转换方法的数据
	 * @param methods
	 * @param strMethod
	 * @param strTimes
	 * @param strStyle
	 * @param strParaNum
	 * @param strMethodID
	 * @param strBYMostTimes
	 * @param strBYBestTimes
	 * @param strGACrossRate1
	 * @param strGACrossRate2
	 * @param strGAInsideIterator
	 * @param strGATemperature
	 * @param strGACooling
	 * @param strGAVariationRate
	 * @param strAPGACrossRate1
	 * @param strAPGACrossRate2
	 * @param strAPGAInsideIterator
	 * @param strAPGATemperature
	 * @param strAPGACooling
	 * @param strAPGAVariationRate
	 * @param strPSOPartStudyDivisor
	 * @param strPSOGlobalStydyDivisor
	 * @param strPSOIntertiaWeight
	 * @param strPSOTuiHuo
	 * @param strPOSIterator
	 * @param strESngInt
	 * @param strESchSigma
	 * @param strESOneFifth_gen
	 */
	
	/**
	 * 此方法根据传入的新算法的参数要做更改，更改方法参考该方法体中的其它算法的代码，结构上都是一直的
	 */
	public static void TransMethodData(Vector<MethodInfo> methods,
			StringBuffer strMethod, StringBuffer strTimes,
			StringBuffer strStyle, StringBuffer strParaNum,
			StringBuffer strMethodID, StringBuffer strBYMostTimes,
			StringBuffer strBYBestTimes, StringBuffer strGACrossRate1,
			StringBuffer strGACrossRate2, StringBuffer strGAInsideIterator,
			StringBuffer strGATemperature, StringBuffer strGACooling,
			StringBuffer strGAVariationRate,StringBuffer strAPGACrossRate1,
			StringBuffer strAPGACrossRate2, StringBuffer strAPGAInsideIterator,
			StringBuffer strAPGATemperature, StringBuffer strAPGACooling,
			StringBuffer strAPGAVariationRate,
			StringBuffer strPSOPartStudyDivisor,
			StringBuffer strPSOGlobalStydyDivisor,
			StringBuffer strPSOIntertiaWeight, StringBuffer strPSOTuiHuo,
			StringBuffer strPOSIterator,StringBuffer strESngInt, StringBuffer strESchSigma,
			StringBuffer strESOneFifth_gen) {

		int a = 0; // 表示量，如果a==1,在有贝叶斯算法参与 a==2 有遗传算法参与 a==3 粒子群 a==4有进化算法参与a==5有聚类遗传算法参与

		Iterator<MethodInfo> it_methods = methods.iterator();
		while (it_methods.hasNext()) {
			MethodInfo methodInfo = (MethodInfo) it_methods.next();
			String methodName = methodInfo.getMethodName();
			// 方法名
			strMethod.append(methodInfo.getMethodName());
			strMethod.append(",");

			// 运行次数
			strTimes.append(methodInfo.getNum());
			strTimes.append(",");

			// most次数
			if (methodName.equalsIgnoreCase("贝叶斯更新") || methodName.equalsIgnoreCase("Bayesian")) {
				a = 1;
				BayesParamsInfo bayes = (BayesParamsInfo) methodInfo
						.getParamStruct();
				strBYMostTimes.append(bayes.getIntMost());
				strBYMostTimes.append(",");

				strBYBestTimes.append(bayes.getIntBest());
				strBYBestTimes.append(",");
			} else if (methodName.equalsIgnoreCase("遗传算法") || methodName.equalsIgnoreCase("GA")) {
				a = 2;
				GAParamsInfo ga = (GAParamsInfo) methodInfo.getParamStruct();

				strGACrossRate1.append(ga.getPc1());
				strGACrossRate1.append(",");

				strGACrossRate2.append(ga.getPc2());
				strGACrossRate2.append(",");

				strGAInsideIterator.append(ga.getNG());
				strGAInsideIterator.append(",");

				strGATemperature.append(ga.getK());
				strGATemperature.append(",");

				strGACooling.append(ga.getPt());
				strGACooling.append(",");

				strGAVariationRate.append(ga.getPm());
				strGAVariationRate.append(",");

			} else if (methodName.equalsIgnoreCase("粒子群") || methodName.equalsIgnoreCase("PSO")) {
				a = 3;
				PSOParamsInfo pso = (PSOParamsInfo) methodInfo.getParamStruct();

				strPSOPartStudyDivisor.append(pso.getC1());
				strPSOPartStudyDivisor.append(",");

				strPSOGlobalStydyDivisor.append(pso.getC2());
				strPSOGlobalStydyDivisor.append(",");

				strPSOIntertiaWeight.append(pso.getW());
				strPSOIntertiaWeight.append(",");

				strPSOTuiHuo.append(pso.getLamda());
				strPSOTuiHuo.append(",");

				strPOSIterator.append(pso.getN());
				strPOSIterator.append(",");
			}else if(methodName.equalsIgnoreCase("进化策略算法") || methodName.equalsIgnoreCase("ES"))
			{
				a = 4;
				ESParamsInfo es = (ESParamsInfo) methodInfo.getParamStruct();
                //内部迭代次数
				strESngInt.append(es.getNgInt());
				strESngInt.append(",");
                //变异方差的变化
				strESchSigma.append(es.getChSigma());
				strESchSigma.append(",");
				//执行五分之一法则的代数
				strESOneFifth_gen.append(es.getOneFifth_gen());
				strESOneFifth_gen.append(",");

			}else if (methodName.equalsIgnoreCase("聚类遗传算法") || methodName.equalsIgnoreCase("APGA")) {
				a = 5;
				APGAParamsInfo apga = (APGAParamsInfo) methodInfo.getParamStruct();

				strAPGACrossRate1.append(apga.getPc1());
				strAPGACrossRate1.append(",");

				strAPGACrossRate2.append(apga.getPc2());
				strAPGACrossRate2.append(",");

				strAPGAInsideIterator.append(apga.getNG());
				strAPGAInsideIterator.append(",");

				strAPGATemperature.append(apga.getK());
				strAPGATemperature.append(",");

				strAPGACooling.append(apga.getPt());
				strAPGACooling.append(",");

				strAPGAVariationRate.append(apga.getPm());
				strAPGAVariationRate.append(",");

			}

			// 运行方式
			strStyle.append(methodInfo.getRunStyle());
			strStyle.append(",");

			// 并行最大次数
			strParaNum.append(methodInfo.getParallelNum());
			strParaNum.append(",");

			// 算法ID
			strMethodID.append(methodInfo.getMethodID());
			strMethodID.append(",");
		}

		// 去掉多出的分割号
		if (!methods.isEmpty()) {
			strMethod.deleteCharAt(strMethod.length() - 1);
			strTimes.deleteCharAt(strTimes.length() - 1);
			strStyle.deleteCharAt(strStyle.length() - 1);
			strParaNum.deleteCharAt(strParaNum.length() - 1);
			strMethodID.deleteCharAt(strMethodID.length() - 1);
			if (a == 1) {
				strBYMostTimes.deleteCharAt(strBYMostTimes.length() - 1);
				strBYBestTimes.deleteCharAt(strBYBestTimes.length() - 1);
			}
			if (a == 2) {
				strGACrossRate1.deleteCharAt(strGACrossRate1.length() - 1);
				strGACrossRate2.deleteCharAt(strGACrossRate2.length() - 1);
				strGAInsideIterator
						.deleteCharAt(strGAInsideIterator.length() - 1);
				strGATemperature.deleteCharAt(strGATemperature.length() - 1);
				strGACooling.deleteCharAt(strGACooling.length() - 1);
				strGAVariationRate
						.deleteCharAt(strGAVariationRate.length() - 1);
			}
			if (a == 3) {
				strPSOPartStudyDivisor.deleteCharAt(strPSOPartStudyDivisor
						.length() - 1);
				strPSOGlobalStydyDivisor.deleteCharAt(strPSOGlobalStydyDivisor
						.length() - 1);
				strPSOIntertiaWeight
						.deleteCharAt(strPSOIntertiaWeight.length() - 1);
				strPSOTuiHuo.deleteCharAt(strPSOTuiHuo.length() - 1);
				strPOSIterator.deleteCharAt(strPOSIterator.length() - 1);
			}
			if(a == 4)
			{
				strESngInt.deleteCharAt(strESngInt.length() - 1);
				strESchSigma.deleteCharAt(strESchSigma.length() - 1);
				strESOneFifth_gen.deleteCharAt(strESOneFifth_gen.length() - 1);
			}
			if (a == 5) {
				strAPGACrossRate1.deleteCharAt(strAPGACrossRate1.length() - 1);
				strAPGACrossRate2.deleteCharAt(strAPGACrossRate2.length() - 1);
				strAPGAInsideIterator
						.deleteCharAt(strAPGAInsideIterator.length() - 1);
				strAPGATemperature.deleteCharAt(strAPGATemperature.length() - 1);
				strAPGACooling.deleteCharAt(strAPGACooling.length() - 1);
				strAPGAVariationRate
						.deleteCharAt(strAPGAVariationRate.length() - 1);
			}
		}
	}

	
	/**
	 * 判断数据的正确性
	 * @param strMethod
	 * @param strTimes
	 * @param strStyle
	 * @param strParaNum
	 * @param strMethodID
	 * @param methods
	 * @param strMostTimes
	 * @param strBestTimes
	 * @param strGACroosRate1
	 * @param strGACroosRate2
	 * @param strGATemperature
	 * @param strGAInsideIterator
	 * @param strGAVariationRate
	 * @param strGACooling
	 * @param strAPGACroosRate1
	 * @param strAPGACroosRate2
	 * @param strAPGATemperature
	 * @param strAPGAInsideIterator
	 * @param strAPGAVariationRate
	 * @param strAPGACooling
	 * @param strPSOPartStudyDivisor
	 * @param strPSOGlobalStudyDivisor
	 * @param strPSOIntertiaWeight
	 * @param strPSOAnneal
	 * @param strPSOIterator
	 * @param strESngInt
	 * @param strESchSigma
	 * @param strESOneFifth_gen
	 * @return
	 */
	
	/**
	 * 此方法根据传入的新算法的参数要做更改，更改方法参考该方法体中的其它算法的代码，结构上都是一直的
	 */
	public static boolean TransMethodData(String strMethod, String strTimes,
			String strStyle, String strParaNum, String strMethodID,
			Vector<MethodInfo> methods, String strMostTimes,
			String strBestTimes, String strGACroosRate1,
			String strGACroosRate2, String strGATemperature,
			String strGAInsideIterator, String strGAVariationRate,
			String strGACooling, String strAPGACroosRate1,
			String strAPGACroosRate2, String strAPGATemperature,
			String strAPGAInsideIterator, String strAPGAVariationRate,
			String strAPGACooling,String strPSOPartStudyDivisor,
			String strPSOGlobalStudyDivisor, String strPSOIntertiaWeight,
			String strPSOAnneal, String strPSOIterator, String strESngInt,
			String strESchSigma, String strESOneFifth_gen) {

		String[] Method = strMethod.split(",");
		String[] Times = strTimes.split(",");
		String[] Style = strStyle.split(",");
		String[] ParaNum = strParaNum.split(",");
		String[] MethodID = strMethodID.split(",");

		// 确保一一对应
		if (Method.length == Times.length && Times.length == Style.length
				&& Style.length == ParaNum.length
				&& ParaNum.length == MethodID.length) {

			for (int i = 0; i < Method.length; i++) {

				MethodInfo mi = new MethodInfo(Method[i]);
				mi.setNum(Integer.valueOf(Times[i]));
				mi.setRunStyle(Style[i]);
				mi.setParallelNum(Integer.valueOf(ParaNum[i]));
				mi.setMethodID(MethodID[i]);
				// mi.setMostNum(Integer.valueOf(mostTimes[i]));
				// mi.setBestNum(Integer.valueOf(bestTimes[i]));
				if (Method[i].equalsIgnoreCase("贝叶斯更新") || Method[i].equalsIgnoreCase("Bayesian")) {
					BayesParamsInfo bayes = new BayesParamsInfo();
					String[] most = strMostTimes.split(",");
					String[] best = strBestTimes.split(",");
					bayes.setIntMost(Integer.parseInt(most[0]));
					bayes.setIntBest(Integer.parseInt(best[0]));
					mi.setParamStruct(bayes);
				} else if (Method[i].equalsIgnoreCase("遗传算法") || Method[i].equalsIgnoreCase("GA")) {
					GAParamsInfo ga = new GAParamsInfo();
					String[] crossRate1 = strGACroosRate1.split(",");
					String[] crossRate2 = strGACroosRate2.split(",");
					String[] temperature = strGATemperature.split(",");
					String[] iterator = strGAInsideIterator.split(",");
					String[] variationRate = strGAVariationRate.split(",");
					String[] cooling = strGACooling.split(",");

					ga.setPc1(Double.parseDouble(crossRate1[0]));
					ga.setPc2(Double.parseDouble(crossRate2[0]));
					ga.setK(Double.parseDouble(temperature[0]));
					ga.setPt(Double.parseDouble(cooling[0]));
					ga.setPm(Double.parseDouble(variationRate[0]));
					ga.setNG(Integer.parseInt(iterator[0]));

					mi.setParamStruct(ga);
				} else if (Method[i].equalsIgnoreCase("粒子群") || Method[i].equalsIgnoreCase("PSO")) {
					PSOParamsInfo pso = new PSOParamsInfo();

					String[] partStudy = strPSOPartStudyDivisor.split(",");
					String[] globalStudy = strPSOGlobalStudyDivisor.split(",");
					String[] intertiaWeight = strPSOIntertiaWeight.split(",");
					String[] anneal = strPSOAnneal.split(",");
					String[] iterator = strPSOIterator.split(",");

					pso.setC1(Double.parseDouble(partStudy[0]));
					pso.setC2(Double.parseDouble(globalStudy[0]));
					pso.setW(Double.parseDouble(intertiaWeight[0]));
					pso.setLamda(Double.parseDouble(anneal[0]));
					pso.setN(Integer.parseInt(iterator[0]));

					mi.setParamStruct(pso);
				}else if(Method[i].equalsIgnoreCase("进化策略算法") || Method[i].equalsIgnoreCase("ES"))
				{
					ESParamsInfo es = new ESParamsInfo();
					String[] ngInt = strESngInt.split(",");
					String[] chSigma = strESchSigma.split(",");
					String[] OneFifth_gen = strESOneFifth_gen.split(",");
					
					es.setNgInt(Integer.parseInt(ngInt[0]));
					es.setChSigma(Double.parseDouble(chSigma[0]));
					es.setOneFifth_gen(Integer.parseInt(OneFifth_gen[0]));
					
					mi.setParamStruct(es);
				}else if (Method[i].equalsIgnoreCase("聚类遗传算法") || Method[i].equalsIgnoreCase("APGA")) {
					APGAParamsInfo apga = new APGAParamsInfo();
					String[] crossRate1 = strAPGACroosRate1.split(",");
					String[] crossRate2 = strAPGACroosRate2.split(",");
					String[] temperature = strAPGATemperature.split(",");
					String[] iterator = strAPGAInsideIterator.split(",");
					String[] variationRate = strAPGAVariationRate.split(",");
					String[] cooling = strAPGACooling.split(",");

					apga.setPc1(Double.parseDouble(crossRate1[0]));
					apga.setPc2(Double.parseDouble(crossRate2[0]));
					apga.setK(Double.parseDouble(temperature[0]));
					apga.setPt(Double.parseDouble(cooling[0]));
					apga.setPm(Double.parseDouble(variationRate[0]));
					apga.setNG(Integer.parseInt(iterator[0]));

					mi.setParamStruct(apga);
				}

				methods.add(mi);
			}

			return true;
		}

		return false;
	}

}
