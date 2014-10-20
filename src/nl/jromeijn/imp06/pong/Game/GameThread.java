package nl.jromeijn.imp06.pong.Game;

import android.graphics.Canvas;
import android.util.Log;
import nl.jromeijn.imp06.pong.View.PlayField;

public class GameThread extends Thread {

	 private PlayField _playField;
	 private boolean _running = false;
	 private int _desiredFramerateTime = (1/30) * 1000;
	 
	 /**
	  * Set whether this thread should be running
	  * 
	  * @param running	Boolean
	  */
	 public void setRunning(boolean running) 
	 {
		 
		 _running = running;
		 
	 }

	 /**
	  * This thread handles the gameloop. It will make the playfield update and draw
	  * 
	  * @param playField
	  */
	public GameThread(PlayField playField)
	{
		
		_playField = playField;
		
	}
	
	/**
	 * The gameloop, it calls the update method and drawmethod of the playField. It tries to stay at 30 fps.
	 */
	@Override
	public void run()
	{
		
		Canvas canvas;
				
		//The gameloop
		while(_running)
		{
			
			long startTime =  System.currentTimeMillis();

			_playField.update();
			
			canvas = _playField.getHolder().lockCanvas();
			
			_playField.draw(canvas);
			
			_playField.getHolder().unlockCanvasAndPost(canvas);
			
			long endTime = System.currentTimeMillis();
			int gameLoopTime = (int)(endTime - startTime);
			
			if(gameLoopTime < _desiredFramerateTime)
			{
				
				long sleepTime = _desiredFramerateTime - gameLoopTime;
				try
				{
					sleep(sleepTime); //Wait till this frame took 1/30 of a second
				}
				catch(Exception e)
				{
					Log.d(GameThread.class.getSimpleName(), e.toString());
				}
				
			}
						
		}
		
	}
	
}
