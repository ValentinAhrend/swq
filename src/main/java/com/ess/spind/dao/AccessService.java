package com.ess.spind.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import com.ess.spind.model.S;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.stereotype.Repository;


@Repository("d300")
public class AccessService implements SDao{
    
    @Override
    public String checkS(S data) {
        
        /*

        data - check

        */

        return init(data.getName(),data.getPw());
    }

    private static WebDriver driver;

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


    private static String init(String name, String pw){
    //    System.setProperty("webdriver.http.factory", "apache");
 
       
        System.setProperty("webdriver.chrome.driver",System.getenv("CHROMEDRIVER_PATH"));
        //System.setProperty("webdriver.chrome.driver","/Users/valentinahrend/Downloads/spind/src/main/resources/static/chromedriver");
        
        ChromeOptions options = new ChromeOptions();
        options.setBinary(System.getenv("GOOGLE_CHROME_BIN"));
        options.setHeadless(true);
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        

        driver = new ChromeDriver(options);

    
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
    
        String src;
        if(codec == null){
        
    src = getContent("static/js.js");
    codec = src;
} src = codec;
   
   // System.out.println("!!"+src);
        
        System.out.println("!!before get");

        try{

            driver.get("https://start.schulportal.hessen.de/index.php?i=5129");
            
            /*
            TASK:   
            */

            
  
            String code = src.replace("X", name).replace("Y",pw);
            
           // ((JavascriptExecutor)driver).executeScript("jQuery.startAES(true,true);");
           

            System.out.println(code);
        
             String datas = (String) ((JavascriptExecutor)driver).executeScript("return ("+code+")");

        
            Thread.sleep(333);
            driver.close();
            
             System.out.println("!!"+datas);
             if(datas.equals("0") || !datas.startsWith("{")){
                return "-1";
             }else {
                
                


                JSONObject jsonObject = new JSONObject(datas);
    
                
                 if(jsonObject.getBoolean("back")){

                    

                    return "1";

                 }else return "-2";
             }

             

             



          



        }catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
