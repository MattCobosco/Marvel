package pl.wsei.marvel.ui.comics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ComicsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ComicsViewModel() {
        this.mText = new MutableLiveData<>();
        this.mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return this.mText;
    }
}