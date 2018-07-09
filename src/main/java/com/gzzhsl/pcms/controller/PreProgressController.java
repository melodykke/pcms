package com.gzzhsl.pcms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/preprogress")
public class PreProgressController {

    @GetMapping("/topreprogress")
    public String toPreprogress() {
        return "pre_progress_show";
    }
}
