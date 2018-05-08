package com;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.data.City;

/**
 * Created by riyagarg on 5/2/18.
 */

public class CityCreateDialog extends DialogFragment{

    private CityHandler cityHandler;

    public interface CityHandler {
        //boolean onNavigationItemSelected(MenuItem item);
        public void onNewCityCreated(String city);

        void onCityUpdated(City city);
    }

    @Override
    public void onAttach (Context context){
        super.onAttach(context);
        if (context instanceof CityHandler){
            cityHandler = (CityHandler)context;
        } else {
            throw new RuntimeException("The Activity does not implement the CityHandler interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter a City");
        final EditText input = new EditText(getActivity());
        builder.setView(input);

            builder.setPositiveButton("Save City", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!TextUtils.isEmpty(input.getText())) {
                        cityHandler.onNewCityCreated(input.getText().toString());
                    }
                }
            });
        return builder.create();
    }

}
