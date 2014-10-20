package nl.jromeijn.imp06.pong.Game.GameObject;

import java.util.Random;

import nl.jromeijn.imp06.pong.R;
import nl.jromeijn.imp06.pong.View.PlayField;
import android.content.Context;
import android.graphics.BitmapFactory;

public class Ball extends DrawableGameObject {

	private float _speedX = 0;
	private float _speedY = 0;
	private float _accelerationX = 0;
	private float _accelerationY = 0;
	private PlayField _playField;
	private Context _context;

	public float getSpeedX() {
		return _speedX;
	}

	public void setSpeedX(float speedX) {
		this._speedX = speedX;
	}

	public float getSpeedY() {
		return _speedY;
	}

	public void setSpeedY(float speedY) {
		this._speedY = speedY;
	}

	public float getAccelerationX() {
		return _accelerationX;
	}

	public void setAccelerationX(float accelerationX) {
		this._accelerationX = accelerationX;
	}

	public float getAccelerationY() {
		return _accelerationY;
	}

	public void setAccelerationY(float accelerationY) {
		this._accelerationY = accelerationY;
	}

	/**
	 * Setup the ball
	 * 
	 * @param context 	The context of the game
	 * @param playfield	The playfield on which the ball will be shown
	 */
	public Ball(Context context, PlayField playfield)
	{
		
		_playField = playfield;
		_context = context;
		_sprite = BitmapFactory.decodeResource(_context.getResources(), R.drawable.ball);
		
		reset();
		
	}
	
	/**
	 * Reset the position of the ball and calculate a random speed
	 */
	public void reset()
	{

		_x = (_playField.getWidth() - _sprite.getWidth()) / 2;
		_y = (_playField.getHeight() - _sprite.getHeight()) / 2;
		
		Random random = new Random();
		
		_speedX = (random.nextFloat() * 8) - 4;
		_speedY = (random.nextFloat() * 8) - 4;
		
	}
	
	/**
	 * Update the position of the ball
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		_speedX += _accelerationX;
		_speedY += _accelerationY;
		
		_x += _speedX;
		_y += _speedY;
	}

}
