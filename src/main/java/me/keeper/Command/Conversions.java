package me.keeper.Command;

public class Conversions{

    private static final String LONG_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefjhijklmnopqrstuvwxyz_";

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