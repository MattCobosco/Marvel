package pl.wsei.marvel.adapters;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import core.api.models.CreatorRow;
import core.enums.Type;
import core.utils.IntentFactory;
import pl.wsei.marvel.R;

public class CreatorAdapter extends RecyclerView.Adapter<CreatorAdapter.ViewHolder> {
    private List<CreatorRow> creators;

    public CreatorAdapter(final List<CreatorRow> creators) {
        this.creators = creators;
    }

    private IntentFactory intentFactory;

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_creators_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        intentFactory = new IntentFactory(holder.itemView.getContext());
        final CreatorRow creatorRow = this.creators.get(position);
        holder.nameTextView.setText(creatorRow.getFullName());

        Glide.with(holder.itemView.getContext())
                .load(creatorRow.getThumbnailUrl())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.thumbnailImageView);

        holder.itemView.setOnClickListener(v -> {
            final String creatorId = creatorRow.getId();
            final String creatorName = creatorRow.getFullName();

            final Intent intent = intentFactory.createIntent(Type.CREATOR, creatorId, creatorName);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return creators.size();
    }

    public void updateData(final List<CreatorRow> creators) {
        this.creators = creators;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        public final TextView nameTextView;
        public final ImageView thumbnailImageView;

        public ViewHolder(final View view) {
            super(view);
            itemView = view;
            nameTextView = view.findViewById(R.id.creator_name);
            thumbnailImageView = view.findViewById(R.id.creator_image);
        }
    }
}
