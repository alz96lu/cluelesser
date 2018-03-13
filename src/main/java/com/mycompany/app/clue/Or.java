package clue;

import java.util.ArrayList;

public class Or {
	ArrayList<Has> possibleTruths = new ArrayList<Has>();

	public Or(Has has) {
		possibleTruths.add(has);
	}

	public Or(Has has1, Has has2) {
		possibleTruths.add(has1);
		possibleTruths.add(has2);
	}

	public Or(Has has1, Has has2, Has has3) {
		possibleTruths.add(has1);
		possibleTruths.add(has2);
		possibleTruths.add(has3);
	}

	Or(ArrayList<Has> hasList) {
		possibleTruths.addAll(hasList);
	}

	void add(Has has) {
		possibleTruths.add(has);
	}

	boolean pertainsTo(Card card) {
		for (int i = 0; i < possibleTruths.size(); i++) {
			if (possibleTruths.get(i).pertainsTo(card)) {
				return true;
			}
		}
		return false;
	}

	int size() {
		return possibleTruths.size();
	}

	boolean pertainsTo(int playerID) {
		for (int i = 0; i < possibleTruths.size(); i++) {
			if (possibleTruths.get(i).pertainsTo(playerID)) {
				return true;
			}
		}
		return false;
	}

	boolean contains(Has has) {
		return possibleTruths.contains(has);
	}

	@Override
	public String toString() {
		String s = "";
		int i;
		if (possibleTruths.size() > 0) {
			for (i = 0; i < possibleTruths.size() - 1; i++) {
				s += possibleTruths.get(i) + " OR ";
			}
			s += possibleTruths.get(i);
		}

		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Or other = (Or) obj;
		if (possibleTruths == null) {
			if (other.possibleTruths != null)
				return false;
		} else if (!possibleTruths.equals(other.possibleTruths))
			return false;
		return true;
	}

	static void test() {
		Tester tester = new Tester();
		Or orr = new Or(new Has(new Weapon("Knife"), 1), new Has(new Suspect("Senator Scarlet"), 1),
				new Has(new Room("Hall"), 1));
		tester.test(orr.pertainsTo(new Weapon("Knife")), "Does pertain to knife");
		tester.test(!orr.pertainsTo(new Weapon("Rope")), "Does not pertain to rope");
		tester.test(orr.size() == 3, "size should be three");
		System.out.println(orr);

		Or orr2 = new Or(new Has(new Weapon("Knife"), 1), new Has(new Room("Hall"), 1),
				new Has(new Suspect("Professor Plum"), 1));

		System.out.println(orr2);
		tester.test(orr.possibleTruths.contains(new Has(new Weapon("Knife"), 1)), "should have");

	}

	public static void main(String args[]) {
		test();
	}

	Has get(int i) {
		return possibleTruths.get(i);
	}

	// we know that the first is true - are any of the remainders necessarily
	// untrue?
	public void resolve(Definite d) {
		for (int i = 0; i < possibleTruths.size(); i++) {
			if (!possibleTruths.get(i).canBeTrue(d)) {
				possibleTruths.remove(i);
			}
		}

	}

	public void remove(Has has) {
		possibleTruths.remove(has);
	}

}
