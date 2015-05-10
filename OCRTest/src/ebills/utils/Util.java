package ebills.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.EditText;

/**
 * Created by Suraj on 09-05-2015.
 */
public class Util {
    public static final boolean isEmptyOrNull(String s) {
        return s == null || s.trim().equals("");
    }

    public static final int convertToPx(Context mContext, int inDp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, inDp, mContext.getResources().getDisplayMetrics());
    }

    public static final boolean assertionFail(EditText mEditText,String error){
        if(isEmptyOrNull(mEditText.getText().toString())){
            mEditText.setError(error);
            return true;
        }
        return false;
    }

}
