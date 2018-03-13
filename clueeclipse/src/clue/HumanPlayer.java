package clue;

import java.util.*;

class HumanPlayer extends Player {

	Scanner scanner = new Scanner(System.in);

    public Guess makeGuess() {
    	System.out.println("Suspects: ");
    	String s = "";
    	int j;
    	for(j = 0; j < Game.SUSPECTS.size()-1;j++){
    		s += Game.SUSPECTS.get(j) + ", ";
    	}
    	s += Game.SUSPECTS.get(j);
    	System.out.println(s);
    	
    	System.out.println("Rooms: ");
    	s = "";
    	for(j = 0; j < Game.ROOMS.size()-1;j++){
    		s += Game.ROOMS.get(j) + ", ";
    	}
    	s += Game.ROOMS.get(j);
    	System.out.println(s);
    	
    	System.out.println("Weapons: ");
    	s = "";
    	for(j = 0; j < Game.WEAPONS.size()-1;j++){
    		s += Game.WEAPONS.get(j) + ", ";
    	}
    	s += Game.WEAPONS.get(j);
    	System.out.println(s);
    	
    	System.out.println("Your Cards: ");
    	s = "";
    	for(j = 0; j < this.cards.size()-1;j++){
    		s += this.cards.get(j) + ", ";
    	}
    	s += this.cards.get(j);
    	System.out.println(s);

    	boolean validSuspect = false;
    	String suspect = null;
    	while(validSuspect == false) {
    		System.out.println("What suspect would you like to guess?");
            suspect = scanner.nextLine();
            for(int i = 0; i < Game.SUSPECTS.size();i++) {
            	if(suspect.equals(Game.SUSPECTS.get(i).getName())) {
            		validSuspect = true;
            	} 
            }
            if(!validSuspect) {
            	System.out.println("Suspect not in game.");
            }
            
    		
    	}
    	boolean validRoom = false;
    	String room = null;
    	while(validRoom == false) {
    		System.out.println("What room would you like to guess?");
            room = scanner.nextLine();
            for(int i = 0; i < Game.ROOMS.size();i++) {
            	if(room.equals(Game.ROOMS.get(i).getName())) {
            		validRoom = true;
            	} 
            }
            if(!validRoom) {
            	System.out.println("Room not in game.");
            }
            
    		
    	}
    	boolean validWeapon = false;
    	String weapon = null;
    	while(validWeapon == false) {
    		System.out.println("What weapon would you like to guess?");
            weapon = scanner.nextLine();
            for(int i = 0; i < Game.WEAPONS.size();i++) {
            	if(weapon.equals(Game.WEAPONS.get(i).getName())) {
            		validWeapon = true;
            	} 
            }
            if(!validWeapon) {
            	System.out.println("Weapon not in game.");
            }
            
    		
    	}
        
    	System.out.println("Guess submitted");
        return new Guess(new Suspect(suspect), new Room(room), new Weapon(weapon));
       
    }

	@Override
	public void observeCard(Card responseCard, int responsePlayer) {
		System.out.println("Player " + responsePlayer + " shows you " + responseCard);
		super.observeCard(responseCard, responsePlayer);
	}

	@Override
	public void observe(Guess guess, int holdingPlayer) {
		// TODO Auto-generated method stub
		super.observe(guess, holdingPlayer);
		System.out.println("Player " + holdingPlayer + " showed a card.");
	}
    
    

//    Card showCard() {
//        System.out.println("What card would you like to show?");
//        String card = scanner.next();
//        return card;
//    }


}
