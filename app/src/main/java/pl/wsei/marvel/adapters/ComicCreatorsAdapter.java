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

public class ComicCreatorsAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> creators;

    public ComicCreatorsAdapter(final Context context, final List<String> creators) {
        super(context, R.layout.fragment_comic_creators, creators);
        this.context = context;
        this.creators = creators;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = LayoutInflater.from(this.context).inflate(R.layout.fragment_comic_creators, parent, false);
        }
        final String creatorName = this.getItem(position);
        final TextView creatorTextView = view.findViewById(R.id.comic_creator_name);
        creatorTextView.setText(creatorName);
        return view;
    }
}
