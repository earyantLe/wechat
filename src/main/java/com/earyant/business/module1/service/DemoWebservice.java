package com.earyant.business.module1.service;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@WebService
@Path("/test")
public interface DemoWebservice {

    //	@GET
    @POST
    @Path("/{user}")
    public String getDevices(@PathParam("user") String user);
}
