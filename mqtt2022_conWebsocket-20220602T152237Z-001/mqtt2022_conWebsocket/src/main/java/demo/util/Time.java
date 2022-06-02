/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 *
 * @author fserna
 */
public class Time {
    
    
    public static String getHHMMSS (long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        String hhmmss ="";
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm= c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        if (hh<10){
            hhmmss = "0";
        }
        hhmmss = hhmmss+hh+":";
        if (mm<10){
            hhmmss += "0";
        }
        hhmmss = hhmmss+mm+":";
        if (ss<10){
            hhmmss += "0";
        }
        hhmmss = hhmmss+ss;
        
        return hhmmss;
    }
    
    public static int getHour (long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        return c.get(Calendar.HOUR_OF_DAY);
    }
    
     public static int getMinute (long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        return c.get(Calendar.MINUTE);
    }
     
     public static int getSecond (long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        return c.get(Calendar.SECOND);
    }
     
    public static String getDDMMYYYY(long _ms){
        String ddmmyyyy ="";
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        
        int dd = c.get(Calendar.DAY_OF_MONTH);
        int mm = c.get(Calendar.MONTH)+1;
        int yyyy = c.get(Calendar.YEAR);
        if (dd<10){
            ddmmyyyy = "0";
        }
        ddmmyyyy = ddmmyyyy+dd+"/";
        if (mm<10){
            ddmmyyyy += "0";
        }
        ddmmyyyy = ddmmyyyy+mm+"/"+yyyy;

        return ddmmyyyy;
    }
    public static int getDay (long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        return c.get(Calendar.DAY_OF_MONTH);
    }
    public static int getMonth (long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        return c.get(Calendar.MONTH);
    }
    
    public static int getYear (long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        return c.get(Calendar.YEAR);
    }
    
    
    /**
     * Sunday is 0, Monday is 1... Saturday is 6
     * @param _ms
     * @return 
     */
    public static int getDayOfWeek(long _ms){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }
 
    public static boolean isSunday(long _ms){
        boolean sunday = false;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek==0){
            sunday=true;
        }
        return sunday;
    }
    
    public static boolean isWeekEnd(long _ms){
        boolean weekEnd = false;
        int dayOfWeek = getDayOfWeek(_ms);
        if ((dayOfWeek==0)||(dayOfWeek==6)){
            weekEnd=true;
        }
        return weekEnd;
    }
    
    

    public static Date getDateFromDDMMYYYY(String _txt){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = sdf.parse(_txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return fecha;
    }
    
          
    public static Long getMsFromDDMMYYYY(String _txt){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long ms = 0;
        Date fecha = null;
        try {
            fecha = sdf.parse(_txt);
            ms = fecha.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return ms;
    }
  
    
    /**
     * 
     * @param _txt
     * @return 
     */
      public static Long getMsFromYYYYMMDD(String _txt){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long ms = 0;
        Date fecha = null;
        try {
            fecha = sdf.parse(_txt);
            ms = fecha.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return ms;
    }
      
      
    /**
     * 
     * @param _ms
     * @return 
     */
      public static Long getMsAt00FromMs(long _ms){
        long msAt00 = 0; // 
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(_ms);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND, 0);
        msAt00 = c.getTimeInMillis();
        return msAt00;
    }
      
      
        
      
      
  
}
