package clue;


import java.util.ArrayList;

interface PlayerInterface {
	
	
	// 
    Guess makeGuess();

    void loadAxiomSet();

    public AxiomSet getAxiomSet();
    
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
	void initialKnowledge();

	boolean openConfidential(Guess currentGuess);

	ArrayList<Card> getCards();

	int getPlayerID();

	ArrayList<Weapon> getWeapons();
	ArrayList<Suspect> getSuspects();
	ArrayList<Room> getRooms();


}
