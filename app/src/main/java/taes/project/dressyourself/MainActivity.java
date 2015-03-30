package taes.project.dressyourself;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ver si el usuario actual es un usuario anónimo
        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){
            // si es anónimo le mostramos el lógin
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            // no es anónimo le llevamos a la aplicación principal
            Intent intent = new Intent(this,DressYourSelfActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
