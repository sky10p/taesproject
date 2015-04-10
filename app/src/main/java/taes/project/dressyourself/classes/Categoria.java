package taes.project.dressyourself.classes;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import taes.project.dressyourself.adapter.AdapterCategoria;

/**
 * Created by isma on 7/04/15.
 */
@ParseClassName("Categoria")
public class Categoria extends ParseObject {

    public Categoria(){
        super();
    }
    public String getNombre()
    {
        return getString("nombre");
    }
    public void setNombre(String nombre)
    {
        put("nombre", nombre);
    }
    public static List<Categoria> getAllByUser(ParseUser user, final AdapterCategoria.AdapterCategoriaCallback callback)
    {
        final List<Categoria> categorias = new ArrayList<>();
        ParseQuery<Categoria> query = ParseQuery.getQuery("Categoria");
        query.whereEqualTo("createdBy", user);
        query.orderByAscending("nombre");
        query.findInBackground(new FindCallback<Categoria>() {
            public void done(List<Categoria> cats, ParseException e) {
                if (e == null) {
                    categorias.addAll(cats);
                    callback.onDataLoaded();
                } else {
                    Log.d("categorias", "Error: " + e.getMessage());
                }
            }
        });
        return categorias;
    }
}
