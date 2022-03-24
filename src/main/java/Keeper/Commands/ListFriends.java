package Keeper.Commands;


import Keeper.Config;
import Keeper.SBBot;
import java.util.UUID;

public class ListFriends {
    private String ign;
    private String apiKey;

    public ListFriends(String ign){
        this.ign = ign;
        this.apiKey = Config.get("APIKEY");
    }

    public String toString(){
        return "lol";
    }

}
