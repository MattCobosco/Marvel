package pl.wsei.marvel.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import core.db.models.Favorite;
import core.enums.Type;
import core.utils.IntentFactory;
import pl.wsei.marvel.R;
import pl.wsei.marvel.TypeToIconDictionary;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoritesViewHolder> {
    private List<Favorite> favorites;

    public FavoriteAdapter(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    private IntentFactory intentFactory;

    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite_row, parent, false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        intentFactory = new IntentFactory(holder.itemView.getContext());
        final Favorite favorite = this.favorites.get(position);

        holder.typeImageView.setImageResource(TypeToIconDictionary.getIcon(favorite.getType()));
        holder.idTextView.setText(favorite.getId());
        holder.nameTextView.setText(favorite.getName());

        holder.itemView.setOnClickListener(v -> {
            final Type type = favorite.getType();
            final String id = favorite.getId();
            final String name = favorite.getName();

            final Intent intent = intentFactory.createIntent(type, id, name);
            v.getContext().startActivity(intent);
        });
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
