package com.petar.weather.events;

import android.support.v4.app.Fragment;

public interface INavigationEvents {

    void replaceTopFragmentOnStack(
            final Fragment fragment,
            final String fragmentNavId
    );
}
