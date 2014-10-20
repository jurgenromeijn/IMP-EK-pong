package nl.jromeijn.imp06.pong.View;

import java.util.ArrayList;

import nl.jromeijn.imp06.pong.R;
import nl.jromeijn.imp06.pong.Activity.Pong;
import nl.jromeijn.imp06.pong.Game.GameThread;
import nl.jromeijn.imp06.pong.Game.GameObject.Ball;
import nl.jromeijn.imp06.pong.Game.GameObject.ComputerBat;
import nl.jromeijn.imp06.pong.Game.GameObject.DrawableGameObject;
import nl.jromeijn.imp06.pong.Game.GameObject.PlayerBat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PlayField extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener
{

	private ArrayList<DrawableGameObject> _gameObjects;
	private GameThread _gameThread;
	private Bitmap _background = null;
	private int _backgroundOffset = 0;
	private boolean _initialized = false;
	private PlayerBat _playerBat;
	private ComputerBat _computerBat;
	private Ball _ball;
	private MediaPlayer _mediaPlayer;
	private int _playerScore = 0;
	private int _computerScore = 0;
	private Pong _controller;

	/**
	 * Setup the playfield
	 * 
	 * @param controller	The Pong activity
	 */
	public PlayField(Pong controller) 
	{
		super((Context)controller);
		
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true); 
		
        _controller  = controller;
		_gameObjects = new ArrayList<DrawableGameObject>();
		_gameThread  = new GameThread(this);
		
		Log.d(PlayField.class.getSimpleName(), "Playfield aangemaakt");
				
	}
	
	/**
	 * Initialize the game when the canvas has been created
	 */
	private void init()
	{
		
		scaleBackground();
						
		_ball = new Ball(getContext(), this);
		_gameObjects.add(_ball);

		_playerBat = new PlayerBat(getContext(), this);
		_gameObjects.add(_playerBat);

		_computerBat = new ComputerBat(getContext(), this, _ball);
		_gameObjects.add(_computerBat);
		
	}
	
	/**
	 * Scale the background so it will fit the screen and will be centered
	 */
	private void scaleBackground()
	{
	    Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.playfield);
	    float scale = background.getHeight()/getHeight();
	    int newWidth = Math.round(background.getWidth()/scale);
	    int newHeight = Math.round(background.getHeight()/scale);
	    _background = Bitmap.createScaledBitmap(background, newWidth, newHeight, true);

	    _backgroundOffset = Math.round((getWidth() - newWidth) / 2);
	    
	}
	
	/**
	 * Handle touchevents and move the playerbat accordingly
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		
		_playerBat.setY(event.getY());

		boolean eventHandled = false;
		
		if(event.getAction() == MotionEvent.ACTION_DOWN || 
				event.getAction() == MotionEvent.ACTION_DOWN || 
				event.getAction() == MotionEvent.ACTION_DOWN)
		{
			_playerBat.setY(event.getY());			
			eventHandled = true;			
		}
		
		return eventHandled;
		
	}

    //Implemented as part of the SurfaceHolder.Callback interface
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

    //Implemented as part of the SurfaceHolder.Callback interface
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		_gameThread.setRunning(true);
		_gameThread.start();
	}

    //Implemented as part of the SurfaceHolder.Callback interface
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		_gameThread.setRunning(false);
		_gameThread.stop();
	}
	
	/**
	 * Update all gameobjects and do the game logic accordingly
	 */
	public void update()
	{
		
		if(_initialized)
		{
		
		    for (DrawableGameObject gameObject : _gameObjects) {
				
		    	gameObject.update();
		    	
			}

		    boolean playPointScoredSound = false;
		    boolean playBumpSound = false;		    
		    
		    //Game logic here
		    if(_ball.getY() <= 0)
		    {
		    	_ball.setSpeedY(-_ball.getSpeedY());
		    	_ball.setY(-_ball.getY());
		    	playBumpSound = true;
		    }
		    else if(_ball.getY() >= (getHeight() - _ball.getHeight()))
		    {
		    	_ball.setSpeedY(-_ball.getSpeedY());
		    	_ball.setY(-_ball.getY() + (2 * (getHeight() - _ball.getHeight()))); 
		    	playBumpSound = true;
		    }
		    
		    //Check for ball in field
		    if(_ball.getX() < -_ball.getWidth())
		    {
		    	
		    	//point for computer
		    	_ball.reset();
		    	playPointScoredSound = true;
		    	_computerScore++;
		    	_controller.addComputerGoal();
		    	
		    }
		    
		    if(_ball.getX() > getWidth())
		    {
		    	
		    	//point got player
		    	_ball.reset();
		    	playPointScoredSound = true;
		    	_playerScore++;
		    	_controller.addPlayerGoal();
		    	
		    }
		    
		    //Check bat colission player bat
		    if(_ball.getSpeedX() < 0 && 
		    		_ball.getY() > (_playerBat.getY() - (_ball.getHeight())) &&
		    		_ball.getY() < (_playerBat.getY() + _playerBat.getHeight()) &&
		    		_ball.getX() <= (_playerBat.getX() + _playerBat.getWidth()) &&
		    		_ball.getX() >= (_playerBat.getX()))
		    {
		    	
		    	_ball.setSpeedX(-_ball.getSpeedX());
		    	playBumpSound = true;
		    	
		    	_controller.addPaddleHit();
		    	
		    }

		    //Check bat colissions computer
		    if(_ball.getSpeedX() > 0 && 
		    		_ball.getY() > (_computerBat.getY() - (_ball.getHeight())) &&
		    		_ball.getY() < (_computerBat.getY() + _computerBat.getHeight()) &&
		    		(_ball.getX() + _ball.getWidth()) <= (_computerBat.getX() + _computerBat.getWidth()) &&
    				(_ball.getX() + _ball.getWidth()) >= (_computerBat.getX()))
		    {
		    	
		    	_ball.setSpeedX(-_ball.getSpeedX());
		    	playBumpSound = true;
		    	
		    }
		    
		    if(playBumpSound)
		    {
		    	
		    	if(_mediaPlayer != null) _mediaPlayer.release();
		    	
		    	_mediaPlayer = MediaPlayer.create(getContext(), R.raw.ballbump);
		    	_mediaPlayer.start();
		    	
		    }
		    
		    if(playPointScoredSound)
		    {
		    	
		    	if(_mediaPlayer != null) _mediaPlayer.release();
		    	
		    	_mediaPlayer = MediaPlayer.create(getContext(), R.raw.pointscored);
		    	_mediaPlayer.start();
		    	
		    }
	    
		}
		
	}
	
	/**
	 * Draw all objects and the score
	 */
	public void draw(Canvas canvas)
	{
		
		if(!_initialized)
		{
			
			init();
			_initialized = true;
			
		}
		
	    canvas.drawBitmap(_background, _backgroundOffset, 0, null); // draw the background
	    
	    for (DrawableGameObject gameObject : _gameObjects) {
			
	    	gameObject.draw(canvas);
	    	
		}
	    
	    Paint textPaint = new Paint();
	    textPaint.setColor(Color.WHITE);
	    textPaint.setTextAlign(Align.CENTER);
	    textPaint.setTextSize(100);
	    
	    canvas.drawText(Integer.toString(_playerScore), (getWidth() / 4), 150, textPaint);
	    canvas.drawText(Integer.toString(_computerScore), 3 * (getWidth() / 4), 150, textPaint);
		
	}
	
	/**
	 * implemented because it is required by the SensorEventListener interface, but not required for this game.
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * handle the sensor event. Use it to set acceleration on the ball.
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		switch(event.sensor.getType())
		{
			case Sensor.TYPE_ORIENTATION:
				if(_ball != null)
				{
					
					float[] sensorValues = event.values;
					_ball.setAccelerationY(sensorValues[2] / 100);
					_ball.setAccelerationX(-sensorValues[1] / 100);
					
				}
				break;
			default:
				//do nothing
				break;
		}
		
	}
	
}
