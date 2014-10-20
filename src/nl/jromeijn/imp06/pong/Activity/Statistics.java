package nl.jromeijn.imp06.pong.Activity;

import nl.jromeijn.imp06.pong.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Statistics extends Activity implements OnClickListener {

	private int _playerPaddleHits = 0;
	private int _playerGoals = 0;
	private int _computerGoals = 0;
	
	private SharedPreferences _preferences;
	
	/**
	 * get the statistics from the shared preferences
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        
        _preferences = getSharedPreferences("stats", MODE_PRIVATE);
        
        _playerPaddleHits = _preferences.getInt(Settings.PLAYER_BAT_HIT, 0);
        _playerGoals = _preferences.getInt(Settings.PLAYER_GOALS, 0);
        _computerGoals = _preferences.getInt(Settings.COMPUTER_GOALS, 0);
        
        TextView playerPaddleHitView = (TextView)findViewById(R.id.StatisticsPlayerHitsValue);
        TextView playerGoalsView = (TextView)findViewById(R.id.StatisticsPlayerGoalsValue);
        TextView computerGoalsView = (TextView)findViewById(R.id.StatisticsComputerGoalsValue);
        
        playerPaddleHitView.setText(Integer.toString(_playerPaddleHits));
        playerGoalsView.setText(Integer.toString(_playerGoals));
        computerGoalsView.setText(Integer.toString(_computerGoals));
        
        setupButtons();
        
    }

	/**
	 * Setup the close button
	 */
    private void setupButtons()
    {
    	
    	View closeButton = findViewById(R.id.InfoCloseButton);
    	closeButton.setOnClickListener(this);
    	
    }
    
    /**
     * Handle click events
     */
	public void onClick(View v) {
		
		switch(v.getId())
		{
		
			case R.id.InfoCloseButton:
				finish();
				break;
		
		}
		
	}

}
