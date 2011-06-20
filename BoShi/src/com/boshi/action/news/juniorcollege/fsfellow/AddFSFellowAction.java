package com.boshi.action.news.juniorcollege.fsfellow;
import com.boshi.action.news.SuperAddNewsAction;
import com.boshi.db.datamodel.news.FSFellow;
public class AddFSFellowAction extends SuperAddNewsAction{

	private static final long				serialVersionUID	= 1L;
	private FSFellow	content				= new FSFellow();
	private String      file                = null;

	public String execute() {
		
		return super.add(content);
	}

	public FSFellow getContent() {
		return content;
	}

	public void setContent(FSFellow content) {
		this.content = content;
	}
	public String getFile(){
		return file;
	}
	public void setFile(String file){
		this.file=file;
	}
}
