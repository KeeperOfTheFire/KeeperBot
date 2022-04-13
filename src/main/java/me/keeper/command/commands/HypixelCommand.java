package me.keeper.command.commands;

import com.github.natanbc.reliqua.util.StatusCodeValidator;
import me.duncte123.botcommons.web.WebUtils;
import me.keeper.Config;
import me.keeper.command.CommandContext;
import me.keeper.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HypixelCommand implements ICommand {


    @Override
    public void handle(CommandContext ctx) {

        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getEvent().getTextChannel();

        if(args.size() < 2){
            channel.sendMessage("Correct usage is: " + Config.get("prefix") + "minecraft rank/friends uuid>").queue ();
            return;
        }

        final String item = args.get(0);
        final String id = args.get(1);



        if(item.equals("rank")){

            fetchRank(id, (rank) -> {
                if (rank == null) {
                    channel.sendMessage("User with name " + id + " was not found").queue();
                    return;
                }
                channel.sendMessage(id + "'s rank is " + rank).queue();
            });

            //System.out.println(this.rank);

        }else if(item.equals("friends")){
            fetchFriends(id, (names) -> {
                if (names == null) {
                    channel.sendMessage("That uuid is not valid").queue();
                    return;
                }

                final String namesJoined = String.join(", ", names);

                channel.sendMessageFormat("Name history for %s:\n%s", id, namesJoined).queue();
            });

        }else{
            channel.sendMessageFormat("%s is not known, please either choose rank or friends", item).queue();
        }

    }

    @Override
    public String getName() {
        return "hypixel";
    }

    @Override
    public String getHelp() {
        return "get the rank or friends of a player\n" +
                "Usage: " + Config.get("prefix") + "minecraft rank/friends <username/uuid>";
    }

    private void fetchUUID(String username, Consumer<String> callback){
        WebUtils.ins.getJSONObject(
                "https://api.mojang.com/users/profiles/minecraft/" + username,
                (builder) -> builder.setStatusCodeValidator(StatusCodeValidator.ACCEPT_200)
        ).async(
                (json) -> callback.accept(json.get("id").asText()),
                (error) -> callback.accept(null)
        );


    }

    private void fetchRank(String username, Consumer<String> callback){
        fetchUUID(username, (uuid) -> {
            if (uuid == null) {
                return;
            }
            WebUtils.ins.getJSONObject(
                    "https://api.hypixel.net/player?key="+Config.get("apikey")+"&uuid=" + uuid,
                    (builder) -> builder.setStatusCodeValidator(StatusCodeValidator.ACCEPT_200)
            ).async(
                    (json) -> callback.accept(formatRank(json.get("player").get("monthlyPackageRank").asText(), json.get("player").get("newPackageRank").asText())),
                    (error) -> callback.accept(null)
            );
        });

    }

    private void fetchFriends(String uuid, Consumer<List<String>> callback){
        WebUtils.ins.getJSONArray(
                "https://api.hypixel.net/friends?key="+Config.get("apikey")+"&uuid=" + uuid,
                (builder) -> builder.setStatusCodeValidator(StatusCodeValidator.ACCEPT_200)
        ).async(
                (json) -> {
                    List<String> names = new ArrayList<>();
                    json.forEach((item) -> names.add(item.get("records").get("uuidReceiver").asText()));
                    callback.accept(names);
                },
                (error) -> callback.accept(null)
        );
    }

    private String formatRank(String monthlyPackageRank, String newPackageRank){
        //checks for ++
        if(monthlyPackageRank.equals("SUPERSTAR"))
            return "mvp++";
        //checks for non + rank
        if(newPackageRank.length()<=3)
            return newPackageRank.toLowerCase();
        //adds + if _PLUS
        else if(newPackageRank.contains("_PLUS")){
            return newPackageRank.substring(0,3).toLowerCase() + "+";
        }
        //handles the nons
        return "none";
    }
}
