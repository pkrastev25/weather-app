package com.petar.weather.util;

import android.os.Build;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class used to manipulate text inside {@link TextView}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 3.10.2017
 */
public class TextUtil {

    /**
     * Converts the HTML string into a text string by interpreting the
     * HTML tags.
     *
     * @param html HTML string
     * @return Interpreted HTML text
     */
    @SuppressWarnings("deprecation")
    public static String convertHTMLToText(String html) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Html.fromHtml(html).toString();
        } else {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        }
    }

    /**
     * Converts the linkText into a valid hyperlink within the text.
     *
     * @param view     {@link TextView} which will render the text with links
     * @param text     Text to be rendered. Note, it should specify an empty param which will be replaced by linkText
     * @param linkText The text which will also act as a link
     * @param URL      URL for navigating the user when the linkText is clicked
     */
    public static void linkifyText(TextView view, String text, String linkText, String URL) {
        view.setText(
                // Construct the full text (link + text) for the view
                String.format(
                        text,
                        linkText
                )
        );

        Pattern matcher = Pattern.compile(linkText);
        // Overwrite the default behavior, appending the linkText at the end of the url is not needed
        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };

        Linkify.addLinks(view, matcher, URL, null, transformFilter);
    }

    /**
     * Converts the links inside the HTML text into valid hyperlinks.
     *
     * @param view     {@link TextView} which will render the text with links
     * @param textHTML Text in the form of HTML, it should contain valid links
     */
    public static void linkifyText(TextView view, String textHTML) {
        view.setText(textHTML);
        Linkify.addLinks(view, Linkify.WEB_URLS);
    }
}
