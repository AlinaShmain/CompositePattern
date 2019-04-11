package alina;

import java.util.List;

public interface User {
    Integer getId();
    void add(User user);
    void draw();
    List<User> getFriends();
}
