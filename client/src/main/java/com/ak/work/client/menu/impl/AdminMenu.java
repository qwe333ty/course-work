package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Admin;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AdminMenu extends Menu {
    @Override
    public void start(User user, Scanner scanner) {
        Admin admin = (Admin) user;
        System.out.println("ADMIN!!!!!!!!!!!!!!!!!!!");
    }
}
