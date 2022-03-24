package me.keeper.Listeners;

import me.keeper.Command.Conversions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Commands extends ListenerAdapter {
    public String prefix = "k!";
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        String[] message = event.getMessage().getContentRaw().split(" ");
        //test command
        if(message[0].equalsIgnoreCase(prefix + "test"))
            event.getMessage().reply("I am up!!").queue();

        //help command
        if(message[0].equalsIgnoreCase(prefix + "help"))
            event.getMessage().reply("These are my commands so far: \n" +
                    prefix + "convertFromBin <binary number to convert> <base to convert to>\n" +
                    prefix + "convertToBin <number to convert to binary> <base of the number>").queue();

        //conversion commands
        if(message[0].equalsIgnoreCase(prefix + "convertFromBin")){
            if(message.length == 3) {
                String temp  = String.valueOf(Conversions.baseNToBase10(message[1], 2));
                event.getMessage().reply("the binary number " + message[1] + " in base " + message[2] + " = "
                        + Conversions.base10ToBaseN(temp, Integer.parseInt(message[2]))).queue();
            }else if(message.length == 2)
                event.getMessage().reply("the binary number " + message[1] + " in base 10 = "
                        + Conversions.baseNToBase10(message[1], 2)).queue();
            else
                event.getChannel().sendMessage("this is not the proper usage, your message should look like this:\n" +
                        ""+ prefix + "convertFromBin <binary number to convert> <base to convert to>").queue();
        }

        if(message[0].equalsIgnoreCase(prefix + "convertToBin")){
            if(message.length == 3) {
                String temp  = String.valueOf(Conversions.baseNToBase10(message[1], Integer.parseInt(message[2])));
                event.getMessage().reply("the base " + message[2] + " number " + message[1] + " in binary = "
                        + Conversions.base10ToBaseN(temp, Integer.parseInt(message[2]))).queue();
            }else if(message.length == 2)
                event.getMessage().reply("the base 10 number " + message[1] + " in binary = "
                        + Conversions.base10ToBaseN(message[1], 2)).queue();
            else
                event.getChannel().sendMessage("this is not the proper usage, your message should look like this:\n" +
                        ""+ prefix + "convertToBin <number to convert to binary> <base of the number>").queue();
        }


    }
}
