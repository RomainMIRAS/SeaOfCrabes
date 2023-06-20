package info3.game;

public class Score {

	private int heures;
	private int minutes;
	private int secondes;

	public Score(int h, int m, int s) {
		heures = h;
		minutes = m;
		secondes = s;
	}

	public Score(String[] s) {
		heures = Integer.valueOf(s[0]);
		minutes = Integer.valueOf(s[1]);
		secondes = Integer.valueOf(s[2]);
	}

	public int getHeures() {
		return heures;
	}

	public void setHeures(int heures) {
		this.heures = heures;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSecondes() {
		return secondes;
	}

	public void setSecondes(int secondes) {
		this.secondes = secondes;
	}

}