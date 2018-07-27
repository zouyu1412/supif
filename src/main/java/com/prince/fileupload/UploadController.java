package com.prince.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zouy-c on 2018/1/8.
 */
@Controller
public class UploadController {

    //初始化上传文件的界面，跳转到index.jsp
    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
        return index();
    }

    //上传文件
    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public @ResponseBody String upload(MultipartFile file){
        //TODO
        return "上传成功";
    }
}
