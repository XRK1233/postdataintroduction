package com.pos.postdataintroduction.domain;

import lombok.Data;

/**
 * 商户对象
 */
@Data
public class Member {
    /**
     *  `agent_id` int(11) NOT NULL AUTO_INCREMENT,
     *   `file_num` int(11) DEFAULT '0' COMMENT '记录在文件中的序号',
     *   `mchid` varchar(30) DEFAULT NULL COMMENT '国通间连商户号',
     *   `company_name` varchar(255) DEFAULT NULL COMMENT '商户名',
     *   `company_short_name` varchar(255) DEFAULT NULL,
     *   `status` tinyint(1) DEFAULT '0' COMMENT '商户状态 01为正常、02为停用、03为注销',
     *   `reg_date` int(11) DEFAULT '0' COMMENT '商户注册时间戳',
     *   `edit_date` int(11) DEFAULT '0' COMMENT '商户最后修改时间',
     *   `cleanning_flag` varbinary(1) DEFAULT NULL COMMENT '商户清分标识Y-邮储银行POS系统清分结算；\r\nN-非邮储银行POS系统清分结算',
     *   `card_type` tinyint(1) DEFAULT '0' COMMENT '01-为营业执照;\r\n02-为社会统一信用证\r\n证照证明01-为营业执照;02-为社会统一信用证99-其他',
     *   `card_no` varchar(20) DEFAULT NULL COMMENT '证照代码',
     *   `legal_person` varchar(255) DEFAULT NULL COMMENT '法人代表姓名',
     *   `legal_person_no` varchar(255) DEFAULT NULL COMMENT '法人代表证件号',
     *   `bank_flag` tinyint(255) DEFAULT '0' COMMENT '开户行标识Y-邮储银行；\r\nN-其他银行。\r\n（区分本行账户、他行账户）',
     *   `bank_code` varchar(255) DEFAULT NULL COMMENT '开户行代码',
     *   `account_type` tinyint(255) DEFAULT '0' COMMENT '结算账户性质1-个人银行结算账户；\r\n2-单位结算账户',
     *   `account_card_name` varchar(255) DEFAULT NULL COMMENT '结算账户名称',
     *   `account_card_no` varchar(255) DEFAULT NULL COMMENT '结算账户账号',
     *   `bank_org_no` int(11) DEFAULT '0' COMMENT '邮储银行网点（二级支行）机构代码',
     *   `bank_org_name` varchar(255) DEFAULT NULL COMMENT '邮储银行网点（二级支行）机构名称',
     *   `export_date` varchar(30) DEFAULT '0' COMMENT '导出日期',
     *   `send_date` varchar(30) DEFAULT '0' COMMENT '上送日期',
     *   `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
     */
    private Integer agentId;
    private Integer fileNum;
    private String  mchid;
    private String  companyName;
    private String  companyShortName;
    private Short   status;
    private Long regDate;
    private Integer editDate;
    private String  cleanningFlag;
    private Short   cradType;
    private String cardNo;
    private String legalPerson;
    private String legalPersonNo;
    private Short bankFlag;
    private String bankCode;
    private Short accountType;
    private String accountCardNo;
    private String bankOrgNo;
    private String bankOrgName;
    private String exportDate;
    private Long sendDate;
    private String fileName;


}
