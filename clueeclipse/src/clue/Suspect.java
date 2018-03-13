package clue;


class Suspect implements Card {
	@Override
	public String toString() {
		return name + " (S)";
	}

	String name;

	public Suspect(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Suspect other = (Suspect) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

	
	

}
