package com.xrervip.super_ai_service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Slf4j
@Component("trainStateListener")
@WithStateMachine(name = "trainStateMachine")
public class TrainStateListenerImpl {

    @OnTransition(source = "WAIT_DATA",target ="READY_TRAIN" )
    public boolean readyTransition(Message<TrainStatusChangeEvent> message) {
        log.info("Ready，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "READY_TRAIN",target ="TRAINING" )
    public boolean trainTransition(Message<TrainStatusChangeEvent> message) {
        log.info("Train，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "TRAINING",target ="DONE" )
    public boolean finishTransition(Message<TrainStatusChangeEvent> message) {
        log.info("Finish，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_DATA",target ="CANCELED" )
    public boolean cancel1Transition(Message<TrainStatusChangeEvent> message) {
        log.info("cancel1，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "READY_TRAIN",target ="CANCELED" )
    public boolean cancel2Transition(Message<TrainStatusChangeEvent> message) {
        log.info("cancel2，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "TRAINING",target ="CANCELED" )
    public boolean cancel3Transition(Message<TrainStatusChangeEvent> message) {
        log.info("cancel3，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }



}
