package com.xrervip.super_ai_service.config;

import com.xrervip.super_ai_service.utils.TrainStatus;
import com.xrervip.super_ai_service.utils.TrainStatusChangeEvent;
import org.springframework.statemachine.config.StateMachineConfig;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

//public class TrainStateMachineConfig extends StateMachineConfigurerAdapter<TrainStatus, TrainStatusChangeEvent> {
//
////    public void configure(StateMachineStateConfigurer<TrainStatus, TrainStatusChangeEvent> states) throws Exception {
////        states
////                .withStates()
////                .initial(TrainStatus.WAIT_DATA)
////                .states(EnumSet.allOf(TrainStatus.class));
////    }
////
////    /**
////     * 配置状态转换事件关系
////     *
////     * @param transitions
////     * @throws Exception
////     */
////    public void configure(StateMachineTransitionConfigurer<TrainStatus, TrainStatusChangeEvent> transitions) throws Exception {
////        transitions
////                .withExternal().source(TrainStatus.WAIT_DATA).target(TrainStatus.READY_TRAIN).event(TrainStatusChangeEvent.READY)
////                .and()
////                .withExternal().source(TrainStatus.WAIT_DATA).target(TrainStatus.CANCELED).event(TrainStatusChangeEvent.CANCEL)
////                .and()
////                .withExternal().source(TrainStatus.READY_TRAIN).target(TrainStatus.TRAINING).event(TrainStatusChangeEvent.TRAIN)
////                .and()
////                .withExternal().source(TrainStatus.READY_TRAIN).target(TrainStatus.CANCELED).event(TrainStatusChangeEvent.CANCEL)
////                .and()
////                .withExternal().source(TrainStatus.TRAINING).target(TrainStatus.DONE).event(TrainStatusChangeEvent.FINISH)
////                .and()
////                .withExternal().source(TrainStatus.TRAINING).target(TrainStatus.CANCELED).event(TrainStatusChangeEvent.CANCEL);
////    }
//
//
//}
