package com.work.app.ztea.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.http.Api;

import java.util.ArrayList;
import java.util.List;

public class URLImageParser implements Html.ImageGetter {

    Context c;

    TextView tv_image;

    private List<Target> targets = new ArrayList<>();

    private OnImageLoadSuccessImp successImp;

    public URLImageParser(TextView t, Context c,OnImageLoadSuccessImp successImp) {
        this.tv_image = t;
        this.c = c;
        this.successImp = successImp;
        tv_image.setTag(targets);
    }

    @Override
    public Drawable getDrawable(final String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable drawable = new BitmapDrawable(bitmap);
                drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                urlDrawable.setDrawable(drawable);
                urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                tv_image.invalidate();
                tv_image.setText(tv_image.getText());
                successImp.imgLoadSuccess();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                errorDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
                urlDrawable.setBounds(0, 0, errorDrawable.getIntrinsicWidth(), errorDrawable.getIntrinsicHeight());
                urlDrawable.setDrawable(errorDrawable);
                tv_image.invalidate();
                successImp.imgLoadSuccess();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                placeHolderDrawable.setBounds(0, 0, placeHolderDrawable.getIntrinsicWidth(), placeHolderDrawable.getIntrinsicHeight());
//                urlDrawable.setBounds(0, 0, placeHolderDrawable.getIntrinsicWidth(), placeHolderDrawable.getIntrinsicHeight());
//                urlDrawable.setDrawable(placeHolderDrawable);
//                tv_image.invalidate();
            }
        };

        targets.add(target);
        loadPlaceholder(c, source, target);
        return urlDrawable;
    }

    public void loadPlaceholder(Context context, String url, Target target) {

        Picasso picasso = new Picasso.Builder(context).loggingEnabled(true).build();
        picasso.load(Api.BASE_URL+url)
//                .placeholder(R.drawable.wu)
//                .error(R.drawable.wu)
                .transform(new ImageTransform())
                .into(target);
    }

    class URLDrawable extends BitmapDrawable {
        private Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

    }

    class ImageTransform implements Transformation {

        private String Key = "ImageTransform";

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = APP.getInstance().getResources().getDisplayMetrics().widthPixels;
            if (source.getWidth() == 0) {
                return source;
            }

            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    source.recycle();
                }
                return result;
            } else {
                return source;
            }
        }

        @Override
        public String key() {
            return Key;
        }
    }

    public interface OnImageLoadSuccessImp{
        void imgLoadSuccess();
    }
}
