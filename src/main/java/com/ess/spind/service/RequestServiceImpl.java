package com.ess.spind.service;

import com.ess.spind.model.S;

import java.io.BufferedReader;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

/**
 * RequestServiceImpl
 */
@Service
public class RequestServiceImpl implements RequestService{

    @Override
    public boolean checkClientId(S data) {
        String IP = data.getIp();
     //   System.out.println(IP);
                String countryCode = fetchUrl( "http://api.wipmania.com/" + IP );
         //       System.out.println("="+countryCode);
                if ( countryCode != null){
                
                    
                    return (countryCode.startsWith("DE"));
                
                }
                return false;
    }

    public static String fetchUrl(String strUrl){
        String output = "";
        String line = null;
        try {
    
            URL url = new URL( strUrl );
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = reader.readLine()) != null) {
                output += line;
            }
            reader.close();
    
        } catch (MalformedURLException e) {
            System.out.println("ERROR CATCHED: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("ERROR CATCHED: " + e.getMessage());
            return null;
        }
    
        return output;
    }
    
}
