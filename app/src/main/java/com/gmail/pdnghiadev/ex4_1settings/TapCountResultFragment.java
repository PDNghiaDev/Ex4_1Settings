package com.gmail.pdnghiadev.ex4_1settings;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.gmail.pdnghiadev.ex4_1settings.adapter.ResultItemAdapter;
import com.gmail.pdnghiadev.ex4_1settings.common.OnButtonClickListener;
import com.gmail.pdnghiadev.ex4_1settings.model.ResultItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PDNghiaDev on 11/30/2015.
 * Class perform load data get to Activity
 */
public class TapCountResultFragment extends Fragment implements OnButtonClickListener {
    public static final String TAG = "TapCountResultFragment";
    private List<ResultItem> list = new ArrayList<>();
    private ResultItemAdapter adapter;
    private static final String LIST_HIGHSCORE = "list_highscore";

    @Override
    public void onClick(ResultItem item) {
        // GET View and update on View
        list.add(item);

        if (list.size() >= 1) {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttack");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList(LIST_HIGHSCORE);
        }
        View view = inflater.inflate(R.layout.result_fragment, container, false);

        if (list != null) {
            adapter = new ResultItemAdapter(this.getActivity(), R.layout.result_item, list);
            ListView listView = (ListView) view.findViewById(R.id.listView);
            listView.setDivider(new ColorDrawable(getResources().getColor(R.color.colorDivider)));
            listView.setDividerHeight(1);
            listView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

        outState.putParcelableArrayList(LIST_HIGHSCORE, (ArrayList<? extends Parcelable>) list);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}
