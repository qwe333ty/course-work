package com.ak.work.client.menu.impl;

import com.ak.work.client.entity.Expert;
import com.ak.work.client.entity.User;
import com.ak.work.client.menu.Menu;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ExpertMenu implements Menu {
    @Override
    public void start(User user, Scanner scanner) {
        Expert expert = (Expert) user;
        System.out.println("EXPERT!!!!!!!!!!!!");
    }
}
