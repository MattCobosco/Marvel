package pl.wsei.marvel.ui.creators;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreatorsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CreatorsViewModel() {
        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}