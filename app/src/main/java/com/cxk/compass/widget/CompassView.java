package com.cxk.compass.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.cxk.compass.DPUtils;
import com.cxk.compass.R;

/**
 * Created by chengxiakuan on 2018/2/1.
 */

public class CompassView extends View {

    private final int MAX_ANGLE = 45;

    private final float DIVISION_WIDTH = 1.5f;
    private Paint paintDivision;
    private Paint paintCircle;
    private Paint paintLevel;
    private Paint paintText;
    private float smallDivision;
    private float mediumDivision;

    private float degree;
    private float zAngle;
    private float yAngle;

    private String[] stringArray;

    private Path path = new Path();
    private int outterStrokeWidth;

    public CompassView(Context context) {
        super(context);
        init(context);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        stringArray = context.getResources().getStringArray(R.array.direction);

        smallDivision = DPUtils.dp2px(getContext(), 10);
        mediumDivision = DPUtils.dp2px(getContext(), 20);
        outterStrokeWidth = (int) DPUtils.dp2px(getContext(), 15);

        paintDivision = new Paint();
        paintDivision.setColor(Color.GRAY);
        paintDivision.setAntiAlias(true);
        paintDivision.setStrokeWidth(DPUtils.dp2px(getContext(), DIVISION_WIDTH));

        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(outterStrokeWidth);
        paintCircle.setColor(Color.GRAY);


        paintLevel = new Paint();
        paintLevel.setStyle(Paint.Style.FILL);
        paintLevel.setAntiAlias(true);
        paintLevel.setColor(Color.RED);


        paintText = new Paint();
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setAntiAlias(true);
        paintText.setColor(Color.GRAY);
        paintText.setTextSize(DPUtils.dp2px(getContext(), 12));
        paintText.setTextAlign(Paint.Align.CENTER);

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


        int paddingTop = getPaddingTop();

        int cx = measuredWidth / 2;
        int cy = measuredHeight / 2;
        int radius = cx - paddingTop;
        canvas.drawCircle(cx, cy, radius, paintCircle);

        paintDivision.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, 10, paintDivision);

        //绘制三角指示箭头
        path.reset();
        int triangleLength = paddingTop / 2;
        path.moveTo(cx - triangleLength / 2, 0);
        path.lineTo(cx + triangleLength / 2, 0);
        path.lineTo(cx, (float) Math.sqrt(triangleLength * triangleLength * 3 / 4));
        path.close();
        //重置为红色
        paintLevel.setColor(Color.RED);
        canvas.drawPath(path, paintLevel);

        //画中心十字架
        paintDivision.setStyle(Paint.Style.STROKE);
        canvas.drawLine(cx - radius / 4, cy, cx + radius / 4, cy, paintDivision);
        canvas.drawLine(cx, cy - radius / 4, cx, cy + radius / 4, paintDivision);

        //绘制中心的圆环
        canvas.drawCircle(cx, cy, radius / 6 + 4, paintDivision);

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
        if (Math.abs(x) < 2 && Math.abs(y) < 2) {
            paintLevel.setColor(Color.GREEN);
        } else {
            paintLevel.setColor(Color.RED);
        }
        canvas.drawCircle(cx - x, cy - y, radius / 6, paintLevel);
        //绘制圆盘刻度逻辑
        canvas.rotate(360 - degree, cx, cy);

        String value;
        for (int i = 0; i < 36; i++) {
            if (i == 0) {
                value = stringArray[0];
            } else if (i == 9) {
                value = stringArray[2];
            } else if (i == 18) {
                value = stringArray[4];
            } else if (i == 27) {
                value = stringArray[6];
            } else {
                value = (i * 10) + "°";
            }
            if (i % 3 == 0) {
                canvas.drawText(value, cx, paddingTop - outterStrokeWidth * 2 / 3, paintText);
            }

            if (i % 9 == 0) {
                paintDivision.setStrokeWidth(DPUtils.dp2px(getContext(), DIVISION_WIDTH * 2));
                canvas.drawLine(cx, paddingTop + outterStrokeWidth / 2, cx, paddingTop + outterStrokeWidth / 2 + mediumDivision, paintDivision);
            } else {
                paintDivision.setStrokeWidth(DPUtils.dp2px(getContext(), DIVISION_WIDTH));
                canvas.drawLine(cx, paddingTop + outterStrokeWidth / 2, cx, paddingTop + outterStrokeWidth / 2 + smallDivision, paintDivision);
            }

            canvas.rotate(10, cx, cy);
        }


        canvas.save();


    }


    public void updateDegree(float degree, float yAngle, float zAngle) {
        this.degree = degree;
        this.yAngle = yAngle;
        this.zAngle = zAngle;

        invalidate();
    }

}
