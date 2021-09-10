package com.teedjay;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sse")
public class SSEEndpoint {

    @Channel("quotes")
    Multi<String> quotes;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> stream() {
        return quotes;
    }

}
