/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;

		case 'w':
			Main.trigger = "W is triggered";
			Main.delta.setX(0);
			Main.delta.setY(-Main.playerSpeed);
			break;

		case 'a':
			Main.trigger = "A is triggered";
			Main.delta.setX(-Main.playerSpeed);
			Main.delta.setY(0);
			break;

		case 's':
			Main.trigger = "S is triggered";
			Main.delta.setX(0);
			Main.delta.setY(Main.playerSpeed);
			break;

		case 'd':
			Main.trigger = "D is triggered";
			Main.delta.setX(Main.playerSpeed);
			Main.delta.setY(0);
			break;

		case '$':
			Main.trigger = "Spacebar is triggered";
			Main.space = true;
			break;

		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
	}
}