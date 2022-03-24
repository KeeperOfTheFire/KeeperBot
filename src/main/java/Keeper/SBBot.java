package Keeper;

import Keeper.Listeners.Commands;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class SBBot {

    public static JDABuilder builder;

    public static void main(String[] args) throws LoginException {

        builder = JDABuilder.createDefault(Config.get("TOKEN"));

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);

        builder.setBulkDeleteSplittingEnabled(false);

        builder.setCompression(Compression.NONE);

        builder.setActivity(Activity.watching("Your Stats"));

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

        registerListenters();

        builder.build();

    }



    public static void registerListenters(){
        builder.addEventListeners(new Commands());
    }



}
