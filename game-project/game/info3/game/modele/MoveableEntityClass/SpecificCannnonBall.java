package info3.game.modele.MoveableEntityClass;

import automate.AutomateLoader;
import automate.EnumCategory;
import automate.EnumDirection;
import info3.game.modele.GameEntity;
import info3.game.vue.avatar.BasicCannonBallAvatar;

public class SpecificCannnonBall extends CannonBall {
	int amount;
	public SpecificCannnonBall(int damage,int range, int rateOfFire) {
		super(damage,range,rateOfFire);
		this.amount = 1;
		setAvatar(new BasicCannonBallAvatar(this));
		this.automate = AutomateLoader.findAutomate(GameEntity.CannonBall);
		this.current_state = automate.initial_state;
	}
	
	public void addBullet() {
		this.amount++;
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hit(EnumDirection d, EnumCategory c) {
		// TODO Auto-generated method stub
		
	}
}
