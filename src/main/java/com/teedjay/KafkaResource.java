package com.teedjay;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/kafka")
public class KafkaResource {

    @Channel("quote-requests")
    Emitter<String> quotes;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        var message = "" + System.nanoTime();
        quotes.send(message);
        return "Wrote '%s' to Kafka\n".formatted(message);
    }

}
