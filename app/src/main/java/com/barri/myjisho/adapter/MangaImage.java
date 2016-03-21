package com.barri.myjisho.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Barri on 20/03/2016.
 */
public class MangaImage extends ImageView {

    public MangaImage(Context context) {
        super(context);
    }

    public MangaImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MangaImage(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth() + 100); //Snap to width
    }


}
