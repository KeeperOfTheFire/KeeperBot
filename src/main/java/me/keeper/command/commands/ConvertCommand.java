package me.keeper.command.commands;

import me.keeper.command.CommandContext;
import me.keeper.command.ICommand;
import me.keeper.Config;

import java.util.List;

public class ConvertCommand implements ICommand {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String LONG_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz_";

    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();

        if(args.size() < 3){
            ctx.getEvent().getChannel().sendMessage("Correct usage is: " + Config.get("prefix") +"convert <starting base> <ending base> <number to convert>\n" +
                                                         "For example: " + Config.get("prefix") +"convert 2 10 1100100").queue();
            return;
        }

        ctx.getEvent().getChannel().sendMessage("the base " + args.get(0) + " number \"" + args.get(2) + "\" in base " + args.get(1) + " is: " +
                                                     base10ToBaseN(String.valueOf(baseNToBase10(args.get(2), Integer.parseInt(args.get(0)))), Integer.parseInt(args.get(1)))).queue();



    }

    @Override
    public String getName() {
        return "convert";
    }

    @Override
    public String getHelp() {
        return "converts between binary and any base up to 63\n" +
                "Usage: " + Config.get("prefix") +"convert <starting base> <ending base> <number to convert>\n";
    }

    public static String base10ToBaseN(String numToConvert, int base){
        String convertedNum = "";
        String remainderChar;
        int result = Integer.parseInt(numToConvert);
        int remainder;

        while(result > 0){
            remainder = result % base;
            result = result / base;

            remainderChar = String.valueOf( LONG_STRING.charAt(remainder) );

            convertedNum = remainderChar.concat(convertedNum);
        }
        return(convertedNum);
    }

    public static int baseNToBase10(String numToConvert, int baseToConvertFrom){
        int convertedNum = 0;
        int digitCount = numToConvert.length()-1;

        for(int n = digitCount, i = 0; n >= 0; n--, i++)
        {
            char ch1 = numToConvert.charAt(i);
            String ch2 = String.valueOf(ch1);
            int char1 = LONG_STRING.indexOf(ch2);

            convertedNum += (int)(char1 * Math.pow(baseToConvertFrom, n) ); // :o)

        }

        return(convertedNum);
    }
}
