package com.seapip.thomas.kai.module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;

import com.seapip.thomas.kai.R;

import java.io.InputStream;
import java.util.Random;

public class MotionModule implements Module {
    private Context mContext;
    private Rect mBounds;
    private Movie mMovie;
    private int mDuration;
    private int mPosition;
    private int mStep;
    private boolean mAmbient;
    private Bitmap mBitmap;
    private Bitmap mBitmapScaled;
    private Canvas mCanvas;
    private Random mRandom;
    private int mLastRandom;
    private int[][] mMovies;
    private boolean mBlocked;
    private int mScene;

    /* Paint */
    private Paint mFadeInPaint;

    public MotionModule(Context context, int scene) {
        mContext = context;

        mRandom = new Random();
        mLastRandom = 0;

        int[] jellyfish = new int[6];
        jellyfish[0] = R.drawable.jellyfish_1;
        jellyfish[1] = R.drawable.jellyfish_2;
        jellyfish[2] = R.drawable.jellyfish_3;
        jellyfish[3] = R.drawable.jellyfish_4;
        jellyfish[4] = R.drawable.jellyfish_5;
        jellyfish[5] = R.drawable.jellyfish_6;

        int[] flower = new int[7];
        flower[0] = R.drawable.flower_1;
        flower[1] = R.drawable.flower_2;
        flower[2] = R.drawable.flower_3;
        flower[3] = R.drawable.flower_4;
        flower[4] = R.drawable.flower_5;
        flower[5] = R.drawable.flower_6;
        flower[6] = R.drawable.flower_7;

        int[] city = new int[5];
        city[0] = R.drawable.city_1;
        city[1] = R.drawable.city_2;
        city[2] = R.drawable.city_3;
        city[3] = R.drawable.city_4;
        city[4] = R.drawable.city_5;
//        city[0] = R.drawable.b1;
//        city[1] = R.drawable.b2;
//        city[2] = R.drawable.b3;
//        city[3] = R.drawable.city_4;
//        city[4] = R.drawable.city_5;

        int[] butterfly = new int[12];
        butterfly[0] = R.drawable.b1;
        butterfly[1] = R.drawable.b2;
        butterfly[2] = R.drawable.b3;
        butterfly[3] = R.drawable.b4;
        butterfly[4] = R.drawable.b5;
        butterfly[5] = R.drawable.b6;
        butterfly[6] = R.drawable.b7;
        butterfly[7] = R.drawable.b8;
        butterfly[8] = R.drawable.b9;
        butterfly[9] = R.drawable.b10;
        butterfly[10] = R.drawable.b11;
        butterfly[11] = R.drawable.b12;


        int[] water = new int[12];
        water[0] = R.drawable.w1;
        water[1] = R.drawable.w2;
        water[2] = R.drawable.w3;
        water[3] = R.drawable.w4;
        water[4] = R.drawable.w5;
        water[5] = R.drawable.w6;
        water[6] = R.drawable.w7;
        water[7] = R.drawable.w8;
        water[8] = R.drawable.w9;
        water[9] = R.drawable.w10;
        water[10] = R.drawable.w11;
        water[11] = R.drawable.w12;


        mMovies = new int[5][];
        mMovies[0] = jellyfish;
        mMovies[1] = flower;
        mMovies[2] = city;
        mMovies[3] = butterfly;
        mMovies[4] = water;

        /* Paint */
        mFadeInPaint = new Paint();
        mFadeInPaint.setColor(Color.BLACK);
        mFadeInPaint.setAlpha(255);

        setScene(scene);
    }

    @Override
    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mAmbient && !mBlocked) {
            if (mPosition <= mDuration) {
                mMovie.setTime(mPosition);
                if (mDuration - mPosition < 500) {
                    mStep -= 5;
                    if (mStep < 30) {
                        mStep = 30;
                    }
                }
                mPosition += mStep;
                int alpha = mFadeInPaint.getAlpha() - 8;
                if (alpha < 92) {
                    alpha = 92;
                }
                mFadeInPaint.setAlpha(alpha);
                mMovie.draw(mCanvas, 0, 0);
                mBitmapScaled = Bitmap.createScaledBitmap(mBitmap, mBounds.width(),
                        mBounds.height(), true);
            } else {
                mMovie = null;
            }
            canvas.drawBitmap(mBitmapScaled, mBounds.left, mBounds.top, null);
        }
        canvas.drawRect(mBounds, mFadeInPaint);
    }

    public void setScene(int scene) {
        mScene = scene;
        start();
    }

    private void start() {
        mBlocked = true;
        mPosition = 0;
        mStep = 60;
        mFadeInPaint.setAlpha(255);
        int random;
        do {
            random = mRandom.nextInt(mMovies[mScene].length);
        } while (random == mLastRandom);
        mLastRandom = random;
        InputStream inputStream = mContext.getResources().openRawResource(+mMovies[mScene][random]);
        mMovie = Movie.decodeStream(inputStream);
        mDuration = mMovie.duration();
        mBitmap = Bitmap.createBitmap(mMovie.width(), mMovie.height(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mBlocked = false;
    }

    public void tap(int x, int y) {
        if (mBounds.contains(x, y)) {
            start();
        }
    }

    @Override
    public void setColor(int color) {
    }

    @Override
    public void setAmbient(boolean ambient) {
        mAmbient = ambient;
        if (!ambient) {
            start();
        } else {
            mFadeInPaint.setAlpha(255);
        }
    }

    @Override
    public void setBurnInProtection(boolean burnInProtection) {
    }

    @Override
    public void setLowBitAmbient(boolean lowBitAmbient) {
    }
}
