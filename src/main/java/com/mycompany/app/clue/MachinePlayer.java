package clue;

import java.util.ArrayList;

class MachinePlayer extends Player {
    AxiomSet reasoner;

    public AxiomSet getAxiomSet() {
        return reasoner;
    }

    public MachinePlayer() {
    }

    @Override
    public void observeCard(Card responseCard, int responsePlayer) {
        reasoner.addAndResolve(new Definite(new Has(responseCard, responsePlayer)));
    }
    
    @Override
	public void loadAxiomSet() {
    		this.reasoner = new AxiomSet(playerID, cards);
	}

    @Override
    public void observe(Guess guess, int holdingPlayer) {
    		if(holdingPlayer != playerID) {
    			reasoner.addAndResolve(new Or(new Has(guess.suspect,holdingPlayer),
        				new Has(guess.weapon,holdingPlayer),
        				new Has(guess.room,holdingPlayer)));
    		}
    		
    }

    @Override
    public boolean openConfidential(Guess currentGuess) {
        if(!reasoner.cardKnown(currentGuess.weapon) 
        		&& !reasoner.cardKnown(currentGuess.room) 
        		&& !reasoner.cardKnown(currentGuess.suspect)) {
        		return true;
        }
        return false;
    }

    @Override
    public Card showCard(Guess guess) {
    		ArrayList<Card> myCards = reasoner.playerCards(this.playerID);
        if(myCards.contains(guess.getRoom())) {
            return guess.getRoom();
        }
        if(myCards.contains(guess.getSuspect())) {
            return guess.getSuspect();
        }
        if(myCards.contains(guess.getWeapon())) {
            return guess.getWeapon();
        }
        return null;
    }

    public void loadReasoner() {
         this.reasoner = new AxiomSet(this.playerID,this.cards);
    }

    public Guess makeGuess() {
        Guess guess = reasoner.randomGuess();
        System.out.println("Player " + this.playerID + " made a guess: " + guess);
        return guess;
    }




}
