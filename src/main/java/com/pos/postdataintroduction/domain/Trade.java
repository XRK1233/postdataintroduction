package com.pos.postdataintroduction.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易对象
 */
@Data
public class Trade {
    /**
     * CREATE TABLE `t_f_pos_tradedata` (
     *   `agent_id` int(11) NOT NULL COMMENT '主键',
     *   `file_num` int(11) DEFAULT NULL COMMENT '记录在本文件中的位置',
     *   `mchid` varchar(30) DEFAULT NULL COMMENT '国通商户号',
     *   `org_no` varchar(30) DEFAULT NULL COMMENT '邮储银行网点机构号',
     *   `trade_date` varchar(30) DEFAULT NULL COMMENT '交易日期',
     *   `trade_money` decimal(16,2) DEFAULT NULL COMMENT '交易金额',
     *   `trade_num` int(11) DEFAULT NULL COMMENT '交易笔数',
     *   `send_date` varchar(30) DEFAULT NULL COMMENT '上送日期',
     *   PRIMARY KEY (`agent_id`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
     */
    private Integer agentId;
    private Integer fileNum;
    private String mchid;
    private String orgNo;
    private Long tradeDate;
    private BigDecimal tradeMoney;
    private Integer tradeNum;
    private Long sendDate;
    private String fileName;

}
