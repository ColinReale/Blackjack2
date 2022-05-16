package com.blackjack;
import java.util.Scanner;

public class Player extends Person {


    //Creates a new Player, uses the exact same code as the Dealer class.
    Scanner input = new Scanner(System.in);

    //Create a new Player
    public Player() {
        super.setName("Desmond");

    }

    public int doubleDown() {
        System.out.println("Would you like to double down? This will double your bet and hit one more card.");
        System.out.println("Enter 1 if you would like to double down, or 2 if you would NOT like to double down");
        int ddVAR = input.nextInt();

        return ddVAR;
    }

    //Allow the player to make decision when we ask questions with integers as their choices of answers
    public void makeDecision(Deck deck, Deck discard) {
        int decision =0;
        boolean getNum = true;

        //while getting a number
        while (getNum) {

            try {
                System.out.println("Would you like to: 1) Hit, or  2) Stand");
                decision = input.nextInt();
                getNum = false;

            } catch (Exception e) {
                System.out.println("Invalid");
                input.next();
            }
            //we don't close the scanner, because we will need it later.
        }

        //if the player hits (1)
        if (decision == 1) {
            //hit the deck using the deck and discard deck
            this.hit(deck, discard);
            //return (exit the method) if they have blackjack or busted
            if (this.getHand().calculatedValue() > 20) {
                return;
            }
            //if they didn't bust or get 21, the player decides to hit or stand again by going back to the makeDecision method
            else {
                this.makeDecision(deck, discard);
            }

            //if they type any number other than 1, we'll assume they're standing
        } else if (decision == 2) {
            System.out.println("You stand.");
        }

        }

//        this is overloading the makeDecision() method and "forcing" through a decision for the player. I am going to use it when the player doubles down,
//    because they no longer have the choice to hit or stay- they are hitting once and then staying.
    public void makeDecision(Deck deck, Deck discard, boolean doubledDown) {
        if (doubledDown) {
            this.hit(deck, discard);
        }
        }

    }



