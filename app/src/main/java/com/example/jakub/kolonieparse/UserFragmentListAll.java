package com.example.jakub.kolonieparse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;



public class UserFragmentListAll extends Fragment {
    private ListView List;
    private ArrayAdapter<String> arrayAdapterUser;
    java.util.List<ParseObject> objects_all;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_list_all, container, false);
        List = (ListView) view.findViewById(R.id.listUserAll);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tour");
        query.findInBackground(new FindCallback<ParseObject>() {
                                   public void done(List<ParseObject> objects, ParseException e) {
                                       if (e == null) {
                                           String[] aaa = new String[objects.size()];
                                           for (int i = 0; i < objects.size(); i++) {

                                               Log.d("Brand", objects.get(i).get("Name").toString());

                                               aaa[i] = "Miejsce: " + objects.get(i).get("Name").toString() + ",\n Data rozpoczecia :" + objects.get(i).get("StartDate").toString();
                                               // use dealsObject.get('columnName') to access the properties of the Deals object.
                                               Log.d("Brand", aaa[i].toString());

                                           }
                                           objects_all = objects;

                                           arrayAdapterUser = new ArrayAdapter<String>(getActivity(), R.layout.item, aaa);
                                           List.setAdapter(arrayAdapterUser);
                                       } else {
                                           Log.d("Brand", "Error: " + e.getMessage());
                                       }

                                   }
                               });


                List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {


                        // Launching new Activity on selecting single List Item
                        Log.d("sdadsadsadsad", objects_all.get(position).getObjectId());
                        Intent i = new Intent(getActivity(), SingleListItemUsr.class);
                        // sending data to new activity
                        i.putExtra("product", objects_all.get(position).getObjectId().toString());
                        startActivity(i);
                        getActivity().finish();

                    }
                });


        return view;
    }


}


