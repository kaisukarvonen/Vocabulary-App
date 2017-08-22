package com.scoctail.vocabularyapp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.scoctail.vocabularyapp.database.DatabaseHelper;

/**
 * Created by Kaisu on 15.8.2017.
 */

public class SortByDialog extends DialogFragment {
    private CharSequence[] sortOptions = {"Alphabetical order", "Themes", "Wordclasses"};
    OnDialogConfirmClickListener listener;


    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        DatabaseHelper db = new DatabaseHelper(getActivity());

        builder.setTitle("Sort by ...").setSingleChoiceItems(sortOptions, Integer.parseInt(db.readFromInternalStorage(getActivity(), "sort_by_selection")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("pressed sort by method", (String) sortOptions[which]);
                listener.onSortByDialogConfirmClick(sortOptions, which);
                dialog.dismiss();

            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnDialogConfirmClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +" does not implement OnDialogConfirmClickListener");
        }
    }

}
