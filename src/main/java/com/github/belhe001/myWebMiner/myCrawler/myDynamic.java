package com.github.belhe001.myWebMiner.myCrawler;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.belhe001.myWebMiner.myBackup.myWebBackup;
import com.github.belhe001.myWebMiner.myConstants.my;



public class myDynamic implements Runnable	{
	String target;
	String file;
	PhantomJSDriver driver;

	public myDynamic(String target) {
		this.target=target;
		this.file = my.dir+"\\myScreenShot\\Screenshot.jpeg";
		init();
	}
	public myDynamic(String target,String filePath) {
		this.target=target;
		this.file = filePath;
		init();
	}

	public void run(){
		try {
			visit();
			File scrFile = ScreenShot();
			FileUtils.copyFile(scrFile, new File(file),true);
			Document doc = Jsoup.parse(driver.getPageSource());
			new myWebBackup(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getSource() {return driver.getPageSource();}
	public PhantomJSDriver getDriver() {return driver;}

	private void init() {
		Capabilities caps = new DesiredCapabilities();
		((DesiredCapabilities) caps).setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,my.phantomjspath);
		driver = new  PhantomJSDriver(caps);
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);		
	}
	private void visit(){
		System.out.println("visiting:"+target);
		driver.get(target);
	}
	public PhantomJSDriver visit(String target){
		System.out.println("visiting:"+target);
		driver.get(target);
		return driver;
	}
	public File ScreenShot(){
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	}
}