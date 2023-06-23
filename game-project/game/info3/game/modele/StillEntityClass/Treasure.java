package info3.game.modele.StillEntityClass;

import automate.AutomateLoader;
import info3.game.modele.GameEntity;
import info3.game.modele.GameModele;
import info3.game.modele.StillEntity;
import info3.game.modele.MoveableEntityClass.EnumCannonBall;
import info3.game.modele.map.MapSection;
import info3.game.vue.avatar.LandTreasureAvatar;

public class Treasure extends StillEntity {
		 
	private MapSection m_mapSection;
	private boolean m_opened;
	
	public Treasure(MapSection mapSection, int x,int y) {
		super(x, y, 100);
		this.m_mapSection = mapSection;
		this.automate = AutomateLoader.findAutomate(GameEntity.Treasure);
		this.current_state = automate.initial_state;
		this.avatar = new LandTreasureAvatar(this);
		this.m_opened = false;
	}

	@Override
	public void die() {
		super.die();

	}

//	public boolean cell(EnumDirection d, EnumCategory c) {
//		if(d == EnumDirection.H && c == EnumCategory.T) {
//			
//			Map map = GameModele.map;
//			PiratePlayer player1 = GameModele.player1;
//			PiratePlayer player2 = GameModele.player2;
//			Tiles myTile = map.getTileUnderEntity(this.x, this.y);
//			
//			if(myTile.equals(map.getTileUnderEntity(player1.x,player1.y ))) {
//				return true;
//			}
//			
//			if(!GameModele.solo) 
//				if(myTile.equals(map.getTileUnderEntity(player2.x,player2.y)))
//					return true;
//			
//		}
//		return false;
//	}
	
	@Override
	public void power() {
		System.out.println("TRIGGER");
		GameModele.pirateBoat.addBoulet(EnumCannonBall.Stunt, m_mapSection.getSectionNumber());
	}
	
	public boolean gotPower() {
		return !this.m_opened;
	}
	

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	public void takeDamage(int damage) {
		this.m_opened = true;
	}

}
