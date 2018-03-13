package com.mycompany.app.clue;

import java.util.ArrayList;

interface PlayerInterface {
	
	
	// 
    Guess makeGuess();
    
	Card showCard(Guess guess);
	
	// process the card that was shown
	void observeCard(Card responseCard, int responsePlayer);

	
	// Does the player have the card?
	Boolean hasCard(Card card);

	int numCards();

	void addCard(Card card);

	void observe(Guess guess, int holdingPlayer);
	void setNumPlayers(int numPlayers);
	void setPlayerID(int playerID);

	boolean openConfidential(Guess currentGuess);

	ArrayList<Card> getCards();

	int getPlayerID();

	void makeOntology(ClueOntologyManager clueOntologyManager, Game game);


}
