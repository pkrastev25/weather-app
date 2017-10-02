package com.petar.weather.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.petar.weather.R;
import com.petar.weather.app.Constants;
import com.petar.weather.networking.ApiLogic;

/**
 * Contains all bindings related to {@link ImageView}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 8.7.2017
 */
public class ImageViewBindings {

    /**
     * Loads the specified image resource using {@link Glide}. Provides a
     * error placeholder if the image resource could not be loaded.
     *
     * @param view The view in which the image resource will be loaded
     * @param type The type of resource that must be loaded, must be one of {@link com.petar.weather.app.Constants.APIWeatherStateSummary}
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, @Constants.APIWeatherStateSummary String type) {
        Glide.with(view.getContext())
                .load(
                        ApiLogic.getInstance().getPNGImageUrl(type)
                )
                .error(R.mipmap.ic_launcher)
                .into(view);
    }
}
