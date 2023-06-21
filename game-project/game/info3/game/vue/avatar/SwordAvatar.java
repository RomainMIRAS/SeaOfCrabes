package info3.game.vue.avatar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.modele.Entity;
import info3.game.modele.GameModele;
import info3.game.modele.Weapon;
import info3.game.vue.SpriteLoader.SpriteLoader;
import info3.game.vue.SpriteLoader.SpriteType;

public class SwordAvatar extends Avatar {

	public static int SCALE_SWORD = 5;

	BufferedImage[] m_imagesNS;
	BufferedImage[] m_imagesEW;

	private int k;

	public SwordAvatar(Entity entity) {
		super(entity);
		m_imagesEW = SpriteLoader.get(SpriteType.SwordEW);
		m_imagesNS = SpriteLoader.get(SpriteType.SwordNS);
		imageIndex = 0;
		k = 0;
		((Weapon) entity).player = GameModele.player1;
	}

	@Override
	public void tick(long elapsed) {
		if (((Weapon) entity).getAttacking()) {
			
			
			if (k == 10) {
				imageIndex = 0;
				k = 0;
				((Weapon) entity).setAttacking(false);
			}
			else if (k == 7) {
				imageIndex = 3;
			}
			else if (k == 5) {
				imageIndex = 2;
			}
			else if (k == 3) {
				imageIndex = 1;
			}
			
			k++;
		}

	}

	@Override
	public void paint(Graphics g, int width, int height) {

		int orientationX, orientationY;
		BufferedImage img;
		int iW = 0;
		int iH = 0;
		if (GameModele.solo) {
			switch (((Weapon)entity).player.facing) {
			case N:
				img = m_imagesNS[imageIndex];
				orientationX = width / 2 - ((Weapon)entity).player.getAvatar().getWidth() / 2;
				orientationY = height / 2 - ((Weapon) entity).range;
				iW = ((Weapon) entity).height;
				iH = ((Weapon) entity).range;
				break;
			case W:
				img = m_imagesEW[imageIndex + 4];
				orientationX = width / 2 - ((Weapon) entity).range;
				orientationY = (height / 2 - ((Weapon)entity).player.getAvatar().getHeight() / 2);
				iW = ((Weapon) entity).range;
				iH = ((Weapon) entity).height;
				break;
			case S:
				img = m_imagesNS[imageIndex + 4];
				orientationX = width / 2 - ((Weapon)entity).player.getAvatar().getWidth() / 2;
				orientationY = height / 2;
				iW = ((Weapon) entity).height;
				iH = ((Weapon) entity).range;
				break;
			case E:
				orientationX = width / 2;
				orientationY = height / 2 - ((Weapon)entity).player.getAvatar().getHeight() / 2;
				img = m_imagesEW[imageIndex];
				iW = ((Weapon) entity).range;
				iH = ((Weapon) entity).height;
				break;
			default:
				orientationX = 0;
				orientationY = 0;
				img = m_imagesEW[0];
				break;
			}
		}
		else {
			switch (((Weapon)entity).player.facing) {
			case N:
				img = m_imagesNS[imageIndex];
				//orientationX = width / 2 - ((Weapon)entity).player.getAvatar().getWidth() / 2;
				orientationX = (((Weapon)entity).player.getX() + ((width-(GameModele.player1.getX()+GameModele.player2.getX()))/2)) - (((Weapon)entity).player.getAvatar().getWidth()/2);
				
				//orientationY = height / 2 - ((Weapon) entity).range;
				orientationY = (((Weapon)entity).player.getY() + ((height-(GameModele.player1.getY()+GameModele.player2.getY()))/2)) - ((Weapon) entity).range;
				
				iW = ((Weapon) entity).height;
				iH = ((Weapon) entity).range;
				break;
			case W:
				img = m_imagesEW[imageIndex + 4];
				//orientationX = width / 2 - ((Weapon) entity).range;
				orientationX = (((Weapon)entity).player.getX() + ((width-(GameModele.player1.getX()+GameModele.player2.getX()))/2)) - ((Weapon) entity).range;
				
				//orientationY = (height / 2 - ((Weapon)entity).player.getAvatar().getHeight() / 2);
				orientationY = (((Weapon)entity).player.getY() + ((height-(GameModele.player1.getY()+GameModele.player2.getY()))/2)) - ((Weapon)entity).player.getAvatar().getHeight() / 2;
				
				
				iW = ((Weapon) entity).range;
				iH = ((Weapon) entity).height;
				break;
			case S:
				img = m_imagesNS[imageIndex + 4];
				//orientationX = width / 2 - ((Weapon)entity).player.getAvatar().getWidth() / 2;
				orientationX = (((Weapon)entity).player.getX() + ((width-(GameModele.player1.getX()+GameModele.player2.getX()))/2)) - ((Weapon)entity).player.getAvatar().getWidth() / 2;
				
				
				//orientationY = height / 2;
				orientationY = (((Weapon)entity).player.getY() + ((height-(GameModele.player1.getY()+GameModele.player2.getY()))/2));
				
				
				iW = ((Weapon) entity).height;
				iH = ((Weapon) entity).range;
				break;
			case E:
				//orientationX = width / 2;
				orientationX = (((Weapon)entity).player.getX() + ((width-(GameModele.player1.getX()+GameModele.player2.getX()))/2));
				
				//orientationY = height / 2 - ((Weapon)entity).player.getAvatar().getHeight() / 2;
				orientationY = (((Weapon)entity).player.getY() + ((height-(GameModele.player1.getY()+GameModele.player2.getY()))/2)) - ((Weapon)entity).player.getAvatar().getHeight() / 2;
				
				
				img = m_imagesEW[imageIndex];
				iW = ((Weapon) entity).range;
				iH = ((Weapon) entity).height;
				break;
			default:
				orientationX = 0;
				orientationY = 0;
				img = m_imagesEW[0];
				break;
			}
		}
		
		//System.out.println(((Weapon) entity).getAttacking());

		if (((Weapon) entity).getAttacking())
			g.drawImage(img, orientationX, orientationY, iW, iH, null);
		/*
		 * g.setColor(Color.white); // East g.fillRect(width / 2, (height/2 -
		 * GameModele.player1.getAvatar().getHeight()/2), ((Weapon) entity).range,
		 * ((Weapon) entity).height); // West g.setColor(Color.red); g.fillRect(width /
		 * 2 - ((Weapon) entity).range, (height/2 -
		 * GameModele.player1.getAvatar().getHeight()/2), ((Weapon) entity).range,
		 * ((Weapon) entity).height);
		 * 
		 * // North g.setColor(Color.yellow); g.fillRect(width / 2 -
		 * GameModele.player1.getAvatar().getWidth()/2, height/2, ((Weapon)
		 * entity).width, ((Weapon) entity).range); // South g.setColor(Color.blue);
		 * g.fillRect(width / 2 - GameModele.player1.getAvatar().getWidth()/2, height/2
		 * - ((Weapon) entity).range, ((Weapon) entity).width, ((Weapon) entity).range);
		 */

		/*
		 * if (((Weapon) entity).getAttacking()) { g.setColor(Color.white);
		 * g.fillRect(-((Weapon) entity).tempX + GameModele.getCurrentPlayerX() + width
		 * / 2, -((Weapon) entity).tempY + GameModele.getCurrentPlayerY() + height / 2,
		 * ((Weapon) entity).width, ((Weapon) entity).height);
		 * System.out.println(((Weapon) entity).tempX + " " + ((Weapon) entity).tempY +
		 * " " + ((Weapon) entity).width + " " + ((Weapon) entity).height); }
		 */
		/*
		 * if (!GameModele.solo) { g.drawImage(img, -entity.getX() +
		 * GameModele.getCurrentPlayerX() + width / 2, -entity.getY() +
		 * GameModele.player2.getY() + height / 2, width_painted, heigth_painted, null);
		 * }
		 */
	}

}
