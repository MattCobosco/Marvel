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

import core.api.models.DTOs.CreatorResourceDto;
import pl.wsei.marvel.R;

public class ComicCreatorsAdapter extends ArrayAdapter<CreatorResourceDto> {
    private final Context context;
    private final List<CreatorResourceDto> creators;

    public ComicCreatorsAdapter(final Context context, final List<CreatorResourceDto> creators) {
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
        final String creatorName = this.creators.get(position).getName();
        final String creatorRole = this.creators.get(position).getRole();
        final String creatorId = this.creators.get(position).getResourceUri().split("/")[this.creators.get(position).getResourceUri().split("/").length - 1];
        final TextView creatorTextView = view.findViewById(R.id.comic_creator_name);
        creatorTextView.setText(creatorName + " (" + creatorRole + ")");

//        creatorTextView.setOnClickListener(v -> {
//            final Intent intent = new Intent(this.context, pl.wsei.marvel.ui.creators.CreatorCardActivity.class);
//            intent.putExtra("id", creatorId);
//            intent.putExtra("name", creatorName);
//            this.context.startActivity(intent);
//        });

        return view;
    }
}
