package com.example.demo.controller;
import com.example.demo.service.DownedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/github")
public class Main {

    @Autowired
    DownedService downedService;

    @GetMapping("/down")
    @ResponseBody
    public String StartDownLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        String GithubUrl = null;
        if(httpServletRequest!=null) {
            GithubUrl =httpServletRequest.getParameter("url");
        }
        int taskId = downedService.startDownedTask(GithubUrl);
        return new Integer(taskId).toString();
    }
    @GetMapping("/query")
    @ResponseBody
    public String QueryDownStatus(HttpServletRequest httpServletRequest)
    {
        if(httpServletRequest!=null) {
            String taskIdString =httpServletRequest.getParameter("id");
            try {
                Integer taskId = Integer.parseInt(taskIdString);
                if(taskId!=null)
                {
                    return new Integer(downedService.getTaskStatus(taskId)).toString();
                }
            }
            catch (Exception e){
                return null;
            }
        }
        return null;
    }
}
