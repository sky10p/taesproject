package taes.project.dressyourself;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseACL;

import taes.project.dressyourself.classes.Categoria;

/**
 * Conexion con Parse.com
 */
public class ParseApplication extends Application {
    private static final String APP_ID = "s90VyHtWPNx9cgndGgresmqQxjT1drJRcRNwBFzX";
    private static final String CLIENT_KEY = "WJRi26caKuA8QAmDiy9Xm1eB79fE950wMM2hHDCc";
    @Override
    public void onCreate() {
        super.onCreate();

        //Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Categoria.class);
        Parse.initialize(this, APP_ID, CLIENT_KEY);
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
