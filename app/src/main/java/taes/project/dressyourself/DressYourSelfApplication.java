package taes.project.dressyourself;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by pablo on 9/03/15.
 */
public class DressYourSelfApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "s90VyHtWPNx9cgndGgresmqQxjT1drJRcRNwBFzX", "WJRi26caKuA8QAmDiy9Xm1eB79fE950wMM2hHDCc");

    }
}
