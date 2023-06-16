package info3.game.modele;

public class Treasure extends StillEntity {
	
	//Type Object à modifier lorsque la classe bonus/malus sera crée
	 
	protected Bonus bonus;
	protected Bonus malus; 

	public Treasure(Bonus bonus, Bonus malus) {
		super();
		this.bonus = bonus;
		this.malus = malus;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	/*
	 * @return bonus ou malus en fonction du choix du joueur
	 */
	public Object open() {
		// TODO
		return bonus;

	}

	public Bonus getBonus() {
		return this.bonus;
	}

	public Bonus getMalus() {
		return this.malus;
	}
	
	public void setBonus(Bonus bonus) {
		this.bonus = bonus;
	}
	
	public void setMalus (Bonus malus) {
		this.malus = malus;
	}
	
	
	public void Egg () {	
		
	}
	
	

}
