package nl.jromeijn.imp06.pong.Game.GameObject;

import nl.jromeijn.imp06.pong.R;
import nl.jromeijn.imp06.pong.View.PlayField;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

public class PlayerBat extends Bat 
{

	private PlayField _playField;
	private MediaPlayer _mediaPlayer;
	private Context _context;
	
	/**
	 * The bat the player will be able to use
	 * 
	 * @param context	The context of the game
	 * @param playfield	The playfield on which all the magic happens
	 */
	public PlayerBat(Context context, PlayField playfield)
	{
		
		_playField = playfield;
		_context = context;
		_sprite = BitmapFactory.decodeResource(_context.getResources(), R.drawable.paddle_dutch);
		
		setY(_playField.getHeight() / 2);
		_x = 50;
		
	}
	
	/**
	 * Set the Y, limits it to inside the playfield and play a sound if the player tries to go out.
	 */
	@Override
	public void setY(float y)
	{
				
		y -= _sprite.getHeight() / 2;
		
		boolean playBumpSound = false;
		
		if(y <= 0)
		{
			
			y = 0;
			playBumpSound = true;
			
		}
		else if(y >= (_playField.getHeight() - _sprite.getHeight()))
		{
			
			y = _playField.getHeight() - _sprite.getHeight();
			playBumpSound = true;
			
		}
		
		_y = y;
		
		if(playBumpSound)
		{
			
			if(_mediaPlayer == null || (_mediaPlayer != null && !_mediaPlayer.isPlaying()))
			{
			
				if(_mediaPlayer != null) _mediaPlayer.release();
					
					//play bump sound
					_mediaPlayer = MediaPlayer.create(_context, R.raw.batbump);
					_mediaPlayer.start();
			
			}
			
		}
							
	}
	
}
