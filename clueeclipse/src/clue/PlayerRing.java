package clue;

import java.util.*;

class PlayerRing {
	private ArrayList<PlayerInterface> allPlayers = new ArrayList<PlayerInterface>();

	void add(PlayerInterface player) {
		player.setPlayerID(allPlayers.size());
		allPlayers.add(player);
	}

	PlayerInterface getPlayerByID(int playerID) {
		for(int i = 0; i < allPlayers.size(); i++) {
			if(allPlayers.get(i).getPlayerID() == playerID) {
				return allPlayers.get(i);
			}
		}
		return null;
	}

	int numPlayers() {
		return allPlayers.size();
	}

	int next(int PlayerID) {
		return (PlayerID + 1) % numPlayers();
	}

	Guess manageTurn(int currentPlayer) {
		System.out.println("Player " + currentPlayer + "'s turn");
		System.out.println("Player " + currentPlayer + " knows: ");
		System.out.println(allPlayers.get(currentPlayer).getWeapons());
		System.out.println(allPlayers.get(currentPlayer).getSuspects());
		System.out.println(allPlayers.get(currentPlayer).getRooms());

		Guess currentGuess = allPlayers.get(currentPlayer).makeGuess();

		Boolean cardShown = false;
		int responsePlayer = next(currentPlayer);
		Card responseCard = null;

		// iterate through all the players that are not the current player
		// end if somebody shows a card or if the player to respond is 
		// back to the current player

		while(cardShown == false && responsePlayer != currentPlayer) {
			responseCard = allPlayers.get(responsePlayer).showCard(currentGuess);
			if(responseCard != null) {
				cardShown = true;
			} else {
				responsePlayer = next(responsePlayer);
			}

		}

		// if nobody showed a card, ask the current player if they want to 
		// open the envelope

		Guess returnGuess = null;
		System.out.println(cardShown);
		if(cardShown == false) {
			if(allPlayers.get(currentPlayer).openConfidential(currentGuess)){
				returnGuess = currentGuess;
			}
		} else {
			System.out.println("Observing");
			allPlayers.get(currentPlayer).observeCard(responseCard, responsePlayer);
		}

		// if cardShown == true:
		// observe that the response player may have any of the cards guessed
		// if cardShown == false:
		// observe that the current player may have any of the cards guessed

		int observingPlayer = next(currentPlayer);  
		while(observingPlayer != currentPlayer) {
			allPlayers.get(observingPlayer).observe(currentGuess, responsePlayer);
			observingPlayer = next(observingPlayer);
		}

		return returnGuess;   
	}


	// testing

	@Override
	public String toString() {
		String s = "";
		s += "Players: \n";
		for(int i = 0; i < allPlayers.size();i++) {
			s += allPlayers.get(i).toString() + "\n";
		}
		return s;
	}

	static void testNext() {
		Tester tester = new Tester();
		PlayerRing pr = new PlayerRing();
		pr.add(new HumanPlayer());
		pr.add(new HumanPlayer());
		pr.add(new HumanPlayer());

		tester.test(pr.next(0) == 1, "0's next should be 1");
		tester.test(pr.next(1) == 2, "1's next should be 2");
		tester.test(pr.next(2) == 0, "2's next should be 3");

	}

	public void tellAll(int playerResponded) {
		// TODO Auto-generated method stub

	}

	public void dealCards(ArrayList<Card> deck) {
		int dealIndex = 0;
		while(deck.size() > 0) {
			allPlayers.get(dealIndex).addCard(deck.remove(Helper.random(deck.size())));
			dealIndex = (dealIndex + 1) % allPlayers.size(); 

		}

	}

	public void loadAxiomSets(Guess confidential) {
		for(int i = 0; i < allPlayers.size(); i++) {
			allPlayers.get(i).loadAxiomSet();
		}
	}

	static void testDealCards() {
		Tester tester = new Tester();
		PlayerRing pring = new PlayerRing();
		pring.add(new Player());
		pring.add(new Player());
		pring.add(new Player());

		tester.test(pring.numPlayers() == 3, "Number of players should be 3");


		ArrayList<Weapon> deckWeapons = new ArrayList<Weapon>(Game.WEAPONS);
		ArrayList<Suspect> deckSuspects = new ArrayList<Suspect>(Game.SUSPECTS);
		ArrayList<Room> deckRooms = new ArrayList<Room>(Game.ROOMS);

		Guess confidential = new Guess(deckSuspects.remove((int)(Helper.random(deckSuspects.size()))),
				deckRooms.remove(Helper.random(deckRooms.size())),
				deckWeapons.remove(Helper.random(deckWeapons.size())));


		ArrayList<Card> deck = new ArrayList<Card>();
		deck.addAll(deckSuspects);
		deck.addAll(deckRooms);
		deck.addAll(deckWeapons);

		int decksize = deck.size();

		pring.dealCards(deck);

		tester.test(pring.allPlayers.get(0).numCards() >= decksize / pring.numPlayers(), "error");
		tester.test(pring.allPlayers.get(0).numCards() <= (decksize / pring.numPlayers())+1, "error");
		tester.test(pring.allPlayers.get(1).numCards() >= decksize / pring.numPlayers(), "error");
		tester.test(pring.allPlayers.get(1).numCards() <= (decksize / pring.numPlayers())+1, "error");
		tester.test(pring.allPlayers.get(2).numCards() >= decksize / pring.numPlayers(), "error");
		tester.test(pring.allPlayers.get(2).numCards() <= (decksize / pring.numPlayers())+1, "error");

		System.out.println(confidential);
		System.out.println(pring);

	}

	public static void main(String[] args) {
		testNext();
		testDealCards();
	}

	public void configurePlayers() {
		for(int i = 0; i < allPlayers.size();i++) {
			allPlayers.get(i).setPlayerID(i);
			allPlayers.get(i).setNumPlayers(allPlayers.size());
		}

	}

	public void initialKnowledge() {
		for(int i = 0; i < allPlayers.size();i++) {
			allPlayers.get(i).initialKnowledge();
		}
	}

	public void removePlayer(int playerID) {
		PlayerInterface donePlayer = allPlayers.remove(playerID);
		ArrayList<Card> doneCards = donePlayer.getCards();
		for(int players = 0; players < this.numPlayers(); players++) {
			for(int cards = 0; cards < doneCards.size(); cards++) {
				allPlayers.get(players).observeCard(doneCards.get(cards), playerID);
			}
		}

	}


}
