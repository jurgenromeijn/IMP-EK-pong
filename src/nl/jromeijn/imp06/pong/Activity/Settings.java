package nl.jromeijn.imp06.pong.Activity;

import nl.jromeijn.imp06.pong.R;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity 
{
	
	private static final String SETTINGS_DIFICULTY = "dificulty";
	private static final int SETTINGS_DIFICULTY_DEFAULT = R.string.PreferencesDificultyNormalValue;
	
	public static final String PLAYER_BAT_HIT = "playerBatHit";
	public static final String PLAYER_GOALS = "playerGoals";
	public static final String COMPUTER_GOALS = "computerGoals";
	
	/**
	 * Get the dificulty from the settings
	 * 
	 * @param context	The context of the app
	 * @return			String with dificulty
	 */
	public static String getDificulty(Context context)
	{
		
		return PreferenceManager.getDefaultSharedPreferences(context).getString(SETTINGS_DIFICULTY, 
				context.getResources().getString(SETTINGS_DIFICULTY_DEFAULT));
		
	}
	
	/**
	 * Read the settingsfile and add it
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

}
