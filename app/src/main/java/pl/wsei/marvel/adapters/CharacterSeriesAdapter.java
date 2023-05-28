package pl.wsei.marvel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import core.api.models.DTOs.SeriesResourceDto;
import pl.wsei.marvel.R;
import pl.wsei.marvel.ui.comics.ComicCardActivity;

public class CharacterSeriesAdapter extends ArrayAdapter<SeriesResourceDto> {
    private final Context context;
    private final List<SeriesResourceDto> series;

    public CharacterSeriesAdapter(final Context context, final List<SeriesResourceDto> series) {
        super(context, R.layout.fragment_character_comics, series);
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = LayoutInflater.from(this.context).inflate(R.layout.fragment_character_comics, parent, false);
        }
        final String seriesName = this.series.get(position).getName();
        final String seriesId = this.series.get(position).getResourceUri().split("/")[this.series.get(position).getResourceUri().split("/").length - 1];
        final TextView seriesTextView = view.findViewById(R.id.character_series_name);
        seriesTextView.setText(seriesName);

        seriesTextView.setOnClickListener(v -> {
            final Intent intent = new Intent(this.context, ComicCardActivity.class);
            intent.putExtra("id", seriesId);
            intent.putExtra("name", seriesName);
            this.context.startActivity(intent);
        });

        return view;
    }
}
