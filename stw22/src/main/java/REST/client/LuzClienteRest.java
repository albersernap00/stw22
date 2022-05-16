/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/*
 * Jersey REST client generated for REST resource:application [locate/ccaa]<br>
 * USAGE:
 * <pre>
 *        LuzClienteRest client = new LuzClienteRest();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 */
public class LuzClienteRest {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "https://api.preciodelaluz.org/v1/prices/";

    public LuzClienteRest() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
    }

    /*
     * @param responseType Class representing the response
     * @return response object (instance of responseType class)
     */
    public <T> T getAllPrices(Class<T> responseType) throws ClientErrorException {
        webTarget = client.target(BASE_URI).path("all").queryParam("zone", "PCB");
        System.out.println(webTarget.getUri().toString());
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }
    
    public void close() {
        client.close();
    }
    
}