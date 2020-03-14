package com.example.mvplogin.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mvplogin.R;

public class DialogFragment extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextSlug;
    private DialogListener listener;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog, null);

        builder.setView(view)
                .setTitle("Create career")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        String slug = editTextSlug.getText().toString();
                        listener.applyText(name, slug);
                    }
                });
        editTextName = view.findViewById(R.id.name);
        editTextSlug = view.findViewById(R.id.slug);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }

    }

    public interface DialogListener {

        void applyText(String name, String slug);
    }
}
