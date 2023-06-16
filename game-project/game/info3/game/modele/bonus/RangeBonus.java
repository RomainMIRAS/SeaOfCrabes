package info3.game.modele.bonus;

import info3.game.modele.GameModele;
import info3.game.modele.StillEntityClass.Bonus;

public class RangeBonus extends Bonus {

	public RangeBonus(int level) {
		super(level);
	}

	@Override
	public void power() {
		GameModele.player1.addRangeCoeff(BONUS_APPLIED);
	}
}
