package com.pos.postdataintroduction.controller;

import com.pos.postdataintroduction.domain.Member;
import com.pos.postdataintroduction.service.impl.MemberService;
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
public class MemberController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MemberService memberService;
    @Value("${app.memberPath}")
    private String sourcePath;
    @RequestMapping("/test")
    public  int  sop(String[] args) {
        Member member = new Member();
        member.setMchid("11111111111111111111");
        member.setCompanyName("测试公司");
        member.setCompanyShortName("测试公司简称");
        return memberService.addMember(member);
    }

    @RequestMapping("/test2")
    public  int  sop2() {
        List<Member> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Member member = new Member();
            member.setMchid("11111111111111111111");
            member.setCompanyName("测试公司");
            member.setCompanyShortName("测试公司简称");
            list.add(member);
        }
        return memberService.addMemberBatch(list);
    }

    public void bathMemberIncrement() throws ParseException {
        memberService.bathMemberIncrement(sourcePath);
    }



}
