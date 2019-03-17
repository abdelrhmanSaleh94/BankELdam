package com.AAA.abdelrahmansaleh.bankeldam.ui.fragment.HomeCycle;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.AAA.abdelrahmansaleh.bankeldam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Fragment[] fragments = {new ArticleFragment(), new BloodRequestsFragment()};

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        TabLayout homeFragmentTabLayout = view.findViewById( R.id.homeFragment__TabLayout );
        ViewPager homeFragmentVpager = view.findViewById( R.id.homeFragment_V_pager );

        PagerAdapterMainActivity pagerAdapter = new PagerAdapterMainActivity( getActivity().getSupportFragmentManager() );
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                ArticleFragment articleFragment = (ArticleFragment) fragment;
                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }
        }
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {

            try {
                BloodRequestsFragment bloodRequestsFragment = (BloodRequestsFragment) fragment;


                getFragmentManager().beginTransaction().remove( fragment ).commit();
            } catch (Exception e) {

            }

        }
        homeFragmentVpager.setAdapter( pagerAdapter );
        return view;


    }

    private class PagerAdapterMainActivity extends FragmentPagerAdapter {
        public PagerAdapterMainActivity(FragmentManager fm) {
            super( fm );
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "مقالات";
                    break;
                case 1:
                    title = "طلبات التبرع";
                    break;
            }
            return title;
        }
    }
}
