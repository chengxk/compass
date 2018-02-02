package com.cxk.compass;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by chengxiakuan on 2018/2/2.
 */

public class DPUtils {

    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
