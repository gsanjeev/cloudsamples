package com.laxtech.stream.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SinkChannel {

    String INPUT = "input";

    @Input(SinkChannel.INPUT)
    SubscribableChannel inputChannel();

}