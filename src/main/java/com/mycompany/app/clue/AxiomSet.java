package clue;

import java.util.ArrayList;

public class AxiomSet {
	ArrayList<Or> ors = new ArrayList<Or>();
	ArrayList<Definite> defs = new ArrayList<Definite>();
	
	ArrayList<Weapon> weapons= new ArrayList<Weapon>();
    ArrayList<Room> rooms = new ArrayList<Room>();
    ArrayList<Suspect> suspects = new ArrayList<Suspect>();	
    
    void addToDefs(Definite d) {
    		if(!defs.contains(d)) {
    			defs.add(d);
    			ruleOut(d);
    		}
    		
    }
	
	public AxiomSet(int playerID, ArrayList<Card> cards) {
		weapons.addAll(Game.WEAPONS);
		rooms.addAll(Game.ROOMS);
		suspects.addAll(Game.SUSPECTS);
		
		for(int i = 0; i < cards.size();i++) {
			addToDefs(new Definite(new Has(cards.get(i),playerID)));
		}

	}

	private void ruleOut(Definite definite) {
		Card card = definite.getCard();
		if(card.getClass().getName().contains("Weapon")) {
			weapons.remove(card);
		} else if(card.getClass().getName().contains("Room")) {
			rooms.remove(card);
		} else {
			suspects.remove(card);
		}
		
	}

	@Override
	public String toString() {
		String s = "";
		s += "AxiomSet: \n";
		for(int i = 0; i < ors.size(); i++) {
			s += ors.get(i).toString() + "\n";
		}
		for(int i = 0; i < defs.size(); i++) {
			s+= defs.get(i).toString() + "\n";
		}
		return s;
	}


	private void add(Or or) {
		ors.add(or);
	}
	
	ArrayList<Or> pertainingOrs(int playerID) {
		ArrayList<Or> pert = new ArrayList<Or>();
		for(int i = 0; i < ors.size(); i++) {
			if(ors.get(i).pertainsTo(playerID)) {
				pert.add(ors.get(i));
			}
		}
		return pert;
	}
	
	ArrayList<Or> pertainingOrs(Card card) {
		ArrayList<Or> pert = new ArrayList<Or>();
		for(int i = 0; i < ors.size(); i++) {
			if(ors.get(i).pertainsTo(card)) {
				pert.add(ors.get(i));
			}
		}
		return pert;
	}
	
	ArrayList<Definite> pertainingDefs(Card card) {
		ArrayList<Definite> pert = new ArrayList<Definite>();
		for(int i = 0; i < defs.size(); i++) {
			if(defs.get(i).pertainsTo(card)) {
				pert.add(defs.get(i));
			}
		}
		return pert;
	}
	
	ArrayList<Definite> pertainingDefs(int playerID) {
		ArrayList<Definite> pert = new ArrayList<Definite>();
		for(int i = 0; i < defs.size(); i++) {
			if(defs.get(i).pertainsTo(playerID)) {
				pert.add(defs.get(i));
			}
		}
		return pert;
	}
	
	
	ArrayList<Card> playerCards(int playerID) {
		ArrayList<Card> mine = new ArrayList<Card>();
		ArrayList<Definite> mydefs = pertainingDefs(playerID);
		for(int i = 0; i < mydefs.size();i++) {
			mine.add(mydefs.get(i).getCard());
		}
		return mine;
	}
	
	boolean cardKnown(Card card) {
		if(this.pertainingDefs(card).size() == 0) {
			return false;
		}
		return true;
	}
	
	// resolves a definite statement with all the ors, and adds it to definite list
	void addAndResolve(Definite d) {
		ArrayList<Or> pertOrs = pertainingOrs(d.has.card);
		for(int axiomit = 0; axiomit < pertOrs.size();axiomit++) {
			pertOrs.get(axiomit).resolve(d);
			if(pertOrs.get(axiomit).size() == 1) {
				ors.remove(pertOrs.get(axiomit));
				addAndResolve(new Definite(pertOrs.get(axiomit).get(0)));
			}
		}
		addToDefs(d);
		
	}
	
	// returns true if a has relationship can be true, false otherwise
	boolean canBeTrue(Has has) {
		// if has conflicts with any of the defs, rule it out
		for(int defit = 0; defit < defs.size(); defit++) {
			if(!has.canBeTrue(defs.get(defit))) {
				return false;
			}
		}
		
		// if has ruins the or statements, rule it out
//		
//		if(!has.canBeTrue(new ArrayList<Or>(ors))) {
//			return false;
//		}
		
		
		
		
		return true;
	}
	
	void addAndResolve(Or or) {
		// check all of them: can they be true with the defs?
		for(int hasit = 0; hasit < or.size(); hasit++) {
			if(!canBeTrue(or.get(hasit))) {
				or.remove(or.get(hasit));
				hasit--;
			}
		}
			
		
		
		if(or.size() == 1) {
			addAndResolve(new Definite(or.get(0)));
		} else {
			ors.add(or);
		}
		
	}
	
	int cardsPlayer(Card card) {
		for(int i = 0; i < defs.size(); i++) {
			if(defs.get(i).getCard().equals(card)) {
				return defs.get(i).getPlayer();
			}
		}
		return -1;
		
	}
	
	static void testConstructor() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Suspect("Senator Scarlet"));
		cards.add(new Suspect("Colonel Mustard"));
		cards.add(new Weapon("Wrench"));
		cards.add(new Room("Hall"));

		AxiomSet ax = new AxiomSet(0, cards);
		Tester t = new Tester();
		t.test(ax.playerCards(0).contains(new Suspect("Senator Scarlet")),"should contain scarlet");
		t.test(ax.playerCards(0).contains(new Suspect("Colonel Mustard")),"should contain mustard");
		t.test(ax.playerCards(0).contains(new Weapon("Wrench")),"should contain wrench");
		t.test(ax.playerCards(0).contains(new Room("Hall")),"should contain hall");
		
		t.test(!ax.suspects.contains(new Suspect("Senator Scarlet")), "should not have Scarlet");
		t.test(!ax.suspects.contains(new Suspect("Colonel Mustard")), "should not have mustard");
		t.test(!ax.weapons.contains(new Weapon("Wrench")), "should not have wrench");
		t.test(!ax.rooms.contains(new Room("Hall")), "should not have hall");

		
//		System.out.println(ax);
//		System.out.println(ax.weapons);
//		System.out.println(ax.suspects);
//		System.out.println(ax.rooms);
		
//		System.out.println(ax.pertainingDefs(0));
		t.test(ax.pertainingDefs(0).size() ==4,"should have 4 cards related to player 0");
		t.assess();



		
	}
	
	Guess randomGuess() {
		return new Guess(
				suspects.get(Helper.random(suspects.size())),
				rooms.get(Helper.random(rooms.size())),
				weapons.get(Helper.random(weapons.size()))
				);
	}
	
	static void test() {
		AxiomSet ax = new AxiomSet(5, new ArrayList<Card>());
//		System.out.println(ax);
		Tester t = new Tester();
		
		ax.add(new Or(new Has(new Weapon("Knife"),1),
				new Has(new Room("Hall"),1),
				new Has(new Suspect("Professor Plum"),1))
				);
//		System.out.println(ax);
		Definite d = new Definite(new Has(new Weapon("Knife"),2));
		ax.addAndResolve(d);
//		System.out.println(ax);
		t.test(!ax.ors.get(0).contains(new Has(new Weapon("Knife"),1)), "should not have knife");
		ax.addAndResolve(new Definite(new Has(new Room("Hall"),2)));
//		System.out.println(ax);
		t.test(ax.defs.size() == 3, "all 3 are added");
		
		ax.add(new Or(new Has(new Weapon("Rope"),1),
				new Has(new Room("Ballroom"),1)));
		ax.add(new Or(new Has(new Suspect("Senator Scarlet"),2),
				new Has(new Room("Ballroom"),2)));
		t.test(ax.cardsPlayer(new Suspect("Senator Scarlet")) <0, "doesn't know who has Senator Scarlet");

		ax.addAndResolve(new Definite(new Has(new Room("Ballroom"),3)));
		t.test(ax.cardsPlayer(new Suspect("Professor Plum")) == 1, "knows Professor Plum belongs to player 1");
		t.test(ax.cardsPlayer(new Suspect("Colonel Mustard")) < 0, "doesn't know who has Colonel Mustard");
		
		ax.addAndResolve(new Or(new Has(new Room("Ballroom"),1),
				new Has(new Suspect("Justice Peacock"),2)));
		t.test(ax.cardsPlayer(new Suspect("Justice Peacock")) == 2, "should know peacock belongs to 2");
//		System.out.println(ax);
		t.assess();

	}
	
	static void testcomplicated() {
		AxiomSet ax = new AxiomSet(5, new ArrayList<Card>());
		Tester t = new Tester();
		ax.add(new Or(new Has(new Weapon("Knife"),1),
				new Has(new Suspect("Professor Plum"),1)));
		ax.add(new Or(new Has(new Weapon("Knife"),2),
				new Has(new Suspect("Professor Plum"),2)));
		ax.addAndResolve(new Or(new Has(new Weapon("Knife"),3),
				new Has(new Suspect("Professor Plum"),3),
				new Has(new Room("Hall"),3)));
		System.out.println(ax);
		t.test(ax.defs.contains(new Definite(new Has(new Room("Hall"),3))), "should know that the third is correct");
		ax.addAndResolve(new Definite(new Has(new Weapon("Knife"),2)));
		t.test(ax.defs.contains(new Definite(new Has(new Weapon("Knife"),2))), "shoud know that 3 has knife");
		t.test(ax.defs.contains(new Definite(new Has(new Suspect("Professor Plum"),1))), "shoud know that 1 has professor plum");
	}
	
	public static void main(String[] args) {
		test();
		testConstructor();
//		testcomplicated();
	}

}
