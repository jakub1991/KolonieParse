package com.example.jakub.kolonieparse;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.Calendar;


public class OrganizerFragmentAddNew extends Fragment  {

    private Button addnewbt;

    private EditText namenew;
    private EditText newprice;
    private EditText newend;
    private EditText newstart;
    private EditText newinfo;

    private ImageButton imgstart;
    private ImageButton imgend;

    private Calendar cal;
    private int day;
    private int month;
    private int year;


    DatePickerDialog.OnDateSetListener from_dateListener,to_dateListener;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizer_fragment0, container, false);

        addnewbt = (Button) view.findViewById(R.id.buttonaddnew);

        namenew = (EditText) view.findViewById(R.id.addname);
        newinfo = (EditText) view.findViewById(R.id.addinfo);
        newprice = (EditText) view.findViewById(R.id.addprice);
        newend = (EditText) view.findViewById(R.id.addend);
        newstart = (EditText) view.findViewById(R.id.addstart);

        imgstart = (ImageButton) view.findViewById(R.id.imageButton);
        imgend = (ImageButton) view.findViewById(R.id.imageButton2);

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);





        addnewbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(namenew)) {
                    validationError = true;
                    validationErrorMessage.append("wpisz nazwe");
                }
                if (isEmpty(newinfo)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append("wpisz opis");
                }

                if (isEmpty(newprice)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append("podaj cene");
                }

                if(!(TextUtils.isDigitsOnly(newprice.getText()))&&!isEmpty(newprice)){
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append("podaj liczby w polu cena");
                }


                if (isEmpty(newstart)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append("wpisz date rozpoczencia");
                }

                if (isEmpty(newend)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append("wpisz datezakonczenia");
                }




                validationErrorMessage.append(getResources().getString(R.string.error_end));

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(getActivity(), validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }







                final ProgressDialog dlg = new ProgressDialog(getActivity());
                dlg.setTitle("Czekaj.");
                dlg.setMessage("Dodawanie.  Czekaj.");
                dlg.show();

                ParseObject tour = new ParseObject("Tour");
                tour.put("Name", namenew.getText().toString());
                tour.put("Info", newinfo.getText().toString());
                tour.put("Price", Integer.parseInt(newprice.getText().toString()));
                tour.put("StartDate",newstart.getText().toString());
                tour.put("EndDate",newend.getText().toString());
                tour.put("createdBy", ParseUser.getCurrentUser());

                tour.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.d("TAG", e.getMessage());


                        } else {
                            Intent refresh = new Intent(getActivity(), OrganizerPanelActivty.class);
                            startActivity(refresh);
                            getActivity().finish();
                            Toast.makeText(getActivity(), "Dodano do bazy", Toast.LENGTH_LONG).show();

                        }
                    }


                });

            }

        });

     imgstart.setOnClickListener(new View.OnClickListener() {
         public void onClick(View view) {

             DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

                 public void onDateSet(DatePicker view, int myear,
                                       int mmonthOfYear, int mdayOfMonth) {
                     newstart.setText(myear + " / " + (mmonthOfYear+ 1)+ " / "+ mdayOfMonth);
                 }
             };

             DatePickerDialog d = new DatePickerDialog(getActivity(),
                      mDateSetListener, year, month, day);
             d.show();
                 }
             } );

        imgend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int myear,
                                          int mmonthOfYear, int mdayOfMonth) {
                        newend.setText(myear + " / " + (mmonthOfYear+ 1)+ " / "+ mdayOfMonth);
                    }
                };

                DatePickerDialog d = new DatePickerDialog(getActivity(), mDateSetListener, year, month, day);
                d.show();
            }
        } );


        return view;





    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


    }


