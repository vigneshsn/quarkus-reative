package org.acme.getting.started;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.getting.started.model.Greetings;
import org.jboss.resteasy.reactive.ResponseStatus;
import org.jboss.resteasy.reactive.RestSseElementType;

@Path("/hello")
public class ReactiveGreetingResource {

    @Inject
    ReactiveGreetingService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    public Uni<String> greeting(String name) {
        return service.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{count}/{name}")
    public Multi<String> greetings(int count, String name) {
        return service.greetings(count, name);
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.TEXT_PLAIN)
    @Path("/stream/{count}/{name}")
    public Multi<String> greetingsAsStream(int count, String name) {
        return service.greetings(count, name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @POST
    @Path("/greetings")
    @ResponseStatus(204)
    public Uni<Void> greeting(Greetings greetings) {
        System.out.println("lots of greetings received "+ greetings.getGreetings().size());
        return Uni.createFrom().voidItem();
    }
}
