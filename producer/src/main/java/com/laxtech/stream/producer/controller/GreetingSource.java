package com.laxtech.stream.producer.controller;

import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableBinding(SourceChannel.class)
public class GreetingSource {

    private final MessageChannel outputChannel;

    public GreetingSource(SourceChannel sourceChannel) {
        this.outputChannel = sourceChannel.outputChannel();
    }

    @PostMapping("/greet/{name}")
    public void publish(@PathVariable String name) {
        String greeting = "Hello " + name + "!";
        System.out.print("Producer received " + greeting);
        Message<String> msg = MessageBuilder.withPayload(greeting).build();
        this.outputChannel.send(msg);
    }
/*
    @InboundChannelAdapter(SourceChannel.OUTPUT)
    public String greet() {
        return "hello world " + System.currentTimeMillis();
    }*/
}
