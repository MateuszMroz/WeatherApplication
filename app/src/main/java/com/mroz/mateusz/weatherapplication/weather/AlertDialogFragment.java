package com.mroz.mateusz.weatherapplication.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.mroz.mateusz.weatherapplication.R;


public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.error_message))
                .setTitle(getString(R.string.error_title))
                .setPositiveButton(getString(R.string.ok_button), null);
        AlertDialog dialog = builder.create();

        return dialog;
    }
}
