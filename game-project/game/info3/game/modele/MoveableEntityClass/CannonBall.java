package info3.game.modele.MoveableEntityClass;

import automate.AutomateLoader;
import automate.EnumCategory;
import automate.EnumDirection;
import info3.game.modele.Entity;
import info3.game.modele.GameEntity;
import info3.game.modele.GameModele;
import info3.game.modele.MoveableEntity;
import info3.game.modele.StillEntityClass.SeaTreasure;
import info3.game.modele.map.Tiles;
import info3.game.sound.SoundEffect;
import info3.game.sound.SoundTool;
import info3.game.vue.GameView;
import info3.game.vue.avatar.Avatar;

public abstract class CannonBall extends MoveableEntity {
	protected int endX;
	protected int endY;
	protected int startX;
	protected int startY;
	protected int damage;
	protected int range;
	protected int rateOfFire;
	protected int speedX;
	protected int speedY;
	protected int radiusX;
	protected int radiusY;
	public boolean fire;

	static final int BASIC_RANGE = 1000;
	static final int BASIC_RATE_OF_FIRE = 1;

	public Entity ennemyAimed;

	public CannonBall(int damage, int range, int rateOfFire) {
		super(0, 0, 0, 0);
		this.damage = damage;
		this.range = range;
		this.rateOfFire = rateOfFire;
		fire = false;
		startX = 500;
		startY = 500;
		this.automate = AutomateLoader.findAutomate(GameEntity.CannonBall);
		this.current_state = automate.initial_state;
		SoundTool.playSoundEffect(SoundEffect.BoatAttack, 0);
		GameModele.entities.add(this);
	}

	public void setPositions(int x, int y, int endX, int endY) {
		startX = x;
		startY = y;
		this.x = x;
		this.y = y;
		this.endX = endX;
		this.endY = endY;
		/*
		 * speedX = (endX - (GameView.screenWidth / 2)) / 50; speedY = (endY -
		 * (GameView.screenHeight / 2)) / 50;
		 */
		speedX = (int) (((endX - (GameView.screenWidth / 2)) * range) / (Math
				.sqrt(Math.pow(endX - (GameView.screenWidth / 2), 2) + Math.pow(endY - (GameView.screenHeight / 2), 2)))
				/ 100);
		speedY = (int) (((endY - (GameView.screenHeight / 2)) * range) / (Math
				.sqrt(Math.pow(endX - (GameView.screenWidth / 2), 2) + Math.pow(endY - (GameView.screenHeight / 2), 2)))
				/ 100);

	}

	public void setPositionsNoShift(int x, int y, int endX, int endY) {
		startX = x;
		startY = y;
		this.x = x;
		this.y = y;
		this.endX = endX;
		this.endY = endY;

		speedX = (int) (((endX) * range) / (Math.sqrt(Math.pow(endX, 2) + Math.pow(endY, 2))) / 100);
		speedY = (int) (((endY) * range) / (Math.sqrt(Math.pow(endX, 2) + Math.pow(endY, 2))) / 100);
		System.out.println(startX);
		System.out.println(startY);
	}

	public void fire() {
		fire = true;
	}

	@Override
	public boolean gotPower() {
		if (Math.abs(speedX) < 1 && Math.abs(speedY) < 1) {
			return false;
		} else {
			Tiles tile = GameModele.map.getTileUnderEntity(x, y);
			radiusX = Math.abs(startX - x);
			radiusY = Math.abs(startY - y);
			boolean b = fire && (radiusX < range) && (radiusY < range) && !tile.isIsland();
			b = b && tile.getTileX() > 8 && tile.getTileX() < GameModele.map.getSectionWidth() - 9;
			int border = GameModele.map.getSectionOfEntity(x, y);
			if (border == 0) {
				return b && tile.getTileY() < 47;
			} else if (border == 8) {
				return b && tile.getTileY() > 0;
			}
			return b;
		}

	}

	@Override
	public boolean cell(EnumDirection d, EnumCategory c) {
		switch (c) {
		case A:
			for (Entity s : GameModele.entities) {
				if (s instanceof Ship || s instanceof Tentacle || s instanceof SeaTreasure) {
					if (collide(this, x - speedX, y - speedY, s)) {
						ennemyAimed = s;
						return true;
					}
				}
			}

		default:
			return false;

		}
	}

	public boolean collide(MoveableEntity m, int x, int y, Entity e) { // Code propre lvl 1
		int centerx = x - m.getAvatar().getWidth() / (2 * Avatar.SCALE_IMG);
		int centery = y - m.getAvatar().getHeight() / (2 * Avatar.SCALE_IMG);

		int w = e.getAvatar().getWidth();
		int h = e.getAvatar().getHeight();

		double rayon = Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2)) / 2;
		double distance = Math.sqrt(Math.pow(e.getX() - e.getAvatar().getWidth() / (2 * Avatar.SCALE_IMG) - centerx, 2)
				+ Math.pow(e.getY() - e.getAvatar().getHeight() / (2 * Avatar.SCALE_IMG) - centery, 2));
		return distance <= rayon;
	}

	@Override
	public void moveEntity(EnumDirection direction, int speed) {
		if (fire) {
			x -= speedX;
			y -= speedY;
			startX += speedX;
			startY += speedY;
		}

	}

	protected abstract void tripleShot(int mouseX, int mouseY, BoatPlayer boatPlayer);

}