package taes.project.dressyourself;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.ParseACL;

/**
 * Conexion con Parse.com
 */
public class ParseApplication extends Application {
    private static final String APP_ID = "s90VyHtWPNx9cgndGgresmqQxjT1drJRcRNwBFzX";
    private static final String CLIENT_KEY = "WJRi26caKuA8QAmDiy9Xm1eB79fE950wMM2hHDCc";
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, APP_ID, CLIENT_KEY);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
