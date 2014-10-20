package nl.jromeijn.imp06.pong.Activity;

import nl.jromeijn.imp06.pong.View.PlayField;
import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

public class Pong extends Activity {

    private SensorManager _sensorManager;
    private Sensor _orientationSensor;
    private PlayField _playField;
    
	private int _playerPaddleHits = 0;
	private int _playerGoals = 0;
	private int _computerGoals = 0;
    
	private SharedPreferences _preferences;
	
	/**
	 * Setup the playfield and get the preferences
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _playField = new PlayField(this);
        setContentView(_playField);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        _sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        _orientationSensor = _sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
       
        Log.d(Pong.class.getSimpleName(), "start pong");
        
        _preferences = getSharedPreferences("stats", MODE_PRIVATE);
        
        _playerPaddleHits = _preferences.getInt(Settings.PLAYER_BAT_HIT, 0);
        _playerGoals = _preferences.getInt(Settings.PLAYER_GOALS, 0);
        _computerGoals = _preferences.getInt(Settings.COMPUTER_GOALS, 0);
                
    }
    
    /**
     * Update statistics
     */
    public void addPaddleHit()
    {
    	_playerPaddleHits++;
    	_preferences.edit().putInt(Settings.PLAYER_BAT_HIT, _playerPaddleHits).commit();
    }

    /**
     * Update statistics
     */
    public void addPlayerGoal()
    {
    	_playerGoals++;
    	_preferences.edit().putInt(Settings.PLAYER_GOALS, _playerGoals).commit();
    }
    
    /**
     * Update statistics
     */
    public void addComputerGoal()
    {
    	_computerGoals++;
    	_preferences.edit().putInt(Settings.COMPUTER_GOALS, _computerGoals).commit();
    }
    
    /**
     * Add sensor event listener when the program resumes
     */
    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(_playField, _orientationSensor, SensorManager.SENSOR_DELAY_GAME);
    }
    
    /**
     * Remove sensor event listener when paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(_playField);        
    }
	
}
