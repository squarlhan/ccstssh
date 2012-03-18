package apriori;



import java.util.StringTokenizer;

/*
 * ��Rule���ڴ����������
 * condition �ǹ��������
 * conditionAndConclution �ǹ����ȫ�������ڷ������confidence
 *                conclution����ͨ������stringDelSubString��condition
 *                ��conditionAndConclution�г�ȥ
 * confidence�Ǹ�����������Ŷ�
 * confidence(A=>B) = support(AB)/support(A)
 *   AB����conditionAndConclution
 *   A��condition
 *   B��conclution
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
		return numToName(condition) + " => " + numToName(stringDelSubString(conditionAndConclution, condition)) + " �����Ŷ��ǣ�"
		   + (Math.round(confidence * 100)) + "%";
	}
	
	/*
	 * �㷨���ã�����stringDelSubString��str2��str1�м�ȥ�������޸ĺ��str1
	 * �㷨˼�룺�� str2����"|"���в�֣����ڲ�ֳ���ÿһ���ַ������估��֮ǰ��"|"
	 *          ��str1��ɾ��
	 * ������   str1���ַ���
	 *          str2�Ǵ�ɾ�������ַ���
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
	 * �㷨���ã��������ַ���ӳ��ؾ������ƷID�ַ���
	 * 
	 * �㷨˼�룺������FormatInput�е����֣�IDӳ���fromNumToStrMap�����ڸ�����condition����conclution,
	 *          ����ӳ��ɾ����ID��
	 * ������    num�������ַ���
	 * 
	 * ������������FormatInput�Ĺ��к���getFromNumToStrMap()��
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
