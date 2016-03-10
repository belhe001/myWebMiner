package com.github.belhe001.myWebMiner.myMain;

import com.github.belhe001.myWebMiner.myCrawler.myDynamic;

public class myLinkScreenShot {
	public static void main(String[] args) {
		Thread t =new Thread(new myDynamic("http://github.com"));
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
