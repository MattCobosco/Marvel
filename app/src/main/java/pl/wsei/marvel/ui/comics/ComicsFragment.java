package pl.wsei.marvel.ui.comics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.wsei.marvel.databinding.FragmentComicsBinding;

public class ComicsFragment extends Fragment {

    private FragmentComicsBinding binding;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        final ComicsViewModel comicsViewModel =
                new ViewModelProvider(this).get(ComicsViewModel.class);

        this.binding = FragmentComicsBinding.inflate(inflater, container, false);
        final View root = this.binding.getRoot();

        TextView textView = this.binding.textDashboard;
        comicsViewModel.getText().observe(this.getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding = null;
    }
}