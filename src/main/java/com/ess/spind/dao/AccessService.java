package com.ess.spind.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.ess.spind.model.S;

import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Repository;


@Repository("d300")
public class AccessService implements SDao{
    
    @Override
    public String checkS(S data) {
        
        /*

        data - check

        */

        Object wait0 = 0;//output;
        String[] args = new String[1];

       


        TaskWait wait = new TaskWait(){
            @Override
            public void callResult(String result) {
                
                args[0] = result;

            //    System.out.println("output");

                synchronized(wait0){
                    wait0.notify();
                }


                
            }
        };

        keyMap.put(data.getId(), wait);
        
        
        int a = QUE_0.size();
        int b = QUE_1.size();
        int c = QUE_2.size();
        int d = QUE_3.size();

        int v;

        if(a == 0 && b == 0 && c == 0 && d == 0){
            v = 0;
        }else if(a > b && a > c && a > d){
            v = 0;
        }else if (b > a && b > c && b > d){
            v = 1;
        }else if(c > a && c > b && c > d){
            v = 2;
        }else{
            v = new Random().nextInt(4);
        }

      //  System.out.println("v:"+v);

        switch(v){
            case 0:{
         //       System.out.println("?");
                QUE_0.add(data);try {
                    
                    //start thread

                    if(!thread0.isAlive()){
                        thread0 = gThread(0, QUE_0 , que0);
                        thread0.start();
                    }

                    
                    que0.put(new Message(1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return "-22";
                }
                break;
            }
            case 1:{
                QUE_1.add(data);try {if(!thread1.isAlive()){
                    thread1 = gThread(1, QUE_1 , que1);
                    thread1.start();
                }
                    que1.put(new Message(1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return "-22";
                }
                break;
            }
            case 2:{
                QUE_2.add(data);try {if(!thread2.isAlive()){
                    thread2 = gThread(2, QUE_2 , que2);
                    thread2.start();
                }
                    que2.put(new Message(1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return "-22";
                }break;
            }
            case 3:{
                QUE_3.add(data);try {
                    if(!thread3.isAlive()){
                        thread3 = gThread(3, QUE_3 , que3);
                        thread3.start();
                    }
                    que3.put(new Message(1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return "-22";
                }break;
            }
            
        }






        try {
            synchronized(wait0){
        wait0.wait();}

            //then

            return args[0];

        } catch (InterruptedException e) {
            e.printStackTrace();
            return "-22";
            
        }

    }

    

   

    private static String codec;

    
    private static String getContent(String s){

        InputStream is = AccessService.class.getClassLoader().getResourceAsStream(s);
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader reader2 = new BufferedReader(reader);

        StringBuilder sb = new StringBuilder();
        String line;
try{
        while((line = reader2.readLine()) != null){

            sb.append(line);

        }
    }catch(IOException ioe){ioe.printStackTrace();}
        return sb.toString();

    }


    /*
    the follwing static fiels are mutli user able

    connections = number of current workers

    QUE_0-3 queues for 4 Executors

    */

    static final ConcurrentHashMap<String, TaskWait> keyMap;

    static final List<S> QUE_0;
    static final List<S> QUE_1;
    static final List<S> QUE_2;
    static final List<S> QUE_3;

    static Thread thread0;
    static Thread thread1;
    static Thread thread2;
    static Thread thread3;

    static final BlockingQueue<Message> que0;
    static final BlockingQueue<Message> que1;
    static final BlockingQueue<Message> que2;
    static final BlockingQueue<Message> que3;
    
    static {
        QUE_0 = new ArrayList<>();
        QUE_1 = new ArrayList<>();
        QUE_2 = new ArrayList<>();
        QUE_3 = new ArrayList<>();

        keyMap = new ConcurrentHashMap<>();

        que0 = new ArrayBlockingQueue<Message>(1);
        que1 = new ArrayBlockingQueue<Message>(1);
        que2 = new ArrayBlockingQueue<Message>(1);
        que3 = new ArrayBlockingQueue<Message>(1);

        thread0 = gThread(0,QUE_0, que0);
        thread1 = gThread(1,QUE_1, que1);
        thread2 = gThread(2,QUE_2, que2);
        thread3 = gThread(3,QUE_3, que3);


    }

    private static Thread gThread(int var, List<S> que, BlockingQueue<Message> queue){

        Thread t = new Thread(gRunnable(que,queue,var), "serviceThread-"+var);
        //t.start();

        //System.out.println("thread opened"+t);

        return t;
        
    }

    private static Runnable gRunnable(List<S> QUE, BlockingQueue<Message> q, int vars){
        return new Runnable() {

            private static final int MAX = 3;
            int exTime;

            @Override
            public void run() {
               
                //log in 

                synchronized(QUE){

                    if(QUE.size() > 0){

                        //run QUE_0[0];

                        S currentValue = QUE.get(0);

                        String output = executeMain(currentValue);

                        QUE.remove(0);

                        keyMap.get(currentValue.getId()).callResult(output);

                        run();

                    }else{

                        exTime = 0;

                        while(true){


                            if(exTime >= MAX){

                                Thread.currentThread().interrupt();
                                break;

                            }
                            
                            try {
                                Message m = q.poll(1, TimeUnit.SECONDS);
                                if(m != null){

                                    //new message arrived

                                    run();

                                }

                                exTime++;

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                exTime = MAX;
                            }

                        }


                    }

                }

            }
        };
    }





    private static String executeMain(S data){
   
        
       
        System.setProperty("webdriver.chrome.driver",System.getenv("CHROMEDRIVER_PATH"));
        //System.setProperty("webdriver.chrome.driver","/Users/valentinahrend/Downloads/spind/src/main/resources/static/chromedriver");
        
        ChromeOptions options = new ChromeOptions();
        options.setBinary(System.getenv("GOOGLE_CHROME_BIN"));
        options.addArguments("headless");
        options.addArguments("window-size=1920,1080");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        

        ChromeDriver driver = new ChromeDriver(options);

    
        new WebDriverWait(driver, Duration.ofSeconds(1)).until(
      webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    
        String src;
        if(codec == null){
        
    src = getContent("static/js.js");
    codec = src;
} src = codec;
   
   // System.out.println("!!"+src);
        
        

        try{

            driver.get("https://start.schulportal.hessen.de/index.php?i=5129");

            
            
            
            /*
            TASK:   
            */

            
  
            String code = src.replace("X", data.getName()).replace("Y",data.getPw());
            
           // ((JavascriptExecutor)driver).executeScript("jQuery.startAES(true,true);");
           
         //   System.out.println(((JavascriptExecutor) driver).executeScript(""));
           
            Thread.sleep(333);

           
        
            String datas = "";
           

            datas = (String) ((JavascriptExecutor)driver).executeScript("return ("+code+")");
           
            System.out.println("output data:"+datas);

            Thread.sleep(333);

            driver.close();
            driver.quit();

            
            



            if(datas.equals("0") || !datas.startsWith("{")){
                return "-1";
             }else {
                
                


                JSONObject jsonObject = new JSONObject(datas);
    
                
                 if(jsonObject.getBoolean("back")){

                    

                    return "1";

                 }else return "-2";
             }

             

             



          



        }catch(Exception e){
            return "-6";
        }

    }

}
