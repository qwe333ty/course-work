package com.ak.work.client;

import com.ak.work.client.service.MenuService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Value("${custom.value}")
    private String value;

    @Autowired
    private MenuService menuService;

    @Override
    public void run(String... args) throws Exception {
        if (value.compareTo("1") == 0) {
            return;
        }

        System.out.println("\n\n\n\n\n\n");
        System.out.print("<> Hello! Welcome on board");
        System.out.println("\n\n-------------------------------------------------------------------\n");

        @Cleanup Scanner scanner = new Scanner(System.in);

        while (true) {
            int choice = menuService.startMenu(scanner);
            switch (choice) {
                case 1: {
                    System.out.println("--------");
                    Boolean b = menuService.login(scanner);
                    if (b != null && !b) {
                        System.out.println("Данные для входа не верны, либо Вы были заблокированы.");
                    }
                }
                break;
                case 2:
                default: {
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
