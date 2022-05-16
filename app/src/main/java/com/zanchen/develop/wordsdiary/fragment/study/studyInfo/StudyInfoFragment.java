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
    private LinearLayout mLayoutCurrentBookFront;
    private LinearLayout mLayoutCurrentBookBack;
    private LinearLayout mLayoutNeedStudy;
    private LinearLayout mLayoutNeedReview;

    private ImageButton mImageBtnChangeBookBack;
    private ImageButton mImageBtnChangeBookFront;

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
        mLayoutCurrentBook = binding.layoutStudyInfoFragmentBook;
        mLayoutCurrentBookFront = binding.layoutStudyInfoFragmentBookFront;
        mLayoutCurrentBookBack = binding.layoutStudyInfoFragmentBookBack;
        mLayoutNeedStudy = binding.layoutStudyInfoFragmentStudy;
        mLayoutNeedReview = binding.layoutStudyInfoFragmentReview;

        mImageBtnChangeBookFront = binding.imageButtonStudyInfoFragmentBookFront;
        mImageBtnChangeBookBack = binding.imageButtonStudyInfoFragmentBookBack;

        mTextCurrentBook = binding.textStudyInfoFragmentCurrentBook;
        mTextTotalAmount = binding.textStudyInfoFragmentTotalAmount;
        mTextStudyAmount = binding.textStudyInfoFragmentStudyAmount;

        //响应事件
        mLayoutCurrentBook.setOnClickListener(onClickCurrentBook);
        mLayoutNeedStudy.setOnClickListener(onClickNeedStudy);
        mLayoutNeedReview.setOnClickListener(onClickNeedReview);
        mImageBtnChangeBookFront.setOnClickListener(onClickChangeBookCard);
        mImageBtnChangeBookBack.setOnClickListener(onClickChangeBookCard);

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
        mTextCurrentBook.setText("CET-4词汇");

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

    public View.OnClickListener onClickChangeBookCard = view -> cardTurnover();

    /**
     * 翻牌
     */
    public void cardTurnover() {
        if (View.VISIBLE == mImageBtnChangeBookFront.getVisibility()) {
            RotatableUtil rotatableUtil = new RotatableUtil.Builder(mLayoutCurrentBook)
                    .sides(mImageBtnChangeBookFront.getId(),mImageBtnChangeBookBack.getId())
                    .direction(RotatableUtil.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatableUtil.setTouchEnable(false);
            rotatableUtil.rotate(RotatableUtil.ROTATE_Y, -180, 1000);
            turnBookCardToBack(true);

        } else if (View.VISIBLE == mImageBtnChangeBookBack.getVisibility()) {
            RotatableUtil rotatableUtil = new RotatableUtil.Builder(mLayoutCurrentBook)
                    .sides(mImageBtnChangeBookFront.getId(),mImageBtnChangeBookBack.getId())
                    .direction(RotatableUtil.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatableUtil.setTouchEnable(false);
            rotatableUtil.rotate(RotatableUtil.ROTATE_Y, 0, 1000);
            turnBookCardToBack(false);

        }
    }

    private void turnBookCardToBack(boolean direction){
        if(direction){
            mLayoutCurrentBookFront.setVisibility(View.GONE);
            mLayoutCurrentBookBack.setAlpha(0f);
            mLayoutCurrentBookBack.setVisibility(View.VISIBLE);
            mLayoutCurrentBookBack.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        }else {
            mLayoutCurrentBookBack.setVisibility(View.GONE);
            mLayoutCurrentBookFront.setAlpha(0f);
            mLayoutCurrentBookFront.setVisibility(View.VISIBLE);
            mLayoutCurrentBookFront.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        }
    }






}