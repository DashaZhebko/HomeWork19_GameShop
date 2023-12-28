package org.example;

import org.example.repository.GameRepositoryImpl;
import org.example.repository.UserRepositoryImpl;
import org.example.service.GameService;
import org.example.service.UserService;
import org.example.service.menu.MainServiceMenu;
import org.example.service.menu.UserServiceManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = ConnectionSingleton.getConnection()) {
            try (Scanner scanner = new Scanner(System.in)) {
                scanner.useDelimiter("\n");
                UserContext userContext = UserContext.getInstance();
                UserService userService = new UserService(new UserRepositoryImpl(connection));
                GameService gameService = new GameService(new GameRepositoryImpl(connection));

                UserServiceManager management = new UserServiceManager(userService, scanner);

                System.out.println("Hello, are you registered?(y/n) ");
                String answer = scanner.nextLine();

                if ("y".equals(answer)) {
                    management.getAuthorize();
                    if (userContext.getUser().getNickname() == null) {
                        management.getRegistration();
                        management.getAuthorize();
                    }
                } else {
                    management.getRegistration();
                    management.getAuthorize();
                }

                if (userContext.getUser().getNickname() == null) {
                    System.out.println("Ok, you try:(");
                } else {
                    System.out.println("Hello " + userContext.getUser().getNickname());
                    new MainServiceMenu(scanner, userService, gameService).getMainMenu();
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
