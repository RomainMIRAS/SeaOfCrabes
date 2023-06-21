package info3.game.sound;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
/**
 * The supported file formats are: "wav", "au" and "aiff". The samples can be either 8-bit or 16-bit, with sampling rate from 8 kHz to 48 kHz.
 * @author MIRAS Romain
 *
 */
public class SoundTool {

	static BackgroundMusic currenBackgroundSound = BackgroundMusic.MainMenu;

	public final static String pathBGM = "assets/audio/bgm/";

	public final static String pathSE = "assets/audio/se/";

	private static HashMap<BackgroundMusic, String> backgroundSounds;
	private static HashMap<SoundEffect, String> soundEffects;
	
	private static Clip soundEffectPlayer;

	private static Clip backgroundMusicPlayer;

	/**
	 * Change la musique actuel du background
	 * 
	 * @param bgs
	 */
	public static void changeBackgroundMusic(BackgroundMusic bgs) {
		SoundTool.currenBackgroundSound = bgs;
		backgroundMusicPlayer.close();
		playBackgroundMusic();
	}

	/**
	 * Joue un sound effect
	 * 
	 * @param se
	 * @param duration
	 * @param volume
	 * @throws FileNotFoundException
	 */
	public static void playSoundEffect(SoundEffect se, long duration) throws FileNotFoundException {
		String filename = pathSE + soundEffects.get(se);
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(fis);
			soundEffectPlayer.open(audioIn);
			soundEffectPlayer.start();
			soundEffectPlayer.close();
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	/**
	 * Fonction jouant la musique de background en r�p�tion Use
	 * changeBackgroundMusic() pour changer la musique
	 */
	public static void playBackgroundMusic() {
		String filename = pathBGM + backgroundSounds.get(currenBackgroundSound);
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(fis);
			backgroundMusicPlayer.open(audioIn);
			backgroundMusicPlayer.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	public static BackgroundMusic stopBackgroundMusic() {
		backgroundMusicPlayer.stop();
		return currenBackgroundSound;
	}

	/**
	 * Fonction permettant d'initialiser les sons d'avances.
	 * The supported file formats are: "wav", "au" and "aiff". The samples can be either 8-bit or 16-bit, with sampling rate from 8 kHz to 48 kHz.
	 */
	public static void initSoundTool() {
		/********
		 * 
		 * HASHMAP DES EFFECTS SONORES
		 * 
		 */
		soundEffects = new HashMap<>();
		SoundTool.soundEffects.put(SoundEffect.Test, "Sword4.ogg"); 
		SoundTool.soundEffects.put(SoundEffect.PirateBoatAttack, "PirateBoatAttack.wav");
		/********
		 * 
		 * HASHMAP DES SONS DE FONDS
		 * 
		 */
		backgroundSounds = new HashMap<>();
		SoundTool.backgroundSounds.put(BackgroundMusic.Game, "Town1.wav");
		SoundTool.backgroundSounds.put(BackgroundMusic.MainMenu, "Town8.wav");
		
		try {
			backgroundMusicPlayer = AudioSystem.getClip();
			soundEffectPlayer = AudioSystem.getClip();
			playBackgroundMusic();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
	}
	
//   public static void replay() {
//	      if (volume != Volume.MUTE) {
//	         if (clip.isRunning())
//	            clip.stop();   // Stop the player if it is still running
//	         clip.setFramePosition(0); // rewind to the beginning
//	         clip.start();     // Start playing
//	      }
//	   }

}
