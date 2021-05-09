package com.ess.spind.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import com.ess.spind.model.S;

import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
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

        return init(data.getName(),data.getPw());
    }

    private static WebDriver driver;

    private static String codec;

    private File getFileFromURL(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }

    private String init(String name, String pw){
    //    System.setProperty("webdriver.http.factory", "apache");
 
    
     //   System.setProperty("webdriver.chrome.driver", getFileFromURL("static/chromedriver").getAbsolutePath());
        
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        driver = new ChromeDriver();

    
        WebDriverWait wait = new WebDriverWait(driver, 10000);
    
        String src;
        StringBuilder sb =  null;
        if(codec == null){
        try{
    
        FileInputStream fis = new FileInputStream(getFileFromURL("static/js"));
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        sb = new StringBuilder();

        String line = "";
        while((line = bufferedReader.readLine()) != null){

            sb.append(line);

        }
    

        

    }catch(IOException  ioe){
       // ioe.printStackTrace();
    }
    src = sb.toString();
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
