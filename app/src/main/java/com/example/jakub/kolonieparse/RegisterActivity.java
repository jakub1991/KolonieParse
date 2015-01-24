package com.example.jakub.kolonieparse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class RegisterActivity extends ActionBarActivity {
    private EditText usernameView;
    private EditText passwordView;
    private EditText passwordAgainView;
    private RadioGroup radioTypeGroup;
    private RadioButton radioTypeButton;
    private RadioButton Radio1;
    private RadioButton Radio2;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the signup form.
        usernameView = (EditText) findViewById(R.id.registerLogin);
        passwordView = (EditText) findViewById(R.id.registerPassword1);
        passwordAgainView = (EditText) findViewById(R.id.registerPassword2) ;
        radioTypeGroup = (RadioGroup) findViewById(R.id.Type);


        // Set up the submit button click handler
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Validate the sign up data
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(usernameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_username));
                }
                if (isEmpty(passwordView)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
                }
                if (!isMatching(passwordView, passwordAgainView)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(
                            R.string.error_mismatched_passwords));
                }



                validationErrorMessage.append(getResources().getString(R.string.error_end));

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(RegisterActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(RegisterActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();

                // find the radiobutton by returned id

                // Set up a new Parse user
                ParseUser user = new ParseUser();
                user.setUsername(usernameView.getText().toString());
                user.setPassword(passwordView.getText().toString());

                // Call the Parse signup method
                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            int selectedId = radioTypeGroup.getCheckedRadioButtonId();
                            RadioButton Radio1 = (RadioButton) findViewById(R.id.radioButton);
                            RadioButton Radio2 = (RadioButton) findViewById(R.id.radioButton2);
                            // Start an intent for the dispatch activity
                            if(Radio1.getId()==selectedId) {
                                ParseUser.getCurrentUser().put("UserType", "user");
                                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Log.d("TAG", "SAVE SUCCESSFUL");

                                        } else {
                                            Log.d("TAG", "SAVE FAILED " + e.getCause());
                                        }
                                    }
                                });
                                Intent intent = new Intent(RegisterActivity.this, UserPanelActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else if(Radio2.getId()==selectedId) {
                                ParseUser.getCurrentUser().put("UserType", "organizer");
                                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Log.d("TAG", "SAVE SUCCESSFUL");

                                        } else {
                                            Log.d("TAG", "SAVE FAILED " + e.getCause());
                                        }
                                    }
                                });
                                Intent intent = new Intent(RegisterActivity.this, OrganizerPanelActivty.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                        }
                    }
                });
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
