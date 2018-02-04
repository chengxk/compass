package com.cxk.compass.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cxk.compass.DPUtils;

/**
 * Created by chengxiakuan on 2018/2/2.
 */

public class LevelView extends View {
    private final int MAX_ANGLE = 45;
    private int strokeWidth;

    private Paint limitPaint;
    private Paint bubblePaint;
    private Paint bubbleRulePaint;

    private float zAngle;
    private float yAngle;

    public LevelView(Context context) {
        super(context);
        init(context);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        strokeWidth = (int)DPUtils.dp2px(context , 10);
        limitPaint = new Paint();
        limitPaint.setStrokeWidth(strokeWidth);
        limitPaint.setStyle(Paint.Style.STROKE);
        limitPaint.setAntiAlias(true);

        bubblePaint = new Paint();
        bubblePaint.setStyle(Paint.Style.FILL);
        bubblePaint.setAntiAlias(true);

        bubbleRulePaint = new Paint();
        bubbleRulePaint.setColor(Color.GRAY);
        bubbleRulePaint.setStyle(Paint.Style.STROKE);
        bubbleRulePaint.setAntiAlias(true);
        bubbleRulePaint.setStrokeWidth(DPUtils.dp2px(context, 1));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int cx = measuredWidth / 2;
        int cy = measuredHeight / 2;

        int paddingTop = getPaddingTop();
        int radius = cx - paddingTop;

        //外圆半径-内圆半径-圆环笔刷宽度/2
        int distance = radius * 5 / 6;
        float x, y;
        if (Math.abs(zAngle) <= MAX_ANGLE) {
            x = distance * zAngle / MAX_ANGLE;
        } else if (zAngle > MAX_ANGLE) {
            x = distance;
        } else {
            x = -distance;
        }

        if (Math.abs(yAngle) <= MAX_ANGLE) {
            y = distance * yAngle / MAX_ANGLE;
        } else if (yAngle > MAX_ANGLE) {
            y = distance;
        } else {
            y = -distance;
        }
        //绘制水平仪
        if (Math.abs(x) < 0.5 && Math.abs(y) < 0.5) {
            limitPaint.setColor(Color.GREEN);
            bubblePaint.setColor(Color.GREEN);
        } else {
            limitPaint.setColor(Color.RED);
            bubblePaint.setColor(Color.RED);
        }

        //绘制中心的圆环
        canvas.drawCircle(cx, cy, radius / 6 + 4, bubbleRulePaint);

        canvas.drawCircle(cx - x, cy - y, radius / 6, bubblePaint);


        canvas.drawCircle(cx, cy, radius, limitPaint);


    }


    public void updateDegree(float degree, float yAngle, float zAngle) {
        this.yAngle = yAngle;
        this.zAngle = zAngle;

        invalidate();
    }
}
