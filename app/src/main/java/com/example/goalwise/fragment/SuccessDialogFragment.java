package com.example.goalwise.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


import com.example.goalwise.R;
import com.example.goalwise.activity.MainActivity;

public class SuccessDialogFragment extends androidx.fragment.app.DialogFragment {

    private TextView gotIt,fundNameTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, container, false);

        iniIds(view);
        handleClickEvent();
        getValueFromBundleAndUpdateMsg();
        return view;
    }

    private void getValueFromBundleAndUpdateMsg() {
        Bundle bundle = getArguments();
        if(bundle != null){
            String fundName = bundle.getString(MainActivity.FUND_NAME);
            if(fundName != null){
                String fundNameStr = fundName.concat(getString(R.string.space)).
                        concat(getString(R.string.has_been_added_your_dashboard));
                fundNameTv.setText(fundNameStr);
            }
        }
    }

    private void handleClickEvent() {
        gotIt.setOnClickListener(view1 -> dismiss());
    }

    private void iniIds(View view) {
        gotIt = view.findViewById(R.id.gotIt);
        fundNameTv = view.findViewById(R.id.fundName);
    }

}
