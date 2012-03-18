package apriori;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/*
 * 类DBPool是一个数据库连接池。
 */
class DBPool {
	/*
	 * 算法作用：方法connectDB用于连接指定数据库，并将其中元素存入ArrayList中
	 * 算法思想：从指定数据库中逐行读入元素，arrayList1存储用户ID，‘
	 *          arrayList2存储商品ID，利用JDBC读入
	 * 参数：    arrayList1存储用户ID，每一个单元是一个用户
	 *          arrayList2存储商品ID，一个单元对应一个ArrayList，此ArrayList存储与arrayList1中
	 *                    相对应的用户所购买的商品。
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
		System.out.println("原始数据库是:");
		System.out.println(arrayList2);
		return arrayList2;
	}
}
