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

    private Context context;
    private List<String> series;

    public CharacterSeriesAdapter(Context context, List<String> series) {
        super(context, R.layout.character_series, series);
        this.context = context;
        this.series = series;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.character_series, parent, false);
        }
        String seriesName = getItem(position);
        TextView seriesTextView = view.findViewById(R.id.character_series_name);
        seriesTextView.setText(seriesName);
        return view;
    }
}
