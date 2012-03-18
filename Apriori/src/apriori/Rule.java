package apriori;



import java.util.StringTokenizer;

/*
 * 类Rule用于储存规则，其中
 * condition 是规则的条件
 * conditionAndConclution 是规则的全部，用于方便计算confidence
 *                conclution可以通过函数stringDelSubString将condition
 *                从conditionAndConclution中除去
 * confidence是该条规则的置信度
 * confidence(A=>B) = support(AB)/support(A)
 *   AB就是conditionAndConclution
 *   A是condition
 *   B是conclution
 *    
 */
public class Rule {
	private String condition;
	private String conditionAndConclution;
	private double confidence;
	private String outc;
	
	Rule(String condition,String conditionAndConclution){
		this.condition = condition;
		this.conditionAndConclution  = conditionAndConclution;
	}

	public String getConditionAndConclution() {
		return conditionAndConclution;
	}
	public void setConditionAndConclution(String conditionAndConclution) {
		this.conditionAndConclution = conditionAndConclution;
	}

	
	public String getOutc() {
		return (Math.round(confidence * 100)) + "%";
	}
	public void setOutc(String outc) {
		this.outc = outc;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String toString(){
		return numToName(condition) + " => " + numToName(stringDelSubString(conditionAndConclution, condition));
	}
	
	public String toStringold(){
		return numToName(condition) + " => " + numToName(stringDelSubString(conditionAndConclution, condition)) + " 的置信度是："
		   + (Math.round(confidence * 100)) + "%";
	}
	
	/*
	 * 算法作用：方法stringDelSubString将str2从str1中减去，返回修改后的str1
	 * 算法思想：将 str2利用"|"进行拆分，对于拆分出的每一个字符串将其及其之前的"|"
	 *          从str1中删除
	 * 参数：   str1是字符串
	 *          str2是待删除的子字符串
	 */
	private String stringDelSubString(String str1, String str2){
		String result = str1;
		StringTokenizer tokenizer = new StringTokenizer(str2, "|");
		while(tokenizer.hasMoreTokens()){
			String temp1 = tokenizer.nextToken().toString();
			if(result.indexOf(temp1) == 0){
				result = result.substring(temp1.length() + 1);
			}
			else{
				result = result.replace(("|" + temp1), "");
			}
		}
		return result;
	}
	
	/*
	 * 算法作用：将数字字符串映射回具体的商品ID字符串
	 * 
	 * 算法思想：根据类FormatInput中的数字－ID映射表fromNumToStrMap，对于给定的condition或者conclution,
	 *          将其映射成具体的ID。
	 * 参数：    num是数字字符串
	 * 
	 * 辅助函数：类FormatInput的公有函数getFromNumToStrMap()。
	 */
	private static String numToName(String num){
		if(!num.contains("|"))
			return FormatInput.getFromNumToStrMap().get(num).toString();
		else{
			StringTokenizer tokenizer = new StringTokenizer(num, "|");
			String result = "";
			while(tokenizer.hasMoreTokens()){
				String tempStr = tokenizer.nextToken().toString();
				result = result + FormatInput.getFromNumToStrMap().get(tempStr).toString() + "|";
			}
			result = result.substring(0, result.length() - 1);
			return result;
		}
		
	}


}
