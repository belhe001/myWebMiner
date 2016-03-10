package com.github.belhe001.myWebMiner.myCrawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.belhe001.myWebMiner.myBackup.myWebBackup;
import com.github.belhe001.myWebMiner.myConstants.my;


public class myAnalyser implements Runnable	{
	String target;
	private HashSet<String> locations = new HashSet<String>();
	private HashSet<String> fb = new HashSet<String>();
	private HashSet<String> ln = new HashSet<String>();
	private HashSet<String> tw = new HashSet<String>();
	private HashSet<String> g = new HashSet<String>();
	private HashSet<String> yt = new HashSet<String>();
	private HashSet<String> tel = new HashSet<String>();
	private HashSet<String> mail = new HashSet<String>();
	public HashMap<String, HashSet<String>> social = new HashMap<String, HashSet<String>>();
	private Matcher mymacher;
	private Document doc;
	private String text;

	public myAnalyser() {}
	public myAnalyser(Document doc) {this.doc=doc;}
	public myAnalyser(String target) {this.target=target;}

	public void run(){
		try {
			visit();
			new myWebBackup(doc);
			setText();
			setSocial();
			setLocation();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void visit() throws IOException  {	doc  = Jsoup.connect(target).userAgent(my.useragent).timeout(my.timeout).get();	}
	public void setText() 					{	text = doc.text();}
	public void setSocial() {
		Elements links = doc.select("a[href]");		
		for (Element link : links) {
			this.target=link.attr("abs:href").toLowerCase();
			if(target.contains("facebook"))			fb.add(target);
			else if(target.contains("linkedin"))	ln.add(target);
			else if(target.contains("twitter"))		tw.add(target);
			else if(target.contains("google"))		g.add(target);
			else if(target.contains("youtube"))		yt.add(target);
		}
		mymacher = my.REmail.matcher(doc.toString());
		while(mymacher.find()) mail.add(doc.toString().substring( mymacher.start(), mymacher.end()));
		mymacher = my.REtel.matcher(doc.toString().toLowerCase());
		while(mymacher.find()) tel.add(doc.toString().substring( mymacher.start(), mymacher.end()));
		if(fb.size()>0) social.put("facebook", fb);
		if(ln.size()>0)social.put("linkedin",ln );
		if(tw.size()>0)social.put("twitter", tw);
		if(g.size()>0)social.put("google", g);
		if(yt.size()>0)social.put("youtube",yt );
		if(tel.size()>0)social.put("tel", tel );
		if(mail.size()>0)social.put("mail", mail );
	}
	public void setLocation(){
		String html = doc.toString().replaceAll("[^a-zA-Z]", " ").replaceAll(" +", " ").toLowerCase();
		for(String a:my.countries)	if(html.contains(" "+a.toLowerCase()+" ")) locations.add(a.toUpperCase());
	}
	public void setLocation(String s){
		String html = s.replaceAll("[^a-zA-Z]", " ").replaceAll(" +", " ").toLowerCase();
		System.out.println(html);
		for(String a:my.countries)	if(html.contains(" "+a.toLowerCase()+" ")) locations.add(a.toUpperCase());
	}

	public HashMap<String,HashSet<String>> getSocial() {	return social;	}
	public HashSet<String> getFB() {return fb;}
	public HashSet<String> getLN() {return ln;}
	public HashSet<String> getTW() {return tw;}
	public HashSet<String> getG() {return g;}
	public HashSet<String> getYT() {return yt;}
	public HashSet<String> getTEL() {return tel;}
	public HashSet<String> getMAIL() {return mail;}
	public HashSet<String> getLocations() {return locations;}
	public String getText() {return text;}

}
