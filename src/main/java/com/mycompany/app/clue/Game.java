package com.mycompany.app.clue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class Game {
	
	// record of all cards in deck
	public static List<Weapon> WEAPONS = Arrays.asList(
			new Weapon("Knife"),
			new Weapon("Rope"),
			new Weapon("Candlestick"),
			new Weapon("Revolver"),
			new Weapon("Wrench"),
			new Weapon("Lead Pipe"));
	public static List<Suspect> SUSPECTS = Arrays.asList(
			new Suspect("Senator Scarlet"),
			new Suspect("Colonal Mustard"),
			new Suspect("Reverend Green"),
			new Suspect("Professor Plum"),
			new Suspect("Justice Peacock"),
			new Suspect("Doctor White"));
	public static List<Room> ROOMS = Arrays.asList(
			new Room("Conservatory"),
			new Room("Billiards Room"),
			new Room("Ballroom"),
			new Room("Cellar"),
			new Room("Dining Room"),
			new Room("Kitchen"),
			new Room("Hall"),
			new Room("Library"),
			new Room("Lounge"),
			new Room("Study"));
	
	Guess confidential;

    PlayerRing playerRing = new PlayerRing();
    
    static int NEXT = 0;
    static int FINALGUESS = 1;
	


    void register(PlayerInterface player) {
        playerRing.add(player);
    }
    


	public void configurePlayers() {
		playerRing.configurePlayers();
		
	}
    

    void start() {
    	// before dealing need to remove one of each pile
    	ArrayList<Weapon> deckWeapons = new ArrayList<Weapon>(WEAPONS);
    	ArrayList<Suspect> deckSuspects = new ArrayList<Suspect>(SUSPECTS);
    	ArrayList<Room> deckRooms = new ArrayList<Room>(ROOMS);
    	
    	confidential = new Guess(deckSuspects.remove(Helper.random(deckSuspects.size())),
    			deckRooms.remove(Helper.random(deckRooms.size())),
    			deckWeapons.remove(Helper.random(deckWeapons.size())));
    	
    	
    	ArrayList<Card> deck = new ArrayList<Card>();
    	deck.addAll(deckSuspects);
    	deck.addAll(deckRooms);
    	deck.addAll(deckWeapons);
    	
    	
    	//TODO: create deck
    	
    	playerRing.dealCards(deck);
    	Guess turnResult = null;
    	Boolean gameOver = false;
    	int turnCount = 0;
    	int winnerID = -1;
    	
    	while(!gameOver && turnCount < 7) {
    		for(int i = 0;i<playerRing.numPlayers();i++) {
    			turnResult = turn(i);
    			turnCount++; // TODO: take this out
    			if(turnResult != null) {
    				if(turnResult.equals(confidential)){
    					gameOver = true;
    					winnerID = i;
    					
    					break;
    				} else {
    					playerRing.removePlayer(i);
    					i--;
    				}    				
    			}
    		}
    	}
    	
    	System.out.println("Winner is Player " + winnerID);    	
    	

    }

    Guess turn(int currentPlayer) {
        return playerRing.manageTurn(currentPlayer);


    }
    
    static void test() {
    	Tester tester = new Tester();
    }
    
    public static void main(String[] args) {
    	test();
    }





}
