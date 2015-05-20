package taes.project.dressyourself.interfaces;

import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by Pablo on 20/05/2015.
 */
public interface OnLoadedFriends {
    void loaded(ArrayList<ParseUser> amigos);
}
