package com.laxtech.stream.producer.controller;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SourceChannel {

    String OUTPUT = "output";

    @Output(SourceChannel.OUTPUT)
    MessageChannel outputChannel();

}