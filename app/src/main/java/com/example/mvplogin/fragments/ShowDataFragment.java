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

public class ShowDataFragment extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextSlug;
    private ShowDataFragment.DialogListener listener;
    private ShowDataFragment.DialogListenerDelete listenerDelete;
    protected int id;
    private String name;
    private String slug;
    private int position;

    public ShowDataFragment(int id, String name, String slug, int position) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.position = position;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.show_dialog, null);

        builder.setView(view)
                .setTitle("Career")
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listenerDelete.applyTextDelete(id, position);
                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editTextName.getText().toString();
                        String slug = editTextSlug.getText().toString();
                        listener.applyTextEdit(id, name, slug);
                    }
                });
        editTextName = view.findViewById(R.id.name);
        editTextName.setText(name);
        editTextSlug = view.findViewById(R.id.slug);
        editTextSlug.setText(slug);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ShowDataFragment.DialogListener) context;
            listenerDelete = (ShowDataFragment.DialogListenerDelete) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }

    }

    public interface DialogListener {
        void applyTextEdit(int id, String name, String slug);
    }

    public interface DialogListenerDelete {
        void applyTextDelete(int id, int position);
    }

}

