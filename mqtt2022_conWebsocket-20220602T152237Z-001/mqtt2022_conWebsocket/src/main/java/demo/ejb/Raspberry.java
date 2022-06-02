/*
 Modela la info asociada a una raspberry.
De momento solo se considera la temperatura de su CPU.
 */
package demo.ejb;

import com.google.gson.Gson;
import javax.ejb.Stateless;

/**
 *
 * @author fsern
 */
@Stateless
public class Raspberry {
    private Long    ms          = 0L;
    private Double  tempCpu = Double.NaN;
    private Double  temp    = Double.NaN;  // tª del BMP280
    private Double  press   = Double.NaN;  // del BMP280

    public Long getMs() {
        return ms;
    }

    public void setMs(Long ms) {
        this.ms = ms;
    }

    public Double getTempCpu() {
        return tempCpu;
    }

    public void setTempCpu(Double _temperatura) {
        this.tempCpu = _temperatura;
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
