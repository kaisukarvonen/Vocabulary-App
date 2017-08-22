package com.scoctail.vocabularyapp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.scoctail.vocabularyapp.beans.Language;
import com.scoctail.vocabularyapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaisu on 14.8.2017.
 */

public class ChooseLanguageDialog extends DialogFragment {
    private List<String> lNames;
    private DatabaseHelper db;
    private List<Language> languages;
    OnDialogConfirmClickListener listener;

    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        db = new DatabaseHelper(getContext());
        languages = db.getLanguages();
        lNames = new ArrayList<>();
        for (Language l : languages) {
            lNames.add(l.getName());
        }
        int selectedLanguage = getSelectedLanguagePosition(lNames,db.getSelectedLanguage(getContext()).getName());
        final CharSequence[] lItems = lNames.toArray(new CharSequence[lNames.size()]);

        builder.setTitle("Select language ...").setSingleChoiceItems(lItems, selectedLanguage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!lNames.get(which).equals(db.getSelectedLanguage(getContext()))) {
                    db.writeToInternalStorage(getContext(), Integer.toString(getSelectedLanguageId(lNames.get(which))), "language_id");
                }
                listener.onChooseLanguageDialogConfirmClick();
                dialog.dismiss();

            }
        });

        return builder.create();

    }

    public int getSelectedLanguagePosition(List<String> array, String languageName) {
        int position = 0;
        for (int i=0; i<array.size(); i++) {
            if (array.get(i).equals(languageName)) {
                position = i;
            }
        }
        return position;
    }

    public int getSelectedLanguageId(String languageName) {
        int id = 0;
        for (Language l : languages) {
            if (l.getName().equals(languageName)) {
                id = l.getId();
            }
        }
        return id;
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
