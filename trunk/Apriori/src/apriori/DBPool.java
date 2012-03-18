package apriori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/*
 * ��DBPool��һ�����ݿ����ӳء�
 */
class DBPool {
	/*
	 * �㷨���ã�����connectDB��������ָ�����ݿ⣬��������Ԫ�ش���ArrayList��
	 * �㷨˼�룺��ָ�����ݿ������ж���Ԫ�أ�arrayList1�洢�û�ID����
	 *          arrayList2�洢��ƷID������JDBC����
	 * ������    arrayList1�洢�û�ID��ÿһ����Ԫ��һ���û�
	 *          arrayList2�洢��ƷID��һ����Ԫ��Ӧһ��ArrayList����ArrayList�洢��arrayList1��
	 *                    ���Ӧ���û����������Ʒ��
	 * 
	 */
	public static ArrayList connectDB(String ip, String db, String username, String password, String car){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList arrayList1 = new ArrayList();
		ArrayList arrayList2 = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://"+ip+":3306/"+db,
					username, password);
			stmt = conn.createStatement();
			String sql = "select * from discretepotential where carType='"+car+"'";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				arrayList1.add(String.valueOf((rs.getInt(1))));
				ArrayList temp = new ArrayList();
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				temp.add(rs.getString(4));
				temp.add(rs.getString(5));
				temp.add(rs.getString(6));
				arrayList2.add(temp);
//				String tempStr = rs.getString(1);
//				if(arrayList1.contains(tempStr)){
//					((ArrayList)(arrayList2.get(arrayList1.indexOf(tempStr)))).add(rs.getString(2));
//				}
//				else{
//					arrayList1.add(tempStr);
//					ArrayList temp = new ArrayList();
//					temp.add(rs.getString(2));
//					arrayList2.add(temp);
//				}
			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				if(rs != null){
					rs.close();
					rs = null;
				}
				if(stmt != null){
					stmt.close();
					stmt = null;
				}
				if(conn != null){
					conn.close();
					conn = null;	
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		System.out.println("ԭʼ���ݿ���:");
		System.out.println(arrayList2);
		return arrayList2;
	}
}
