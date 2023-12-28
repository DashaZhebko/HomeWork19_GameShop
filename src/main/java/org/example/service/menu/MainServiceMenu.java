package org.example.service.menu;

import org.example.service.GameService;
import org.example.service.UserService;

import java.util.Scanner;

public class MainServiceMenu {
   private final Scanner scanner;
   private final UserService userService;
   private final GameService gameService;


    public MainServiceMenu(Scanner scanner, UserService userService, GameService gameService) {
        this.scanner = scanner;
        this.userService = userService;
        this.gameService = gameService;
    }

    public void getMainMenu() {
        System.out.println(
                "==== Main menu ====" + '\n' +
                        "1. Games management" + '\n' +
                        "2. Accounts management" + '\n' +
                        "3. Exit "
        );
        switch (scanner.nextInt()) {
            case 1:new GameServiceManager(this.scanner, this.userService, this.gameService).showGamesMenu();
                break;
            case 2:
                 new AccountServiceManager(this.scanner, this.userService).updateAccount();
                 getMainMenu();
                 break;
            case 3:
                scanner.close();
                System.exit(0);
            default:
                getMainMenu();
        }

    }


}
