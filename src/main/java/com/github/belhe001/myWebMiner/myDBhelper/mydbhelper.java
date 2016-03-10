package com.github.belhe001.myWebMiner.myDBhelper;

import com.mongodb.BasicDBObject;

public class mydbhelper implements Runnable	{
	private BasicDBObject doc = new BasicDBObject();
	
	public mydbhelper(BasicDBObject doc) { this.doc=doc; }
	public void run(){
		try {
			myMongoHelper.coll.insert(doc);
			System.out.println(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
