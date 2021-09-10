package com.teedjay;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

public class KafkaProcessor {

    @Incoming("requests")
    @Outgoing("processed")
    // if you add @Blocking it will run on a worker thread (instead of the event loop thread)
    public String process(String message) {
        var msg =  "Processed message " + message + " by instance " + this + " on thread " + Thread.currentThread();
        System.out.println(msg);
        return msg;
    }

}
