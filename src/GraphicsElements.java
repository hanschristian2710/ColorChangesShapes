import java.awt.Color;
import java.util.*;
import java.awt.Point;
import uwcse.io.*;
import uwcse.graphics.*;
import javax.swing.*;

/**
 * A class to create and manipulate graphics elements stored in an ArrayList
 * 
 * @author Hans Christian
 */

public class GraphicsElements {

	/** Maximum number of wedges in a pie */
	public static final int MAXIMUM_NUMBER_OF_PIE_WEDGES = 100;

	/** Maximum number of stripes of one color in the pattern of stripes */
	public static final int MAXIMUM_NUMBER_OF_STRIPES = 15;

	/** Maximum number of divisions in a Koch snow flake */
	public static final int MAXIMUM_NUMBER_OF_DIVISIONS = 5;

	// intialize the color of stripes
	private Color stripesColor1, stripesColor2;

	// The window is 400 pixels wide and 300 pixels high

	/**
	 * Create the view of a pie Use filled arcs. The color of each arc is
	 * random. The pie should fill the window. Store the arcs in an ArrayList
	 * and return that ArrayList. The number of pie wedges (= arcs) is given by
	 * the user (use a dialog box). If that number is less than or equal to 0 or
	 * greater than MAXIMUM_NUMBER_OF_PIE_WEDGES, display an error message (use
	 * JOptionPane.showMessageDialog)and ask for it again. Make sure that no gap
	 * appears in the pie when drawing it.
	 */
	public ArrayList<Arc> createAPie() {
		ArrayList<Arc> graphicsList = new ArrayList<Arc>();

		// Add your code here

		// Asking input for pie
		Input in = new Input();
		int n;
		do {
			n = in.readIntDialog("Enter the number of pie (between 1 and "
					+ MAXIMUM_NUMBER_OF_PIE_WEDGES + ")");
			if (n < 1 || n > MAXIMUM_NUMBER_OF_PIE_WEDGES) {
				JOptionPane.showMessageDialog(null, "Invalid number of pie",
						"Input error", JOptionPane.WARNING_MESSAGE);
			}

		} while (n < 1 || n > MAXIMUM_NUMBER_OF_PIE_WEDGES);

		// create the arcs
		// location of the arcs initially
		int x = 50;
		int y = 0;

		// size of the bounding box
		int height = 300;
		int width = 300;

		// starting angle
		int startAngle = 0;

		// declaring additional variable
		int arcAngle;
		int gap;

		// ifs angle is divisible by 360
		if (360 % n == 0) {
			// find each arc's degree
			arcAngle = 360 / n;
			// create the arcs according to the input
			for (int i = 1; i <= n; i++) {
				// color of the arc
				Color color = new Color((float) Math.random(),
						(float) Math.random(), (float) Math.random());
				Arc arc = new Arc(x, y, width, height, startAngle, arcAngle,
						color, true);
				graphicsList.add(arc);
				startAngle += arcAngle;
			}

		} else {
			// find the gap of degree
			gap = 360 - ((360 / n) * n);

			// find the leftover arc that use
			// full degree (360/n)
			int leftOver = n - gap;

			// adding one degree to the angle
			arcAngle = 360 / n + 1;

			// creating the first part of arcs that
			// are added with one degree, not obvious to eyes
			for (int j = 1; j <= gap; j++) {
				// color of the arc
				Color ArcColor = new Color((float) Math.random(),
						(float) Math.random(), (float) Math.random());
				Arc arc = new Arc(x, y, width, height, startAngle, arcAngle,
						ArcColor, true);
				graphicsList.add(arc);
				startAngle += arcAngle;
			}

			// creating the second part of arcs that are
			// leftover for the full degree (360 / n)
			for (int i = 1; i <= leftOver; i++) {
				// color of the arc
				Color ArcColor = new Color((float) Math.random(),
						(float) Math.random(), (float) Math.random());
				Arc arc = new Arc(x, y, width, height, startAngle,
						arcAngle - 1, ArcColor, true);
				graphicsList.add(arc);
				startAngle += arcAngle - 1;
			}
		}
		return graphicsList;
	}

	/**
	 * Create a pattern of stripes as shown in the homework instructions. Store
	 * the triangles in an ArrayList and return that ArrayList. Use two colors
	 * only to paint the triangles. The pattern should cover most of the window.
	 * The number of stripes (of one color) is given by the user (use a dialog
	 * box). If that number is less than or equal to 0 or greater than
	 * MAXIMUM_NUMBER_OF_STRIPES, display an error message (use
	 * JOptionPane.showMessageDialog)and ask for it again.
	 */
	public ArrayList<Triangle> createStripes() {
		ArrayList<Triangle> graphicsList = new ArrayList<Triangle>();

		// Add your code here

		// Asking input for stripes
		Input in = new Input();
		int n;
		do {
			n = in.readIntDialog("Enter the number of stripes (between 1 and "
					+ MAXIMUM_NUMBER_OF_STRIPES + ")");
			if (n < 1 || n > MAXIMUM_NUMBER_OF_STRIPES) {
				JOptionPane.showMessageDialog(null,
						"Invalid number of stripes", "Input error",
						JOptionPane.WARNING_MESSAGE);
			}
		} while (n < 1 || n > MAXIMUM_NUMBER_OF_STRIPES);

		// create the triangles
		// height of one triangle
		int height = ViewWindow.WINDOW_HEIGHT / n;

		// horizontal gap
		int gapHorizontal = ViewWindow.WINDOW_WIDTH - height * n;

		// vertical gap
		int gapVertical = ViewWindow.WINDOW_HEIGHT - height * n;

		// starting x0 and y0
		int y0 = gapVertical / 2;
		int x0 = gapHorizontal / 2;

		// using counter to arrange the colors
		int triangleCount = 0;

		// random color for triangle 1
		stripesColor1 = new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random());

		// random color for triangle 2
		stripesColor2 = new Color((float) Math.random(), (float) Math.random(),
				(float) Math.random());

		// using loops to draw the triangle
		for (int y = y0; y < y0 + n * height; y += height) {

			// ifs for the even input
			if (n % 2 == 0) {
				triangleCount++;
			}

			for (int x = x0; x < x0 + n * height; x += height) {

				// create the triangle 1
				Triangle triangle1 = new Triangle(x, y, x + height, y, x
						+ height, y + height, stripesColor1, true);
				graphicsList.add(triangle1);

				// create the triangle 2
				Triangle triangle2 = new Triangle(x, y, x, y + height, x
						+ height, y + height, stripesColor2, true);
				graphicsList.add(triangle2);

				triangleCount++;
				if (triangleCount % 2 == 0) {
					triangle1.setColor(stripesColor2);
					triangle2.setColor(stripesColor1);
				}
			}
		}
		return graphicsList;
	}

	/**
	 * Create a Koch snow flake. Use the class java.awt.Point. Store the Points
	 * in an ArrayList and return that ArrayList. Note that you can't set the
	 * color of a point. The initial color of the lines making up the snow flake
	 * is black. But, you can change the color in the method
	 * changeColorOfSnowFlake below. The snow flake should cover most of the
	 * window, and be always visible. The number of divisions of the initial
	 * triangle is given by the user (use a dialog box). If that number is less
	 * than 0 or greater than MAXIMUM_NUMBER_OF_DIVISIONS, display an error
	 * message (use JOptionPane.showMessageDialog)and ask for it again.
	 */
	public ArrayList<Point> createASnowFlake() {
		ArrayList<Point> graphicsList = new ArrayList<Point>();

		// Add your code here

		// Asking input for snow flake division
		Input in = new Input();
		int n;
		do {
			n = in.readIntDialog("Enter the number of Snow FLake Division "
					+ "(between 1 and " + MAXIMUM_NUMBER_OF_DIVISIONS + ")");
			if (n < 1 || n > MAXIMUM_NUMBER_OF_DIVISIONS) {
				JOptionPane.showMessageDialog(null,
						"Invalid number of divisions", "Input error",
						JOptionPane.WARNING_MESSAGE);
			}
		} while (n < 1 || n > MAXIMUM_NUMBER_OF_DIVISIONS);

		// initial triangle points without any division
		Point p1 = new Point(125, 50);
		Point p2 = new Point(325, 150);
		Point p3 = new Point(125, 250);
		graphicsList.add(p1);
		graphicsList.add(p2);
		graphicsList.add(p3);

		// for-loop to start the division count
		// from one and ends at n (user's input)
		for (int divide = 1; divide <= n; divide++) {

			// creating new graphic list to store
			// the moment lists of window
			ArrayList<Point> graphicsList2 = new ArrayList<Point>();

			// for loop to draw the extra (abc) triangle in each
			// line of p1, p2, p3
			for (int d = 0; d < graphicsList.size(); d++) {
				
				// get the points of p to start 
				// the abc drawing
				Point p = graphicsList.get(d);
				Point q = new Point();
				int e = graphicsList.size() - 1;
				if (d == e) {
					q = graphicsList.get(0);
				} else {
					q = graphicsList.get(1 + d);
				}

				// formula given to draw the extra triangle division
				Point a = new Point((int) (p.x + (q.x - p.x) / 3.0),
						(int) (p.y + (q.y - p.y) / 3.0));
				Point c = new Point((int) (p.x + 2 * (q.x - p.x) / 3.0),
						(int) (p.y + 2 * (q.y - p.y) / 3.0));
				Point b = new Point();
				b.x = (int) (a.x + (c.x - a.x) * Math.cos(Math.PI / 3.0) + (c.y - a.y)
						* Math.sin(Math.PI / 3.0));
				b.y = (int) (a.y - (c.x - a.x) * Math.sin(Math.PI / 3.0) + (c.y - a.y)
						* Math.cos(Math.PI / 3.0));

				// adding the element in the window
				graphicsList2.add(p);
				graphicsList2.add(a);
				graphicsList2.add(b);
				graphicsList2.add(c);

			}
			graphicsList = graphicsList2;
		}
		return graphicsList;
	}

	/**
	 * Rotate the colors in the pie, in a clockwise direction. Precondition:
	 * graphicsList describes a pie Return the updated ArrayList
	 */
	public ArrayList<Arc> rotateColorsInPie(ArrayList<Arc> graphicsList) {
		// Add your code here
		// using loop to loop through the list of pie
		for (int i = 1; i < graphicsList.size(); i++) {

			// get the list of the first pie
			Arc startPie = graphicsList.get(i - 1);

			// get the list of the following pie
			Arc afterPie = graphicsList.get(i);

			// get the color of the starting pie
			Color color1 = startPie.getColor();

			// get the color of the following pie
			Color color2 = afterPie.getColor();

			// set both pies in swap color as it loop through
			startPie.setColor(color2);
			afterPie.setColor(color1);
		}
		return graphicsList;
	}

	/**
	 * Flip the 2 colors of the pattern of stripes Precondition: graphicsList
	 * describes a pattern of stripes Return the updated ArrayList
	 */
	public ArrayList<Triangle> flipColorsInStripes(
			ArrayList<Triangle> graphicsList) {

		// Add your code here

		// loop through every list
		for (int i = 0; i < graphicsList.size(); i++) {

			// get the list of each triangle
			Triangle stripesList = (Triangle) (graphicsList.get(i));

			// get the color of the triangles
			Color stripesColor = stripesList.getColor();

			// ifs to swap between each other color
			if (stripesColor.equals(stripesColor1)) {
				stripesList.setColor(stripesColor2);
			} else {
				stripesList.setColor(stripesColor1);
			}
		}
		return graphicsList;
	}

	/**
	 * Return the new color of the snow flake (select a new random color) Use
	 * the Random class (in java.util) to select the new random color. The color
	 * that you create and return in this method will automatically be assigned
	 * to the snow flake.
	 */
	public Color changeColorOfSnowFlake() {
		// using math random to generate random colors to snow flake's outline
		Color colorOfSnowFlake = new Color((float) Math.random(),
				(float) Math.random(), (float) Math.random());

		return colorOfSnowFlake;
	}
}
