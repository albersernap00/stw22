/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST.server;

import java.util.Date;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import stw22.db.PrecioLuzDAO;

/**
 * REST Web Service
 *
 * @author Alberto Serna
 */
@Path("priceLuz")
@RequestScoped
public class PrecioLuzREST {
    @EJB PrecioLuzDAO preciosDB;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PrecioLuzREST
     */
    public PrecioLuzREST() {
    }

    /**
     * Retrieves representation of an instance of REST.server.PrecioLuzREST
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPrecioLuzNow() {        
        return String.valueOf(preciosDB.obtenerPreciosNow());
    }
 
}
