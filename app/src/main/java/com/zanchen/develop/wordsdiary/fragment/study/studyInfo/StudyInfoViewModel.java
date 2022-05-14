package com.zanchen.develop.wordsdiary.fragment.study.studyInfo;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudyInfoViewModel extends ViewModel {

    private static final String TAG = "StudyInfoViewModel";

    private MutableLiveData<String> currentBook;
    private MutableLiveData<String> totalAmount;
    private MutableLiveData<String> studyAmount;


    public void initViewModel(){
        currentBook = new MutableLiveData<>();
        totalAmount = new MutableLiveData<>();
        studyAmount = new MutableLiveData<>();
        currentBook.postValue("CET-4词汇");

    }

    public LiveData<String> getTotalAmount() {
        new Thread(() -> {
            try{
                //http客户端
                OkHttpClient client = new OkHttpClient();
                //http请求
                Request requestTotalAmount = new Request.Builder()
                        .url("http://192.168.1.34:8080/cet4/queryWordsTotalAmount")
                        .post(RequestBody.create(null,""))
                        .build();
                //执行发送
                Response response = client.newCall(requestTotalAmount).execute();
                Log.d(TAG,"请求成功！");
                updateTotalAmount(Objects.requireNonNull(response.body()).string());
            }catch (Exception e){
                e.printStackTrace();
                Log.d(TAG,"请求失败！错误信息：" + e);
            }
        }).start();
        return totalAmount;
    }

    public LiveData<String> getStudyAmount() {
        new Thread(() -> {
            try{
                //http客户端
                OkHttpClient client = new OkHttpClient();
                //http请求
                Request requestTotalAmount = new Request.Builder()
                        .url("http://192.168.1.34:8080/cet4/queryWordsStudyAmount")
                        .post(RequestBody.create(null,""))
                        .build();
                //执行发送
                Response response = client.newCall(requestTotalAmount).execute();
                Log.d(TAG,"请求成功！");
                updateStudyAmount(Objects.requireNonNull(response.body()).string());
            }catch (Exception e){
                e.printStackTrace();
                Log.d(TAG,"请求失败！错误信息：" + e);
            }
        }).start();
        return studyAmount;
    }

    public void updateTotalAmount(String amount){
        totalAmount.postValue(amount);
    }

    public void updateStudyAmount(String amount){
        studyAmount.postValue(amount);
    }

}