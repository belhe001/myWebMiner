package com.github.belhe001.myWebMiner.myCrawler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.belhe001.myWebMiner.myBackup.myWebBackup;
import com.github.belhe001.myWebMiner.myConstants.my;
import com.github.belhe001.myWebMiner.myDBhelper.mydbhelper;
import com.mongodb.BasicDBObject;

public class mytarget implements Runnable	{
	String result;
	String target;
	String title;
	String about="";
	String career="";
	String contact="";
	String txt="";
	BasicDBObject json = new BasicDBObject();
	Map<String, String> url = new HashMap<String, String>();
	Map<String, String> text = new HashMap<String, String>();
	HashSet<String> countries = new HashSet<String>();
	public mytarget(String target) {
		this.target=target;
		System.out.println("visiting:"+target);
	}

	public void run(){
		SubThreadController();
		(new Thread(new mydbhelper(getjson()))).start(); 
	}
	public BasicDBObject getjson() {return json;}
	public void SubThreadController() {
		if(target.startsWith("http")){
			try {
				//find about, career & contact links
				Document doc = Jsoup.connect(target).userAgent(my.useragent).timeout(my.timeout).get();
				new myWebBackup(doc);
				Elements links = doc.select("a[href]");

				for (Element link : links) {
					this.result=link.attr("abs:href");
					this.title=link.text().toLowerCase();
					if(title.contains("about") ){
						about = (about.length()>result.length()||about=="") ? result:about;
					}
					if(title.contains("contact") ){
						contact = (contact.length()>result.length()||contact=="") ? result:contact;
					}
					if(title.contains("career")||title.contains("work with")||title.contains("work for")||title.contains("hiring")||title.contains("job") ){
						career = (career.length()>result.length()||career=="") ? result:career;
					}
				}
				//create thread
				myAnalyser targetS = new myAnalyser(contact);
				myAnalyser targetA = new myAnalyser(about);
				myAnalyser targetC = new myAnalyser(career);
				Thread Tcontact = new Thread(targetS);
				Thread Tabout = new Thread(targetA);
				Thread Tcareer = new Thread(targetC);
				if(contact.startsWith("http")) Tcontact.start();
				if(about.startsWith("http")) Tabout.start();
				if(career.startsWith("http")) Tcareer.start();
				//threads r running
				//create json doc
				json.append("Title", doc.title());//set title

				url.put("home", target);
				url.put("about", about);
				url.put("career", career);
				url.put("contact", contact);
				json.append("urls", url);//set urls map

				Tabout.join();
				Tcareer.join();
				Tcontact.join();
				json.append("social", targetS.social );//set social map

				countries.addAll(targetA.getLocations());//aboutPlace
				countries.addAll(targetC.getLocations());//careerPlace
				json.append("countries", countries);

				text.put("home", doc.text());
				text.put("about",targetA.getText());
				text.put("career", targetC.getText());
				json.append("text", text); //set text map
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
