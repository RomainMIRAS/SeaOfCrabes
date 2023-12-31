package info3.game.modele.map;

import java.util.Random;

import info3.game.modele.GameModele;
import info3.game.vue.GameView;
import info3.game.vue.avatar.MapRepresentation;
import info3.game.vue.avatar.MiniMap;
import info3.game.vue.avatar.SectionTitle;

/*
 * This class contain a reprensation of the map section by section and the offset of each tiles to create the wave effect
 */
public class Map {
	private double[][] wave; // The offset of each tiles of the map to create a wave effect
	private MapSection[] map; // The map itself composed of section

	private int sectionWidth; // The width of a section
	private int sectionHeight; // The height of a section

	private int nbSection; // The number of section

	private int seed; // The seed of the map (two map generate with the same seed are the same)
	private Random rand; // The random generator created with the seed

	private MapRepresentation mapRepres; // The graphic representation of the map

	private MiniMap miniMap; // The graphic representation of the map

	private SectionTitle title;

	private int tileWidth;
	private int tileHeight;

	private double determinant;

	// Optimisation variable (to cut down the amount of calcul each tick)
	private int tileWidthHalf;
	private int tileHeightForth;
	private double tileHeightForthDotDet;
	private double tileWidthHalfDotDet;

	/*
	 * @param seed : the seed of the map
	 * 
	 * @param nbSection : the number of section in the map
	 * 
	 * @param sectionWidth and sectionHeight : the dimension of a section
	 * 
	 */
	public Map(int seed, int nbSection, int sectionWidth, int sectionHeight) throws Exception {
		this.seed = seed;
		this.rand = new Random(this.seed);

		this.sectionHeight = sectionHeight;
		this.sectionWidth = sectionWidth;
		this.nbSection = nbSection;

		this.map = new MapSection[this.nbSection];

		this.wave = new double[this.sectionHeight * this.nbSection][this.sectionWidth];

		generateBaseMap();

		generateWave();

		this.title = new SectionTitle();

		this.mapRepres = new MapRepresentation(this);

		this.miniMap = new MiniMap(this, GameModele.seaEnnemie);
	}

	/*
	 * @param seed : the seed of the map
	 * 
	 * @param nbSection : the number of section in the map
	 * 
	 * @param sectionWidth and sectionHeight : the dimension of a section
	 * 
	 */
	public Map(int seed, int nbSection) throws Exception {

		if ((nbSection - 10) % 3 != 0) {
			throw new Exception("Number of section not OK (must be 10 + a multiple of 3 (10, 13, 16, 19, 22...))");
		}

		this.seed = seed;
		this.rand = new Random(this.seed);

		this.sectionHeight = 48;
		this.sectionWidth = 128;
		this.nbSection = nbSection;

		this.map = new MapSection[this.nbSection];

		this.wave = new double[this.sectionHeight * this.nbSection][this.sectionWidth];

		if (this.nbSection == 10) {
			generateMapClassic();
		} else {
			generateMap();
		}

		generateWave();

		this.mapRepres = new MapRepresentation(this);

		this.miniMap = new MiniMap(this, GameModele.seaEnnemie);

		this.title = new SectionTitle();
	}

	/*
	 * Generate a map based on the seed and the section parameters
	 */
	public void generateBaseMap() throws Exception {
		for (int i = 0; i < this.nbSection; i++) {
			this.map[i] = new MapSection(EnumSectionType.CALM_SEA, this.sectionWidth, this.sectionHeight, this.rand, i);
		}
	}

	public void openKraken() {
		this.map[this.nbSection - 3].addTransitionCrabToKraken();
		this.map[this.nbSection - 2].openKraken();
	}

	/*
	 * Generate a map based on the seed and the section parameters
	 */
	public void generateMapClassic() throws Exception {
		this.map[0] = new MapSection(EnumSectionType.HARBOR, this.sectionWidth, this.sectionHeight, this.rand, 0);

		this.map[1] = new MapSection(EnumSectionType.CALM_SEA, this.sectionWidth, this.sectionHeight, this.rand, 1);
		this.map[2] = new MapSection(EnumSectionType.CALM_SEA_TO_STORMY_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, 2);

		this.map[3] = new MapSection(EnumSectionType.STORMY_SEA_FROM_CALM_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, 3);
		this.map[4] = new MapSection(EnumSectionType.STORMY_SEA_TO_RAGING_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, 4);

		this.map[5] = new MapSection(EnumSectionType.RAGING_SEA_FROM_STORMY_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, 5);
		this.map[6] = new MapSection(EnumSectionType.RAGING_SEA, this.sectionWidth, this.sectionHeight, this.rand, 6);

		this.map[7] = new MapSection(EnumSectionType.CRAB_KING_SEA, this.sectionWidth, this.sectionHeight, this.rand,
				7);

		this.map[8] = new MapSection(EnumSectionType.KRAKEN_SEA, this.sectionWidth, this.sectionHeight, this.rand, 8);

		this.map[9] = new MapSection(EnumSectionType.MOUTAIN, this.sectionWidth, this.sectionHeight, this.rand,
				this.map[8].getMountainHeight(), 9);
	}

	/*
	 * Generate a map based on the seed and the section parameters
	 */
	public void generateMap() throws Exception {

		int currentSection = 0;

		this.map[currentSection] = new MapSection(EnumSectionType.HARBOR, this.sectionWidth, this.sectionHeight,
				this.rand, currentSection);
		currentSection++;

		this.map[currentSection] = new MapSection(EnumSectionType.CALM_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, currentSection);
		currentSection++;

		for (int i = 0; i < (this.nbSection - 10) / 3; i++) {
			this.map[currentSection] = new MapSection(EnumSectionType.CALM_SEA, this.sectionWidth, this.sectionHeight,
					this.rand, currentSection);
			currentSection++;
		}

		this.map[currentSection] = new MapSection(EnumSectionType.CALM_SEA_TO_STORMY_SEA, this.sectionWidth,
				this.sectionHeight, this.rand, currentSection);
		currentSection++;

		this.map[currentSection] = new MapSection(EnumSectionType.STORMY_SEA_FROM_CALM_SEA, this.sectionWidth,
				this.sectionHeight, this.rand, currentSection);
		currentSection++;

		for (int i = 0; i < (this.nbSection - 10) / 3; i++) {
			this.map[currentSection] = new MapSection(EnumSectionType.STORMY_SEA, this.sectionWidth, this.sectionHeight,
					this.rand, currentSection);
			currentSection++;
		}

		this.map[currentSection] = new MapSection(EnumSectionType.STORMY_SEA_TO_RAGING_SEA, this.sectionWidth,
				this.sectionHeight, this.rand, currentSection);
		currentSection++;

		this.map[currentSection] = new MapSection(EnumSectionType.RAGING_SEA_FROM_STORMY_SEA, this.sectionWidth,
				this.sectionHeight, this.rand, currentSection);
		currentSection++;
		this.map[currentSection] = new MapSection(EnumSectionType.RAGING_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, currentSection);
		currentSection++;
		for (int i = 0; i < (this.nbSection - 10) / 3; i++) {
			this.map[currentSection] = new MapSection(EnumSectionType.RAGING_SEA, this.sectionWidth, this.sectionHeight,
					this.rand, currentSection);
			currentSection++;
		}

		this.map[currentSection] = new MapSection(EnumSectionType.CRAB_KING_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, currentSection);
		currentSection++;

		this.map[currentSection] = new MapSection(EnumSectionType.KRAKEN_SEA, this.sectionWidth, this.sectionHeight,
				this.rand, currentSection);
		currentSection++;

		this.map[currentSection] = new MapSection(EnumSectionType.MOUTAIN, this.sectionWidth, this.sectionHeight,
				this.rand, this.map[currentSection - 1].getMountainHeight(), currentSection);

	}

	public void setImageSize(int width, int height) {
		this.tileWidth = width;
		this.tileHeight = height;
		this.determinant = this.determinant();
		this.initOptimisationVariable();
	}

	public EnumSectionType getSectionType(int section) {
		switch (this.map[section].getSeaType()) {
		case HARBOR:
			return EnumSectionType.HARBOR;
		case CALM_SEA:
		case CALM_SEA_TO_STORMY_SEA:
			return EnumSectionType.CALM_SEA;
		case STORMY_SEA_FROM_CALM_SEA:
		case STORMY_SEA:
		case STORMY_SEA_TO_RAGING_SEA:
			return EnumSectionType.STORMY_SEA;
		case RAGING_SEA:
		case RAGING_SEA_FROM_STORMY_SEA:
			return EnumSectionType.RAGING_SEA;
		case CRAB_KING_SEA:
			return EnumSectionType.CRAB_KING_SEA;
		default:
			return EnumSectionType.KRAKEN_SEA;
		}
	}

	/*
	 * Set the coordonate of each tiles
	 */
	public void setCoordiate(int tileWidth, int tileHeight) {
		Tiles[][] section;
		int leftCalculus;
		for (int i = 0; i < this.nbSection; i++) {
			section = this.map[i].getTiles();
			leftCalculus = (this.nbSection - i - 1) * this.sectionHeight;
			for (int j = 0; j < this.sectionHeight; j++) {
				for (int k = 0; k < this.sectionWidth; k++) {
					section[j][k].setCoordinate(transpoXCoordinateToIsometric(k, leftCalculus + j),
							transpoYCoordinateToIsometric(k, leftCalculus + j));
				}
			}
		}
	}

	/*
	 * Convert normal (x,y) coordinate to isometric x coordinate
	 */
	public int transpoXCoordinateToIsometric(int tileX, int tileY) {
		return (tileX * this.tileWidthHalf) + ((-tileY) * this.tileWidthHalf);
	}

	/*
	 * Convert normal (x,y) coordinate to isometric y coordinate
	 */
	public int transpoYCoordinateToIsometric(int tileX, int tileY) {
		return (tileX * this.tileHeightForth) + (tileY * this.tileHeightForth);
	}

	private void initOptimisationVariable() {
		this.tileHeightForth = this.tileHeight / 4;
		this.tileWidthHalf = this.tileWidth / 2;
		this.tileHeightForthDotDet = this.tileHeightForth * this.determinant;
		this.tileWidthHalfDotDet = this.tileWidthHalf * this.determinant;
	}

	/*
	 * Convert normal (x,y) coordinate to isometric x coordinate
	 */
	public int transpoYCoordinateToTile(int xPos, int yPos) {
		return (int) Math.ceil(xPos * (-tileHeightForthDotDet) + yPos * tileWidthHalfDotDet);
	}

	/*
	 * Convert normal (x,y) coordinate to isometric y coordinate
	 */
	public int transpoXCoordinateToTile(int xPos, int yPos) {
		return (int) Math.ceil(xPos * tileHeightForthDotDet + yPos * tileWidthHalfDotDet);
	}

	public int getSectionOfEntity(int xPos, int yPos) {
		int y = transpoYCoordinateToTile(-(xPos - GameView.screenWidth / 2), -(yPos - GameView.screenHeight / 2));

		int numSection = this.nbSection;

		while (y >= 0) {
			y -= this.sectionHeight;
			numSection--;
		}
		return numSection;
	}

	public int getSectionOfEntity(int tileY) {
		int numSection = this.nbSection;

		while (tileY >= 0) {
			tileY -= this.sectionHeight;
			numSection--;
		}
		return numSection;
	}

	public void updateDamagingTick() {
		Tiles[][] section;

		if (GameModele.pirateBoat == null)
			return;
		int currentSection = GameModele.pirateBoat.getCurrentSection();

		int min = currentSection - 1 > 0 ? currentSection - 1 : 0;

		int max = currentSection + 1 < this.nbSection ? currentSection + 1 : this.nbSection - 1;

		for (int i = min; i <= max; i++) {
			section = this.map[i].getTiles();
			for (int j = 0; j < this.sectionHeight; j++) {
				for (int k = 0; k < this.sectionWidth; k++) {
					if (section[j][k].isWaterDamaging() || section[j][k].isWaterPreDamage()) {
						section[j][k].update();
					}
				}
			}
		}
	}

	public void setPoisoning(int xPos, int yPos) {
		this.getTileUnderEntity(xPos, yPos).setType(EnumTiles.POISONED_WATER);
	}

	public void setDamaging(int xPos, int yPos) {
		int section = this.getSectionOfEntity(xPos, yPos);
		int tileX = this.getTileUnderEntity(xPos, yPos).getTileX();
		int tileY = this.getTileUnderEntity(xPos, yPos).getTileY();

		Tiles[] tile = getTileAttackOfShip(section, tileX, tileY);

		for (int i = 0; i < tile.length; i++) {
			if (tile[i] != null) {
				tile[i].setPreDamaging();
			}
		}
	}

	public Tiles[] getTileAttackOfShip(int section, int tileX, int tileY) {
		Tiles[] tilesAround = new Tiles[9];

		if (tileX > 0) {
			if (tileY < this.sectionHeight - 1) {
				// Angle bas gauche OK
				tilesAround[6] = this.map[section].getTiles()[tileY + 1][tileX - 1];
			} else {
				// Angle bas gauche not OK
				tilesAround[6] = null;
			}

			if (tileY > 0) {
				// Angle haut gauche OK
				tilesAround[0] = this.map[section].getTiles()[tileY - 1][tileX - 1];
			} else {
				// Angle haut gauche not OK
				tilesAround[0] = null;
			}

			// Cote gauche OK
			tilesAround[3] = this.map[section].getTiles()[tileY][tileX - 1];
		} else {
			// Cote gauche not OK
			tilesAround[3] = null;
		}

		if (tileY > 0) {
			// Haut OK
			tilesAround[1] = this.map[section].getTiles()[tileY - 1][tileX];
		} else {
			// Haut not OK
			tilesAround[1] = null;
		}

		if (tileY < this.sectionHeight - 1) {
			// bas OK
			tilesAround[7] = this.map[section].getTiles()[tileY + 1][tileX];
		} else {
			// bas not OK
			tilesAround[7] = null;
		}

		tilesAround[4] = this.map[section].getTiles()[tileY][tileX];

		if (tileX < this.sectionWidth - 1) {
			if (tileY < this.sectionHeight - 1) {
				// Angle bas droite OK
				tilesAround[8] = this.map[section].getTiles()[tileY + 1][tileX + 1];
			} else {
				// Angle bas droite not OK
				tilesAround[8] = null;
			}

			if (tileY > 0) {
				// Angle haut droite OK
				tilesAround[2] = this.map[section].getTiles()[tileY - 1][tileX + 1];
			} else {
				// Angle haut droite not OK
				tilesAround[2] = null;
			}

			// Cote droit OK
			tilesAround[5] = this.map[section].getTiles()[tileY][tileX + 1];
		} else {
			// Cote droit not OK
			tilesAround[5] = null;
		}

		return tilesAround;
	}

	public Tiles getTileUnderEntity(int xPos, int yPos) {
		int x = transpoXCoordinateToTile(-(xPos - GameView.screenWidth / 2), -(yPos - GameView.screenHeight / 2));
		int y = transpoYCoordinateToTile(-(xPos - GameView.screenWidth / 2), -(yPos - GameView.screenHeight / 2));

		int numSection = this.getSectionOfEntity(y);

		return this.map[numSection].getTiles()[y % 48][x];
	}

	double determinant() {
		float bottom = ((this.tileWidth * this.tileHeight) / 4);
		return (1 / bottom);
	}

	/*
	 * Print the map, usefull for debbuging
	 */
	public void printMap() {
		for (int i = 0; i < this.nbSection; i++) {
			System.out.print("\n----" + i + "----\n");
			this.map[i].printSection();

		}
	}

	/**
	 * Get the Wave Offset for a pos
	 * 
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public double getWaveOffset(int xPos, int yPos) {

		int x = transpoXCoordinateToTile(-(xPos - GameView.screenWidth / 2), -(yPos - GameView.screenHeight / 2));
		int y = transpoYCoordinateToTile(-(xPos - GameView.screenWidth / 2), -(yPos - GameView.screenHeight / 2));

		int numSection = this.getSectionOfEntity(y);

		if (this.map[numSection].getTiles()[y % 48][x].isWater()) {
			return this.wave[(this.nbSection - numSection - 1) * this.sectionHeight + (y % 48)][x];
		} else {
			return 0;
		}
	}

	/*
	 * Print the waveMap, usefull for debbuging
	 */
	public void printWaveMap() {
		for (int i = 0; i < this.nbSection; i++) {
			for (int j = 0; j < this.sectionHeight; j++) {
				for (int k = 0; k < this.sectionWidth; k++) {
					System.out.print(" " + this.wave[i * this.sectionHeight + j][k] + " ");
				}
				System.out.print("\n");
			}
			System.out.print("\n\n-------------\n\n");
		}
	}

	/*
	 * Map a value in a range to another range
	 * 
	 * @param value : The value in the initialRange to transform into a value of the
	 * transformedRange
	 * 
	 * @param initialRangeMin and initialRangeMax : the range of the initial value
	 * 
	 * @param transformedRangeMin and transformedRangeMax : the range of the
	 * transformed value
	 */
	private double map(double value, double initialRangeMin, double initialRangeMax, double transformedRangeMin,
			double transformedRangeMax) {
		return transformedRangeMin + ((transformedRangeMax - transformedRangeMin) / (initialRangeMax - initialRangeMin))
				* (value - initialRangeMin);
	}

	/*
	 * Generate the wave of the map section by section regarding of the section type
	 * The generation is done with perlin noise mapped to a range depending of the
	 * section type
	 */
	public void generateWave() {
		PerlinNoiseGenerator perlin = new PerlinNoiseGenerator(0.05);
		double[][] waveNoise = new double[this.sectionHeight * this.nbSection][this.sectionWidth];

		waveNoise = perlin.generateNoiseArray(this.sectionHeight * this.nbSection, this.sectionWidth,
				rand.nextDouble() * 10000, rand.nextDouble() * 10000);

		int waveRange;
		int heightMax = this.nbSection * this.sectionHeight - 1;
		for (int i = 0; i < this.nbSection; i++) {
			switch (this.map[i].getSeaType()) {
			case HARBOR:
			case CALM_SEA:
			case CALM_SEA_TO_STORMY_SEA:
				waveRange = 25;
				break;
			case STORMY_SEA:
			case STORMY_SEA_FROM_CALM_SEA:
			case STORMY_SEA_TO_RAGING_SEA:
				waveRange = 35;
				break;
			case RAGING_SEA:
			case RAGING_SEA_FROM_STORMY_SEA:
			case CRAB_KING_SEA:
			case KRAKEN_SEA:
			case MOUTAIN:
				waveRange = 45;
				break;
			default:
				waveRange = 0;
			}
			for (int j = 0; j < this.sectionHeight; j++) {
				for (int k = 0; k < this.sectionWidth; k++) {
					if (k > 15 && k < this.sectionWidth - 16) {
						this.wave[heightMax - (i * this.sectionHeight + j)][k] = map(
								waveNoise[i * this.sectionHeight + j][k], -1, 1, -waveRange, waveRange);
					} else {
						this.wave[heightMax - (i * this.sectionHeight + j)][k] = 0;
					}
				}
			}
		}

		smoothWaveBorder(); // Smooth the border of the array for no relica when cycling the wave
	}

	/*
	 * Smooth all the border
	 */
	public void smoothWaveBorder() {
		smoothWaveBorderNorthAndSouth();
		smoothWaveBorderEastAndWest();
	}

	/*
	 * Smooth North and South border
	 */
	private void smoothWaveBorderNorthAndSouth() {
		double valueNorth;
		double valueSouth;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < this.sectionWidth; j++) {

				// South value become combinaison of south and north wave
				if (i == 0) {
					valueSouth = 0.5 * this.wave[i][j]
							+ 0.5 * this.wave[(this.nbSection * this.sectionHeight) - i - 1][j];
				} else {
					valueSouth = 0.6 * this.wave[i][j] + 0.4 * this.wave[i - 1][j];
				}

				// North value become combinaison of south and north wave
				if (i == 0) {
					valueNorth = 0.5 * this.wave[i][j]
							+ 0.5 * this.wave[(this.nbSection * this.sectionHeight) - i - 1][j];
				} else {
					valueNorth = 0.4 * this.wave[(this.nbSection * this.sectionHeight) - i][j]
							+ 0.6 * this.wave[(this.nbSection * this.sectionHeight) - i - 1][j];
				}

				this.wave[i][j] = valueSouth;
				this.wave[(this.nbSection * this.sectionHeight) - i - 1][j] = valueNorth;
			}
		}
	}

	/*
	 * Smooth West and East border
	 */
	private void smoothWaveBorderEastAndWest() {
		double valueEast;
		double valueWest;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < this.sectionHeight * this.nbSection; j++) {
				// West value become combinaison of west and east wave
				valueWest = 0.3 * this.wave[j][i + 16] + i * 0.25 * this.wave[j][i + 16];

				// East value become combinaison of west and east wave
				valueEast = 0.3 * this.wave[j][this.sectionWidth - i - 17]
						+ i * 0.25 * this.wave[j][this.sectionWidth - i - 17];

				this.wave[j][i + 16] = valueWest;
				this.wave[j][this.sectionWidth - i - 17] = valueEast;
			}
		}
	}

	private int getRangeOfWave(int section) {
		switch (this.map[section].getSeaType()) {
		case HARBOR:
		case CALM_SEA:
		case CALM_SEA_TO_STORMY_SEA:
			return 25;
		case STORMY_SEA:
		case STORMY_SEA_FROM_CALM_SEA:
		case STORMY_SEA_TO_RAGING_SEA:
			return 35;
		case RAGING_SEA:
		case RAGING_SEA_FROM_STORMY_SEA:
		case CRAB_KING_SEA:
		case KRAKEN_SEA:
		case MOUTAIN:
			return 45;
		default:
			return 0;
		}
	}

	/*
	 * The wave cicle torward the north
	 */
	public void cicleWaveNorth() {
		double temp[] = new double[this.sectionWidth];

		int maxX = this.nbSection * this.sectionHeight - 1;

		int rangeStart = getRangeOfWave(this.nbSection - 1);
		int rangeEnd = getRangeOfWave(0);

		for (int i = 0; i < this.sectionWidth; i++) {
			temp[i] = this.map(this.wave[0][i], -rangeStart, rangeStart, -rangeEnd, rangeEnd);
		}

		int optiHeight;
		int optiCondition;

		for (int i = 0; i < this.nbSection; i++) {
			if (i < this.nbSection - 1) {
				rangeStart = getRangeOfWave(this.nbSection - i - 2);
				rangeEnd = getRangeOfWave(this.nbSection - i - 1);
			}

			optiHeight = i * this.sectionHeight;
			optiCondition = (i == this.nbSection - 1 ? this.sectionHeight - 1 : this.sectionHeight);

			for (int j = 0; j < optiCondition; j++) {
				for (int k = 0; k < this.sectionWidth; k++) {
					if (j == this.sectionHeight - 1 && i < this.nbSection - 1) {
						this.wave[optiHeight + j][k] = this.map(this.wave[optiHeight + j + 1][k], -rangeStart,
								rangeStart, -rangeEnd, rangeEnd);
					} else {
						this.wave[optiHeight + j][k] = this.wave[optiHeight + j + 1][k];
					}
				}
			}
		}

		for (int i = 0; i < this.sectionWidth; i++) {
			this.wave[maxX][i] = temp[i];
		}
	}

	/*
	 * The wave cicle torward the west
	 */
	public void cicleWaveWest() {
		double temp[] = new double[this.sectionHeight * this.nbSection];
		for (int i = 0; i < this.nbSection; i++) {
			for (int j = 0; j < this.sectionHeight; j++) {
				temp[i * this.sectionHeight + j] = this.wave[i * this.sectionHeight + j][0];
			}
		}

		for (int i = 0; i < this.nbSection; i++) {
			for (int j = 0; j < this.sectionHeight; j++) {
				for (int k = 0; k < this.sectionWidth - 1; k++) {
					this.wave[i * this.sectionHeight + j][k] = this.wave[i * this.sectionHeight + j][k + 1];
				}
			}
		}

		for (int i = 0; i < this.nbSection; i++) {
			for (int j = 0; j < this.sectionHeight; j++) {
				this.wave[i * this.sectionHeight + j][this.sectionWidth - 1] = temp[i * this.sectionHeight + j];
			}
		}
	}

	/*
	 * The wave cicle torward the east
	 */
	public void cicleWaveEast() {
		double temp[] = new double[this.sectionHeight * this.nbSection];
		for (int i = 0; i < this.nbSection; i++) {
			for (int j = 0; j < this.sectionHeight; j++) {
				temp[i * this.sectionHeight + j] = this.wave[i * this.sectionHeight + j][this.sectionWidth - 1];
			}
		}

		for (int i = 0; i < this.nbSection; i++) {
			for (int j = 0; j < this.sectionHeight; j++) {
				for (int k = this.sectionWidth - 1; k > 0; k--) {
					this.wave[i * this.sectionHeight + j][k] = this.wave[i * this.sectionHeight + j][k - 1];
				}
			}
		}

		for (int i = 0; i < this.nbSection; i++) {
			for (int j = 0; j < this.sectionHeight; j++) {
				this.wave[i * this.sectionHeight + j][0] = temp[i * this.sectionHeight + j];
			}
		}
	}

	public double[][] getWave() {
		return wave;
	}

	public int getSectionWidth() {
		return sectionWidth;
	}

	public void setSectionWidth(int sectionWidth) {
		this.sectionWidth = sectionWidth;
	}

	public int getSectionHeight() {
		return sectionHeight;
	}

	public void setSectionHeight(int sectionHeight) {
		this.sectionHeight = sectionHeight;
	}

	public int getNbSection() {
		return nbSection;
	}

	public void setNbSection(int nbSection) {
		this.nbSection = nbSection;
	}

	public int getSeed() {
		return seed;
	}

	public MapSection[] getMap() {
		return this.map;
	}

	public MapRepresentation getRepresentation() {
		return this.mapRepres;
	}

	public MiniMap getMiniMap() {
		return this.miniMap;
	}

	public SectionTitle getSectionTitle() {
		return this.title;
	}

	public Random getRand() {
		return this.rand;
	}
}
