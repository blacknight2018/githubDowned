package com.example.demo.controller;

import com.example.demo.service.DownedService;
import com.example.demo.service.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
public class FileServer {
    @Autowired
    DownedService downedService;

    @ResponseBody
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public Object downedLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        String taskIdString = httpServletRequest.getParameter("id");
        if(null!=taskIdString)
        {
            try {
                int taskId = Integer.parseInt(taskIdString);
                String fileRealName = downedService.getRealFileNameById(taskId);
                String fileLocalName = downedService.getLocalFileNameById(taskId);
                httpServletResponse.setHeader("Content-Type", "application/octet-stream");
                httpServletResponse.setHeader("Connection", "keep-alive");
                fileRealName = java.net.URLEncoder.encode(fileRealName, "UTF-8");
                httpServletResponse.setHeader("Content-disposition", "attachment; filename="+fileRealName);
                httpServletResponse.setHeader("Content-Length", new Long(FileUtils.getFileLength(fileLocalName)).toString());
                //
                byte[] fileDates = FileUtils.getFileDates(fileLocalName);
                OutputStream outputStream =  httpServletResponse.getOutputStream();
                outputStream.write(fileDates);
                outputStream.flush();
                outputStream.close();
                return null;
            }
            catch (Exception e){
                return null;
            }
        }
        return null;
    }
}
