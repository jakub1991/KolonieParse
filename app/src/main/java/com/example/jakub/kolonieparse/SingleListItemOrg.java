package com.example.jakub.kolonieparse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class SingleListItemOrg extends ActionBarActivity {
    TextView nametext;
    TextView infotext;
    TextView pricetext;
    TextView timetext;
    TextView userlist;
    Button deleterec;
    String aaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_list_item_org);
        nametext=(TextView) findViewById(R.id.nametext);
        infotext=(TextView) findViewById(R.id.infyext);
        pricetext=(TextView) findViewById(R.id.pricetext);
        timetext=(TextView) findViewById(R.id.timetext);
        deleterec=(Button) findViewById(R.id.delbutton);
        userlist=(TextView) findViewById(R.id.userlist);
         aaa="";
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tour");
        query.whereEqualTo("objectId", getIntent().getExtras().getString("product"));
        final ProgressDialog dlg = new ProgressDialog(this);
        final ProgressDialog dlga = new ProgressDialog(this);
        dlg.setTitle("Czekaj.");
        dlg.setMessage("Wczytwanie.  Czekaj.");
        dlga.setTitle("Czekaj.");
        dlga.setMessage("Usuwanie.  Czekaj.");
        dlg.show();

        Log.d("Brand", "AAjhgjghjhgj ");
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {


                if (e == null) {
                    nametext.setText(objects.get(0).get("Name").toString());
                    infotext.setText("Opis: \n" + objects.get(0).get("Info").toString());
                    pricetext.setText( "Cena: " + Integer.toString(objects.get(0).getInt("Price"))+ "zł");
                    timetext.setText("Czas trawania :\n od " + objects.get(0).get("StartDate").toString() + " do " + objects.get(0).get("EndDate").toString());

                    ParseQuery a= objects.get(0).getRelation("User").getQuery();

                    a.findInBackground(new FindCallback<ParseObject>(){
                                           public void done(final List<ParseObject> obje, ParseException e) {
                                               dlg.dismiss();
                                               String names=("Lista zapisanych: \n"  );
                                               if (e == null) {

                                                 for (int i = 0; i < obje.size(); i++)
                                                        {
                                                       names= names+Integer.toString(i+1)+". "+obje.get(i).get("Name").toString()+" "+obje.get(i).get("Surname").toString()+"\n"+"Nr. tel. "+obje.get(i).get("NrTel").toString()+"\n\n";
                                                   }

                                               } else {

                                                   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                               }
                                               userlist.setText(names.toString());
                                           }}
                    )
                    ;











                    deleterec.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         try {
                                                              dlga.show();
                                                             objects.get(0).delete();
                                                             Intent refresh = new Intent(getApplicationContext(), OrganizerPanelActivty.class);
                                                             dlga.dismiss();
                                                             startActivity(refresh);

                                                             finish();
                                                             Toast.makeText(getApplicationContext(), "Usunieto", Toast.LENGTH_LONG).show();
                                                         } catch (ParseException e1) {
                                                             e1.printStackTrace();
                                                         }



                                                     }
                                                 }


                    );

                } else {
                    Log.d("Brand", "Error: " + e.getMessage());
                }



            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            System.exit(0);
        }
        else if(id==R.id.action_logout)
        {   ParseUser.logOut();
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Intent login = new Intent(getApplicationContext(), OrganizerPanelActivty.class);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(login);
        // Closing dashboard screen
        finish();
    }

}
