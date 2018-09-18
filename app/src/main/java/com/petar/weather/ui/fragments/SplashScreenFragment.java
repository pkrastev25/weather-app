package com.petar.weather.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.petar.weather.R;
import com.petar.weather.events.INavigationEvents;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenFragment extends Fragment implements Animation.AnimationListener {

    public static final String NAV_ID = "SPLASH_SCREEN_FRAGMENT_NAV_ID";

    @Nullable
    private INavigationEvents mNavigationEvents;

    @BindView(R.id.splash_screen_view)
    View splashScreen;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mNavigationEvents = (INavigationEvents) context;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        final View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_animation);
        fadeInAnimation.setAnimationListener(this);
        splashScreen.setAnimation(fadeInAnimation);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mNavigationEvents = null;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // Do nothing...
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        animation.setAnimationListener(null);

        if (mNavigationEvents != null) {
            mNavigationEvents.replaceTopFragmentOnStack(
                    ForecastOverviewFragment.newInstance(),
                    ForecastOverviewFragment.NAV_ID
            );
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // Do nothing...
    }

    public static Fragment newInstance() {
        return new SplashScreenFragment();
    }
}
