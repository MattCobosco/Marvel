package pl.wsei.marvel.ui.creators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.wsei.marvel.databinding.FragmentCreatorsBinding;

public class CreatorsFragment extends Fragment {

    private FragmentCreatorsBinding binding;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        final CreatorsViewModel creatorsViewModel =
                new ViewModelProvider(this).get(CreatorsViewModel.class);

        this.binding = FragmentCreatorsBinding.inflate(inflater, container, false);
        final View root = this.binding.getRoot();

        TextView textView = this.binding.textNotifications;
        creatorsViewModel.getText().observe(this.getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }
}