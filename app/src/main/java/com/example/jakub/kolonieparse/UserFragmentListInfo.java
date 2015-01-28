package com.example.jakub.kolonieparse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;



public class UserFragmentListInfo extends Fragment{
    private EditText nameView;
    private EditText surnameView;
    private EditText telView;
    private Button save;
    ParseUser currentUser;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_user_fragment_list_info, container, false);

        nameView = (EditText) view.findViewById(R.id.infName) ;
        surnameView = (EditText) view.findViewById(R.id.infSur) ;
        telView = (EditText) view.findViewById(R.id.infTel) ;
        save=(Button) view.findViewById(R.id.infbutton);
        final ProgressDialog dlg = new ProgressDialog(getActivity());
        final ProgressDialog dlga = new ProgressDialog(getActivity());
        dlg.setTitle("Czekaj.");
        dlg.setMessage("Zapisywanie.  Czekaj.");




        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            nameView.setText(currentUser.getString("Name").toString());
            surnameView.setText(currentUser.getString("Surname").toString());
            telView.setText(currentUser.getString("NrTel").toString());
        } else {
            // show the signup or login screen
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.show();
                currentUser.put("Name",nameView.getText().toString());
                currentUser.put("Surname", surnameView.getText().toString());
                currentUser.put("NrTel",telView.getText().toString());
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null) {
                            Intent refresh = new Intent(getActivity(), UserPanelActivity.class);
                            dlg.dismiss();
                            startActivity(refresh);

                        } else

                        { dlg.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
                }});










        return view;
    }


}