package com.pos.postdataintroduction.service;

import com.pos.postdataintroduction.domain.Member;

import java.text.ParseException;
import java.util.List;

public interface ITradeService {
     Boolean bathTradeIncrement(String sourceFile) throws ParseException;
}
