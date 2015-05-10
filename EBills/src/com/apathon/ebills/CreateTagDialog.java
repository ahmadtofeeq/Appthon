package com.apathon.ebills;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;
import ebills.models.Tags;
import com.apathon.ebills.R;

/**
 * Created by pranavsharma on 20/04/15.
 */
public class CreateTagDialog extends DialogFragment implements OnClickListener {

    private EditText mEditText1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mEditText1= (EditText) LayoutInflater.from(getActivity()).inflate(R.layout.layout_edittext,null);
        AlertDialog.Builder mBuilder=new Builder(getActivity()).setTitle("Write your tag").setView(mEditText1).setPositiveButton("Save",this).setNegativeButton("Cancel",this);
        return mBuilder.show();
    }



    @Override
    public void onClick(DialogInterface dialog, int which) {
     if (which==DialogInterface.BUTTON_POSITIVE){
         String text = mEditText1.getText().toString();
         Tags tags = new Tags();
         tags.setColumn_tags_name(text);
         long l = App.getDb().insertTags(tags);
         if(l!=-1)
         {
             Toast.makeText(getActivity(),"Tag Inserted Successfully" , Toast.LENGTH_SHORT).show();
         }
         else
         {
             Toast.makeText(getActivity(),"Opps , there's some problem!" , Toast.LENGTH_SHORT).show();
         }
     }
    }
}
