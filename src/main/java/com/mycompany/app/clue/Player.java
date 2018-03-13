package com.mycompany.app.clue;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import java.util.ArrayList;
import java.util.Iterator;

// player class does not add to knowledge base

class Player implements PlayerInterface {
	// cards: cards belonging to player
	ArrayList<Card> cards = new ArrayList<Card>();
	int playerID;
	int numPlayers;
	// cards in the game
	ArrayList<Suspect> suspects = new ArrayList<Suspect>();
	ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	ArrayList<Room> rooms = new ArrayList<Room>();
	
	public int numCards() {
		return cards.size();
	}

	public void makeOntology(ClueOntologyManager clueOntologyManager, Game game) throws OWLOntologyCreationException {

	}

	public void loadOntology(Guess confidential) {

	}

	@Override
	public Ontology getOntology() {
		return null;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public ArrayList<Suspect> getSuspects() {
		return suspects;
	}
	public ArrayList<Room> getRooms() {
		return rooms;
	}




	@Override
	public String toString() {
		String s = "";
		s += "Player " + this.playerID + ": \n";
		int i;
		for(i = 0; i < this.numCards()-1;i++) {
			s += cards.get(i).toString() + ", ";
		}
		s += cards.get(i).toString();
		return  s;
	}



	// initialize any player
	public Player() {
		this.suspects.addAll(Game.SUSPECTS);
		this.weapons.addAll(Game.WEAPONS);
		this.rooms.addAll(Game.ROOMS);

		// remove from options all the cards in hand

	}

	public void initialKnowledge() {
		for(int susit = 0; susit < suspects.size(); susit++) {
			if(hasCard(suspects.get(susit))) {
				this.suspects.remove(suspects.get(susit));
				susit--;
			}
		}

		for(int roomit = 0; roomit < rooms.size(); roomit++) {
			if(hasCard(rooms.get(roomit))) {
				this.rooms.remove(rooms.get(roomit));
				roomit--;
			}
		}

		for(int weapit = 0; weapit < weapons.size(); weapit++) {
			if(hasCard(weapons.get(weapit))) {
				this.weapons.remove(weapons.get(weapit));
				weapit--;
			}
		}
	}


	// used during dealing
	public void addCard(Card card) {
		cards.add(card);
	}

	// does the player have this card?
	public Boolean hasCard(Card card) {
		Iterator<Card> iterator = cards.iterator();
		while(iterator.hasNext()) {
			if(card.getName().equals(iterator.next().getName())) {
				return true;
			}
		}
		return false;


	}

	@Override
	public Guess makeGuess() {
		Guess guess = new Guess(suspects.get(Helper.random(suspects.size())),
				rooms.get(Helper.random(rooms.size())),
				weapons.get(Helper.random(weapons.size())));
		System.out.println("Player " + this.playerID + " made a guess: " + guess);
		return guess;
	}

	@Override
	public Card showCard(Guess guess) {
		if(hasCard(guess.getSuspect())){
			return guess.getSuspect();
		}
		if(hasCard(guess.getRoom())) {
			return guess.getRoom();
		}
		if(hasCard(guess.getWeapon())) {
			return guess.getWeapon();
		}

		return null;
	}




	@Override
	public void observeCard(Card responseCard, int responsePlayer) {

		System.out.println("Player " + responsePlayer + " showed Player " + this.playerID + " "+ responseCard);

			String classname = responseCard.getClass().getName();
			if(classname.contains("Weapon")) {
				weapons.remove(responseCard);
			} else if (classname.contains("Room")) {
				rooms.remove(responseCard);
			} else {
				suspects.remove(responseCard);
			}
	}

	static void testObserveCard() {
		Tester tester = new Tester();
		Player player = new Player();
		player.addCard(new Weapon("Knife"));
		player.addCard(new Weapon("Rope"));
		player.addCard(new Suspect("Senator Scarlet"));
		player.initialKnowledge();
		System.out.println(player.weapons);
		tester.test(player.weapons.size() == Game.WEAPONS.size()-2, "should know 2 weapons");

		Card responseCard = new Weapon("Wrench");
		player.observeCard(responseCard,1);
		System.out.println(responseCard.getClass().getName());
		player.cards.remove(new Weapon("Knife"));
		System.out.println(player.weapons);
		tester.test(player.weapons.size() == Game.WEAPONS.size()-3, "should know 3 weapons");
	}

	public static void main(String args[]) {
		testObserveCard();
	}



	@Override
	public void observe(Guess guess, int holdingPlayer) {

		// what do you do when you see a card?
		// Human Player can keep track themselves
		// Machine Player should add to knowledge base
		
	}

	@Override
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
		
	}

	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}


	@Override
	public boolean openConfidential(Guess currentGuess) {
		if((!this.hasCard(currentGuess.suspect))
				&& (!this.hasCard(currentGuess.weapon))
				&& (!this.hasCard(currentGuess.room))
		) {
			return true;
		}
		return false;
	}




	@Override
	public ArrayList<Card> getCards() {
		return new ArrayList<Card>(cards);
	}


	@Override
	public int getPlayerID() {
		return playerID;
	}

}
