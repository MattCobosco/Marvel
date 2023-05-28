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

import core.api.models.DTOs.CharacterResourceDto;
import pl.wsei.marvel.R;
import pl.wsei.marvel.ui.characters.CharacterCardActivity;

public class ComicCharactersAdapter extends ArrayAdapter<CharacterResourceDto> {
    private final Context context;
    private final List<CharacterResourceDto> characters;

    public ComicCharactersAdapter(final Context context, final List<CharacterResourceDto> characters) {
        super(context, R.layout.fragment_comic_characters, characters);
        this.context = context;
        this.characters = characters;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, final @NonNull ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            view = LayoutInflater.from(this.context).inflate(R.layout.fragment_comic_characters, parent, false);
        }
        final String characterName = this.characters.get(position).getName();
        final String characterId = this.characters.get(position).getResourceUri().split("/")[this.characters.get(position).getResourceUri().split("/").length - 1];
        final TextView characterTextView = view.findViewById(R.id.comic_character_name);
        characterTextView.setText(characterName);

        characterTextView.setOnClickListener(v -> {
            final Intent intent = new Intent(this.context, CharacterCardActivity.class);
            intent.putExtra("id", characterId);
            intent.putExtra("name", characterName);
            this.context.startActivity(intent);
        });

        return view;
    }
}
