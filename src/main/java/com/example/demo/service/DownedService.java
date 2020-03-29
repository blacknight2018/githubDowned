package com.example.demo.service;

import com.example.demo.javabean.dao.List;
import com.example.demo.mapper.ListDAO;
import com.example.demo.service.thread.TaskThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownedService {

    @Autowired
    ListDAO listDAO;

    public int startDownedTask(String githubUrl)
    {
        List list = new List();
        list.setStatus(0);
        listDAO.insertSelective(list);

        //Start
        new TaskThread(list.getId(),githubUrl).start();
        return list.getId();
    }
    public String getLocalFileNameById(int taskId)
    {
        List list = new List();
        list.setId(taskId);
        list = listDAO.selectByPrimaryKey(taskId);
        if(null!=list)
        {
            return list.getLocalPath();
        }
        return null;
    }
    public String getRealFileNameById(int taskId)
    {
        List list = new List();
        list.setId(taskId);
        list = listDAO.selectByPrimaryKey(taskId);
        if(null!=list)
        {
            return list.getFileName();
        }
        return null;
    }
    public int getTaskStatus(int taskId)
    {
        List list = null;
        list = listDAO.selectByPrimaryKey(taskId);
        return list.getStatus();
    }
}
