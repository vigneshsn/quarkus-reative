package org.acme.getting.started;

import org.acme.getting.started.model.Greetings;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/hello-non-reactive")

public class NonReactiveGreetingResource {

    @POST
    @ResponseStatus(204)
    @Path("/greetings")
    public void greeting(Greetings greetings) {
        System.out.println("lots of greetings received "+ greetings.getGreetings().size());
        System.out.println("input greeting name "+ greetings.getGreetings().get(0).toString());
    }
}
