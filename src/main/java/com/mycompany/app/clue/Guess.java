package com.mycompany.app.clue;


public class Guess {
	Suspect suspect;
	Room room;	
	Weapon weapon;
	
	public Guess(Suspect suspect, Room room, Weapon weapon) {
		this.suspect = suspect;
		this.room = room;
		this.weapon = weapon;
	}
	
	Suspect getSuspect() {
		return suspect;
	}
	
	Room getRoom() {
		return room;
	}
	
	Weapon getWeapon() {
		return weapon;
	}
	
	

	@Override
	public String toString() {
		return suspect + ", " + room + ", " + weapon;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Guess other = (Guess) obj;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (suspect == null) {
			if (other.suspect != null)
				return false;
		} else if (!suspect.equals(other.suspect))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}
	
	
}
