package com.pos.postdataintroduction.mapper;

import com.pos.postdataintroduction.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMapper {
    /**
     * 往数据库中插入一条数据
     */
    @Insert("insert  into t_a_pos_memberdata (company_name,company_short_name,mchid) values (#{companyName},#{companyShortName},#{mchid})")
    @Options(useGeneratedKeys = true, keyProperty = "agentId")
    Integer addMember(Member member);


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

             //获取文件编号
        member.setFileNum(Integer.parseInt(sb.substring(0,8)));
        //获取国通商户号
        member.setMchid(sb.substring(9,24));
        //注册日期
        member.setRegDate(DateUtil.str2Time(sb.substring(415,423)));
        //结算账户性质
        member.setAccountType(Short.parseShort(sb.substring(781,782)));
        //结算账户账号
        member.setAccountCardNo(sb.substring(912,932));
    )*/


    @Insert("<script>" +
            "insert into t_a_pos_memberdata (file_num,reg_date,mchid,account_type,account_card_no,send_date,file_name) " +
            "values " +
            "<foreach collection='listMember' item='member' index='index' separator=','>" +
            "(#{member.fileNum},#{member.regDate},#{member.mchid},#{member.accountType},#{member.accountCardNo},#{member.sendDate},#{member.fileName})" +
            "</foreach>" +
            "</script>")
    Integer addMemberBatch(@Param("listMember") List<Member> listMember);
}
