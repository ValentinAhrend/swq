package com.ess.spind.dao;

import java.util.concurrent.ConcurrentHashMap;

import com.ess.spind.model.S;

/**
 * Manager
 */
public class Manager {

    private static final ConcurrentHashMap<String, Integer> dl = new ConcurrentHashMap<>();
    private static long time = System.currentTimeMillis();

    public static boolean register(S data){
       
            if(time + 600000 < System.currentTimeMillis()){

                //reset

                time = System.currentTimeMillis();
                reset();



            }

            dl.put(data.getIp(), dl.containsKey(data.getIp())?dl.get(data.getIp())+1:1);

            return (dl.get(data.getIp()) < 10);
    }

    public static void reset(){

        dl.clear();

    }



 
}