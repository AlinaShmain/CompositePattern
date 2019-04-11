package alina;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.responses.GetResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {

    private static VkApiClient vk;
    private static ServiceActor actor;
    private static List<Integer> ids;
    private static HashMap<Integer, User> userMap;

    public static void main(String[] args) throws IOException, ClientException, ApiException, InterruptedException {

        String access_token = "6bf75dfe73a6b3f2ed594c7092d4b66ba15a2047d72ef03b7133b6cb7a5e902beb5e9b5ebb72fcc8a18b1";
        Integer user_id = 253030595;
        Integer app_id = 6934041;

        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);

        actor = new ServiceActor(app_id, access_token);

        ids = new ArrayList<Integer>();

        Graph.getInstance().init();

        load(user_id);

        userMap.get(user_id).draw();

        Graph.getInstance().save();
    }

    public static void load(Integer user_id) throws ClientException, ApiException, InterruptedException {

        ids.add(user_id);

        GetResponse userFriends = vk.friends().get(actor)
                .userId(user_id)
                .count(2)
                .execute();
//        System.out.println(userFriends.getItems());

        for(Integer friendId:userFriends.getItems()){
//            System.out.println(friendId);

            for(int i=0; i< ids.size(); i++) {
                if(!friendId.equals(ids.get(i))){
                    if(i==ids.size()-1) {
//                    List<UserXtrCounters>
                        TimeUnit.SECONDS.sleep(1);
                        String userInfo = vk.users().get(actor)
                                .userIds(friendId.toString())
                                .executeAsString();
//                        System.out.println(userInfo);

//                    for (UserXtrCounters user : userInfo) {
//                System.out.println(user.getFirstName() + " " + user.getLastName());
//                System.out.println(user.getHidden());
//                System.out.println(user.getCounters().getFriends());
//            }

                        JsonParser parser = new JsonParser();
                        JsonObject mainObject = parser.parse(userInfo).getAsJsonObject();

                        JsonArray resp = mainObject.get("response").getAsJsonArray();
                        JsonObject obj = resp.get(0).getAsJsonObject();

                        if(obj.get("can_access_closed") != null) {
                            String can_access_closed = obj.get("can_access_closed").toString();

                            if (can_access_closed.equals("true")) {
                                TimeUnit.SECONDS.sleep(2);
                                load(friendId);
                            } else {
                                ids.add(friendId);
                            }
                        } else {
                            ids.add(friendId);
                        }
                    }
                } else { break; }
            }
        }

//        System.out.println("!!User " + user_id);
//        System.out.println("Friends " + userFriends.getItems());

        if(userMap == null){
            userMap = new HashMap<Integer, User>();
        }

        User userWithFriends = new UserWithFriends(user_id);
        for(Integer friend:userFriends.getItems()) {
            if(userMap.get(friend) != null) {
                User user = userMap.get(friend);
//                System.out.println("Id: " + friend);
//                for (User u:userMap.get(friend).getFriends()){
//                    System.out.println("Friend Id: " + u.getId());
//                }
                userWithFriends.add(user);
            } else {
                User user = new UserWithNoFriends(friend);
                userWithFriends.add(user);
            }
        }

        userMap.put(user_id, userWithFriends);
     }
}

