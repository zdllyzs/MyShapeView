package com.zdlly.myshapeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.Toast;

/**
 * Created by zdlly on 2017/3/5.
 */
public class MyShapeView extends android.support.v7.widget.AppCompatImageView {


    private int mSides = 3;
    private Paint mPaint;
    private Xfermode mXfermode;
    private Bitmap mMask;
    private Path path;


    private Canvas canvas;


    public void setmSides(int mSides) {
        this.mSides = mSides;
    }

    public MyShapeView(Context context) {
        this(context, null);
    }

    public MyShapeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        int id = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        createMask();
        super.onDraw(canvas);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mMask, 0, 0, mPaint);
        mPaint.setXfermode(null);
        path.reset();
        canvas.restoreToCount(id);
    }

    private void createMask() {
        int maskWidth = getMeasuredWidth();
        int maskHeight = getMeasuredHeight();
        mMask = Bitmap.createBitmap(maskWidth, maskHeight, Bitmap.Config.ARGB_8888);

        canvas= new Canvas(mMask);
        int rectSize = maskWidth / 2;
        drawMultShape(canvas, mSides, rectSize);
    }

    public void drawMultShape(Canvas canvas, int count, float radius) {

        canvas.translate(radius, radius);
        if (count < 3) {
            Toast.makeText(getContext(), "无法绘制边数少于3的图形", Toast.LENGTH_SHORT).show();
        } else if (count < 30) {
            for (int i = 0; i < count; i++) {
                if (i == 0) {
                    path.moveTo(radius * cos(360 / count * i), radius * sin(360 / count * i));
                } else {
                    path.lineTo(radius * cos(360 / count * i), radius * sin(360 / count * i));
                }
            }
            path.close();
            canvas.drawPath(path, mPaint);
        } else {
            canvas.drawCircle(0, 0, radius, mPaint);
        }
    }


    float sin(int num) {
        return (float) Math.sin(num * Math.PI / 180);
    }

    float cos(int num) {
        return (float) Math.cos(num * Math.PI / 180);
    }
}