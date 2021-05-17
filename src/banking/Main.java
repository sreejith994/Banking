package banking;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private HashMap<String, String> dictionary = new HashMap<>();

    private Random random = new Random();
    private String accountIdentifer;
    private String cardNumber;
    private String BIN = "400000";
    private String checkSum;
    private String pinNumber;



    public void createAccount(){

        this.accountIdentifer = String.format("%09d", random.nextInt(999_999_999));
        this.checkSum = getCheckSum((BIN + accountIdentifer));
        this.cardNumber = BIN + accountIdentifer + checkSum;
        this.pinNumber = String.format("%04d", random.nextInt(9999));

        dictionary.put(cardNumber,pinNumber);

        System.out.println("Your card has been created\n" +
                "Your card number:\n" +
                cardNumber+"\n" +
                "Your card PIN:\n" +
                pinNumber);

    }

    public boolean isLogIn(Long CardNumber, int pin){
        String cardNum = String.valueOf(CardNumber);
        String pinNum = String.format("%04d", pin);

        try{
            return (dictionary.get(cardNum).equals(pinNum));
        }
        catch (java.lang.NullPointerException ex){
            return false;
        }

    }

    public static void main(String[] args){
        textUI UI = new textUI();
        UI.showUI();
    }

    private String getCheckSum(String cardNumber){
        String[] cardArray = cardNumber.split("");

        //double odd values with odd indices
        for(int i=0; i<cardArray.length; i++){
            if((i+1)%2 != 0){
                cardArray[i] = String.valueOf(Integer.valueOf(cardArray[i])*2);
            }
        }

        int sum = 0;
        //minus 9 from values above 9 and get sum
        for(int i=0;i<cardArray.length;i++){
            int value =Integer.valueOf(cardArray[i]);
            if(value > 9){
                value -=9;
                cardArray[i] = String.valueOf(value);
            }
            sum += value;
        }

        int checkSum = (10-(sum%10) );
        if(checkSum==10){
            checkSum = 0;
        }
        return String.valueOf(checkSum);
    }
}

class textUI{
    Scanner scanner = new Scanner(System.in);
    Main allAccounts = new Main();
    int userChoice;
    Long userInput =0L;
    int passInput;
    boolean run = true;

    public  void showUI(){
        while(run){
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            try{
                run = readInput(userChoice, userInput, passInput);
            }
            catch (IllegalArgumentException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    private boolean readInput(int userChoice,long userInput, int passInput){
        userChoice = scanner.nextInt();
        switch (userChoice){
            case 1:
                allAccounts.createAccount();
                break;
            case 2:
                System.out.println("Enter your card number:");
                userInput = scanner.nextLong();
                System.out.println("Enter your PIN:");
                passInput = scanner.nextInt();

                if(allAccounts.isLogIn(userInput,passInput)){
                    return showLogIn(scanner);

                } else{
                    System.out.println("Wrong card number or PIN!\n");
                }
                break;
            case 0:
                return false;
        }
        return true;
    }

    static boolean showLogIn(Scanner scanner){
        System.out.println("You have successfully logged in!\n" +
                "\n");
        System.out.println();

        while(true){
            System.out.printf("1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");

            int userChoice2 = scanner.nextInt();

            switch (userChoice2){
                case 1:
                    System.out.println("Balance: 0");
                    break;
                case 2:
                    System.out.println("You have successfully logged out!\n");
                    return true;
                case 0:
                    System.out.println("Bye!");
                    return false;
            }
        }

    }


}




