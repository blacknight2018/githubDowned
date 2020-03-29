package com.example.demo.service.thread;

import com.example.demo.javabean.DownedObject;
import com.example.demo.javabean.dao.List;
import com.example.demo.mapper.ListDAO;
import com.example.demo.service.utils.Encryption;
import com.example.demo.service.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.demo.download.GitHub.githubDownload;

public class TaskThread extends Thread{
    @Autowired
    ListDAO listDAO;

    int taskId;
    String downedUrl;

    public TaskThread(int taskId,String downedUrl)
    {
        this.taskId = taskId;
        this.downedUrl = downedUrl;
        listDAO = ApplicationContextProvider.getBean(ListDAO.class);
        return ;
    }
    public void run()
    {
        List list = new List();
        System.out.println("Start DownLoaded Task : "+this.downedUrl);
        DownedObject downedObject = githubDownload(this.downedUrl);
        list.setId(this.taskId);
        list.setFileName(downedObject.getFileName());
        //Save
        String hashString = Encryption.encrypByMD5(downedObject.getFileName()+list.getId());
        String localFileName = FileUtils.getSaveContents()+"\\"+hashString;

        boolean saveFlag = FileUtils.writeBytesToFile(localFileName,downedObject.getFileDatas());
        list.setLocalPath(localFileName);
        list.setStatus(saveFlag==true?1:0);
        listDAO.updateByPrimaryKeySelective(list);
    }
}
