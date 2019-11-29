package com.pos.postdataintroduction.service.impl;

import com.pos.postdataintroduction.domain.Member;
import com.pos.postdataintroduction.domain.Trade;
import com.pos.postdataintroduction.mapper.MemberMapper;
import com.pos.postdataintroduction.mapper.TradeMapper;
import com.pos.postdataintroduction.service.IMemberService;
import com.pos.postdataintroduction.service.ITradeService;
import com.pos.postdataintroduction.utils.DateUtil;
import com.pos.postdataintroduction.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Service
public class TradeService implements ITradeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${app.batchSize}")
    private Integer batchSize = 0;
    private String fileName = "";
    //上送日期
    private Long sendDate;
    @Autowired
    TradeMapper tradeMapper;


    @Override
    public Boolean bathTradeIncrement(String sourcePath) throws ParseException {
        logger.info("--------开始处理{}文件夹下的数据----strat",sourcePath);
        //1.根据配置文件读取member文件下的所有压缩包
        logger.info("开始查找压缩包......");
        File[] files = FileUtil.getAllZip(sourcePath);
        // 2.将所有压缩包解压到下一级temp目录
        logger.info("正在解压资源包......");
        File tempDir = unZip(files,sourcePath);
        List<Trade> listTrade = null;
        //3.遍历读取所有文件
        if(!tempDir.exists()){
            return null;
        }
        files = tempDir.listFiles();
        logger.info("找到资源包:{}个，开始读取文件内容......",files.length);
        for (int i = 0; i < 1; i++) {
            //根据文件名获取上送日期
            sendDate = getSendDateByFileName(files[i].getName());
            fileName = files[i].getName();
            logger.info("正在处理{}......",fileName);
            //获取单个文件数据
            listTrade = getListFromFile(files[i]);
            //每一千条进行一次插入操作
            logger.info("数据库存档{}......",fileName);
            boolean flag = batchExpire(listTrade);
            if(flag){
                logger.info("第{}个文件:{}处理完毕......",i,fileName);
            }else {
                logger.info("第{}个文件:{}处理失败......",i,fileName);
            }
        }
        return null;
    }

    /**
     * 根据文件名获取上送的时间戳
     * @param fileName
     * @return
     */
    private Long getSendDateByFileName(String fileName){
        String[] str = fileName.split("_");
        try {
            return DateUtil.str2Time(str[3]);
        } catch (ParseException e) {
            logger.info("获取商户上送日期失败:{}",fileName);
        }
        return new Long("0");
    }

    /**
     * 分批次导入
     * @param lisTrade
     * @return
     */
    private boolean batchExpire(List<Trade> lisTrade){
        List<Trade> listTemp =null;
        int batch = lisTrade.size() % batchSize ==0?lisTrade.size() / batchSize:(lisTrade.size()/batchSize+1);
        for (int i=1;i<=batch;i++){
            if(i<batch){
                listTemp = lisTrade.subList((i-1)*batchSize,(i-1)*batchSize+batchSize);
            }else{
                listTemp = lisTrade.subList((i-1)*batchSize,lisTrade.size());
            }
            //做插入处理
            int num = tradeMapper.addMemberBatch(listTemp);
            if(num>0){
                logger.info("成功插入:{}条记录,当前文件总执行次数{},已执行次数:{}",listTemp.size(),batch,i);
            }else{
                logger.info("插入失败:{}条记录,当前文件总执行次数{},已执行次数:{}",listTemp.size(),batch,i);
                return false;
            }
        }
        return true;
    }
    /**
     * 把文件中的字符串转化为文件对象
     * @param file
     * @return
     */
    private  List<Trade> getListFromFile(File file) throws ParseException {
        List<String> listStr = FileUtil.readFileByLines(file);
        List<Trade> listMember = new LinkedList<>();
        Trade trade = null;
        //去除文件头
        listStr.remove(0);
        //将字符串解析为对象
        for (String str:listStr) {
            trade = new Trade();
            trade = str2Member(str,trade);
            listMember.add(trade);
        }
        return listMember;
    }

    /**
     * 按照域解析每一行内容
     * @param str
     * @param trade
     * @return
     */
    private Trade str2Member(String str,Trade trade) throws ParseException {

        return doStr2Member(str,trade);
    }

    /**
     * 对象和域的对应关系
     * @param str
     * @param trade
     * @return
     */
    private Trade doStr2Member(String str,Trade trade) throws ParseException {
        StringBuilder sb  =new StringBuilder(str);
        //记录编号
        trade.setFileNum(Integer.parseInt(sb.substring(0,8)));
        //国通商户号
        trade.setMchid(sb.substring(9,24));
        //组织机构号
        trade.setOrgNo(sb.substring(26,34));
        //交易日期
        trade.setTradeDate(DateUtil.str2Time(sb.substring(35,44)));
        //交易金额
        trade.setTradeMoney(new BigDecimal((sb.substring(44,60))).divide(new BigDecimal("100")));
        //交易笔数
        trade.setTradeNum(Integer.parseInt(sb.substring(62,69)));
        //上送日期
        trade.setSendDate(sendDate);
        //文件名
        trade.setFileName(fileName);
        return trade;
    }
    /**
     * 将所有压缩包解压到源目录的temp目录下
     * @param files
     * @return
     */
    private File unZip(File[] files,String sourcePath){
        String newFile = sourcePath+"/temp";
        for (int i = 0; i < files.length; i++) {
            FileUtil.unZip(files[i],newFile);
        }
        return new File(newFile);
    }

//    public static void main(String[] args) {
//        String str="00000001|856280000046007 |                                                                                                                                                                                                                                                                |                                                                                                                                |00|20190403|00000000| |00|000000000000000000              |                                                                                                                                |0                               | |0               |                                                                                                                                |1|                                                                                                                                |6217985540001305919             |        |                                                                                                                                |                                                                                                                                |                                                                                                                                ";
//        str = str.substring(0,8);
//        str = str.substring(9,24);
//        //str = str.substring(0,8);
//        System.out.println(str);
//    }
}
