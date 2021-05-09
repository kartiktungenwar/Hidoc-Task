package com.hidoc_task.home;
import android.util.SparseArray;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    private  SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private MainActivity activity;



    public ViewPagerAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new FragmentViewNews(activity);
                break;
            case 1:
                result = new FragmentViewBooks(activity);
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return "News";
            case 1:
                return "Books";
            default:
                return null;

        }


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */


    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
