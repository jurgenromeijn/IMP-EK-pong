package nl.jromeijn.imp06.pong.Activity;

import nl.jromeijn.imp06.pong.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Info extends Activity implements OnClickListener {
    
	/**
	 * Set up the layout and setup the buttons
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info);
        
        setupButtons();
        
    }

    /**
     * Insert the buttons and add an event listener
     */
    private void setupButtons()
    {
    	
    	View closeButton = findViewById(R.id.InfoCloseButton);
    	closeButton.setOnClickListener(this);
    	
    }
    
    /**
     * Handle the click event
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