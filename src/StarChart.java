/*
 * Name: Leoul Gezu
 * Date: May 10, 2021 
 * CSC 202 
 * Project 4-StarChart.java
 * 
 * This class represents information about stars to be displayed including
 * the position of each star, names of stars, and constellations. The
 * distance between two stars can be determined. A supernova can be 
 * simulated creating dead stars.
 * 
 * Document Assistance(who and describe; if no assistance, declare that fact):
 * 		Dr. Mueller helped me make sure my singly linked list implementation in fact worked the way I was imagining
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class StarChart {
	private static double SUPERNOVA_DISTANCE = 0.25;
	private int width;
	private int height;
	HashMap<String, Star> nameToStar = new HashMap<String, Star>();
	HashMap<Star, String> starToName = new HashMap<Star, String>();
	HashMap<String, StarNode> constellationToStarNode = new HashMap<String, StarNode>();
	HashSet<Star> deadStars = new HashSet<Star>();
	/**
	 * A StarNode class to implement constellations as a singly linked list
	 * @author leoul
	 *
	 */
	private class StarNode {
		private Star data;
		private StarNode next;
		/**
		 * Makes a StarNode object with data Star and refernece to next star
		 * @param data
		 * @param next
		 */
		private StarNode(Star data, StarNode next) {
			this.data = data;
			this.next = next;

		}
		/**
		 * Draws a line between two StarNodes
		 * @param g: graphics window
		 * @param showStarNames: star name setting
		 * @param constellation: constellation name
		 */
		private void drawLine(Graphics g, boolean showStarNames, String constellation) {

			if (showStarNames == true) {
				g.setColor(Color.YELLOW);
				StarNode current = this;

				while (current != null) {

					g.drawLine(current.data.pixelX(width), current.data.pixelY(height), current.next.data.pixelX(width),
							current.next.data.pixelY(height));
					g.drawString(starToName.get(current.data), current.data.pixelX(width), current.data.pixelY(height));
					g.drawString(starToName.get(current.next.data), current.next.data.pixelX(width),
							current.next.data.pixelY(height));
					
					if (current.next.next == null) {
						StarNode lastStar = current.next;
						g.setColor(Color.BLUE);
						g.drawString(constellation, lastStar.data.pixelX(width), lastStar.data.pixelY(height)-20);      //  - 20 because otherwise it just overlaps with name of last star
						
					}
					
					
					
					current = current.next.next;

				}
			} else {
				g.setColor(Color.YELLOW);
				StarNode current = this;

				while (current != null) {

					g.drawLine(current.data.pixelX(width), current.data.pixelY(height), current.next.data.pixelX(width),
							current.next.data.pixelY(height));

					current = current.next.next;

				}

			}

		}
		/**
		 * Returns a clean tag for a StarNode object
		 */
		public String toString() {
			String tag = "";
			StarNode current = this;
			while (current.next != null) {
				tag += "[" + starToName.get(current.data) + "]";
				current = current.next;
			}
			return tag;
		}
	}
	/**
	 * Makes a StarChart object with height and width of the graphics window
	 * @param width
	 * @param height
	 */
	public StarChart(int width, int height) {
		this.width = width;
		this.height = height;

	}
	/**
	 * Adds a star to the StarChart
	 * @param star: Star object
	 * @param name: name of star(if applicable)
	 */
	public void addStar(Star star, String name) {
		starToName.put(star, name);
		nameToStar.put(name, star);

	}
	/**
	 * Adds a constellation to the StarChart
	 * @param constellationName
	 * @param starNames
	 */
	public void addConstellation(String constellationName, String[] starNames) {
		Star firstStar = nameToStar.get(starNames[0]);
		StarNode firstNode = new StarNode(firstStar, null);
		constellationToStarNode.put(constellationName, firstNode);
		StarNode current = firstNode;

		for (int i = 1; i < starNames.length; i++) {
			StarNode next = new StarNode(nameToStar.get(starNames[i]), null);
			current.next = next;
			current = next;

		}

	}
	/**
	 * Returns the star object that corresponds to a given name
	 * @param name
	 * @return
	 */
	public Star getStar(String name) {
		return nameToStar.get(name);
	}
	/**
	 * Explodes a star, destroying stars within SUPERNOVA_DISTANCE
	 * @param star
	 * @return: number of stars destroyed
	 */
	public int supernova(Star star) {
		if (!deadStars.contains(star)) {
			deadStars.add(star);
			for (Star current : starToName.keySet()) {
				if (star.distance(current) <= SUPERNOVA_DISTANCE) {
					deadStars.add(current);
				}
			}
			return deadStars.size();
		} else {
			return 0;
		}

	}
	/**
	 * Returns the name that corresponds to a given star
	 * @param star
	 * @return
	 */
	public String getName(Star star) {
		return starToName.get(star);
	}
	/**
	 * Draws stars, constellations and supernovas in graphics window
	 * @param g
	 * @param showStarNames
	 */
	public void draw(Graphics g, boolean showStarNames) {
		for (Star star : starToName.keySet()) {
			Star current = star;

			if (deadStars.contains(star)) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			g.fillRect(current.pixelX(width), current.pixelY(height), current.pixelSize(), current.pixelSize());

		}

		for (String constellation : constellationToStarNode.keySet()) {
			StarNode first = constellationToStarNode.get(constellation);
			first.drawLine(g, showStarNames, constellation);
		}

	}

}
