package com.prudential.climberdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.prudential.climberdemo.R;

/**
 * Customize a circular progress bar
 */
public class CircleProgressView extends View {
    private int mProgressColor =0xFFFF0000;//progress color
    private int bgColor=0xFFD8D8D8;// background color
    private int mCurrent;//current progress
    private Paint mBgPaint;//background arc paint
    private Paint mProgressPaint;//progress paint
    private float mProgressWidth;//progress width

    private int locationStart;//start position   {1,2,3,4}
    private float startAngle;//start angle    0-360
    private ValueAnimator mAnimator;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        locationStart = typedArray.getInt(R.styleable.CircleProgressView_location_start, 1);
        mProgressWidth = typedArray.getDimension(R.styleable.CircleProgressView_progress_width, dp2px(context, 30));
        mProgressColor = typedArray.getColor(R.styleable.CircleProgressView_progress_color, mProgressColor);
        typedArray.recycle();

        //define background arc
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStrokeWidth(mProgressWidth);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setColor(bgColor);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);

        //define progress arc
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        //define the progress start angle
        if (locationStart == 1) {//left
            startAngle = -180;
        } else if (locationStart == 2) {//up
            startAngle = -90;
        } else if (locationStart == 3) {//right
            startAngle = 0;
        } else if (locationStart == 4) {//down
            startAngle = 90;
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width < height ? width : height;
        setMeasuredDimension(size, size);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //draw background arc
        RectF rectF = new RectF(mProgressWidth / 2, mProgressWidth / 2, getWidth() - mProgressWidth / 2, getHeight() - mProgressWidth / 2);
        canvas.drawArc(rectF, 0, 360, false, mBgPaint);

        //draw current progress
        float sweepAngle = 360 * mCurrent / 50000;

        canvas.drawArc(rectF, startAngle, sweepAngle, false, mProgressPaint);
    }

    public int getCurrent() {
        return mCurrent;
    }

    /**
     * set current progress
     *
     * @param current
     */
    public void setCurrent(int current) {
        mCurrent = current;
        invalidate();
    }


    /**
     * define animation
     *
     * @param current  to current：0-50000
     * @param duration animation time
     */
    public void startAnimProgress(int current, int duration) {
        mAnimator = ValueAnimator.ofInt(mCurrent, current);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                if (mCurrent != current) {
                    System.out.println("mCurrent:"+mCurrent+"            current:"+current);
                    mCurrent = current;
                    setCurrent(mCurrent);
                    if (mOnAnimProgressListener != null)
                        mOnAnimProgressListener.valueUpdate(mCurrent);
                }
            }
        });
        mAnimator.start();
    }

    public interface OnAnimProgressListener {
        void valueUpdate(int progress);
    }

    private OnAnimProgressListener mOnAnimProgressListener;

    /**
     * set progress listener
     *
     * @param onAnimProgressListener
     */
    public void setOnAnimProgressListener(OnAnimProgressListener onAnimProgressListener) {
        mOnAnimProgressListener = onAnimProgressListener;
    }


    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
