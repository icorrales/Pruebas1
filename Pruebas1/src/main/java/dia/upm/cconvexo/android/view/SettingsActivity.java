package dia.upm.cconvexo.android.view;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.pruebas1.R;


public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addPreferencesFromResource(R.xml.preferences);
    }
    @Override
    public void onBackPressed()
    {
       super.onBackPressed();
    }
}
/*
public class SettingsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .addToBackStack("settings")
                .commit();
    }

    boolean inSettings = false;

    @Override
    public void onBackPressed()
    {
        if (inSettings)
        {
            backFromSettingsFragment();
            return;
        }
        super.onBackPressed();
    }

    private void backFromSettingsFragment()
    {
        inSettings = false;
        getFragmentManager().popBackStack();
    }
}*/
