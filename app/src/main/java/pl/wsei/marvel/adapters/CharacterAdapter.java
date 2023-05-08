package pl.wsei.marvel.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import core.api.models.CharacterRow;
import pl.wsei.marvel.R;
import pl.wsei.marvel.ui.characters.CharacterCardActivity;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private List<CharacterRow> characters;

    public CharacterAdapter(final List<CharacterRow> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_character_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final CharacterRow characterRow = this.characters.get(position);
        holder.nameTextView.setText(characterRow.getName());
        holder.descriptionTextView.setText(characterRow.getDescription());
        Glide.with(holder.itemView.getContext())
                .load(characterRow.getThumbnailUrl())
                .placeholder(R.drawable.character_placeholder)
                .into(holder.thumbnailImageView);

        holder.itemView.setOnClickListener(v -> {
            final String characterId = characterRow.getId();

            final Intent intent = new Intent(v.getContext(), CharacterCardActivity.class);
            intent.putExtra("character_id", characterId);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void updateData(List<CharacterRow> newData) {
        this.characters = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnailImageView;
        public TextView nameTextView;
        public TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.character_image);
            nameTextView = itemView.findViewById(R.id.character_name);
            descriptionTextView = itemView.findViewById(R.id.character_description);
        }
    }
}