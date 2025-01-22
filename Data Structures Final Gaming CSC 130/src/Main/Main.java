package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static Color yellow = new Color(255, 255, 0);
	public static boolean isImageDrawn = false;
	public static stopWatchX timer = new stopWatchX(100);
	public static Vector2D vec1 = new Vector2D(100, 50);
	public static Queue<Vector2D> vecs1 = new LinkedList<>();
	public static Queue<Vector2D> vecs2 = new LinkedList<>();
	public static Vector2D currentVec = new Vector2D(-100, -100);
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;
	public static HashMap<String, String> map = new HashMap<>();
	public static String trigger = "";
	public static spriteInfo wallSpriteInfo;
	public static spriteInfo broccoliInfo;
	public static spriteInfo chickenInfo;
	public static Vector2D delta;
	public static int playerSpeed = 20;
	public static Vector2D playerPos;
	public static Vector2D spriteSize = new Vector2D(128, 128);
	public static Vector2D wallSize = new Vector2D(1280, 720);
	public static boolean space = false;
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start() {
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		delta = new Vector2D(0, 0);
		for (int i = 0; i < 1280-50; i+=5) {
			vecs1.add(new Vector2D(i, 300));
		}

		playerPos = new Vector2D(0, 100);
	
		sprites.add(new spriteInfo(playerPos, "jamal"));
		sprites.add(new spriteInfo(playerPos, "rightfoot"));
		sprites.add(new spriteInfo(playerPos, "leftfoot"));
		// int step = 20;
		// for (int i = 0; i < 1280;) {
		// 	sprites.add(new spriteInfo(new Vector2D(i, 100), "rightfoot"));
		// 	i+=step;
		// 	sprites.add(new spriteInfo(new Vector2D(i, 100), "leftfoot"));
		// 	i+=step;
		// }
		
		EZFileRead ezr = new EZFileRead("Script/Gigachad.txt");
		for(int i = 0; i < ezr.getNumLines(); i++){
			String raw = ezr.getLine(i);
			StringTokenizer st = new StringTokenizer(raw, "*");
			String script_key = st.nextToken();
			String script_string = st.nextToken();
			map.put(script_key, script_string);
		}

		// wall sprite setup
		wallSpriteInfo = new spriteInfo(new Vector2D(0, 0), "walltwo");
		// item sprite setup
		broccoliInfo = new spriteInfo(new Vector2D(600, 300), "chicken");
		chickenInfo = new spriteInfo(new Vector2D(300, 300), "broccoli");
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		spriteInfo currentSpriteInfo = sprites.get(
			currentSpriteIndex % sprites.size()
		);

		playerPos.adjustX(delta.getX());
		playerPos.adjustY(delta.getY());

		if (!isInside(playerPos, spriteSize, wallSize)) {
			playerPos.setX(Math.min(
				Math.max(playerPos.getX(), 0),
				wallSize.getX() - spriteSize.getX()
			));
			playerPos.setY(Math.min(
				Math.max(playerPos.getY(), 0),
				wallSize.getY() - spriteSize.getY()
			));
		}
		currentSpriteInfo.setCoords(playerPos);

		if (space) {
			if (isCollision(playerPos, spriteSize, broccoliInfo.getCoords(), spriteSize)) {
				ctrl.drawString(100, 350, map.get("string6"), yellow);
			}
			if (isCollision(playerPos, spriteSize, chickenInfo.getCoords(), spriteSize)) {
				ctrl.drawString(100, 350, map.get("string7"), yellow);
			}
		}

		if (timer.isTimeUp()) {
			// ctrl.addSpriteToFrontBuffer(currentVec.getX(), currentVec.getY(), "jamal");
			// Add a tester sprite to render list by tag (Remove later! Test only!)

			

			// System.out.println("debug: vecs1 size=" + vecs1.size());
			if (vecs1.size() == 0) {
				Queue<Vector2D> tmp = vecs1;
				vecs1 = vecs2;
				vecs2 = tmp;
			}

			currentVec = vecs1.poll();
			vecs2.add(currentVec);

			currentSpriteIndex++;

			// System.out.println(currentVec.getX());
			// System.out.println(currentVec.getY());
			timer.resetWatch();
			isImageDrawn = !isImageDrawn;
		}

		ctrl.addSpriteToFrontBuffer(
			currentSpriteInfo.getCoords().getX(),
			currentSpriteInfo.getCoords().getY(),
			currentSpriteInfo.getTag()
		);

		// Draw walls
		ctrl.addSpriteToFrontBuffer(
			wallSpriteInfo.getCoords().getX(),
			wallSpriteInfo.getCoords().getY(),
			wallSpriteInfo.getTag()
		);

		// draw broccoli
		ctrl.addSpriteToFrontBuffer(
			broccoliInfo.getCoords().getX(),
			broccoliInfo.getCoords().getY(),
			broccoliInfo.getTag()
		);

			// draw chicken
		ctrl.addSpriteToFrontBuffer(
			chickenInfo.getCoords().getX(),
			chickenInfo.getCoords().getY(),
			chickenInfo.getTag()
		);

		// Test drawing text on screen where you want (Remove later! Test only!)
		ctrl.drawString(1020, 600, trigger, yellow);
		// ctrl.drawString(100, 250, map.get("string4"), yellow);
		delta.setX(0);
		delta.setY(0);
	}
	
	// Additional Static methods below...(if needed)
	public static boolean isCollision(Vector2D pos1, Vector2D size1, Vector2D pos2, Vector2D size2) {
		if(
			pos1.getX() < pos2.getX() + size2.getX() &&
			pos1.getX() + size1.getX() > pos2.getX() &&
			pos1.getY() < pos2.getY() + size2.getY() &&
			pos1.getY() + size1.getX() > pos2.getY()
		) {
			return true;
		}
		return false;
	};

	public static boolean isInside(Vector2D pos, Vector2D size, Vector2D wallSize) {
		if (
			pos.getX() > 0 &&
			pos.getX() + size.getX() < wallSize.getX() &&
			pos.getY() > 0 &&
			pos.getY() + size.getY() < wallSize.getY()
		) {
			return true;
		}
		return false;
	};
}

