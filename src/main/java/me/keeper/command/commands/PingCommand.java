package me.keeper.command.commands;

import me.keeper.Config;
import me.keeper.command.CommandContext;
import me.keeper.command.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getEvent().getJDA();
        jda.getRestPing().queue(
                (ping) -> ctx.getEvent().getChannel()
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "sends the ping of the user\n" +
                "Usage: " + Config.get("prefix") + "ping";
    }
}
