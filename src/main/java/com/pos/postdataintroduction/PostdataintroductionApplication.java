package com.pos.postdataintroduction;

import com.pos.postdataintroduction.controller.MemberController;
import com.pos.postdataintroduction.controller.TradeController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.text.ParseException;

@MapperScan("com.pos.postdataintroduction.mapper")
@SpringBootApplication
@PropertySource("classpath:application.yml")
public class PostdataintroductionApplication {

    public static void main(String[] args) throws ParseException {
        ApplicationContext applicationContext= SpringApplication.run(PostdataintroductionApplication.class,args);
        MemberController memberController= applicationContext.getBean(MemberController.class);
        //TradeController tradeController= applicationContext.getBean(TradeController.class);
        memberController.bathMemberIncrement();
        //tradeController.bathTradeIncrement();
    }

}
