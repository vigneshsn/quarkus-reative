package org.acme.getting.started;

import org.acme.getting.started.model.Greetings;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/hello-non-reactive")
public class NonReactiveGreetingResource {

    @POST
    @ResponseStatus(204)
    @Path("/greetings")
    public void greeting(Greetings greetings) {
        System.out.println("lots of greetings received "+ greetings.getGreetings().size());
    }
}
