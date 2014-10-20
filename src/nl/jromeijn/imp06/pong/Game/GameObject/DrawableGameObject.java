package nl.jromeijn.imp06.pong.Game.GameObject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class DrawableGameObject {

	protected float _x = 0;
	protected float _y = 0;
	protected Bitmap _sprite;

	public float getX() {
		return _x;
	}
	
	public void setX(float x) {
		this._x = x;
	}
	
	public float getY() {
		return _y;
	}
	
	public void setY(float y) {
		this._y = y;
	}
	
	public int getWidth()
	{
		return _sprite.getWidth();
	}
	
	public int getHeight()
	{
		return _sprite.getHeight();
	}
	
	public abstract void update();
	
	/**
	 * Draw the sprite on the canvas
	 * 
	 * @param canvas	Canvas on which the sprite has to be drawn
	 */
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(_sprite, _x, _y, null);
	}
	
}
