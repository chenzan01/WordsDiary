package com.zanchen.develop.wordsdiary.fragment.study;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class StudyViewModel extends ViewModel {

    private MutableLiveData<String> dailyWords;

    private MutableLiveData<String> dailyWordsTranslate;


    public LiveData<String> getDailyWords(){
        if(dailyWords == null){
            dailyWords = new MutableLiveData<>();
            dailyWords.setValue("Library");
        }
        return dailyWords;
    }

    public LiveData<String> getDailyWordsTranslate(){
        if(dailyWordsTranslate == null){
            dailyWordsTranslate = new MutableLiveData<>();
            dailyWordsTranslate.setValue("n.图书馆；书房；");
        }
        return dailyWordsTranslate;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}