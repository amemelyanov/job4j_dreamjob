package ru.job4j.dreamjob.utils;

import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpServletRequest;

public final class UserUtil {

    private UserUtil() {
        throw new AssertionError();
    }

    public static User getSessionUser(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            user = new User();
            user.setEmail("Гость");
        }
        return user;
    }
}
