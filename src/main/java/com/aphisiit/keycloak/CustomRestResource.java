package com.aphisiit.keycloak;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class CustomRestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomData() {
        return Response.ok("{\"message\": \"Hello Aphisit Namracha from custom Keycloak REST API!\"}").build();
    }

    @GET
    @Path("/custom")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustom() {
        return Response.ok("{\"message\": \"Hello Aphisit Namracha from custom!\"}").build();
    }
}
