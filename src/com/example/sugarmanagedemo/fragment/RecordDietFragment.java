package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.fragment.BaseFragment;

import com.example.sugarmanagedemo.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecordDietFragment extends BaseFragment {

    private View mParent;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        mParent = inflater.inflate(R.layout.fragment_blank, null);
        return mParent;
    }

}
