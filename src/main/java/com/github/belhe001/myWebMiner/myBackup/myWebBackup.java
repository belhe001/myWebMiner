package com.github.belhe001.myWebMiner.myBackup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;

import com.github.belhe001.myWebMiner.myConstants.my;


public class myWebBackup {

	private String dir;
	public myWebBackup() {
		{
			this.dir = my.dir+"\\mybackup";
			File file = new File(my.dir+"\\mybackup");
			if (!file.exists()) file.mkdirs();
		}
	}
	public myWebBackup(Document doc) {
		try {
			my.count++;
			File f = new File(dir+"//"+my.count+".html");
			FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public myWebBackup(Document doc , String name) {
		try {
			File f = new File(dir+"//"+name+".html");
			FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public myWebBackup(String file,String url) {
		try {
			FileWriter fileWritter = new FileWriter(dir+"//"+file+".txt",true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write("\n\n"+url);
	        bufferWritter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
