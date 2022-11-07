package com.example.culturetracker.ui.add_book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddBookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddBookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Add Book fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}