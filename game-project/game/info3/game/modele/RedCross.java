package info3.game.modele;

import info3.game.modele.bonus.AttackSpeedbonus;
import info3.game.modele.map.MapSection;

public class RedCross extends StillEntity {
	private MapSection section;

	public RedCross(MapSection section) {
		super();
		this.section = section;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	public void die() {
		// TODO Auto-generated method stub
	}
	/*
	 * Si le joueur touche la croix, elle disparait et laisse place à son trésor
	 */
	public void interact() {
		// TODO
	}
	
	
	@Override
	public boolean gotPower() {
		return this.section.getCrabLair().getNbCrabs() != 0;
	}
	
	public  Treasure Egg () {
		
		AttackSpeedbonus bonus = new AttackSpeedbonus(1);
		AttackSpeedbonus malus = new AttackSpeedbonus (1);
		Treasure treasure = new Treasure (bonus,malus);
		treasure.setX(this.getX());
		treasure.setY(this.getY());
		return treasure;
	}
}
