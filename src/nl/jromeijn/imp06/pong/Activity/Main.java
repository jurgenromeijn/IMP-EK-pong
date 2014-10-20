package nl.jromeijn.imp06.pong.Activity;

import nl.jromeijn.imp06.pong.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Main extends Activity implements OnClickListener {
	
	/**
	 * Setup the layout and the buttons
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Log.d(getPackageName(), "start");
		
        setupButtons();
        
    }
    
    /**
     * Setup the buttons and add the event listener
     */
    private void setupButtons()
    {
    	
    	View closeButton = findViewById(R.id.MainStartGameButton);
    	closeButton.setOnClickListener(this);
    	
    }
    
    /**
     * Handle the click events
     */
	public void onClick(View v) {
		
		switch(v.getId())
		{
		
			case R.id.MainStartGameButton:
				Intent pong = new Intent(this, Pong.class);
				startActivity(pong);
				break;
		}
		
	}
    
	/**
	 * Show the menu when needed
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		
		return true;
		
	}
	
	/**
	 * handle a selected option
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		boolean returnValue = false;
		
		Log.d(getPackageName(), item.getTitle().toString());
				
		switch(item.getItemId())
		{
			case R.id.infoMenu:
				Intent info = new Intent(this, Info.class);
				startActivity(info);
				returnValue = true;
				break;
			case R.id.statisticsMenu:
				Intent statistics = new Intent(this, Statistics.class);
				startActivity(statistics);
				returnValue = true;
				break;
			case R.id.settingsMenu:
				Intent settings = new Intent(this, Settings.class);
				startActivity(settings);
				returnValue = true;
				break;
			case R.id.exitMenu:
				finish();
				returnValue = true;
				break;
			default:
				returnValue = false;
				break;
		}
				
		return returnValue;
		
	}

    
}