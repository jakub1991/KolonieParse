package com.example.jakub.kolonieparse;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;


public class SingleListItemUsr extends ActionBarActivity {

    TextView nametext;
    TextView infotext;
    TextView pricetext;
    TextView timetext;
    Button deleterec;
    String aaa;
    ParseObject tour;
    private Switch mySwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list_item_usr);
        nametext=(TextView) findViewById(R.id.nametext);
        infotext=(TextView) findViewById(R.id.infyext);
        pricetext=(TextView) findViewById(R.id.pricetext);
        timetext=(TextView) findViewById(R.id.timetext);
        deleterec=(Button) findViewById(R.id.delbutton);
        aaa="";
        mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    ParseObject list = new ParseObject("tour");
                    ParseRelation<ParseObject> relation = tour.getRelation("User");
                    relation.add(ParseUser.getCurrentUser());
                    tour.saveInBackground();
                }else{
                    ParseRelation<ParseObject> relation = tour.getRelation("User");
                    relation.remove(ParseUser.getCurrentUser());
                    tour.saveInBackground();
                }

            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tour");
        query.whereEqualTo("objectId", getIntent().getExtras().getString("product"));

        Log.d("Brand", "AAjhgjghjhgj ");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {


                if (e == null) {
                    nametext.setText(objects.get(0).get("Name").toString());
                    infotext.setText("Opis: \n" + objects.get(0).get("Info").toString());
                    pricetext.setText(Integer.toString(objects.get(0).getInt("Price")));
                    timetext.setText("Czas trwania:\n od " + objects.get(0).get("StartDate").toString() + " do " + objects.get(0).get("EndDate").toString());
                    tour=objects.get(0);
                    ParseQuery a= objects.get(0).getRelation("User").getQuery();
                    a.whereEqualTo("objectId",ParseUser.getCurrentUser().getObjectId().toString());
                    a.findInBackground(new FindCallback<ParseObject>(){
                        public void done(final List<ParseObject> obje, ParseException e) {
                            if (e == null) {
                                if(obje.isEmpty()) {
                                    mySwitch.setChecked(false);
                                    Log.d("Brand", "false");
                                }
                                else {
                                    mySwitch.setChecked(true);
                                    Log.d("Brand", "true");
                                }

                            } else {
                                Log.d("Brand", "erorr");

                            }
                        }}
                    )
                    ;


                } else {
                    Log.d("Brand", "Error: " + e.getMessage());
                }


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_list_item_org, menu);
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
