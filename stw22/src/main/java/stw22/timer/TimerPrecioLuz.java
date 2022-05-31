package stw22.timer;

import REST.client.LuzClienteRest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import stw22.db.PrecioLuz;
import stw22.db.PrecioLuzDAO;
import stw22.serializable.PrecioLuzJSON;

/**
 *
 * @author rober
 */
@Stateless

public class TimerPrecioLuz {
    
    @EJB PrecioLuzDAO preciosDB;
    
    @Schedule(dayOfWeek = "*", month = "*", hour = "9", dayOfMonth = "*", year = "*", minute = "0", second = "0", persistent = false)
    public void timer(){
        obtenerPrecioLuz();
        System.out.println("[!] joder");
    }
    
    
    public void obtenerPrecioLuz(){
        
        System.out.println("===== REFRESH REGISTRO dd");
        LuzClienteRest client = new LuzClienteRest();
        String prueba = client.getAllPrices(String.class);
        //Map<String,PrecioLuzJSON> lista = Arrancador.gson.fromJson(prueba, Map.class);
        System.out.println("Lo que es es: " + prueba);
        obtenerObjJSON(prueba);
        client.close();
        
    }
    private void obtenerObjJSON(String _json){
        
        JsonReader jsonReader = Json.createReader(new StringReader(_json));
        JsonObject object = jsonReader.readObject();
        System.out.println("[!!!]  "  );
        
        System.out.println("object: " + object.toString());
        List <PrecioLuzJSON> lista = new ArrayList<>();
        
        object.forEach((key, value) -> {
            try {
                PrecioLuz precioAnyadir = new PrecioLuz();
                PrecioLuzJSON precioAdd = new PrecioLuzJSON();
                
                System.out.println("[dd] " + value);
                precioAdd = Arrancador.gson.fromJson(value.toString(), PrecioLuzJSON.class);
                lista.add(precioAdd);
                precioAnyadir.setPrecio(precioAdd.getPrice());
                precioAnyadir.setFecha(new SimpleDateFormat("dd-MM-yyyy").parse(precioAdd.getDate()));
                precioAnyadir.setHora(precioAdd.getHour());
                precioAnyadir.setUnidades(precioAdd.getUnits());
                preciosDB.createIfNotExists(precioAnyadir);
            } catch (ParseException ex) {
                System.out.println("[!] Excepcion parseando la fecha");
            }
        });
        
        
   
        
    }
    

    
}
