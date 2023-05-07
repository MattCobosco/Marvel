package pl.wsei.marvel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import pl.wsei.marvel.R;

public class CharacterSeriesAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> series;

    public CharacterSeriesAdapter(final Context context, final List<String> series) {
        super(context, R.layout.character_series, series);
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = LayoutInflater.from(this.context).inflate(R.layout.character_series, parent, false);
        }
        final String seriesName = this.getItem(position);
        final TextView seriesTextView = view.findViewById(R.id.character_series_name);
        seriesTextView.setText(seriesName);
        return view;
    }
}
