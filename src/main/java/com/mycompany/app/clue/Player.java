package com.mycompany.app.clue;

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
		return new Guess(suspects.get((int)Math.random()*suspects.size()),
				rooms.get((int)Math.random()*rooms.size()),
				weapons.get((int)Math.random()*rooms.size()));
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
		if(hasCard(responseCard)) {
			String classname = responseCard.getClass().getName();
			if(classname.equals("Weapon")) {
				weapons.remove(responseCard);
			} else if (classname.equals("Room")) {
				rooms.remove(responseCard);
			} else {
				suspects.remove(responseCard);
			}
		}
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
		if((this.suspects.size() == 1 && this.suspects.get(0).equals(currentGuess.suspect))
				&& (this.rooms.size() == 1 && this.rooms.get(0).equals(currentGuess.room))
				&& (this.weapons.size() == 1 && this.weapons.get(0).equals(currentGuess.weapon))) {
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
