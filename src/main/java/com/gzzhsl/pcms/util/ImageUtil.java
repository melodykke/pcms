package com.gzzhsl.pcms.util;


import com.gzzhsl.pcms.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /*
     * 将CommonsMutipartFile 流 转换为 java io File
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error("【图片错误】 文件流转换文件出错");
            e.printStackTrace();
        }
        return newFile;
    }

    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        mkDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("相对路径 relativeAddr {}", relativeAddr);
        File dest = new File(PathUtil.getFileBasePath() + relativeAddr);
        logger.debug("绝对路径: {}", PathUtil.getFileBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File("D://watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);

        } catch (IOException e) {
            logger.error("【图片出错】 生成图片出错， 相对路径为：" + relativeAddr);
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 随机生成文件名，当前时间+5为随机数
     */
    public static String getRandomFileName(){
        // 获取5位随机数
        int rannum = r.nextInt(89999)+10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }

    /**
     * 获取输入文件的扩展名
     */
    public static String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/melodykke/xxx.jpg, 那么home work melodykke这三个文件都得自动创建
     */
    public static void mkDirPath(String targetAddr){
        String realFileParentPath = PathUtil.getFileBasePath() +  targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * storePath是文件的路径还是目录的路径
     * 如果是文件的路径则删除该文件
     * 如果是目录路径 则删除该路径下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath = new File(PathUtil.getFileBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (File targetFile : files) {
                    targetFile.delete();
                }
            }
            fileOrPath.delete();
        }
    }

    /**
     * 处理详情图，并返回新生成图片的相对值路径
     */
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则自动创建
        mkDirPath(targetAddr);
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getFileBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getFileBasePath() + relativeAddr);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D://watermark.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩图片失败：" + e.toString());
        }
        // 返回图片相对路径地址
        return relativeAddr;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(basePath);
        Thumbnails.of(new File("D://dashboard.jpg")).size(819, 487)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D://watermark.jpg")), 0.25f)
                .outputQuality(1f).toFile(basePath+"/dashboardnew.jpg");
    }

}
