/*
 * Name: Leoul Gezu
 * Date: May 4, 2021
 * CSC 202
 * Project 4--Star.java
 * 
 * Each Star object represents an individual star that can be put into a star chart.
 * A star is represented by a 3-dimensional (x, y, z) position, where each coordinate
 * is in the range [-1.0 .. 1.0] from bottom/left to top/right.
 * A star also has a magnitude which is inversely related to its brightness and size.
 * 
  * Document Assistance(who and describe; if no assistance, declare that fact):
  * 	I did not give or receive any assistance in the development of this class
 * 
 */

import java.awt.Color;
import java.util.*;

public class Star implements Comparable<Star> {
	private final double x;
	private final double y;
	private final double z;
	private final double magnitude;
	private final Color color; // cached to save memory

	/**
	 * Constructs a new star with the given position and magnitude. The x/y/z are in
	 * range [-1.0 .. 1.0] from bottom/left to top/right. The magnitude is a
	 * non-negative number representing the star's size. Assumes all parameters are
	 * in valid ranges.
	 * 
	 * @param x         - the x position of this star
	 * @param y         - the y position of this star
	 * @param z         - the z position of this star
	 * @param magnitude - the size of this star
	 */
	
	/**
	 * Makes a star object
	 * @param x: x position
	 * @param y: y position
	 * @param z: z position
	 * @param magnitude: magnitude
	 */
	public Star(double x, double y, double z, double magnitude) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.magnitude = magnitude;
		int brightness = (int) ((z + 1.0) / 2 * 128) + 128;
		this.color = new Color(brightness, brightness, brightness);
	}

	/**
	 * Returns this star's magnitude as was passed to the constructor.
	 * 
	 * @return this star's magnitude
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Returns this star's x-coordinate as was passed to the constructor.
	 * 
	 * @return this star's x-coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns this star's y-coordinate as was passed to the constructor.
	 * 
	 * @return this star's y-coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns this star's y-coordinate as was passed to the constructor.
	 * 
	 * @return this star's z coordinate
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns an integer code that can be used by hash-based collections to store
	 * Star objects properly.
	 * 
	 * @return this star's hashcode
	 */
	public int hashCode() {
		return Double.valueOf(x + 1).hashCode() + 103 * Double.valueOf(y + 1).hashCode()
				+ 317 * Double.valueOf(z + 1).hashCode() + 1337 * Double.valueOf(magnitude).hashCode();
	}

	/**
	 * Returns the x-coordinate in the graphics window at which this star should be
	 * drawn. This is a translation of coordinates from [-1.0 .. 1.0] to [0 ..
	 * width).
	 * 
	 * @return this star's x position for the graphics window
	 */
	public int pixelX(int width) {
		return (int) ((x + 1.0) / 2 * width);
	}

	/**
	 * Returns the y-coordinate in the graphics window at which this star should be
	 * drawn. This is a translation of coordinates from [-1.0 .. 1.0] to [0 ..
	 * height). The y-axis is flipped between star coordinates and window
	 * coordinates.
	 * 
	 * @return this star's y position for the graphics window
	 */
	public int pixelY(int height) {
		return height - (int) ((y + 1.0) / 2 * height);
	}

	/**
	 * Returns the color with which this star should be drawn.
	 * 
	 * @return this star's color when drawn in the graphics window
	 */
	public Color pixelColor() {
		return color;
	}

	/**
	 * Returns the width/height of the oval that should be drawn to display this
	 * star. Brighter stars have smaller magnitudes.
	 * 
	 * @return this star's size when drawn in the graphics window
	 */
	public int pixelSize() {
		return Math.max(2, (int) Math.round(10.0 / (magnitude + 2)));
	}

	// --------- YOUR NEW METHODS GO BELOW ---------- //

	/**
	 * Returns the distance between two stars
	 * @param other: other star
	 * @return
	 */
	public double distance(Star other) {
		double distance = Math.sqrt(Math.pow((this.getX() - other.getX()), 2)
				+ Math.pow((this.getY() - other.getY()), 2) + Math.pow((this.getZ() - other.getZ()), 2));
		return distance;
	}
	/**
	 * Compares two stars to determine order
	 */
	public int compareTo(Star other) {
		if (this.getZ() < other.getZ()) {
			return -1;
		} else if (this.getZ() > other.getZ()) {
			return 1;
		} else {
			if (this.getY() < other.getY()) {
				return -1;
			} else if (this.getY() > other.getY()) {
				return 1;
			} else {
				if (this.getX() < other.getX()) {
					return -1;
				} else if (this.getX() > other.getX()) {
					return 1;
				} else {
					if (this.getMagnitude() < other.getMagnitude()) {
						return -1;
					} else if (this.getMagnitude() > other.getMagnitude()) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		}
	}
	/**
	 * Comapres to stars to determine equality
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Star)) {
			return false;
		}

		Star other = (Star) object;
		if ((this.getX() == other.getX()) && (this.getY() == other.getY()) && (this.getZ() == other.getZ())
				&& (this.getMagnitude() == other.getMagnitude())) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Returns a clean star tag
	 */
	public String toString() {
		String tag = "(" + this.getX() + "," + this.getY() + "," + this.getZ() + "):" + this.getMagnitude();
		return tag;
	}
}
