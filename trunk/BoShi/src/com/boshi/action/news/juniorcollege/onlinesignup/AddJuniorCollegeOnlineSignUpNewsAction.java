package com.boshi.action.news.juniorcollege.onlinesignup;

import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.JuniorCollegeOnlineSignUpNews;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

public class AddJuniorCollegeOnlineSignUpNewsAction extends SuperAddNewsAction {

	private static final long serialVersionUID = 1L;
	private JuniorCollegeOnlineSignUpNews content = new JuniorCollegeOnlineSignUpNews();
	private static final int BUFFER_SIZE = 16 * 1024;

	private File upphoto = null;
	private String contentType = "";
	private String fileName = "";

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

	public String execute() {

		if (this.getDataCenterInterface().checkdumplicate(content.getExamid())) {
			return "error1";
		}
		if (fileName.isEmpty()) {
//			return "error2";
			content.setPhoto("default.gif");
			return super.add(content);
		} else {

			String photodir = ServletActionContext.getServletContext()
					.getRealPath("UserFiles");
			String newdir = new SimpleDateFormat("yyyy").format(new Date());
			photodir = photodir + "\\" + newdir;
			File dirFile = new File(photodir);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			content.setPhoto(newdir + "/" + content.getExamid()
					+ getExtention(fileName));
			String photourl = photodir + "\\" + content.getExamid()
					+ getExtention(fileName);
			File imageFile = new File(photourl);

			// System.out.println("url = "+photourl);
			copy(upphoto, imageFile);

			FileInputStream is = null;
			try {
				is = new FileInputStream(imageFile);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			BufferedImage sourceImg = null;
			try {
				sourceImg = javax.imageio.ImageIO.read(is);
				is.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			int width = sourceImg.getWidth();
			int height = sourceImg.getHeight();
			// System.out.println("width = "+sourceImg.getWidth() + "height = "
			// + sourceImg.getHeight());
			content.setIsaccept("请等待");
			if (width == 240 && height == 320) {
				return super.add(content);
			} else {
				imageFile.delete();
				return "error";
			}
		}

	}

	public File getUpphoto() {
		return upphoto;
	}

	public void setUpphoto(File upphoto) {
		this.upphoto = upphoto;
	}

	public void setUpphotoContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUpphotoFileName(String fileName) {
		this.fileName = fileName;
	}

	public JuniorCollegeOnlineSignUpNews getContent() {
		return content;
	}

	public void setContent(JuniorCollegeOnlineSignUpNews content) {
		this.content = content;
	}

}
