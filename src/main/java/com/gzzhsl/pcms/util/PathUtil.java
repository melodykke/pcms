package com.gzzhsl.pcms.util;

public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getFileBasePath(Boolean temp){

        String osName = System.getProperty("os.name");
        String basePath = "";
        if (temp == true) {
            if (osName.toLowerCase().startsWith("windows")){
                basePath = "D:/pcmsFileTemp/";
            } else {
                basePath = "/home/pcmsFileTemp/";
            }
        } else {
            if (osName.toLowerCase().startsWith("windows")) {
                basePath = "D:/pcmsFile/";
            } else {
                basePath = "/home/pcmsFile/";
            }
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }
    // relative path  月报
    public static String getMonthlyReportImagePath(String projectName, String date){
        String imagePath = "upload/monthlyreport/"+ projectName + "/" + date + "/";
        return imagePath.replace("/", separator);
    }

    // relative path  基础信息
    public static String getBaseInfoImagePath(String projectName){
        String imagePath = "upload/baseinfo/"+ projectName + "/";
        return imagePath.replace("/", separator);
    }
    public static void main(String[] args) {
        System.out.println(PathUtil.getFileBasePath(false));
    }

}
