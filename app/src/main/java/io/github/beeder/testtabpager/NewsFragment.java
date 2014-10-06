package io.github.beeder.testtabpager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;


public class NewsFragment extends Fragment {

    private static final String[] TITLE = new String[] { "TAB0", "TAB1","TAB2"};

    private ViewPager pager;
    private TabPageIndicator tabPageIndicator;
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("MyTag", "NEWS--onActivityResult");

        view = inflater.inflate(R.layout.fragment_tab,container,false);
        //Set the pager with an adapter
        pager = (ViewPager)view.findViewById(R.id.pager);
        //Bind the title indicator to the adapter
        tabPageIndicator = (TabPageIndicator)view.findViewById(R.id.indicator);
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getFragmentManager());

        pager.setAdapter(tabPageIndicatorAdapter);
        tabPageIndicator.setViewPager(pager);

        return view;
    }



    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            Bundle args = new Bundle();
            if(position==0)
            {
                fragment = new TabFragment0();
                args.putString("text", "This is TAB0");
                fragment.setArguments(args);
            }
            else if(position==1)
            {
                fragment = new TabFragment1();
                args.putString("text", "This is TAB1");
                fragment.setArguments(args);
            }
            else if(position==2)
            {
                fragment = new TabFragment2();
                args.putString("text", "This is TAB2");
                fragment.setArguments(args);
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MyTag", "NEWS--onActivityResult");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("MyTag", "NEWS--onAttach");
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        Log.d("MyTag", "NEWS--onInflate");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyTag", "NEWS--onCreate");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("MyTag", "NEWS--onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("MyTag", "NEWS--onActivityCreated");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int selectedItem=sharedPreferences.getInt("NewsSelected",0);//Default value =0
        Toast.makeText(getActivity(), "Last time, you chose TAB"+selectedItem, Toast.LENGTH_SHORT).show();
        tabPageIndicator.setCurrentItem(selectedItem);//reselect the tab last selected. but the inner fragment isn't recreated, i got empty, why?
        //pager.setAdapter(tabPageIndicatorAdapter);
        //tabPageIndicator.setViewPager(pager);

        Log.d("MyTag", "NEWS--onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MyTag", "NEWS--onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MyTag", "NEWS--onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MyTag", "NEWS--onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("MyTag", "NEWS--onConfigurationChanged");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyTag", "NEWS--onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("NewsSelected",pager.getCurrentItem());
        editor.commit();

        Log.d("MyTag", "NEWS--onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("MyTag", "NEWS--onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyTag", "NEWS--onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MyTag", "NEWS--onDetach");
    }
}
