package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.ProjectMonthlyReportImg;
import com.gzzhsl.pcms.service.ProjectMonthlyReportImgService;
import com.gzzhsl.pcms.util.PathUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/download")
public class DownLoadController {

    @Autowired
    private ProjectMonthlyReportImgService projectMonthlyReportImgService;

    //fileId 就是 img_id
    @GetMapping("/downloadFile")
    public void downloadFileAction(@RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) {
        ProjectMonthlyReportImg projectMonthlyReportImg = projectMonthlyReportImgService.getById(fileId);
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        String downloadPath = PathUtil.getFileBasePath(false)+projectMonthlyReportImg.getImgAddr();
        try {
            File file = new File(downloadPath);
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
            IOUtils.copy(fis,response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

 /**/