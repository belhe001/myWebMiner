# myWebMiner
An Open Source data mining tool build for all platforms using java.  
This tool allows you to Crawl a AJAX/JS enabled web site  
to fetch Social site links, email addresses along with contact numbers.  
This tool has ability to store all visited sites along with their screenshot in File system.  

Features:

- Finds official Social site links ( fb,tw,yt,g+,LI ).

- Finds official email addresses.

- Finds official contact numbers.

- Dynamic page crawling.

- Smart Search.

- Speedy ( 150 links/sec ).

- Page Backup ( Screenshot , Source Code )

Download
-------
Download the latest version via Gradle:
```groovy
compile 'com.github.belhe001:myWebMiner:0.0.2'
```
Maven:
```xml
<dependency>
	<groupId>com.github.belhe001</groupId>
	<artifactId>myWebMiner</artifactId>
	<version>0.0.2</version>
</dependency>
```


Usage
-------
#### Initialize 
```java
import com.github.belhe001.myWebMiner.myConstants.my;
...
my.mongohost="localhost";
my.mongodb="mydb";
...
```
**Initialize required variables**

#### Create Home Page Thread 
```java
(new Thread(new mytarget("http://example.com"))).start();
```
**This thread finds About, career and contact pages**  
**Works on only `HTTP` protocol**
#### Create single page analyser Thread
```java
(new Thread(new myAnalyser("http://example.com/contact"))).start();
```
**This thread finds Email, Telephone numbers, along with Social site links.**  
**Regex can be updated in class myConstants.my**
#### Store ScreenShot
```java
(new Thread(new myDynamic("http://example.com"))).start();
```
**This thread Takes a screenshot of provided URL**  
**Initialize Storage Directory & place where PhantomJS executable exists**
