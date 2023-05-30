package pl.wsei.marvel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import core.api.models.DTOs.SeriesResourceDto;
import pl.wsei.marvel.R;
import pl.wsei.marvel.ui.comics.ComicCardActivity;

public class CreatorSeriesAdapter extends ArrayAdapter<SeriesResourceDto> {
    private final Context context;
    private final List<SeriesResourceDto> series;

    public CreatorSeriesAdapter(final Context context, final List<SeriesResourceDto> series) {
        super(context, R.layout.fragment_creator_comics, series);
        this.context = context;
        this.series = series;
    }

    @Override
    public View getView(final int position, final View convertView, final @NonNull ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = LayoutInflater.from(this.context).inflate(R.layout.fragment_creator_comics, parent, false);
        }

        final String serieName = this.series.get(position).getName();
        final String serieId = this.series.get(position).getResourceUri().split("/")[this.series.get(position).getResourceUri().split("/").length - 1];
        final TextView serieTextView = view.findViewById(R.id.creator_comic_name);
        serieTextView.setText(serieName);

        serieTextView.setOnClickListener(v -> {
            final Intent intent = new Intent(this.context, ComicCardActivity.class);
            intent.putExtra("id", serieId);
            intent.putExtra("name", serieName);
            this.context.startActivity(intent);
        });

        return view;
    }
}
