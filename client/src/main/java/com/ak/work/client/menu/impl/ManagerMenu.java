package com.ak.work.client.menu.impl;

import com.ak.work.client.menu.Menu;
import org.springframework.stereotype.Component;

@Component
public class ManagerMenu implements Menu {
    @Override
    public void start() {
        System.out.println("MANAGER!!!!!!!!!!!!");
    }
}
