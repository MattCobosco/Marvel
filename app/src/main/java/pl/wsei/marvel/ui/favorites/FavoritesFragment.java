package pl.wsei.marvel.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import core.db.FavoriteTableManager;
import core.db.models.Favorite;
import pl.wsei.marvel.adapters.FavoriteAdapter;
import pl.wsei.marvel.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment {
    private FavoriteTableManager favoriteTableManager;
    private List<Favorite> favorites = new ArrayList<>();
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavoritesBinding binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        favoriteTableManager = new FavoriteTableManager(this.getActivity());
        String[] params = {"type", "name"};
        favorites = favoriteTableManager.getAllFavorites(params);

        adapter = new FavoriteAdapter(favorites);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        binding.favoritesRecyclerView.setLayoutManager(layoutManager);
        binding.favoritesRecyclerView.setAdapter(adapter);

        return root;
    }
}
