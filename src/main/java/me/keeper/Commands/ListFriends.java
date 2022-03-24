package me.keeper.Commands;


import me.keeper.Config;

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
