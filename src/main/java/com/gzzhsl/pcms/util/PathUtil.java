package com.gzzhsl.pcms.util;

public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getFileBasePath(){

        String osName = System.getProperty("os.name");
        String basePath = "";
        if (osName.toLowerCase().startsWith("windows")){
            basePath = "D:/pcmsFile/";
        } else {
            basePath = "/home/pcmsFile/";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    // relative path
    public static String getMonthlyReportImagePath(String projectName, String date){
        String imagePath = "upload/monthlyreport/"+ projectName + "/" + date + "/";
        return imagePath.replace("/", separator);
    }

    public static void main(String[] args) {
        System.out.println(PathUtil.getFileBasePath());
    }

}
