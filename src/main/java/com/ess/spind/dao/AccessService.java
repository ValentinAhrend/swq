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

    
    private String getContent(String s){

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(s);
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

    private String getPath(String s) {try{
        
        String c = getContent(s);

        File f = s.endsWith("js")?File.createTempFile("js","js"):File.createTempFile("chromedriver","");

        FileOutputStream fos = new FileOutputStream(f);
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        BufferedWriter writer2 = new BufferedWriter(writer);
        writer2.write(c);
        writer2.close();

        if(s.endsWith("er"))f.setExecutable(true);

        return f.getAbsolutePath();
    }catch(IOException ioe){
        ioe.printStackTrace();
        return null;
    }
    }

    private String init(String name, String pw){
    //    System.setProperty("webdriver.http.factory", "apache");
 
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", "static", "chromedriver");

        System.setProperty("webdriver.chrome.driver",filePath.toString());
        
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        driver = new ChromeDriver();

    
        WebDriverWait wait = new WebDriverWait(driver, 10000);
    
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

            
  
            String code = src.replace("X", name).replace("Y",pw);
            
           // ((JavascriptExecutor)driver).executeScript("jQuery.startAES(true,true);");
           

            System.out.println(code);
        
             String datas = (String) ((JavascriptExecutor)driver).executeScript("return ("+code+")");

        

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
