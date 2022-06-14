/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST.server;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import stw22.db.Usuario;
import stw22.db.UsuarioDAO;

/**
 * REST Web Service
 *
 * @author Alberto Serna
 */
@Path("login")
@RequestScoped
public class LoginREST {
    @EJB UsuarioDAO usuarioDB;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginREST
     */
    public LoginREST() {
    }

    /**
     * Retrieves representation of an instance of REST.server.LoginREST
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{username}&{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginOK(@PathParam ("username") String _username, @PathParam ("password") String _password ) {
        Usuario existeUsuario = usuarioDB.checkAutenticacion(_username, _password);        
        if (existeUsuario != null){
            return "OK";
        }else{
            return "NOK";
        }
    }
    
    
    
    
    @POST    
    @Consumes(MediaType.APPLICATION_JSON)
    public String registerUser(Usuario _user) {
        if (usuarioDB.createIfNotExists(_user)){
            return "201";
        }else{
            return "409";
        }
        
    }
    
    

    /**
     * PUT method for updating or creating an instance of LoginREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
