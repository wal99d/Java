/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.webservices;

//import com.sun.xml.ws.api.message.Packet;
import java.math.BigDecimal;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import loan.system.entities.Request;
import loan.system.entities.Status;
import loan.system.entities.User;

/**
 * REST Web Service
 *
 * @author me
 */
@Path("Employee")
public class EmployeeDashboard {

    @Context
    private UriInfo context;
    private static final JsonBuilderFactory bf = Json.createBuilderFactory(null);
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    
    
    /**
     * Creates a new instance of DashboardResource
     */
    public EmployeeDashboard() {
    }

    /**
     * Retrieves representation of an instance of loan.system.webservices.DashboardResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public JsonArray getJson() {
        
        List<User> tmp = emf.createEntityManager().createNamedQuery("User.findByRoleID", User.class)
                .setParameter("roleId", 3)
                .getResultList();
        int r;
        JsonArrayBuilder jb = bf.createArrayBuilder();
        
        for (User u:tmp){
            r = emf.createEntityManager().createNamedQuery("Request.findByUserId", Request.class)
                    .setParameter("userId", u.getUserId())
                    .getResultList().size();
            jb.add(bf.createObjectBuilder()
                .add("employeeName", u.getUserName())
                .add("value", r));
        }
        
        return jb.build();
        
    }

    /**
     * PUT method for updating or creating an instance of DashboardResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(JsonArray content) {
        System.out.println(content);
    }
}
