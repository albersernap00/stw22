package stw22.timer;

import REST.client.LuzClienteRest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import stw22.db.PreciosLuz;
import stw22.db.PreciosLuzDAO;
import stw22.serializable.PrecioLuzJSON;

/**
 *
 * @author rober
 */
@Stateless

public class TimerPrecioLuz {
    
    @EJB PreciosLuzDAO preciosDB;
    
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "1", persistent = false)
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
            System.out.println("[dd] " + value);         
            
            lista.add(Arrancador.gson.fromJson( value.toString(), PrecioLuzJSON.class));
            
        });
        PreciosLuz precioAdd = new PreciosLuz();
        precioAdd.setPrecio(2.0);
        precioAdd.setFecha(new Date(2010,10,10));
        precioAdd.setHora("hora");
        precioAdd.setUnidades("€/MWh");
                        
        System.out.println("lista 0 " + lista.get(0).getPrice());
        System.out.println(preciosDB.findAll());
        //preciosDB.create(precioAdd);
              
        /*Iterator<String> keys = json.keys();
        
        while(keys.hasNext()) { 
            String key = keys.next();
            if (json.get(key) instanceof JSONObject) {
                  System.out.println("eeee");   
            }
        }*/
        
    }
    

    
}
