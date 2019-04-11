package alina;

import java.util.ArrayList;
import java.util.List;

public class UserWithFriends implements User{

    private Integer id;
    private List<User> friends;

    UserWithFriends(Integer id){
        this.id = id;
        this.friends = new ArrayList<User>();
    }

    public Integer getId(){
        return id;
    }

    public List<User> getFriends(){
        return friends;
    }

    public void add(User friend) {
        this.friends.add(friend);
    }

    public void draw(){
        Graph.getInstance().addV(this.id);
        System.out.println(this.id);
        for (User friend:this.friends) {
            Graph.getInstance().addV(friend.getId());
            Graph.getInstance().addE(this.id, friend.getId());

            friend.draw();
        }
    }
}
