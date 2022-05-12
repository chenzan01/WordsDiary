package com.zanchen.develop.wordsdiary.fragment.study.studyInfo;

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

import com.zanchen.develop.wordsdiary.databinding.FragmentStudyInfoBinding;
import com.zanchen.develop.wordsdiary.util.RotatableUtil;

public class StudyInfoFragment extends Fragment {

    private static final String TAG = "StudyInfoFragment";

    private StudyInfoViewModel mViewModel;

    private int shortAnimationDuration;

    private LinearLayout mCurrentBook;
    private LinearLayout mCurrentBookFront;
    private LinearLayout mCurrentBookBack;
    private LinearLayout mNeedStudy;
    private LinearLayout mNeedReview;

    private ImageButton imageBtnChangeBookBack;
    private ImageButton imageBtnChangeBookFront;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //初始化binding
        com.zanchen.develop.wordsdiary.databinding.FragmentStudyInfoBinding binding =
                FragmentStudyInfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mCurrentBook = binding.layoutStudyInfoFragmentBook;
        mCurrentBook.setOnClickListener(onClickCurrentBook);
        mCurrentBookFront = binding.layoutStudyInfoFragmentBookFront;
        mCurrentBookBack = binding.layoutStudyInfoFragmentBookBack;
        mNeedStudy = binding.layoutStudyInfoFragmentStudy;
        mNeedStudy.setOnClickListener(onClickNeedStudy);
        mNeedReview = binding.layoutStudyInfoFragmentReview;
        mNeedReview.setOnClickListener(onClickNeedReview);
        imageBtnChangeBookFront = binding.imageButtonStudyInfoFragmentBookFront;
        imageBtnChangeBookBack = binding.imageButtonStudyInfoFragmentBookBack;
        imageBtnChangeBookFront.setOnClickListener(onClickChangeBookCard);
        imageBtnChangeBookBack.setOnClickListener(onClickChangeBookCard);

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"s = " + StudyInfoFragmentArgs.fromBundle(getArguments()).getTestString());
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
        if (View.VISIBLE == imageBtnChangeBookFront.getVisibility()) {
            RotatableUtil rotatableUtil = new RotatableUtil.Builder(mCurrentBook)
                    .sides(imageBtnChangeBookFront.getId(),imageBtnChangeBookBack.getId())
                    .direction(RotatableUtil.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatableUtil.setTouchEnable(false);
            rotatableUtil.rotate(RotatableUtil.ROTATE_Y, -180, 1000);
            turnBookCardToBack(true);

        } else if (View.VISIBLE == imageBtnChangeBookBack.getVisibility()) {
            RotatableUtil rotatableUtil = new RotatableUtil.Builder(mCurrentBook)
                    .sides(imageBtnChangeBookFront.getId(),imageBtnChangeBookBack.getId())
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
            mCurrentBookFront.setVisibility(View.GONE);
            mCurrentBookBack.setAlpha(0f);
            mCurrentBookBack.setVisibility(View.VISIBLE);
            mCurrentBookBack.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        }else {
            mCurrentBookBack.setVisibility(View.GONE);
            mCurrentBookFront.setAlpha(0f);
            mCurrentBookFront.setVisibility(View.VISIBLE);
            mCurrentBookFront.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);
        }
    }






}