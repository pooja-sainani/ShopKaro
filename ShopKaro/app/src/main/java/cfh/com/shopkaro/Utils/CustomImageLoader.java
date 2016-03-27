package cfh.com.shopkaro.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import cfh.com.shopkaro.R;

import java.io.File;
import java.util.concurrent.RejectedExecutionException;

public class CustomImageLoader {


    public static final int OPTION_DEFAULT = 0;
    public static final int OPTION_FORCE_FADE = 1;
    public static final int OPTION_NO_ANIMATION = 2;
    public static final int OPTION_NO_IMAGE_BACKGROUND_WHILE_LOADING = 3;

    public static final DisplayImageOptions defaultOptions = getDefaultDisplayImageOptions();
    public static final DisplayImageOptions forceFadeOptions = getForceFadeDisplayImageOptions();
    public static final DisplayImageOptions noAnimationOptions = getNonAnimateDisplayImageOptions();
    public static final DisplayImageOptions noImageBackgroundOptions = getNoImageBackgroundImageOptions();

    public static void cancelDisplayTask(ImageView imageView) {
        ImageLoader.getInstance().cancelDisplayTask(imageView);
    }

    public interface ZImageLoadingListener {


        void onLoadingStarted(View view);
        void onLoadingFailed(View view);

        void onLoadingComplete(View view, Bitmap bitmap);

        void onLoadingCancelled(View view);

    }

    public static void initiate(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(10 * 1024 * 1024)
                .memoryCacheSizePercentage(8)
                .build();

        ImageLoader.getInstance().init(config);
    }

    private static DisplayImageOptions getDefaultDisplayImageOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.color_mid_grey)
                .showImageForEmptyUri(R.color.color_mid_grey)
                .showImageOnFail(null)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return displayImageOptions;
    }

    private static DisplayImageOptions getForceFadeDisplayImageOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.color_mid_grey)
                .showImageForEmptyUri(R.color.color_mid_grey)
                .showImageOnFail(null)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(400))
                .build();
        return displayImageOptions;
    }

    private static DisplayImageOptions getNonAnimateDisplayImageOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.color_mid_grey)
                .showImageForEmptyUri(R.color.color_mid_grey)
                .showImageOnFail(null)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return displayImageOptions;
    }

    private static DisplayImageOptions getNoImageBackgroundImageOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(400))
                .build();
        return displayImageOptions;
    }

    public static void displayImage(ImageView imageView, String uri, int maxWidth, int maxHeight) {
        displayImage(imageView, null, uri, OPTION_DEFAULT, null, ViewUtils.INVALID_INT, ViewUtils.INVALID_INT, maxWidth, maxHeight);
    }

    public static void displayImage(ImageView imageView, ProgressBar progressBar, String uri) {
        displayImage(imageView, progressBar, uri, OPTION_DEFAULT, null, ViewUtils.INVALID_INT, ViewUtils.INVALID_INT, 1024, 1024);
    }

    public static void displayImage(ImageView imageView, ProgressBar progressBar, String uri, int imageResOnLoading, int imageResOnError) {
        displayImage(imageView, progressBar, uri, OPTION_DEFAULT, null, imageResOnLoading, imageResOnError, 1024, 1024);
    }

    public static void displayImage(ImageView imageView, ProgressBar progressBar, String uri, int option) {
        displayImage(imageView, progressBar, uri, option, null, ViewUtils.INVALID_INT, ViewUtils.INVALID_INT, 1024, 1024);
    }

    public static void displayImage(ImageView imageView, ProgressBar progressBar, String uri, int option, int imageResOnLoading, int imageResOnError) {
        displayImage(imageView, progressBar, uri, option, null, imageResOnLoading, imageResOnError, 1024, 1024);
    }

    public static void displayImage(ImageView imageView, ProgressBar progressBar, String uri, int option, ZImageLoadingListener listener) {
        displayImage(imageView, progressBar, uri, option, listener, ViewUtils.INVALID_INT, ViewUtils.INVALID_INT, 1024, 1024);
    }

    public static void displayImage(ImageView imageView, ProgressBar progressBar, String uri, int option, ZImageLoadingListener listener
            , int imageResOnLoading, int imageResOnError, int width, int height) {

        ImageViewAware imageViewAware = new ImageViewAware(imageView, false);

        ImageLoader.getInstance().cancelDisplayTask(imageViewAware);

        DisplayImageOptions.Builder imageOptionsBuilder = new DisplayImageOptions.Builder().cloneFrom(getDisplayImageOption(option));
        if (imageResOnLoading != ViewUtils.INVALID_INT) {
            imageOptionsBuilder.showImageOnLoading(imageResOnLoading);
            imageOptionsBuilder.resetViewBeforeLoading(false);
        }

        if (imageResOnError != ViewUtils.INVALID_INT)
            imageOptionsBuilder.showImageOnFail(imageResOnError);

        ImageLoader.getInstance().displayImage(uri, imageViewAware, imageOptionsBuilder.build(), new CustomImageLoader.MyImageLoadListener(listener, progressBar));
    }

    public static void displayImageFromDisk(String imagePath, ImageView imageView, DisplayImageOptions displayImageOptions) {
        imagePath = Uri.fromFile(new File(imagePath)).toString();
        final String decoded = Uri.decode(imagePath);
        ImageLoader.getInstance().displayImage(decoded, imageView, displayImageOptions);
    }

    private static class MyImageLoadListener implements ImageLoadingListener {
        private ZImageLoadingListener listener = null;
        ProgressBar progressBar = null;

        public MyImageLoadListener(ZImageLoadingListener listener, ProgressBar progressBar) {
            this.listener = listener;
            this.progressBar = progressBar;
        }

        @Override
        public void onLoadingStarted(String s, View view) {
            if (listener != null)
                listener.onLoadingStarted(view);
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            if (listener != null)
                listener.onLoadingFailed(view);
        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            if (listener != null)
                listener.onLoadingComplete(view, bitmap);

            if (progressBar != null && progressBar.getVisibility() != View.GONE)
                progressBar.setVisibility(View.GONE);

        }

        @Override
        public void onLoadingCancelled(String s, View view) {
            if (listener != null)
                listener.onLoadingCancelled(view);
        }
    }

    private static DisplayImageOptions getDisplayImageOption(int option) {
        DisplayImageOptions options;
        switch (option) {
            case OPTION_DEFAULT:
                options = defaultOptions;
                break;
            case OPTION_FORCE_FADE:
                options = forceFadeOptions;
                break;
            case OPTION_NO_ANIMATION:
                options = noAnimationOptions;
                break;
            case OPTION_NO_IMAGE_BACKGROUND_WHILE_LOADING:
                options = noImageBackgroundOptions;
                break;
            default:
                options = defaultOptions;
                break;
        }
        return options;
    }

    public static abstract class FetchSampledBitmapFromResourceId extends AsyncTask<Object, Void, Bitmap> {

        private Context mContext;

        public void start(Context context, int id, int requiredWidth, int requiredHeight) {
            Object[] object = {id, requiredWidth, requiredHeight};
            mContext = context;
            try{
                executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, object);
            }
            catch(RejectedExecutionException e){

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            onImageBitmapFetchStarted();
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            try {
                int resourceId = (int) params[0];
                int mRequiredWidth = (int) params[1];
                int mRequiredHeight = (int) params[2];

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);


                options.inJustDecodeBounds = false;

                try {
                    options.inSampleSize = ViewUtils.calculateInSampleSize(options, mRequiredWidth, mRequiredHeight);
                    options.inDither = true;
                    return BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);

                } catch (OutOfMemoryError e) {
                    e.printStackTrace();

                    options.inSampleSize = ViewUtils.calculateInSampleSize(options, mRequiredWidth / 2, mRequiredHeight / 2);
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    try {
                        return BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);
                    } catch (OutOfMemoryError oe) {

                        options.inSampleSize = ViewUtils.calculateInSampleSize(options, mRequiredWidth / 3, mRequiredHeight / 3);

                        try {
                            return BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);
                        } catch (OutOfMemoryError oe2) {

                            oe2.printStackTrace();
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                onImageBitmapFetched(bitmap);
            } else {
                onImageBitmapFetchFailure();
            }
        }

        protected abstract void onImageBitmapFetchStarted();
        protected abstract void onImageBitmapFetched(Bitmap image);
        protected abstract void onImageBitmapFetchFailure();
    }

}
