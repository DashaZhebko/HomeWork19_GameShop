package org.example;

import org.example.model.User;

public class UserContext {
    private static UserContext context;
    public static UserContext getInstance() {
        if (context == null) {
            context = new UserContext();
        }
        return context;
    }

    private User user;

    public User getUser() {
        if (user==null){
            user = User.builder().build();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
