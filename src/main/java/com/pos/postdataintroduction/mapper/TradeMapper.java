package com.pos.postdataintroduction.mapper;

import com.pos.postdataintroduction.domain.Member;
import com.pos.postdataintroduction.domain.Trade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeMapper {
    /**
     * 往数据库中批量插入数据
     */
   /* @Insert(
            "<insert id=\"addMemberBatch\"'>"+
            "INSERT INTO t_a_pos_memberdata (company_name,company_short_name,mchid) VALUES "+
                    "<foreach  collection=\"listMember\" item=\"member\" separator=\",\">" +
                    "(#{member.companyName},#{member.companyShortName},#{member.mchid})" +
                    "</foreach>"+
            "</insert>"


    )*/


    @Insert("<script>" +
            "insert into t_f_pos_tradedata (file_num,mchid,org_no,trade_date,trade_money,trade_num,send_date,file_name) " +
            "values " +
            "<foreach collection='listTrade' item='trade' index='index' separator=','>" +
            "(#{trade.fileNum},#{trade.mchid},#{trade.orgNo},#{trade.tradeDate},#{trade.tradeMoney},#{trade.tradeNum},#{trade.sendDate},#{trade.fileName})" +
            "</foreach>" +
            "</script>")
    Integer addMemberBatch(@Param("listTrade") List<Trade> listTrade);
}
