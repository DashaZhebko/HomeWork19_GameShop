package org.example.service.menu;

import org.example.UserContext;
import org.example.model.Game;
import org.example.service.GameService;
import org.example.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameServiceManager {
    private final Scanner scanner;
    private final UserService userService;
    private final GameService gameService;
    private final UserContext userContext;
    private Map<Integer, Game> games = new HashMap<>();

    public GameServiceManager(Scanner scanner, UserService userService, GameService gameService) {
        this.scanner = scanner;
        this.userService = userService;
        this.gameService = gameService;
        this.userContext = UserContext.getInstance();
    }

    public void getGamesMenu() {
        games = this.gameService.getAllGames();
        System.out.println(
                "1. Show all games" + '\n' +
                        "2. Buy game" + '\n' +
                        "3. Go to main menu"
        );
        switch (scanner.nextInt()) {
            case 1:
                showAllGames();
                getGamesMenu();
                break;
            case 2:
                makePayment();
                getGamesMenu();
                break;
            case 3:
                new MainServiceMenu(this.scanner, this.userService, this.gameService).getMainMenu();
                break;
            default:
                getGamesMenu();
                break;
        }
    }

    private void showAllGames() {
        System.out.println("======Games:=====");
        for (Map.Entry<Integer, Game> entry : games.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private boolean purchaseIsAvailable(int gameId) {
        if (gameIsPresent(gameId)) {
            int projectedBalance = getProjectedBalance(gameId);
            if (projectedBalance < 0) {
                System.out.println("Insufficient funds in the account. You are" + projectedBalance + "short" +
                        "Top up your account through the menu Account service");
                return false;
            }
        }
        return true;
    }

    private boolean gameIsPresent(int gameId) {
        return games.get(gameId) == null;
    }

    private int getProjectedBalance(int gameId) {
        return this.userContext.getUser().getAmount() - games.get(gameId).getCost();
    }

    private void makePayment() {
        System.out.println("Enter number of game:");
        int gameId = this.scanner.nextInt();
        if (purchaseIsAvailable(gameId)) {
            executePurchase(gameId);
        }
    }

    private void executePurchase(int gameId) {
        this.userService.updateAccount(this.userContext.getUser().getId(), getProjectedBalance(gameId));
        this.userService.addGameToUserAccount(this.userContext.getUser().getId(), gameId);
    }
}
