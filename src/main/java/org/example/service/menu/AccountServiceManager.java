package org.example.service.menu;

import org.example.UserContext;
import org.example.service.UserService;

import java.util.Scanner;

public class AccountServiceManager {
    private final Scanner scanner;
    private final UserService userService;

    private final UserContext userContext;

    public AccountServiceManager(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
        this.userContext = UserContext.getInstance();
    }

    public void updateAccount() {
        System.out.println(userContext.getUser().getNickname() + " , now your balance " +
                userContext.getUser().getAmount() + ". How much money do you want to deposit?");
        int totalAmount = getCurrentBalance() + scanner.nextInt();
        this.userService.updateAccount(userContext.getUser().getId(), totalAmount);
        userContext.getUser().setAmount(totalAmount);
        System.out.println("You account balance " + getCurrentBalance());

    }

    private int getCurrentBalance(){
        return userContext.getUser().getAmount();
    }

}
