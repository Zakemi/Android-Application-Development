package fi.jamk.deregi.exercise_04102016_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by DoubleD on 2016. 10. 08..
 */

public class AddItemDialog extends DialogFragment {

    public interface AddItemDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name, Integer count, Double price);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    AddItemDialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_item_layout, null);
        builder.setView(dialogView)
            .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = ((EditText)dialogView.findViewById(R.id.dialog_name)).getText().toString();
                    String count = ((EditText)dialogView.findViewById(R.id.dialog_count)).getText().toString();
                    String price = ((EditText)dialogView.findViewById(R.id.dialog_price)).getText().toString();
                    if (!name.equals("") && !count.equals("") && !price.equals("")) {
                        dialogListener.onDialogPositiveClick(AddItemDialog.this, name, Integer.parseInt(count), Double.parseDouble(price));
                    }
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogListener.onDialogNegativeClick(AddItemDialog.this);
                }
            });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            dialogListener = (AddItemDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement AddItemDialogListener");
        }
    }
}
