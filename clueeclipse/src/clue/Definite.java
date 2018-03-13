package clue;

public class Definite {
	Has has;
	
	Definite(Has has) {
		this.has = has;
	}
	
	Card getCard() {
		return this.has.card;
	}

	public int getPlayer() {
		return this.has.playerid;
	}

	@Override
	public String toString() {
		return has.toString();
	}

	public boolean pertainsTo(Card card) {
		if(this.has.pertainsTo(card)) {
			return true;
		}
		return false;
	}

	public boolean pertainsTo(int playerID) {
		if(this.has.pertainsTo(playerID)) {
			return true;
		}
		return false;
	}
	

}
