package com.zdlly.myshapeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by zdlly on 2017/3/5.
 */


public class MyScaleView extends android.support.v7.widget.AppCompatImageView {

    final public static int DRAG = 1;
    final public static int ZOOM = 2;

    public int mode = 0;

    private Matrix matrix = new Matrix();
    private Matrix matrix1 = new Matrix();
    private Matrix saveMatrix = new Matrix();

    private float x_down = 0;
    private float y_down = 0;

    private Bitmap touchImg;

    private PointF mid = new PointF();


    private float initDis = 1f;

    private int screenWidth, screenHeight;

    private float[] x = new float[4];
    private float[] y = new float[4];

    private boolean flag = false;

    public MyScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScaleView(Context context) {
        super(context);
        touchImg = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        canvas.drawBitmap(touchImg, matrix, null);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                saveMatrix.set(matrix);
                x_down = event.getX();
                y_down = event.getY();
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                saveMatrix.set(matrix);
                initDis = spacing(event);
                mode = ZOOM;
                midPoint(mid, event);
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix1.set(saveMatrix);
                    matrix1.postTranslate(event.getX() - x_down, event.getY()
                            - y_down);
                    flag = checkMatrix(matrix1);
                    if (flag) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                } else if (mode == ZOOM) {
                    matrix1.set(saveMatrix);
                    float newDis = spacing(event);
                    float scale = newDis / initDis;
                    matrix1.postScale(scale, scale, mid.x, mid.y);
                    flag = checkMatrix(matrix1);
                    if (flag) {
                        matrix.set(matrix1);
                        invalidate();
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
        }

        return true;

    }


    private float spacing(MotionEvent event) {
        try {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float)Math.sqrt(x * x + y * y);
        } catch (IllegalArgumentException ex) {
            Log.v("TAG", ex.getLocalizedMessage());
            return 0;
        }
    }

    private void midPoint(PointF point, MotionEvent event) {
        try {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
        } catch (IllegalArgumentException ex) {

            Log.v("TAG", ex.getLocalizedMessage());
        }
    }

    private boolean checkMatrix(Matrix m) {

        GetFour(m);

        if ((x[0] < screenWidth / 3 && x[1] < screenWidth / 3
                && x[2] < screenWidth / 3 && x[3] < screenWidth / 3)
                || (x[0] > screenWidth * 2 / 3 && x[1] > screenWidth * 2 / 3
                && x[2] > screenWidth * 2 / 3 && x[3] > screenWidth * 2 / 3)
                || (y[0] < screenHeight / 3 && y[1] < screenHeight / 3
                && y[2] < screenHeight / 3 && y[3] < screenHeight / 3)
                || (y[0] > screenHeight * 2 / 3 && y[1] > screenHeight * 2 / 3
                && y[2] > screenHeight * 2 / 3 && y[3] > screenHeight * 2 / 3)) {
            return true;
        }
        double width = Math.sqrt((x[0] - x[1]) * (x[0] - x[1]) + (y[0] - y[1])
                * (y[0] - y[1]));
        return width < screenWidth / 3 || width > screenWidth * 3;
    }

    private void GetFour(Matrix matrix) {
        float[] f = new float[9];
        matrix.getValues(f);
        x[0] = f[Matrix.MSCALE_X] * 0 + f[Matrix.MSKEW_X] * 0
                + f[Matrix.MTRANS_X];
        y[0] = f[Matrix.MSKEW_Y] * 0 + f[Matrix.MSCALE_Y] * 0
                + f[Matrix.MTRANS_Y];
        x[1] = f[Matrix.MSCALE_X] * touchImg.getWidth() + f[Matrix.MSKEW_X] * 0
                + f[Matrix.MTRANS_X];
        y[1] = f[Matrix.MSKEW_Y] * touchImg.getWidth() + f[Matrix.MSCALE_Y] * 0
                + f[Matrix.MTRANS_Y];
        x[2] = f[Matrix.MSCALE_X] * 0 + f[Matrix.MSKEW_X]
                * touchImg.getHeight() + f[Matrix.MTRANS_X];
        y[2] = f[Matrix.MSKEW_Y] * 0 + f[Matrix.MSCALE_Y]
                * touchImg.getHeight() + f[Matrix.MTRANS_Y];
        x[3] = f[Matrix.MSCALE_X] * touchImg.getWidth() + f[Matrix.MSKEW_X]
                * touchImg.getHeight() + f[Matrix.MTRANS_X];
        y[3] = f[Matrix.MSKEW_Y] * touchImg.getWidth() + f[Matrix.MSCALE_Y]
                * touchImg.getHeight() + f[Matrix.MTRANS_Y];
    }

}
