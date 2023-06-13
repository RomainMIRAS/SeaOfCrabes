package info3.game.vue.toolkitUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// Cette classe implémente un titre à partir de la class Label.
// Le titre est toujours centré et avec la FONT2.
public class UITitle extends UILabel {

	public UITitle(int widthWindow, int heightWindow, String text, Font font, Color fg) {
		super(widthWindow, heightWindow, text, font, fg);
	}

	public void paint(Graphics g) {
		g.setFont(this.getFont());
		int labelWidth = g.getFontMetrics().stringWidth(this.getText());
		int labelHeight = g.getFontMetrics().getHeight();

		int x = (this.getPositionX() - labelWidth) / 2; // Ici X et Y servent à connaître la taille de la fenêtre
		int y = (int) (labelHeight * 1.5);

		g.setColor(getFontColor());
		g.setFont(getFont());
		g.drawString(getText(), x, y);
	}

}
