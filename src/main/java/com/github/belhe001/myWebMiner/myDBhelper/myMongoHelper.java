package com.github.belhe001.myWebMiner.myDBhelper;

import com.github.belhe001.myWebMiner.myConstants.my;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class myMongoHelper {
	private static MongoClient mongoClient;
	private static DB db ;
	public static DBCollection coll;
	int count ;

	@SuppressWarnings("deprecation")
	public myMongoHelper() {		
		myMongoHelper.mongoClient = new MongoClient( my.mongohost , my.mongodbport );
		myMongoHelper.db = mongoClient.getDB( my.mongodb );
		myMongoHelper.coll = db.getCollection(my.mongocoll);
		myMongoHelper.coll.drop();
		myMongoHelper.coll = db.getCollection(my.mongocoll);
		System.out.println("Mongodb : "+coll.getFullName());
	}
	public DB getdb() { return db;}
	public DBCollection getCollection() {return coll;}
	public void myclose() {mongoClient.close();}
}
