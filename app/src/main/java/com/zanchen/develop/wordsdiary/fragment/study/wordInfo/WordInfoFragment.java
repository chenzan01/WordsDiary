package com.zanchen.develop.wordsdiary.fragment.study.wordInfo;

import androidx.constraintlayout.helper.widget.Layer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zanchen.develop.wordsdiary.R;
import com.zanchen.develop.wordsdiary.databinding.FragmentWordInfoBinding;
import com.zanchen.develop.wordsdiary.databinding.PopupWordinfoViewBinding;
import com.zanchen.develop.wordsdiary.fragment.study.studyInfo.StudyInfoFragmentDirections;
import com.zanchen.develop.wordsdiary.util.BlurTransformationUtil;

public class WordInfoFragment extends Fragment {

    private static final String TAG = "WordInfoFragment";

    //定义binding
    private FragmentWordInfoBinding binding;

    private WordInfoViewModel mViewModel;


    private ImageButton mImageBtnBack;
    private ImageButton mImageBtnSetMemory4;
    private ImageButton mImageBtnCollect;
    private ImageButton mImageBtnToolBar;

    private Context mContext;


    public static WordInfoFragment newInstance() {
        return new WordInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //上下文
        mContext = getContext();

        binding = FragmentWordInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //绑定控件
        mImageBtnBack = binding.imageWordInfoFragmentBack;
        mImageBtnSetMemory4 = binding.imageWordInfoFragmentSetMemory4;
        mImageBtnCollect = binding.imageWordInfoFragmentCollect;
        mImageBtnToolBar = binding.imageWordInfoFragmentToolBar;

        //响应事件
        mImageBtnBack.setOnClickListener(onClickBack);
        mImageBtnSetMemory4.setOnClickListener(onClickSetMemory4);
        mImageBtnCollect.setOnClickListener(onClickCollect);
        mImageBtnToolBar.setOnClickListener(onClickToolBar);





        return root;
    }

    @SuppressLint("InflateParams")
    private void showPopUpView(){
        View view = getLayoutInflater().inflate(R.layout.popup_wordinfo_view, null);
        PopupWindow popupWindow = new PopupWindow(mContext);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(mImageBtnToolBar);
    }

    public View.OnClickListener onClickBack = view -> {
        Log.d(TAG,"onClickBack onclick");

    };

    public View.OnClickListener onClickSetMemory4 = view -> {
        Log.d(TAG,"onClickSetMemory4 onclick");

    };

    public View.OnClickListener onClickCollect = view -> {
        Log.d(TAG,"onClickCollect onclick");

    };

    public View.OnClickListener onClickToolBar = view -> {
        Log.d(TAG,"onClickToolBar onclick");
        showPopUpView();
    };


}