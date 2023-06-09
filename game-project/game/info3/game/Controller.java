/*
 * Copyright (C) 2020  Pr. Olivier Gruber
 * Educational software for a basic game development
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created on: March, 2020
 *      Author: Pr. Olivier Gruber
 */
package info3.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import info3.game.graphics.GameCanvasListener;
import info3.game.modele.GameModele;
import info3.game.vue.GameView;

public class Controller implements GameCanvasListener {
	GameModele gameModele;
	GameView gameView;

	public Controller() throws Exception {
		gameModele = new GameModele();
		gameView = new GameView(gameModele, this);
		gameModele.setGameview(gameView);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked: (" + e.getX() + "," + e.getY() + ")");
		System.out.println("   modifiers=" + e.getModifiersEx());
		System.out.println("   buttons=" + e.getButton());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed: (" + e.getX() + "," + e.getY() + ")");
		System.out.println("   modifiers=" + e.getModifiersEx());
		System.out.println("   buttons=" + e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Mouse released: (" + e.getX() + "," + e.getY() + ")");
		System.out.println("   modifiers=" + e.getModifiersEx());
		System.out.println("   buttons=" + e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse entered: (" + e.getX() + "," + e.getY() + ")");
		System.out.println("   modifiers=" + e.getModifiersEx());
		System.out.println("   buttons=" + e.getButton());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse exited: (" + e.getX() + "," + e.getY() + ")");
		System.out.println("   modifiers=" + e.getModifiersEx());
		System.out.println("   buttons=" + e.getButton());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Mouse dragged: (" + e.getX() + "," + e.getY() + ")");
		System.out.println("   modifiers=" + e.getModifiersEx());
		System.out.println("   buttons=" + e.getButton());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("Mouse moved: (" + e.getX() + "," + e.getY() + ")");
		System.out.println("   modifiers=" + e.getModifiersEx());
		System.out.println("   buttons=" + e.getButton());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Key typed: " + e.getKeyChar() + " code=" + e.getKeyCode());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key pressed: " + e.getKeyChar() + " code=" + e.getKeyCode());

		System.out.println("DEBUT");

		try {
			this.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Key released: " + e.getKeyChar() + " code=" + e.getKeyCode());
	}

	@Override
	public void tick(long elapsed) {
		gameModele.tick(elapsed);
		gameView.tick(elapsed);
	}

	@Override
	public void paint(Graphics g) {
		gameView.paint(g);
	}

	@Override
	public void windowOpened() {
		gameView.loadMusic();
		// game.m_canvas.setTimer(6000);
	}

	@Override
	public void exit() {
	}

	// boolean m_expired;
	@Override
	public void endOfPlay(String name) {
		// if (!m_expired) // only reload if it was a forced reload by timer
		gameView.loadMusic();
		// m_expired = false;
	}

	@Override
	public void expired() {
		// will force a change of music, after 6s of play
		// System.out.println("Forcing an ealy change of music");
		// m_expired = true;
		// game.loadMusic();
	}

	private void start() throws IOException {
		this.gameModele.start();
	}

}