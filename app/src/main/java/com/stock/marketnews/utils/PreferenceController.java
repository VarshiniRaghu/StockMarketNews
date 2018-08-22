package com.stock.marketnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.stock.marketnews.StockMarketNewsApplication;

/**
 * Created by Varshini on 19/08/2018.
 */

public class PreferenceController {
    public static final class Keys {
        public static final String FILE_NAME_KEY = "login_credentials_file";
        public static final String USER_NAME_KEY = "user_name";
        public static final String PASSWORD_KEY = "password";
    }
    private static PreferenceController mInstance;

    private PreferenceController() {

    }

    public static PreferenceController getInstance() {
        if(mInstance == null) {
            mInstance = new PreferenceController();
        }
        return mInstance;
    }

    public boolean validateCredentials(String userName, String password){
        SharedPreferences sharedPref = StockMarketNewsApplication.getAppContext().getSharedPreferences(
                Keys.FILE_NAME_KEY, Context.MODE_PRIVATE);
        if(userName.equals(sharedPref.getString(Keys.USER_NAME_KEY, ""))
                && (password.equals(sharedPref.getString(Keys.PASSWORD_KEY, "")))){
            return true;
        }
        return false;
    }

    public boolean isUserRegistered(){
        SharedPreferences sharedPref = StockMarketNewsApplication.getAppContext().getSharedPreferences(
                Keys.FILE_NAME_KEY, Context.MODE_PRIVATE);
        if(!sharedPref.getString(Keys.USER_NAME_KEY, "").equals("")){
            return true;
        }
        return false;
    }

    public void registerUser(String userName, String password){
        if(!isUserRegistered()){
            writeToSharedPreference(userName,password);
        }
    }

    public void writeToSharedPreference(String userName, String password){
        SharedPreferences sharedPref = StockMarketNewsApplication.getAppContext().getSharedPreferences(
                Keys.FILE_NAME_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Keys.USER_NAME_KEY, userName);
        editor.putString(Keys.PASSWORD_KEY, password);
        editor.commit();
    }
}
