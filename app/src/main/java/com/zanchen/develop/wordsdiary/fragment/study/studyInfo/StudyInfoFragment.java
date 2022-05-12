package com.zanchen.develop.wordsdiary.fragment.study.studyInfo;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zanchen.develop.wordsdiary.R;
import com.zanchen.develop.wordsdiary.databinding.FragmentStudyInfoBinding;

public class StudyInfoFragment extends Fragment {

    private static final String TAG = "StudyInfoFragment";

    private FragmentStudyInfoBinding binding;

    private StudyInfoViewModel mViewModel;

    private LinearLayout mCurrentBook;
    private LinearLayout mCurrentBookBack;
    private LinearLayout mNeedStudy;
    private LinearLayout mNeedReview;

    private ImageButton imageBtnChangeBookBack;
    private ImageButton imageBtnChangeBookFront;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //初始化binding
        binding = FragmentStudyInfoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        mCurrentBook = binding.layoutStudyInfoFragmentBook;
        mCurrentBook.setOnClickListener(onClickCurrentBook);
        mCurrentBookBack = binding.layoutStudyInfoFragmentBookBack;
        mNeedStudy = binding.layoutStudyInfoFragmentStudy;
        mNeedStudy.setOnClickListener(onClickNeedStudy);
        mNeedReview = binding.layoutStudyInfoFragmentReview;
        mNeedReview.setOnClickListener(onClickNeedReview);
//        imageBtnChangeBookBack = binding.imageButtonStudyInfoFragmentChangeBookBack;
//        imageBtnChangeBookFront = binding.imageButtonStudyInfoFragmentChangeBookFront;
        imageBtnChangeBookBack.setVisibility(View.VISIBLE);
        imageBtnChangeBookFront.setVisibility(View.INVISIBLE);
        setCameraDistance();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"s = " + StudyInfoFragmentArgs.fromBundle(getArguments()).getTestString());
    }

    public View.OnClickListener onClickCurrentBook = view -> {
        Log.d(TAG,"onClickCurrentBook onclick");
//        String s = "test";
//        StudyInfoFragmentDirections.ActionStudyInfoFragmentToBookInfoFragment action;
//        action = StudyInfoFragmentDirections.actionStudyInfoFragmentToBookInfoFragment();
//        action.setTestString(s);
//        Navigation.findNavController(view).navigate(action);


//        cardTurnover();

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

    /**
     * 翻牌
     */
    public void cardTurnover() {
        if (View.VISIBLE == imageBtnChangeBookBack.getVisibility()) {
            Rotatable rotatable = new Rotatable.Builder(mCurrentBook)
                    .sides(imageBtnChangeBookBack.getId(), imageBtnChangeBookFront.getId())
                    .direction(Rotatable.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatable.setTouchEnable(false);
            rotatable.rotate(Rotatable.ROTATE_Y, -180, 500);
            mCurrentBook.removeAllViewsInLayout();
            binding.getRoot().removeView(mCurrentBookBack);
            mCurrentBook.addView(mCurrentBookBack);
        } else if (View.VISIBLE == imageBtnChangeBookFront.getVisibility()) {
            Rotatable rotatable = new Rotatable.Builder(mCurrentBook)
                    .sides(imageBtnChangeBookBack.getId(), imageBtnChangeBookFront.getId())
                    .direction(Rotatable.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatable.setTouchEnable(false);
            rotatable.rotate(Rotatable.ROTATE_Y, 0, 500);
            mCurrentBook.removeAllViewsInLayout();
            binding.getRoot().removeView(mCurrentBook);
            binding.getRoot().addView(mCurrentBookBack);
        }
    }

    /**
     * 改变视角距离, 贴近屏幕
     */
    private void setCameraDistance() {
        int distance = 10000;
        float scale = getResources().getDisplayMetrics().density * distance;
        binding.getRoot().setCameraDistance(scale);
    }






}