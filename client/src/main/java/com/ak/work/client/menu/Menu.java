package com.ak.work.client.menu;

import com.ak.work.client.entity.User;

import java.util.Scanner;

@FunctionalInterface
public interface Menu {
    void start(User user, Scanner scanner);
}
