package com.zero.midas.controller;

import com.zero.midas.task.BaseDataCollectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/task"})
public class TaskController {
    @Autowired
    private BaseDataCollectTask baseDataCollectTask;

    @GetMapping({"/daily"})
    public void daily() {
        (new Thread(() -> this.baseDataCollectTask.daily())).start();
    }

    @GetMapping({"/weekly"})
    public void weekly() {
        (new Thread(() -> this.baseDataCollectTask.weekly())).start();
    }

    @GetMapping({"/monthly"})
    public void monthly() {
        (new Thread(() -> this.baseDataCollectTask.monthly())).start();
    }
}
