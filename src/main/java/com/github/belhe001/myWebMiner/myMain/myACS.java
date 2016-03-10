package com.github.belhe001.myWebMiner.myMain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.github.belhe001.myWebMiner.myBackup.myWebBackup;
import com.github.belhe001.myWebMiner.myConstants.my;
import com.github.belhe001.myWebMiner.myCrawler.mytarget;
import com.github.belhe001.myWebMiner.myDBhelper.myMongoHelper;


public class myACS {
	
	public static void main(String[] args) {
		try {
			new myMongoHelper();
			new myWebBackup();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection("jdbc:mysql://"+my.sqlhost+"/"+my.sqldb, my.sqluser, my.sqlpwd);
			String query = "SELECT * FROM career limit 25";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				(new Thread(new mytarget(result.getString("targets")))).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
