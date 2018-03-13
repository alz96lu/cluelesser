package com.mycompany.app.clue;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

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
			new Suspect("Colonel Mustard"),
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
	ClueOntologyManager ontologyManager;

    PlayerRing playerRing = new PlayerRing();

	


    void register(PlayerInterface player) throws OWLOntologyCreationException {
//    	if(player.getClass().getName().contains("MachinePlayer")) {
//			player.makeOntology(ontologyManager, this);
//		}
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

    	System.out.println("Confidential file: " + confidential);
    	
    	
    	ArrayList<Card> deck = new ArrayList<Card>();
    	deck.addAll(deckSuspects);
    	deck.addAll(deckRooms);
    	deck.addAll(deckWeapons);
    	
    	
    	//TODO: create deck
    	
    	playerRing.dealCards(deck);
		playerRing.loadOntologies(confidential);
    	Guess turnResult = null;
    	Boolean gameOver = false;
    	int turnCount = 0;
    	int winnerID = -1;

    	playerRing.initialKnowledge();
		for(int i = 0; i < this.playerRing.numPlayers(); i++) {
			System.out.println("Player " + i + "'s cards:");
			System.out.println(this.playerRing.getPlayerByID(i).getCards());
		}
    	
    	while(!gameOver) {
    		for(int i = 0;i<playerRing.numPlayers();i++) {
    			turnResult = turn(i);
    			turnCount++; // TODO: take this out
    			if(turnResult != null) {
    				if(turnResult.equals(confidential)){
    					gameOver = true;
    					winnerID = i;
    					
    					break;
    				} else {
    					gameOver = true; // take out
    					winnerID = -1; // take out
    					playerRing.removePlayer(i);
    					// TODO: decriment number of players for all players
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
