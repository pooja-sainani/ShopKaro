package cfh.com.shopkaro;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

import cfh.com.shopkaro.Utils.CustomImageLoader;
import cfh.com.shopkaro.Utils.ViewUtils;
import cfh.com.shopkaro.views.ViewPagerCustomDuration;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Activity mActivity;
    private View mGetView;
    private int width, height;
    ArrayList<Integer> banners;
    BannersPagerAdapter mBannersPagerAdapter;

    ArrayList<ImageView> dots;
    ViewPagerCustomDuration mViewPager;
    private int mContainerHeight;
    private static final int AUTO_SLIDING_TIME = 2500;

    // Tells whether the tour pages need to auto transit or not depending upon the scroll state
    private boolean mShouldTransit = true;
    private boolean mStopTransition = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        banners = new ArrayList<Integer>(Arrays.asList(R.drawable.cakesbanner, R.drawable.jewellerybanner, R.drawable.cookiesbanner, R.drawable.handicraftsbanner));

        mGetView = getView();
        mActivity = getActivity();
        width = ViewUtils.getScreenWidth(mActivity);
        height = ViewUtils.getScreenHeight(mActivity);

        mContainerHeight = width/2;

        setTheHeaderViewPagerAdapter(banners);
        setAutoTransitionOfBannerImages();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private class BannersPagerAdapter extends PagerAdapter {

        ArrayList<Integer> localBannersList;

        public BannersPagerAdapter(ArrayList<Integer> bannersList) {
            this.localBannersList = bannersList;
        }

        @Override
        public int getCount() {
            return localBannersList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            LayoutInflater inflater = LayoutInflater.from(mActivity);
            View layout = inflater.inflate(R.layout.carousal_pager_item, null);
            final View progressBar = layout.findViewById(R.id.progressBar);
            View imageContainer = layout.findViewById(R.id.image_container);
            final ImageView imageView = (ImageView) layout.findViewById(R.id.iv_image);

            new CustomImageLoader.FetchSampledBitmapFromResourceId() {
                @Override
                protected void onImageBitmapFetchStarted() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onImageBitmapFetched(Bitmap image) {
                    progressBar.setVisibility(View.GONE);
                    imageView.setImageBitmap(image);
                }

                @Override
                protected void onImageBitmapFetchFailure() {
                    imageView.setBackgroundColor(mActivity.getResources().getColor(R.color.color_mid_grey));
                    progressBar.setVisibility(View.GONE);
                }
            }.start(mActivity, localBannersList.get(position), width / 2, width / 2);

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            try {
                container.removeView((View) object);
            } catch (Throwable t) {
            }
        }
    }


    private void setTheHeaderViewPagerAdapter(ArrayList<Integer> newBanners) {


        if (!isAdded())
            return;

        if (newBanners != null && newBanners.size() != 0) {
            mGetView.findViewById(R.id.pager_container).setVisibility(View.VISIBLE);

        } else {
            mGetView.findViewById(R.id.pager_container).setVisibility(View.GONE);
        }

        banners = newBanners;
        mViewPager = (ViewPagerCustomDuration) mGetView.findViewById(R.id.fragment_pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.getLayoutParams().height = mContainerHeight;

        mBannersPagerAdapter = new BannersPagerAdapter(banners);
        mViewPager.setAdapter(mBannersPagerAdapter);

        dots = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        LinearLayout dotsLayout = (LinearLayout) mGetView.findViewById(R.id.dots);
        dotsLayout.removeAllViews();

        int dotSize = newBanners.size();

        if (dotSize > 1) {
            for (int i = 0; i < dotSize; i++) {
                ImageView dot = new ImageView(mActivity.getApplicationContext());
                if (i != 0) {
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.home_fragment_pager_white_dot));
                    dot.setPadding(10, 5, 5, 5);

                } else {
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.home_fragment_pager_red_dot));
                    dot.setPadding(5, 5, 5, 5);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                dotsLayout.addView(dot, params);
                dots.add(dot);
            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        mShouldTransit = true;
                        mViewPager.postDelayed(new AutoPageTransitionRunnable(), AUTO_SLIDING_TIME);
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mShouldTransit = false;
                        break;
                    default:
                        mShouldTransit = false;
                }
            }
        });

    }


    public void selectDot(int idx) {

        if (!isAdded() || mActivity == null)
            return;

        Resources res = getResources();
        int dotSize = banners.size();

        for (int i = 0; i < dotSize; i++) {
            int drawableId = (i == idx) ? (R.drawable.home_fragment_pager_red_dot)
                    : (R.drawable.home_fragment_pager_white_dot);
            Drawable drawable = res.getDrawable(drawableId);
            dots.get(i).setImageDrawable(drawable);
        }
    }

    private class AutoPageTransitionRunnable implements Runnable {

        @Override
        public void run() {
            if (mViewPager != null) {

                int currentPage = mViewPager.getCurrentItem();
                int nextPage;

                if (currentPage == mBannersPagerAdapter.getCount() - 1) {
                    nextPage = 0;
                } else {
                    nextPage = currentPage + 1;
                }

                if (mShouldTransit && !mStopTransition) {
                    mViewPager.setScrollDurationFactor(3); // Double scrolling speed
                    mViewPager.setCurrentItem(nextPage, true);
                    mViewPager.setScrollDurationFactor(1); // Double scrolling speed
                }
            }
        }
    }

    private void setAutoTransitionOfBannerImages() {
        mViewPager.postDelayed(new AutoPageTransitionRunnable(), (int) ((float) AUTO_SLIDING_TIME * 1.5f));
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStopTransition = true;
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {

        try {
            if (mViewPager != null) {
                mViewPager.setAdapter(null);
                mViewPager.removeAllViewsInLayout();
                mViewPager = null;
            }

            if (mBannersPagerAdapter != null) {
                mBannersPagerAdapter = null;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }
}
