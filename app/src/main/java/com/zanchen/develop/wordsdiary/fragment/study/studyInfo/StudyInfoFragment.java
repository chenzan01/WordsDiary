package com.zanchen.develop.wordsdiary.fragment.study.studyInfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zanchen.develop.wordsdiary.databinding.FragmentStudyInfoBinding;
import com.zanchen.develop.wordsdiary.util.RotatableUtil;

public class StudyInfoFragment extends Fragment {

    private static final String TAG = "StudyInfoFragment";

    //定义binding
    private FragmentStudyInfoBinding binding;

    private StudyInfoViewModel mViewModel;

    private int shortAnimationDuration;

    private LinearLayout mLayoutCurrentBook;
    private LinearLayout mLayoutNeedStudy;
    private LinearLayout mLayoutNeedReview;

    private TextView mTextCurrentBook;
    private TextView mTextTotalAmount;
    private TextView mTextStudyAmount;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //初始化binding
        binding = FragmentStudyInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //绑定控件
        mLayoutCurrentBook = binding.layoutStudyInfoFragmentBookInfo;
        mLayoutNeedStudy = binding.layoutStudyInfoFragmentStudy;
        mLayoutNeedReview = binding.layoutStudyInfoFragmentReview;
        mTextCurrentBook = binding.textStudyInfoFragmentCurrentBook;
        mTextTotalAmount = binding.textStudyInfoFragmentTotalAmount;
        mTextStudyAmount = binding.textStudyInfoFragmentStudyAmount;

        //响应事件
        mLayoutCurrentBook.setOnClickListener(onClickCurrentBook);
        mLayoutNeedStudy.setOnClickListener(onClickNeedStudy);
        mLayoutNeedReview.setOnClickListener(onClickNeedReview);

        shortAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);
        //初始化数据
        initData();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"s = " + StudyInfoFragmentArgs.fromBundle(getArguments()).getTestString());
    }

    private void initData(){
        mViewModel = new ViewModelProvider(this).get(StudyInfoViewModel.class);
        mViewModel.initViewModel();
        //从数据库获取数据
        final MutableLiveData<String> currentBook;
        mTextCurrentBook.setText("CET-4");

        final MutableLiveData<String> totalAmount = (MutableLiveData<String>)mViewModel.getTotalAmount();
        totalAmount.observe(getViewLifecycleOwner(),s -> mTextTotalAmount.setText(s));

        final MutableLiveData<String> studyAmount = (MutableLiveData<String>)mViewModel.getStudyAmount();
        studyAmount.observe(getViewLifecycleOwner(),s -> mTextStudyAmount.setText(s));



    }

    public View.OnClickListener onClickCurrentBook = view -> {
        Log.d(TAG,"onClickCurrentBook onclick");
        String s = "test";
        StudyInfoFragmentDirections.ActionStudyInfoFragmentToBookInfoFragment action;
        action = StudyInfoFragmentDirections.actionStudyInfoFragmentToBookInfoFragment();
        action.setTestString(s);
        Navigation.findNavController(view).navigate(action);
    };

    public View.OnClickListener onClickNeedStudy = view -> {
        Log.d(TAG,"onClickNeedStudy onclick");
        String s = "test";
        StudyInfoFragmentDirections.ActionStudyInfoFragmentToWordInfoFragment action;
        action = StudyInfoFragmentDirections.actionStudyInfoFragmentToWordInfoFragment();
        action.setTestString(s);
        Navigation.findNavController(view).navigate(action);
    };

    public View.OnClickListener onClickNeedReview = view -> {
        Log.d(TAG,"onClickNeedReview onclick");
        String s = "test";
        StudyInfoFragmentDirections.ActionStudyInfoFragmentToWordInfoFragment action;
        action = StudyInfoFragmentDirections.actionStudyInfoFragmentToWordInfoFragment();
        action.setTestString(s);
        Navigation.findNavController(view).navigate(action);
    };






}