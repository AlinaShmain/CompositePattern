package alina;

import java.util.List;

public class UserWithNoFriends implements User{

    private Integer id;

    UserWithNoFriends(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void add(User user) {

    }

    public void draw() {
        Graph.getInstance().addV(this.id);
    }

    public List<User> getFriends() {
        return null;
    }
}
