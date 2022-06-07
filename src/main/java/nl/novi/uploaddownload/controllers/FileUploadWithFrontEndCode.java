package nl.novi.uploaddownload.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadWithFrontEndCode {

    @GetMapping("/files")
    ModelAndView fileUpload(){
        return new ModelAndView("index.html");
    }
}
