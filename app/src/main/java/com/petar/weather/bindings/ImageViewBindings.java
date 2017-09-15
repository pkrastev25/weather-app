package com.petar.weather.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.petar.weather.R;
import com.petar.weather.app.Constants;
import com.petar.weather.networking.ApiLogic;

/**
 * Created by User on 8.7.2017 г..
 */

public class ImageViewBindings {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, @Constants.APIWeatherStateSummary String type) {
        Glide.with(view.getContext()).load(
                ApiLogic.getInstance().getPNGImageUrl(type)
        ).error(R.mipmap.ic_launcher).into(view);
    }
}
