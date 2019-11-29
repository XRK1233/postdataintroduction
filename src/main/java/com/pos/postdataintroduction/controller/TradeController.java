package com.pos.postdataintroduction.controller;


import com.pos.postdataintroduction.service.impl.MemberService;
import com.pos.postdataintroduction.service.impl.TradeService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TradeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TradeService tradeService;
    @Value("${app.tradePath}")
    private String sourcePath;

    public void bathTradeIncrement() throws ParseException {
        tradeService.bathTradeIncrement(sourcePath);
    }



}
