package com.blackjack;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Game {

    //Declare variables needed for Game class
    private Deck deck, discarded;

    private Dealer dealer;
    private Player player;
    private int wins, losses, pushes;

    private int chips;


    /**
     * Basically each game we need to create a new deck of 52 cards, create an empty deck (for shuffling purposes),
     * create the dealer and player,
     * shuffle the deck and start the first round
     */
    public Game() {
        chips = 100;
        //Create a new deck with 52 cards
        deck = new Deck(true);
        //Create a new empty deck
        discarded = new Deck();

        //Create the People
        dealer = new Dealer();
        player = new Player();


        //Shuffle the deck and start the first round
        deck.shuffle();
        startRound();
    }

    public Integer makeBet(){
        System.out.println("How much would you like to bet? (In multiples of 5 chips only please!)");
        Scanner scanner = new Scanner(System.in);
        int betAmt = scanner.nextInt();
        if (betAmt % 5 == 0 && betAmt != 0) {
            return betAmt;

        } else {
            System.out.println("Sorry, please enter a bet in multiples of 5 only.");
            makeBet();
        }

        return 0;

    }


    //This  method will handle the logic for each round
    private void startRound() {


        if (wins > 0 || losses > 0 || pushes > 0) {
            System.out.println();
            System.out.println("Starting Next Round... Wins: " + wins + " Losses: " + losses + " Pushes: " + pushes);
            dealer.getHand().discardHandToDeck(discarded);
            player.getHand().discardHandToDeck(discarded);
        }

        //Checking to make sure the deck has at least 4 cards left
        if (deck.cardsLeft() < 4) {
            deck.reloadDeckFromDiscard(discarded);
        }


        System.out.println("You have " +chips+" chips.");

        int betAmt = makeBet();
        //Gives the player two cards
        player.getHand().takeCardFromDeck(deck);
        player.getHand().takeCardFromDeck(deck);

        //Gives the dealer two cards
        dealer.getHand().takeCardFromDeck(deck);
        dealer.getHand().takeCardFromDeck(deck);


        //Prints the hand of the player and of the dealer so the player can see  what is happening
        dealer.printFirstHand();
        player.printHand();


        //Check if dealer has BlackJack at the start
        if (dealer.hasBlackjack()) {
            //shows that the dealer has BlackJack
            dealer.printHand();

            //Check if the player also has BlackJack
            if (player.hasBlackjack()) {
                //End the round with a push
                System.out.println("You both have 21 - Push.");
                pushes++;
                startRound();
            } else {
                System.out.println("Dealer has BlackJack. You lose.");
                dealer.printHand();
                losses++;
                chips -= betAmt;

                startRound();
            }
        }

        //Check if player has blackjack to start
        //If we got to this point, we already know the dealer didn't have blackjack
        if (player.hasBlackjack()) {
            System.out.println("You have Blackjack! You win!");
            wins++;
            chips += betAmt;

            startRound();
        }

        if (player.doubleDown() < 2){
            betAmt*=2;
            player.makeDecision(deck, discarded, true);
        }else {
            player.makeDecision(deck, discarded);
        }


        //Check if they busted
        if (player.getHand().

                calculatedValue() > 21) {
            System.out.println("You have gone over 21.");
            //count the losses
            losses++;
            //start the round over
            chips -= betAmt;

            startRound();
        }

//Now it's the dealer's turn
        dealer.printHand();
        while (dealer.getHand().

                calculatedValue() < 17) {
            dealer.hit(deck, discarded);
        }

//Check who wins
        if (dealer.getHand().

                calculatedValue() > 21) {
            System.out.println("Dealer busts");
            wins++;
            chips += betAmt;
        } else if (dealer.getHand().

                calculatedValue() > player.getHand().

                calculatedValue()) {
            System.out.println("You lose.");
            losses++;
            chips -= betAmt;

        } else if (player.getHand().

                calculatedValue() > dealer.getHand().

                calculatedValue()) {
            System.out.println("You win.");
            wins++;
            chips += betAmt;

        } else {
            System.out.println("Push.");
        }

        //Start a new round
        startRound();
    }

}
