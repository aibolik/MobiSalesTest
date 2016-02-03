package kz.aibol.mobisalestest.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aibol on 2/3/16.
 */
public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private Context mContext;

    private static final String PREF_NAME = "MobiSales";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

}
