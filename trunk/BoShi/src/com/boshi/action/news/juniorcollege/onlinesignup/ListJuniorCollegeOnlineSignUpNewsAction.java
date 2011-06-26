package com.boshi.action.news.juniorcollege.onlinesignup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.Query;

import jxl.Cell;
import jxl.write.Number;
import jxl.CellType;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

import com.boshi.action.news.SuperListNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeOnlineSignUpNews;

public class ListJuniorCollegeOnlineSignUpNewsAction extends
		SuperListNewsAction {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 16 * 1024;
	public String curyear;
	private File upexl = null;
	private String contentType = "";
	private String fileName = "";

	public String getCuryear() {
		return curyear;
	}

	public void setCuryear(String curyear) {
		this.curyear = curyear;
	}

	public String execute() {
		super.superExecute(null);
		return SUCCESS;
	}

	public String pagination() {
		super.superPagination(null);
		return SUCCESS;
	}

	public String remove() {
		return super.remove(JuniorCollegeOnlineSignUpNews.class);
	}

	private static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	/**
	 * 
	 * 功能：把 sourceDir 目录下的所有文件进行 zip 格式的压缩，保存为指定 zip 文件 create date:2009- 6- 9
	 * 
	 * author:Administrator
	 * 
	 * 
	 * 
	 * @param sourceDir
	 * 
	 * E:\\ 我的备份
	 * 
	 * @param zipFile
	 * 
	 * 格式： E:\\stu \\zipFile.zip 注意：加入 zipFile 我们传入的字符串值是 ： "E:\\stu \\" 或者
	 * "E:\\stu "
	 * 
	 * 如果 E 盘已经存在 stu 这个文件夹的话，那么就会出现 java.io.FileNotFoundException: E:\stu (
	 * 拒绝访问。 ) 这个异常，所以要注意正确传参调用本函数哦
	 * 
	 * 
	 * 
	 */

	public static void zip(String sourceDir, String zipFile) {

		OutputStream os;

		try {

			os = new FileOutputStream(zipFile);

			BufferedOutputStream bos = new BufferedOutputStream(os);

			ZipOutputStream zos = new ZipOutputStream(bos);

			File file = new File(sourceDir);

			String basePath = null;

			if (file.isDirectory()) {

				basePath = file.getPath();

			} else {

				basePath = file.getParent();

			}

			zipFile(file, basePath, zos);

			zos.closeEntry();

			zos.close();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	/**
	 * 
	 * 
	 * 
	 * create date:2009- 6- 9 author:Administrator
	 * 
	 * 
	 * 
	 * @param source
	 * 
	 * @param basePath
	 * 
	 * @param zos
	 * 
	 * @throws IOException
	 * 
	 */

	private static void zipFile(File source, String basePath,
			ZipOutputStream zos) {

		File[] files = new File[0];

		if (source.isDirectory()) {

			files = source.listFiles();

		} else {

			files = new File[1];

			files[0] = source;

		}

		String pathName;

		byte[] buf = new byte[1024];

		int length = 0;

		try {

			for (File file : files) {

				if (file.isDirectory()) {

					pathName = file.getPath().substring(basePath.length() + 1)

					+ "/";

					zos.putNextEntry(new ZipEntry(pathName));

					zipFile(file, basePath, zos);

				} else {

					pathName = file.getPath().substring(basePath.length() + 1);

					InputStream is = new FileInputStream(file);

					BufferedInputStream bis = new BufferedInputStream(is);

					zos.putNextEntry(new ZipEntry(pathName));

					while ((length = bis.read(buf)) > 0) {

						zos.write(buf, 0, length);

					}

					is.close();

				}

			}

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	/**
	 * 读取Excel
	 * 
	 * @param filePath
	 */
	public static String[][] readExcel(String filePath) {
		String[][] results = null;
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook rwb = Workbook.getWorkbook(is);
			// Sheet st = rwb.getSheet("0")这里有两种方法获取sheet表,1为名字，2为下标，从0开始
			Sheet st = rwb.getSheet(0);
			results = new String[st.getRows()][st.getColumns()];
			for (int a = 0; a <= st.getRows() - 1; a++) {
				for (int i = 0; i <= st.getColumns()-1; i++) {
					Cell c = st.getCell(i, a);
					String strc = c.getContents();
					if (c.getType() == CellType.LABEL) {
						LabelCell labelc = (LabelCell) c;
						strc = labelc.getString();
					}
					if (strc == "" || strc == null) {
						break;
					}
					results[a][i]=strc;
				}
			}
			// Cell c00 = st.getCell(0,0);
			// 通用的获取cell值的方式,返回字符串
			// String strc00 = c00.getContents();
			// 获得cell具体类型值的方式

			// 输出
			System.out.println(st.getRows());
			// 关闭
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * 输出Excel
	 * 
	 * @param os
	 */
	public static void writeExcel(OutputStream os) {
		try {
			/**
			 * 只能通过API提供的工厂方法来创建Workbook，而不能使用WritableWorkbook的构造函数，
			 * 因为类WritableWorkbook的构造函数为protected类型
			 * method(1)直接从目标文件中读取WritableWorkbook wwb =
			 * Workbook.createWorkbook(new File(targetfile)); method(2)如下实例所示
			 * 将WritableWorkbook直接写入到输出流
			 * 
			 */
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);

			// **************往工作表中添加数据*****************

			// 1.添加Label对象
			Label label = new Label(0, 0, "this is a label test");
			ws.addCell(label);

			// 添加带有字型formatting对象
			WritableFont wf = new WritableFont(WritableFont.TIMES, 18,
					WritableFont.BOLD, true);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			Label labelcf = new Label(1, 0, "this is a label test", wcf);
			ws.addCell(labelcf);

			// 添加带有字体颜色的formatting对象
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.RED);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			Label labelCF = new Label(1, 0, "This is a Label Cell", wcfFC);
			ws.addCell(labelCF);

			// 2.添加Number对象
			Number labelN = new Number(0, 1, 3.1415926);
			ws.addCell(labelN);

			// 添加带有formatting的Number对象
			NumberFormat nf = new NumberFormat("#.##");
			WritableCellFormat wcfN = new WritableCellFormat(nf);
			Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			ws.addCell(labelNF);

			// 3.添加Boolean对象
			jxl.write.Boolean labelB = new jxl.write.Boolean(0, 2, false);
			ws.addCell(labelB);

			// 4.添加DateTime对象
			jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3,
					new java.util.Date());
			ws.addCell(labelDT);

			// 添加带有formatting的Dateformat对象
			DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
			WritableCellFormat wcfDF = new WritableCellFormat(df);
			DateTime labelDTF = new DateTime(1, 3, new java.util.Date(), wcfDF);
			ws.addCell(labelDTF);

			// 添加图片对象,jxl只支持png格式图片
			File image = new File("f:\\2.png");
			WritableImage wimage = new WritableImage(0, 1, 2, 2, image);
			ws.addImage(wimage);
			// 写入工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writemyExcel(List<JuniorCollegeOnlineSignUpNews> mydata,
			OutputStream os) {
		try {
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			// 创建Excel工作表 指定名称和位置
			WritableSheet ws = wwb.createSheet("Sheet1", 0);

			// **************往工作表中添加数据*****************

			// 1.添加Label对象
			Label label0 = new Label(0, 0, "姓名");
			Label label1 = new Label(1, 0, "性别");
			Label label2 = new Label(2, 0, "考试号");
			Label label3 = new Label(3, 0, "身份证号");
			Label label4 = new Label(4, 0, "报考专业");
			Label label5 = new Label(5, 0, "录取信息");
			Label label6 = new Label(6, 0, "手机电话");
			Label label7 = new Label(7, 0, "固定电话");
			Label label8 = new Label(8, 0, "报名时间");
			Label label9 = new Label(9, 0, "其它");
			ws.addCell(label0);
			ws.addCell(label1);
			ws.addCell(label2);
			ws.addCell(label3);
			ws.addCell(label4);
			ws.addCell(label5);
			ws.addCell(label6);
			ws.addCell(label7);
			ws.addCell(label8);
			ws.addCell(label9);
			for (int i = 0; i <= mydata.size() - 1; i++) {
				label0 = new Label(0, i + 1, mydata.get(i).getName());
				label1 = new Label(1, i + 1, mydata.get(i).getSex());
				label2 = new Label(2, i + 1, mydata.get(i).getExamid());
				label3 = new Label(3, i + 1, mydata.get(i).getPid());
				label4 = new Label(4, i + 1, mydata.get(i).getProfession());
				label5 = new Label(5, i + 1, mydata.get(i).getIsaccept());
				label6 = new Label(6, i + 1, mydata.get(i).getMobile());
				label7 = new Label(7, i + 1, mydata.get(i).getPhone());
				label8 = new Label(8, i + 1, new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(mydata.get(i).getDate()));
				label9 = new Label(9, i + 1, mydata.get(i).getContent());
				ws.addCell(label0);
				ws.addCell(label1);
				ws.addCell(label2);
				ws.addCell(label3);
				ws.addCell(label4);
				ws.addCell(label5);
				ws.addCell(label6);
				ws.addCell(label7);
				ws.addCell(label8);
				ws.addCell(label9);
			}
			//	           
			// System.out.println("Well Done!");
			// 写入工作表
			wwb.write();
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拷贝后,进行修改,其中file1为被copy对象，file2为修改后创建的对象
	 * 尽单元格原有的格式化修饰是不能去掉的，我们还是可以将新的单元格修饰加上去， 以使单元格的内容以不同的形式表现
	 * 
	 * @param file1
	 * @param file2
	 */
	public static void modifyExcel(File file1, File file2) {
		try {
			Workbook rwb = Workbook.getWorkbook(file1);
			WritableWorkbook wwb = Workbook.createWorkbook(file2, rwb);// copy
			WritableSheet ws = wwb.getSheet(0);
			WritableCell wc = ws.getWritableCell(0, 0);
			// 判断单元格的类型,做出相应的转换
			if (wc.getType() == CellType.LABEL) {
				Label label = (Label) wc;
				label.setString("The value has been modified");
			}
			wwb.write();
			wwb.close();
			rwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String downexl() {
		List<JuniorCollegeOnlineSignUpNews> allups = (List<JuniorCollegeOnlineSignUpNews>) this
				.getDataCenterInterface().getall();
		String basedir = ServletActionContext.getServletContext().getRealPath(
				"UserFiles");
		String newdir = new SimpleDateFormat("yyyy").format(new Date());
		String exceldir = basedir + "\\" + newdir;
		File dirFile = new File(exceldir);
		if (!dirFile.exists()) {
			newdir = String.valueOf(Integer.parseInt(newdir) - 1);
			exceldir = basedir + "\\" + newdir;
		}
		curyear = newdir;
		File fileWrite = new File(basedir + "\\" + "excel" + newdir + ".xls");
		try {
			fileWrite.createNewFile();
			OutputStream os = new FileOutputStream(fileWrite);
			this.writemyExcel(allups, os);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "downexl";

	}

	public String downpic() {
		String basedir = ServletActionContext.getServletContext().getRealPath(
				"UserFiles");
		String newdir = new SimpleDateFormat("yyyy").format(new Date());
		String photodir = basedir + "\\" + newdir;
		File dirFile = new File(photodir);
		if (!dirFile.exists()) {
			newdir = String.valueOf(Integer.parseInt(newdir) - 1);
			photodir = basedir + "\\" + newdir;
		}
		zip(photodir, basedir + "\\" + "photo" + newdir + ".zip");
		curyear = newdir;
		return "downpic";

	}

	@SuppressWarnings("deprecation")
	public String upexl() {
		String exldir = ServletActionContext.getServletContext().getRealPath(
				"UserFiles");
		String newdir = new SimpleDateFormat("yyyy").format(new Date());
		exldir = exldir + "\\" + newdir;
		// System.out.println("dir = "+exldir);
		File dirFile = new File(exldir);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		// System.out.println("dir1 = "+exldir);
		String exlurl = exldir + "\\" + fileName;
		File exlFile = new File(exlurl);

		// System.out.println("url = "+exlurl);
		copy(upexl, exlFile);
		String[][] newdata = this.readExcel(exlurl);
		int rows = newdata.length;
		int cols = newdata[0].length;
//		Label label0 = new Label(0, 0, "姓名");
//		Label label1 = new Label(1, 0, "性别");
//		Label label2 = new Label(2, 0, "考号");
//		Label label3 = new Label(3, 0, "身份证");
//		Label label4 = new Label(4, 0, "报考专业");
//		Label label5 = new Label(5, 0, "录取信息");
//		Label label6 = new Label(6, 0, "手机");
//		Label label7 = new Label(7, 0, "固定电话");
//		Label label8 = new Label(8, 0, "报名时间");
//		Label label9 = new Label(9, 0, "其它");
		int nameno = 0;
		int sexno = 0;
		int examidno = 0;
		int pidno = 0;
		int profno = 0;
		int isno = 0;
		int mobileno = 0;
		int phoneno = 0;
		int dateno = 0;
		int memono = 0;
		
		for(int i=0; i<= cols-1; i++){
			if(newdata[0][i].equals("姓名")){
				nameno=i;
			}
			if(newdata[0][i].equals("性别")){
				sexno=i;
			}
			if(newdata[0][i].equals("考试号")){
				examidno=i;
			}
			if(newdata[0][i].equals("报考专业")){
				profno=i;
			}
			if(newdata[0][i].equals("录取信息")){
				isno=i;
			}
			if(newdata[0][i].equals("手机电话")){
				mobileno=i;
			}
			if(newdata[0][i].equals("固定电话")){
				phoneno=i;
			}
			if(newdata[0][i].equals("报名时间")){
				dateno=i;
			}
			if(newdata[0][i].equals("身份证号")){
				pidno=i;
			}
			if(newdata[0][i].equals("其它")){
				memono=i;
			}
		}
		JuniorCollegeOnlineSignUpNews newobj = new JuniorCollegeOnlineSignUpNews();
		for(int a = 1; a<=rows-1; a++){
			
			newobj.setContent(newdata[a][memono]);
			newobj.setExamid(newdata[a][examidno]);
			newobj.setIsaccept(newdata[a][isno]);
			newobj.setMobile(newdata[a][mobileno]);
			newobj.setName(newdata[a][nameno]);
			newobj.setPhone(newdata[a][phoneno]);
			newobj.setProfession(newdata[a][profno]);
			newobj.setPid(newdata[a][pidno]);
			newobj.setSex(newdata[a][sexno]);
//			newobj.setDate(new Calendar(newdata[a][dateno]));
			List<?> rs =this.getDataCenterInterface().find(JuniorCollegeOnlineSignUpNews.class, newobj);
			if(rs.size()<1)
				this.getDataCenterInterface().createObject(newobj);
			else {
				newobj.setId(((JuniorCollegeOnlineSignUpNews)rs.get(0)).getId());
				this.getDataCenterInterface().changeContent(JuniorCollegeOnlineSignUpNews.class, newobj);
			}
		}
		
		return "input";
	}

	public File getUpexl() {
		return upexl;
	}

	public void setUpexl(File upexl) {
		this.upexl = upexl;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setUpexlContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUpexlFileName(String fileName) {
		this.fileName = fileName;
	}
}
