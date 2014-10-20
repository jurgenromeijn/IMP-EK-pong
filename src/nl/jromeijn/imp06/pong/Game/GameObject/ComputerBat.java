package nl.jromeijn.imp06.pong.Game.GameObject;

import nl.jromeijn.imp06.pong.R;
import nl.jromeijn.imp06.pong.Activity.Settings;
import nl.jromeijn.imp06.pong.View.PlayField;
import android.content.Context;
import android.graphics.BitmapFactory;

public class ComputerBat extends Bat {

	private PlayField _playField;
	private Ball _ball;
	private Context _context;
	private float _maxSpeed = 1;
	
	/**
	 * Setup the bat for the computer
	 * 
	 * @param context	Context of the game
	 * @param playfield	The playField on which the bat is shown
	 * @param ball		The ball the computerbat needs to hit
	 */
	public ComputerBat(Context context, PlayField playfield, Ball ball)
	{
		
		_playField = playfield;
		_context = context;
		_ball = ball;
		_sprite = BitmapFactory.decodeResource(_context.getResources(), R.drawable.paddle_german);
		
		setY((_playField.getHeight() - _sprite.getHeight()) / 2);
		_x = _playField.getWidth() - _sprite.getWidth() - 50;
		
		setSpeed();
		
	}
	
	/**
	 * Set the maxspeed of the bat based on the dificulty
	 */
	private void setSpeed()
	{
		
		String dificulty = Settings.getDificulty(_context);
		
		if(dificulty.equals(_context.getResources().getString(R.string.PreferencesDificultyHardValue)))
		{
			
			_maxSpeed = 4;
			
		}
		else if(dificulty.equals(_context.getResources().getString(R.string.PreferencesDificultyEasyValue)))
		{
			
			_maxSpeed = 1;
			
		}
		else
		{
			 _maxSpeed = 2;
		}
		
	}
	
	/**
	 * Update the position of the computerbat, it will try to follow the ball at its maxpeed.
	 */
	@Override
	public void update()
	{
		
		float targetPositionY = _ball.getY() - ((_sprite.getHeight() - _ball.getHeight()) / 2);
		
		float deltaY = targetPositionY - _y;
		
		float newPositionY = _y;
		
		if(deltaY < -_maxSpeed)
		{
			
			newPositionY -= _maxSpeed;
			
		}
		else if(deltaY > _maxSpeed)
		{
			
			newPositionY += _maxSpeed;
			
		}
		else
		{
			
			newPositionY += deltaY;
			
		}
		
		_y = newPositionY;
				
	}
	
}
