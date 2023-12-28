package org.example.service.menu;

import org.example.UserContext;
import org.example.model.User;
import org.example.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserServiceManager {

    private final Scanner scanner;
    private final UserService service;
    private final UserContext userContext;

    public UserServiceManager(UserService service, Scanner scanner) {
        this.scanner = scanner;
        this.service = service;
        this.userContext = UserContext.getInstance();
    }

    public void getRegistration() {
        System.out.println("Please enter your data for registration");

        String name = getInputNotEmpty("Enter your name");
        String nickname = getUniqueNickname(scanner.nextLine());
        String password = getInputNotEmpty("Enter your password:");
        Date birthday = parseDate(scanner.nextLine());
        scanner.nextLine();
        System.out.println("Enter money to add for your account:");
        int amount = scanner.nextInt();

        userContext.setUser(
                this.service.save(
                        User.builder()
                                .name(name)
                                .nickname(nickname)
                                .password(password)
                                .birthday(birthday)
                                .amount(amount)
                                .build()));
        System.out.println("You are registered! Log in!");
    }

    private Date parseDate(String input) {
        String dateFormatPattern = "dd.MM.yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        Date date = new Date();
        boolean mark = true;
        while (mark) {
            System.out.println("Enter date of birth in format dd.MM.yyyy");
            try {
                mark = false;
                date = dateFormat.parse(input);
            } catch (ParseException e) {
                System.err.println("Wrong format of Date. Enter dd.MM.yyyy");
            }
        }
        return date;
    }

    private String getUniqueNickname(String nickname) {
        boolean mark = true;
        while (mark) {
            System.out.println("Enter nickname");
            nickname = scanner.nextLine();

            if (!this.service.nicknameIsPresent(nickname)) {
                return nickname;
            } else {
                System.out.println("This nickname is present in System!Choose another nickname!");
            }
        }
        return nickname;
    }

    private String getInputNotEmpty(String sentence) {
        String input = scanner.nextLine();
        boolean mark = true;
        while (mark) {
            System.out.println(sentence);
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                return input;
            }
        }
        return input;
    }

    public void getAuthorize() {
        scanner.nextLine();
        System.out.println("Enter your nickName");
        String nickName = scanner.nextLine();

        System.out.println("Enter your password");
        String password = scanner.nextLine();
        User findUser = this.service.findUser(nickName, password);
        if (findUser != null) {
            userContext.setUser(findUser);

        }
    }

}
