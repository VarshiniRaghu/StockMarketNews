package com.stock.marketnews;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stock.marketnews.fragment.NewsFeedFragment;
import com.stock.marketnews.utils.PreferenceController;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLoginButton();
    }
    public void initLoginButton(){
        if(PreferenceController.getInstance().isUserRegistered()) {
            ((Button)findViewById(R.id.loginButton)).setText(getResources().getText(R.string.login_button));
        } else {
            ((Button)findViewById(R.id.loginButton)).setText(getResources().getText(R.string.register_button));
        }
    }
    public void setButtonClick(View view){
        findViewById(R.id.login_error_display).setVisibility(View.GONE);
        validateLoginDetails(((EditText)findViewById(R.id.userName)).getText().toString(),
                ((EditText)findViewById(R.id.password)).getText().toString());
        hideKeyboard(findViewById(R.id.password));
    }

    private void validateLoginDetails(String userName, String password){
        if(userName.equals("") || password.equals("")) {
            ((TextView)findViewById(R.id.login_error_display))
                    .setText(getResources().getText(R.string.login_details_blank_display));
            findViewById(R.id.login_error_display).setVisibility(View.VISIBLE);
            return;
        }
        if(PreferenceController.getInstance().isUserRegistered()) {
            boolean value = PreferenceController.getInstance().validateCredentials(userName, password);
            if(value) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new NewsFeedFragment())
                        .commit();
            } else {
                ((TextView)findViewById(R.id.login_error_display))
                        .setText(getResources().getText(R.string.login_failed_display));
                findViewById(R.id.login_error_display).setVisibility(View.VISIBLE);
            }
        } else {
            PreferenceController.getInstance().registerUser(userName, password);
            Toast.makeText(this,getResources().getText(R.string.register_success_display),Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NewsFeedFragment())
                    .commit();
        }
    }

    public static void hideKeyboard(View view) {

        if (view == null) return;
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
