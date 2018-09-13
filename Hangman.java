/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

/**
 *
 * @author snehr
 */
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 *  You must fully comment this class
 **/
public class Hangman
{
    private boolean donezo;
    private Scanner scan;
    private HashMap<Integer,String> man;
    private ArrayList<String> alphabet;
    private int guesses;
    private String wordToGuess, guess;
    private StringBuilder wordToShow;   
    private String[] bodyParts;
    private char again;

    public Hangman()
    {
        donezo = false;
        Scanner scan = new Scanner(System.in);
        bodyParts = new String[11];
        for(int i = 0; i < 11; i++){
            bodyParts[i] = " ";
        }
        alphabet = new ArrayList<>();
        man = new HashMap<>();
        man.put(1,"!");
        man.put(2,"0");
        man.put(3,"/");
        man.put(4,"|");
        man.put(5,"\\");
        man.put(6,"^");
        man.put(7, "/");
        man.put(8, "\\");
        man.put(9, "_");
        man.put(10, "_");
        wordToShow = new StringBuilder();

        do{
            resetAlphabet();
            splashScreen();

            System.out.println("If you are guessing the puzzle, turn away.");
            System.out.println("If you are creating the puzzle, enter the word or phrase:");
            String wordToGuess1 = scan.nextLine().toUpperCase();
            String [] array = wordToGuess1.split(" ");
            wordToGuess = "";
            for (String a : array){
            wordToGuess += a;}
            wordToShow = new StringBuilder(wordToGuess);
            anonymize(wordToShow.toString());
            do{
                letters();
                System.out.println();
                hangman();
                System.out.println(wordToShow);
                System.out.println("Guess a letter:");
                guess = scan.nextLine().toUpperCase();
                while(!(alphabet.contains(guess))){
                guess = scan.nextLine().toUpperCase();}
                donezo = process(guess);

            }while(!donezo);
            System.out.println("Play again? (y/n)");
            again = scan.nextLine().charAt(0);
            if(again == 'n' || again == 'N'){
               System.exit(0); 
            }
        }while(again == 'y' || again == 'Y');
    }

    public boolean process(String guess)
    {
        if(guess.equals(wordToGuess)){
            donezo = true;
        }
        else{
            guess = guess.substring(0,1).toUpperCase();
        }

        if(!(wordToGuess.contains(guess))){
            guesses++;
            alphabet.set(alphabet.indexOf(guess), " ");
            if(guesses == 10){
                System.out.println("You have been hung! The answer was " + wordToGuess);
                return true;
            }
            return false;
        }
        else{
            for(int i = 0; i < wordToGuess.length(); i++){
                if(wordToGuess.charAt(i) == guess.charAt(0)){
                    wordToShow.setCharAt(i, guess.charAt(0));     
                }
            }
            if(wordToShow.toString().equals(wordToGuess)){
                System.out.println("O yea!");
                return true;
            }
            alphabet.set(alphabet.indexOf(guess), " ");
        }
        return false;
    }

    public void hangman()
    {
        for(int i = 1; i < 11; i++){
            if(guesses == i){
                bodyParts[i] = man.get(i);  
            }
        }
        System.out.println(" +----+");
        System.out.println(" |    " + bodyParts[1]);
        System.out.println(" |    " + bodyParts[2]);
        System.out.println(" |   " + bodyParts[3]  + bodyParts[4]  + bodyParts[5]);
        System.out.println(" |    " + bodyParts[6]);
        System.out.println(" |  " +  bodyParts[10]+ bodyParts[7] + " " + bodyParts[8] + bodyParts[9]);
        System.out.println(" =============");

    }

    public void letters()
    {
        for(int i = 0; i < alphabet.size(); i++){
            System.out.print(alphabet.get(i));
        }
    }

    public String anonymize(String s)
    {
        for(int i = 0; i < s.length(); i++){
            wordToShow.setCharAt(i, (char)95);
        }
        return s;
    }

    public void resetAlphabet() 
    {
        alphabet.clear();
        
        for(int i = 1; i < 11; i++){
                bodyParts[i] = ""; 
        }
        for(int i = 65; i < 91; i++){
            char boi = (char)i;
            alphabet.add("" + boi);
        }
        
        guesses = 0;
    }

    public void setUp()
    {
        while(donezo != true){
            guess = scan.nextLine();
            process(guess);
        }
    }

    public void splashScreen()
    {
        System.out.print("\f");
        System.out.println("  _");
        System.out.println(" | |__   __ _ _ __   __ _ _ __ ___   __ _ _ __");
        System.out.println(" | '_ \\ / _` | '_ \\ / _` | '_ ` _ \\ / _` | '_ \\");
        System.out.println(" | | | | (_| | | | | (_| | | | | | | (_| | | | |");
        System.out.println(" |_| |_|\\__,_|_| |_|\\__, |_| |_| |_|\\__,_|_| |_|");
        System.out.println("                     |___/");
    }
}
