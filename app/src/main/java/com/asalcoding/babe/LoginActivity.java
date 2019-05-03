package com.asalcoding.babe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration TextInputLayout
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    //Declaration Button
    Button buttonLogin;

    //Declaration SqliteHelper
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqliteHelper = new SqliteHelper(this);
        initCreateAccountTextView();
        initViews();

        //set click event of login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check user input is correct or not
                if (validate()) {

                    //Get values from EditText fields
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Authenticate user
                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password));

                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Snackbar.make(buttonLogin, "Successfully Logged in!", Snackbar.LENGTH_LONG).show();

                        //User Logged in Successfully Launch You home screen activity
                        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        //User Logged in Failed
                        Snackbar.make(buttonLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>I don't have account yet. </font><font color='#0c0099'>create one</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

    }

    //This method is for handling fromHtml method deprecation
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
            textInputLayoutEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() >= 8) {
                valid = true;
                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
                textInputLayoutPassword.setError("Password is to short!");
            }
        }

        return valid;
    }


}

