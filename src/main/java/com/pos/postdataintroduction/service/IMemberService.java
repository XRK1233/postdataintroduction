package com.pos.postdataintroduction.service;

import com.pos.postdataintroduction.domain.Member;

import java.text.ParseException;
import java.util.List;

public interface IMemberService {
     Integer addMember(Member member);
     Integer addMemberBatch(List<Member> list);
     Boolean bathMemberIncrement(String sourceFile) throws ParseException;
}
