package eu.execom.todolistgrouptwo.preference;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * {@link android.content.SharedPreferences SharedPreferences} are used for storing primitive data
 * which will persist across user sessions.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface TaskStore {

    @DefaultString("[]")
    String tasks();
}
