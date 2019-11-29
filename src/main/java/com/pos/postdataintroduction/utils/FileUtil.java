package com.pos.postdataintroduction.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static final int  BUFFER_SIZE = 1024;
    public static void main(String[] args) throws IOException {
//        MappedFileReader reader = new MappedFileReader("/home/zfh/movie.mkv", 65536);
//        long start = System.nanoTime();
//        while (reader.read() != -1);
//        long end = System.nanoTime();
//        reader.close();
//        System.out.println("MappedFileReader: " + (end - start));
        //readFileByLines("F:\\vue\\05000000004_TXNS_0000_20190909_A_0001_0001.txn");
    }

    /**
     * zip解压.mer文件或.txn文件
     * @param srcFile        zip源文件
     * @param destDirPath     解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     */
    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "当前文件不存在:"+srcFile.getName());
        }
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("当前解压文件:" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    //根据文件大小筛掉周上传控制文件
                    System.out.println(entry.getSize());
                    if(entry.getSize()<=1024){
                        continue;
                    }
                    // 保证这个文件的父文件夹必须要存在
                    if(!targetFile.getParentFile().exists()){
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[BUFFER_SIZE];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if(zipFile != null){
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
    /**
     * 以行为单位读取文件
     * @param file  需要读取的文件对象
     */
    public static List<String> readFileByLines(File file) {
        List<String> listFile = new LinkedList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String tempString = null;
            int line = 1;
            // 一次读一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                listFile.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    return listFile;
                }
            }
        }
        return listFile;
    }

    /**
     * 根据提供的目录获取所有的压缩包
     * @return
     */
    public static File[] getAllZip(String sourcePath){
        File file = new File(sourcePath);
        File temp = new File(sourcePath+"/temp");
        if(!temp.exists()){
            temp.mkdir();
        }
        if(!file.exists()){
            logger.info("获取压缩文件失败:{}，请将压缩文件放到指定目录。",sourcePath);
            return null;
        }
        if(!file.isDirectory()){
            logger.info("{}不是个文件夹路径，请将使用文件夹路径。",sourcePath);
            return null;
        }
        File[] files = file.listFiles();
        File[] files1 = new File[files.length-1];
        //将文件夹过滤
        for (int i=0;i<files.length;i++) {
            if(!files[i].isDirectory()){
                files1[i] = files[i];
            }
        }
        return files1;
    }
}
