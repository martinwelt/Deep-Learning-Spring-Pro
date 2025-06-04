package com.xrervip.super_ai_service.controller;


import com.xrervip.super_ai_service.service.TrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/train")
@RequiredArgsConstructor
@Validated
public class TrainController {

    TrainService trainService;


}
