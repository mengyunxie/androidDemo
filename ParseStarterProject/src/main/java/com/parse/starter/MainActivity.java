/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  Boolean signUpModeActivity = true;
  TextView changeModeTextView;
  EditText passwordEditText;

  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {
    if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN) {

      signUp(view);
    }
    return false;
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.changeModeTextView) {
      Button signupButton  = (Button) findViewById(R.id.signupButton);
      if (signUpModeActivity) {
        signUpModeActivity = false;
        signupButton.setText("Login");
        changeModeTextView.setText("Or, Sign Up");
      } else {
        signUpModeActivity = true;
        signupButton.setText("Sign Up");
        changeModeTextView.setText("Or, Login");
      }
    } else if (view.getId() == R.id.backgroundLayout || view.getId() == R.id.logoImageView) {
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }

  public void showUserList() {
    Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
    startActivity(intent);
  }

  public void signUp(View view) {
    EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
    if (usernameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("")) {
      Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();
    } else {
      if (signUpModeActivity) {
        System.out.println("inner, here - - - - - 1");
        ParseUser user = new ParseUser();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        user.signUpInBackground(new SignUpCallback() {

          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("signUp", "successful");
              showUserList();
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      } else {
        System.out.println("inner, here - - - - - 2");
        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if (user != null) {
              Log.i("signup", "Login successful");
              showUserList();
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setTitle("Instagram");

    changeModeTextView = (TextView) findViewById(R.id.changeModeTextView);
    changeModeTextView.setOnClickListener(this);

    RelativeLayout backgroundLayout = (RelativeLayout) findViewById(R.id.backgroundLayout);
    backgroundLayout.setOnClickListener(this);

    ImageView logoImageView = (ImageView) findViewById(R.id.logoImageView);
    logoImageView.setOnClickListener(this);

    passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    passwordEditText.setOnKeyListener(this);

    if (ParseUser.getCurrentUser() != null) {
      showUserList();
    }

    /*
    // log out
    ParseUser.logOut();

    if (ParseUser.getCurrentUser() != null) {
      Log.i("currentUser", "User logged in " + ParseUser.getCurrentUser().getUsername());
    } else {
      Log.i("currentUser", "User not logged in");
    }
     */


/*
    // create a user
    ParseUser user = new ParseUser();
    user.setUsername("mimiya1");
    user.setPassword("1234");

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.i("sign up", "successful");
        } else {
          Log.i("sign up", "failed");
        }
      }
    });

 */


    /*
    // log in
    ParseUser.logInInBackground("mimiya", "1234", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if (user != null) {
          Log.i("Login", "successful");
        } else {
          Log.i("Login", "failed: " + e.toString());
        }
      }
    });
     */



    /*
    //create a table
    ParseObject score = new ParseObject("Score");
    score.put("username", "miya");
    score.put("score", 90);
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.i("SaveInBackground", "Successful");
        } else {
          Log.i("SaveInBackground", "Failed. Error: " + e.toString());
        }

      }
    });
     */

    /*
    // query and update
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("FNPIVqyPZB", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if (e == null && object != null) {

          object.put("score", 100);
          object.saveInBackground();
          Log.i("ObjectValue", object.getString("username"));
          Log.i("ObjectValue", Integer.toString(object.getInt("score")));
        } else {
          Log.i("ObjectValue", "Failed. Error: " + e.toString());
        }
      }
    });
    */

    /*
    // filter and update
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

    // special query
    // query.whereEqualTo("username", "miya");
    // query.setLimit(1);
    query.whereGreaterThan("score", 90);

    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null && objects != null) {
          // Log.i("findInBackground", "Retrieved " + objects.size() + " objects");
            for (ParseObject object : objects) {
              // Log.i("findInBackgroundResult", object.toString());
              object.put("score", object.getInt("score") + 3);
              object.saveInBackground();
            }
        }
      }
    });
     */

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}