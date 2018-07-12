package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.BaseInfoImg;
import com.gzzhsl.pcms.entity.ContractImg;
import com.gzzhsl.pcms.entity.PreProgressImg;
import com.gzzhsl.pcms.entity.ProjectMonthlyReportImg;
import com.gzzhsl.pcms.service.BaseInfoImgService;
import com.gzzhsl.pcms.service.ContractImgService;
import com.gzzhsl.pcms.service.PreProgressImgService;
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
    @Autowired
    private BaseInfoImgService baseInfoImgService;
    @Autowired
    private PreProgressImgService preProgressImgService;
    @Autowired
    private ContractImgService contractImgService;

    //fileId 就是 img_id
    @GetMapping("/monthlyreportfile")
    public void monthlyReportFile(@RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) {
        ProjectMonthlyReportImg projectMonthlyReportImg = projectMonthlyReportImgService.getById(fileId);
        String downloadPath = PathUtil.getFileBasePath(false)+projectMonthlyReportImg.getImgAddr();
        this.downloadFileAction(downloadPath, request, response);
    }

    @GetMapping("/baseinfofile")
    public void baseInfoFile(@RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) {
        BaseInfoImg baseInfoImg = baseInfoImgService.getByBaseInfoImgId(fileId);
        String downloadPath = PathUtil.getFileBasePath(false)+baseInfoImg.getImgAddr();
        this.downloadFileAction(downloadPath, request, response);
    }
    @GetMapping("/preprogressfile")
    public void preProgressFile(@RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) {
        PreProgressImg preProgressImg = preProgressImgService.getByPreProgressImgId(fileId);
        String downloadPath = PathUtil.getFileBasePath(false)+preProgressImg.getImgAddr();
        this.downloadFileAction(downloadPath, request, response);
    }
    @GetMapping("/contractfile")
    public void contractFile(@RequestParam String fileId, HttpServletRequest request, HttpServletResponse response) {
        ContractImg contractImg = contractImgService.getByContractImgId(fileId);
        String downloadPath = PathUtil.getFileBasePath(false)+contractImg.getImgAddr();
        this.downloadFileAction(downloadPath, request, response);
    }

    private void downloadFileAction(String downloadPath, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
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
