package clue;

import java.util.ArrayList;

public class Has {
	Card card;
	int playerid;
	
	public Has(Card card, int playerid) {
		this.card = card;
		this.playerid = playerid;
	}
	
	boolean pertainsTo(Card card) {
		if(this.card.equals(card)) {
			return true;
		}
		return false;
	}
	
	boolean pertainsTo(int playerid) {
		if(this.playerid == playerid) {
			return true;
		}
		return false;
	}
	
	boolean canBeTrue(Definite def) {
		if(def.getCard().equals(card) && (def.getPlayer() != this.playerid)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Player " + playerid + " has " + card;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Has other = (Has) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		if (playerid != other.playerid)
			return false;
		return true;
	}

	public boolean canBeTrue(ArrayList<Or> ors) {
		// can modify these ors. 
		// pretend has is definite
		ArrayList<Definite> defs = new ArrayList<Definite>();
		defs.add(new Definite(this));
		
		boolean orsunchanged = false;
		
//		while(orsunchanged == false) {
			ArrayList<Or>oldOrs = new ArrayList<Or>(ors);
			for(int orit = 0; orit < ors.size();orit++) {
				
				Or testOr = ors.get(orit);
				
				System.out.println("Defs: " + defs);
				System.out.println("Ors: "+ ors);
				System.out.println("TestOr: " + testOr);
				for(int defsit = 0; defsit < defs.size(); defsit++) {
					testOr.resolve(defs.get(defsit));
				}
				if(testOr.size() == 0) {
					return false;
				}
				System.out.println("Resolved testOr: " + testOr);
				if(testOr.size() == 1) {
					System.out.println("Added " +  new Definite(testOr.get(0)) + " to definite");
					System.out.println(ors.get(orit));
					System.out.println("REmoved: " + ors.remove(ors.get(orit)));
					orit--;
					defs.add(new Definite(testOr.get(0)));
				} 
				ors.set(orit, testOr);
			}
//			if(oldOrs.equals(ors)) {
//				orsunchanged = true;
//			}
//		}
		
		
		
		return true;
	}
	
	static void testCanBeTrue() {
		ArrayList<Or> ors = new ArrayList<Or>();
		Tester t = new Tester();
		Has has = new Has(new Suspect("Miss Peacock"),1);
		ors.add(new Or(new Has(new Suspect("Miss Peacock"),2),
				new Has(new Weapon("Wrench"),2)));
		ors.add(new Or(new Has(new Suspect("Miss Peacock"),3),
				new Has(new Weapon("Wrench"),3)));
		t.test(has.canBeTrue(ors) == false, "cannot be true 1");
	}
	
	static void testCanBeTrue2() {
		
		ArrayList<Or> ors = new ArrayList<Or>();
		Tester t = new Tester();
		
		Has has = new Has(new Suspect("Miss Peacock"),4);
		ors.add(new Or(new Has(new Suspect("Miss Peacock"),2),
				new Has(new Room("Hall"),2),
				new Has(new Weapon("Wrench"),2)));
		ors.add(new Or(new Has(new Suspect("Miss Peacock"),3),
				new Has(new Room("Hall"),3)));
		ors.add(new Or(new Has(new Suspect("Miss Peacock"),1),
				new Has(new Weapon("Wrench"),1)));
		t.test(has.canBeTrue(ors) == false, "cannot be true 2");
	}
	
	
	public static void main(String args[]) {
//		testCanBeTrue();
		testCanBeTrue2();
	}
	
	
	
}
