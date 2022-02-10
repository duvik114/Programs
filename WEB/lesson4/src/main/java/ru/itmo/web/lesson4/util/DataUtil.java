package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.User;
import ru.itmo.web.lesson4.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static ru.itmo.web.lesson4.model.UserColor.*;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", RED),
            new User(6, "pashka", "Pavel Mavrin", BLUE),
            new User(9, "geranazarov555", "Georgiy Nazarov", GREEN),
            new User(11, "tourist", "Gennady Korotkevich", RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "First Post", "How much money do you have?" +
                    "svhdgfskdvghgdddddddddddddddddgghhhhhhhhhhhhhhhhhhhhhhhgggggggggfffffffyyyyyyyyyrrrrrrrrrrrrrrrrrrrrrrrrhhhhhjjjjjjjjjjjjjjjjjjjjjjvvvvvvvvkkkkkkkkkkkkkkkkkkkkkkkjjjjjjjjjffffffffffffffffffffffffffffffffffnnnnnnnnnnjjjjjjjjjjjjjjjjjjjjjjjjjjjjj             hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhgggggggddddddddddddd***************&&&&&&&&&&&&&&&&&&&&Sssssssssssskkkkkkkkkkkkkkkkkkkkkkkffffffffffffk", 1),
            new Post(4, "Second Post", "How much time do you have?", 6)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("uri", request.getRequestURI()); //

        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }

        // posts?
    }
}
