package com.petar.weather.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import com.petar.weather.R;

import java.util.ArrayList;
import java.util.List;

public final class FragmentNavigationUtil {

    public static final int PHONE_CONTENT_VIEW_ID = R.id.phone_content_view;

    private static SparseArray<List<String>> sFragmentStack = new SparseArray<>();

    public static void addFragmentToStack(
            final int contentViewId,
            final FragmentManager fragmentManager,
            final Fragment fragment,
            final String fragmentNavId
    ) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(contentViewId, fragment, fragmentNavId)
                .addToBackStack(fragmentNavId)
                .commit();

        final List<String> fragmentStack = getFragmentStackForId(contentViewId);
        fragmentStack.add(fragmentNavId);
        sFragmentStack.append(contentViewId, fragmentStack);
    }

    public static void replaceTopFragmentOnStack(
            final int contentViewId,
            final FragmentManager fragmentManager,
            final Fragment fragment,
            final String fragmentNavId
    ) {
        if (isStackEmptyForId(contentViewId)) {
            return;
        }

        final List<String> fragmentStack = getFragmentStackForId(contentViewId);
        final Fragment topFragment = fragmentManager.findFragmentByTag(fragmentStack.get(fragmentStack.size() - 1));
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .remove(topFragment)
                .replace(contentViewId, fragment, fragmentNavId)
                .addToBackStack(fragmentNavId)
                .commit();

        fragmentStack.remove(fragmentStack.size() - 1);
        fragmentStack.add(fragmentNavId);
    }

    public static void showTopFragmentOnStack(
            final int contentViewId,
            final FragmentManager fragmentManager
    ) {
        if (isStackEmptyForId(contentViewId)) {
            return;
        }

        final List<String> fragmentStack = getFragmentStackForId(contentViewId);
        final String topFragmentNavId = fragmentStack.get(fragmentStack.size() - 1);
        final Fragment topFragment = fragmentManager.findFragmentByTag(topFragmentNavId);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(contentViewId, topFragment, topFragmentNavId)
                .commit();
    }

    public static void navigateBack(
            final int contentViewId,
            final FragmentManager fragmentManager
    ) {
        if (isStackEmptyForId(contentViewId)) {
            return;
        }

        final List<String> fragmentStack = getFragmentStackForId(contentViewId);
        final String fragmentToShowNavId = fragmentStack.size() > 1
                ? fragmentStack.get(fragmentStack.size() - 2)
                : fragmentStack.get(fragmentStack.size() - 1);

        final Fragment fragmentToShow = fragmentManager.findFragmentByTag(fragmentToShowNavId);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(contentViewId, fragmentToShow, fragmentToShowNavId)
                .commit();

        fragmentStack.remove(fragmentStack.size() - 1);

        if (fragmentStack.size() == 0) {
            sFragmentStack.remove(contentViewId);
        }
    }

    public static boolean isWholeStackEmpty() {
        return sFragmentStack.size() == 0;
    }

    private static boolean isStackEmptyForId(final int contentViewId) {
        return getFragmentStackForId(contentViewId).size() == 0;
    }

    private static List<String> getFragmentStackForId(final int contentViewId) {
        final List<String> fragmentStack = sFragmentStack.get(contentViewId);

        return fragmentStack == null
                ? new ArrayList<>()
                : fragmentStack;
    }
}
