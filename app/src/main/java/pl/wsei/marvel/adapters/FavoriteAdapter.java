package pl.wsei.marvel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import core.db.models.Favorite;
import pl.wsei.marvel.R;
import pl.wsei.marvel.TypeToIconDictionary;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoritesViewHolder> {
    private List<Favorite> favorites;
    public FavoriteAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite_row, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        final Favorite favorite = this.favorites.get(position);

        holder.typeImageView.setImageResource(TypeToIconDictionary.getIcon(favorite.getType()));
        holder.idTextView.setText(favorite.getId());
        holder.nameTextView.setText(favorite.getName());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        public ImageView typeImageView;
        public TextView idTextView;
        public TextView nameTextView;

        public FavoritesViewHolder(@NonNull final View view) {
            super(view);
            typeImageView = view.findViewById(R.id.favorite_type_image_view);
            idTextView = view.findViewById(R.id.favorite_id_text_view);
            nameTextView = view.findViewById(R.id.favorite_name_text_view);
        }
    }
}
