package com.github.belhe001.myWebMiner.myMain;

import com.github.belhe001.myWebMiner.myConstants.my;
import com.github.belhe001.myWebMiner.myCrawler.mytarget;

public class mytest1 {

	public static void main(String[] args) {
		new my();
		mytarget c ;
		c= new mytarget("http://www.oracle.com/in/index.html");
		c.SubThreadController();
		System.out.println(c.getjson());
	}
}
