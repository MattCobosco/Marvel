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

import core.api.models.SerieRow;
import pl.wsei.marvel.R;
import pl.wsei.marvel.ui.comics.ComicCardActivity;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {
    private List<SerieRow> series;

    public ComicAdapter(final List<SerieRow> series) {
        this.series = series;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comics_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final SerieRow seriesRow = this.series.get(position);
        holder.titleTextView.setText(seriesRow.getTitle());
        holder.descriptionTextView.setText(seriesRow.getDescription());
        Glide.with(holder.itemView.getContext())
                .load(seriesRow.getThumbnailUrl())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.thumbnailImageView);

        holder.itemView.setOnClickListener(v -> {
            final String comicId = seriesRow.getId();
            final String comicTitle = seriesRow.getTitle();

            final Intent intent = new Intent(v.getContext(), ComicCardActivity.class);
            intent.putExtra("comic_id", comicId);
            intent.putExtra("comic_title", comicTitle);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public void updateData(List<SerieRow> series) {
        this.series = series;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView thumbnailImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.comic_title);
            descriptionTextView = itemView.findViewById(R.id.comic_description);
            thumbnailImageView = itemView.findViewById(R.id.comic_image);
        }
    }
}
