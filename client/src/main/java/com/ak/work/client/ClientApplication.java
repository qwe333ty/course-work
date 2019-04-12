package com.ak.work.client;

import com.ak.work.client.entity.Message;
import com.ak.work.client.util.MessageProducer;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    @Value("${custom.value}")
    private String value;

    @Autowired
    private MessageProducer messageProducer;

    private static int showMenuAndReturnClientChoice(String name, Scanner scanner) {
        System.out.println("\n\n");
        System.out.println("Menu:");
        System.out.println("    1) Send message");
        System.out.println("    2) How message history");
        System.out.println("    3) Exit");
        System.out.println("\n");

        do {
            System.out.print("Number: ");
            String number = scanner.nextLine();
            try {
                return Integer.valueOf(number);
            } catch (NumberFormatException e) {
                System.out.println(name + ", PLEASE ENTER INTEGER NUMBER!");
            }
        } while (true);

    }

    @Override
    public void run(String... args) throws Exception {
        if (value.compareTo("1") == 0) {
            return;
        }

        @Cleanup Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n");
        System.out.print("<> Hello! Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("\n\n-------------------------------------------------------------------\n\n");
        System.out.println("Welcome on board, " + name);

        while (true) {
            int choice = showMenuAndReturnClientChoice(name, scanner);
            switch (choice) {
                case 1: {
                    System.out.println("--------");
                    System.out.println("Enter your message:");
                    System.out.print("> ");

                    String message = scanner.nextLine();

                    HttpStatus status = messageProducer.sendMessage(name, message);
                    System.out.println(status);
                    if (status.value() >= 400) {
                        System.out.println(">>> Something went wrong...");
                    }
                }
                break;
                case 2: {
                    System.out.println("--------");
                    ResponseEntity<List<Message>> response = messageProducer.getMessageHistory();
                    System.out.println(response.getStatusCode());

                    System.out.println("Messages:");
                    List<Message> messages = response.getBody();

                    if (!CollectionUtils.isEmpty(messages)) {
                        response.getBody().forEach(System.out::println);
                    } else {
                        System.out.println(">>> No messages in our history.");
                    }
                }
                break;
                case 3:
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
