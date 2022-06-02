/*
Clase auxiliar, solo se usa para parsear el json 
recibido en el topic ".../rpi/cpuTemp".
 */
package demo;

import com.google.gson.Gson;


/**
 *
 * @author fsern
 */

public class RpiDTO {
    private Long    ms      = 0L;
    private Double  tempCpu = Double.NaN;
    private Double  temp    = Double.NaN;  // tª del BMP280
    private Double  press   = Double.NaN; 


    
    public Long getMs() {
        return ms;
    }

    public void setMs(Long _ms) {
        this.ms     = _ms;

        
    }

    public Double getTempCpu() {
        return tempCpu;
    }

    public void setTempCpu(Double tempCpu) {
        this.tempCpu = tempCpu;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getPress() {
        return press;
    }

    public void setPress(Double press) {
        this.press = press;
    }

   
    
    
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
   
}
