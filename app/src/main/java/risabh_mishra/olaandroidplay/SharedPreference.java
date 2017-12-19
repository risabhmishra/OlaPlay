package risabh_mishra.olaandroidplay;

/**
 * Created by Risabh Mishra on 12/17/2017.
 */


        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;
        import android.support.annotation.NonNull;

        import com.google.gson.Gson;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    List<Songs> favorites = new ArrayList<Songs>();

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Songs> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Songs songs) {
        favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Songs>();
        if(favorites.contains(songs)){saveFavorites(context, favorites);}
        else{
            favorites.add(songs);
            saveFavorites(context, favorites);
        }


    }

    public void removeFavorite(Context context, Songs songs) {
        favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(songs);
            saveFavorites(context, favorites);

        }
    }

    public ArrayList<Songs> getFavorites(Context context) {
        SharedPreferences settings;


        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Songs[] favoriteItems = gson.fromJson(jsonFavorites,
                    Songs[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Songs>(favorites) ;
        } else
            return null;

        return (ArrayList<Songs>) favorites;
    }
}