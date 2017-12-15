package com.skin.finder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;
import java.util.Scanner;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

import com.alibaba.fastjson.JSON;
public class TaskAssignAndResultCollection {
	
	private final static int DEFAULT_THREAD_NUM = 5;  
	 
    private int threadNum = DEFAULT_THREAD_NUM;  
    private Worker[] threads = null;  
 
    public TaskAssignAndResultCollection(int threadNum) {  
        super();  
        if (threadNum == 0) {  
            threadNum = DEFAULT_THREAD_NUM;  
        } else {  
            this.threadNum = threadNum;  
        }  
 
    }  
 
    public ArrayList<String> processStringBatchly(  
            String[] datas) {  
 
        if (threads == null) {  
            synchronized (this) {  
                threads = new Worker[threadNum];  
                  
                for(int i = 0 ; i < threadNum; i++) {  
                    threads[i] = new Worker();  
                }  
            }  
        }  
 
        // 怎么把domainName分配给线程， 让它们自己运行去？平均分配，  
        int domainSize = datas.length;  
        int domainNamePerThread = domainSize / threadNum;  
        int leftDomainName = domainSize % threadNum;   
          
        List<String> listDomainName = Arrays.asList(datas);  
 
        //先每个线程平均地分domainNamePerThread个DomainName，  
        int endIndex = 0;  
        for (int i=0; i<threadNum; i++) {  
            int beginIndex = i * domainNamePerThread;  
            int step = domainNamePerThread;  
            endIndex = beginIndex + step;   
            List<String> subDomainNames = new ArrayList<String>(listDomainName.subList(beginIndex, endIndex));  
              
            threads[i].setDomainNameList(subDomainNames);  
        }  
          
        //然后，再把剩下的逐个分配。  
        for(int i=0; i< leftDomainName; i++) {  
            threads[i].addDomainName(listDomainName.get(endIndex + i));  
        }  
          
        for(Worker thread : threads ) {  
            thread.start();  
            try {  
                thread.join();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
          
        ArrayList<String> totalResult = new ArrayList<String>(); 
          
        for(Worker thread : threads) {  
            totalResult.addAll(thread.getResultCollector());  
        }  
          
        return totalResult;  
    }  
      
      
    public static void main(String[] args) {  
        String[] datas = new String[] {"baidu.com", "baiduaa.com","sohu.com", "163.com", "iteye.com", "sohu.com", "163.com", "iteye.com",
        		"163.com", "iteye.com", "sohu.com", "163.com", "iteye.com", "163.com", "iteye.com", "sohu.com", "163.com", "iteye.com", "163.com"
        		, "iteye.com", "sohu.com", "163.com", "iteye.com", "163.com", "iteye.com", "sohu.com", "163.com", "iteye.com", 
        		"163.com", "iteye.com", "sohu.com", "163.com", "iteye.com", "163.com", "iteye.com", "sohu.com", "163.com", "iteye.com", "163.com", "iteye.com", "sohu.com", "163.com", "iteye.com", "haha.com"};  
//        System.out.print(datas.length+"输入所要建立的worker数量：");
        
        String result="";//访问返回结果
        BufferedReader read=null;//读取访问结果
        String urlNameString = "https://api.github.com/repos/ZJDong/SocketServer/commits" ;
        URL realUrl;
		try {
			realUrl = new URL(urlNameString);
	        // 打开和URL之间的连接
	        URLConnection connection = realUrl.openConnection();
	        // 设置通用的请求属性
	        connection.setRequestProperty("accept", "*/*");
	        connection.setRequestProperty("connection", "Keep-Alive");
	        connection.setRequestProperty("user-agent",
	                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
//	         for (String key : map.keySet()) {
//	             System.out.println(key + "--->" + map.get(key));
//	         }
	         // 定义 BufferedReader输入流来读取URL的响应
	         read = new BufferedReader(new InputStreamReader(
	                 connection.getInputStream(),"UTF-8"));
	         String line;//循环读取
	         while ((line = read.readLine()) != null) {
	             result += line;
	         }
	         List<String> strArray = JSON.parseArray(result, String.class);
	         String[] str=new String[strArray.size()];
	         for(int i=0;i<strArray.size();i++){
	        	 Map maps = (Map)JSON.parse(strArray.get(i)); 
	        	 str[i]=maps.get("html_url").toString();
//	        	 System.out.println(maps.get("html_url"));
	         }
	         System.out.println("请输入要建立的worker数");
	         Scanner scan = new Scanner(System.in);
	         String reads = scan.nextLine();
	         TaskAssignAndResultCollection c = new TaskAssignAndResultCollection(Integer.parseInt(reads));  
	           
	         ArrayList<String> resultCollector = c.processStringBatchly(str);  
	         c.showMsg(resultCollector);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         
         
        // 打开一个存在的仓库
//        try {
//			Repository repo = new FileRepositoryBuilder()
//			    .setGitDir(new File("C:/Users/EDD/Desktop/董知见分布式文件系统/第五版12121423/img/.git"))
//			    .build();
//			// 获取引用
//			Ref master = repo.getRef("master");
//
//			// 获取该引用所指向的对象
//			ObjectId masterTip = master.getObjectId();
//			
//			// 装载对象原始内容
//			ObjectLoader loader = repo.open(masterTip);
//			loader.isLarge();
//			System.out.println("aaaa");
//			loader.copyTo(System.out);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
        
        
        
        
        
//		File rootDir = new File("C:/Users/EDD/Desktop/董知见分布式文件系统/第五版12121423/img/.git"); 
//		try {
//			Git git = Git.open(rootDir);
//			Repository repository = git.getRepository();  
//			  
//	        repository = git.getRepository();  
//	        RevWalk walk = new RevWalk(repository);  
//	        Ref ref = repository.getRef("SocketServer");  
//	        if (ref == null) {  
//	            //获取远程分支  
//	            ref = repository.getRef("https://api.github.com/repos/ZJDong/SocketServer" + "SocketServer");  
//	        }
//	        ObjectId objId = ref.getObjectId();  
//	        RevCommit revCommit = walk.parseCommit(objId); 
//	        RevTree revTree = revCommit.getTree(); 
//	        TreeWalk treeWalk = TreeWalk.forPath(repository, "3191377947cd5ed80f7d298627fb65a8ce80ed7b", revTree);  
//	        //文件名错误  
//	        if (treeWalk == null) {
//	        	System.out.println("文件名错误");
//	        }
//	        ObjectId blobId = treeWalk.getObjectId(0);  
//	        ObjectLoader loader = repository.open(blobId);  
//	        byte[] bytes = loader.getBytes();  
//			System.out.println("aaa"+new String(bytes));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
        
        

    }  
 
    private void showMsg(ArrayList<String> result) {  
//        for(Map.Entry<String, String> me : result.entrySet()) {  
//            String data = me.getKey();  
//            String r = me.getValue();  
//              
//            String msg = "原始值[" + data + "]" + " 处理后[" + r + "]" ;  
//              
//            System.out.println(msg);  
//        }  
        for(int i=0;i<result.size();i++){
        	System.out.println("返回的值"+result.get(i));
        }
        System.out.println("总条数为："+result.size());
    }  
      
      
      
}  
 
class Worker extends Thread {  
    private List<String> datas;  
    private ArrayList<String> resultCollector = new ArrayList<String>(); 
    String result="";//访问返回结果
    BufferedReader read=null;//读取访问结果
    public void run() {  
    	long startTime = System.currentTimeMillis(); 
        for (String d : datas) {  
//            String result = d + "@";  

            // 建立实际的连接
            try {
            	String urlNameString = "http://192.168.1.124:8080/JavaTeam/Test/testBackResult.html" + "?d=" + d;
                URL realUrl = new URL(urlNameString);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				connection.connect();
				Map<String, List<String>> map = connection.getHeaderFields();
				// 遍历所有的响应头字段，获取到cookies等
//	             for (String key : map.keySet()) {
//	                 System.out.println(key + "--->" + map.get(key));
//	             }
	             // 定义 BufferedReader输入流来读取URL的响应
	             read = new BufferedReader(new InputStreamReader(
	                     connection.getInputStream(),"UTF-8"));
	             String line;//循环读取
	             resultCollector.add(read.readLine());
//	             while ((line = read.readLine()) != null) {
//	                 result += line;
//	             }
//	             System.out.println("返回的结果"+result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
        }  
        long endTime = System.currentTimeMillis();  
        float seconds = (endTime - startTime) / 1000F; 
        System.out.println("time:"+seconds);
    }  
 
    public void setDomainNameList(List<String> subDomainNames) {  
        datas = subDomainNames;  
    }  
      
    public void addDomainName(String domainName) {  
        if (datas == null ) {  
            datas = new ArrayList<String>();  
        }  
        datas.add(domainName);  
    }  
 
    public ArrayList<String> getResultCollector() {  
        return resultCollector;  
    }  

}
