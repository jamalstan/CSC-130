/* This is a way to pass a sprite's key information in one entity. (x, y, tag) */

package Data;

public class spriteInfo {
	// Fields
	private Vector2D v2d;
	private String tag;

	// Constructor
	public spriteInfo(Vector2D v2d, String tag) {
		this.v2d = new Vector2D(v2d.getX(), v2d.getY());
		this.tag = tag;
	}
	
	// Methods
	public String getTag() {
		return this.tag;
	}
	
	public Vector2D getCoords() {
		return this.v2d;
	}
	
	public void setTag(String newTag) {
		this.tag = newTag;
	}
	
	public void setCoords(Vector2D newV2D) {
		this.v2d = new Vector2D(newV2D.getX(), newV2D.getY());
	}
	
	public void setCoords(int x, int y) {
		this.v2d.setX(x);
		this.v2d.setY(y);
	}
	
	public String toString() {
		return String.format("[%d, %d, %s]", this.v2d.getX(), this.v2d.getY(), this.tag);
	}
}
