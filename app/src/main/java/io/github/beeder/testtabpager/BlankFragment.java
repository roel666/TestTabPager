package io.github.beeder.testtabpager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BlankFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("MyTag", "TestPage--onCreateView");

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView textView = (TextView)view.findViewById(R.id.text_view);
        textView.setText(getArguments().getString("text"));
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MyTag", "TestPage--onCreate");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("MyTag", "TestPage--onDestroyView");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MyTag", "TestPage--onActivityResult");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d("MyTag", "TestPage--onAttach");
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        Log.d("MyTag", "TestPage--onInflate");
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("MyTag", "TestPage--onViewCreated");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("MyTag", "TestPage--onActivityCreated");
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("MyTag", "TestPage--onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MyTag", "TestPage--onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MyTag", "TestPage--onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MyTag", "TestPage--onSaveInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("MyTag", "TestPage--onConfigurationChanged");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyTag", "TestPage--onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MyTag", "TestPage--onStop");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyTag", "TestPage--onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MyTag", "TestPage--onDetach");
    }

}
