package pl.wsei.marvel.ui.characters;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import core.MarvelApiConfig;
import core.api.clients.CharacterClient;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CharacterDto;
import core.api.models.DTOs.ImageDto;
import core.api.models.DTOs.ResourceDto;
import core.db.FavoriteTableManager;
import core.db.models.Favorite;
import pl.wsei.marvel.BuildConfig;
import pl.wsei.marvel.R;
import pl.wsei.marvel.adapters.CharacterSeriesAdapter;

public class CharacterCardActivity extends AppCompatActivity {
    private CharacterDto character;
    private final FavoriteTableManager favoriteTableManager = new FavoriteTableManager(this);
    private final int favoriteIcon = R.drawable.star_24;
    private final int notFavoriteIcon = R.drawable.star_outline_24;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_character_card);

        final String characterId = this.getIntent().getStringExtra("character_id");

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<BaseResponse<CharacterDto>> callable = () -> {
            final MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.PUBLIC_KEY, BuildConfig.PRIVATE_KEY).build();

            final CharacterClient characterClient = new CharacterClient(marvelApiConfig);

            return characterClient.getCharacter(characterId);
        };

        final Future<BaseResponse<CharacterDto>> future = executorService.submit(callable);

        try {
            final BaseResponse<CharacterDto> response = future.get();
            this.character = response.getResponse();
            final Boolean isFavorite = this.favoriteTableManager.isFavorite(new Favorite("character", this.character.getId()));

            final TextView nameTextView = this.findViewById(R.id.character_name);
            nameTextView.setText(this.character.getName());

            final ImageView characterFavorite = this.findViewById(R.id.character_favorite);
            characterFavorite.setImageDrawable(isFavorite ? this.getDrawable(this.favoriteIcon) : this.getDrawable(this.notFavoriteIcon));

            final TextView descriptionTextView = this.findViewById(R.id.character_description);

            descriptionTextView.setText(this.character.getDescription());
            if (this.character.getDescription().isEmpty()) {
                final NestedScrollView characterDescriptionNestedScroll = this.findViewById(R.id.character_description_scroll);
                characterDescriptionNestedScroll.getLayoutParams().height = 0;
            }

            final ImageView imageView = this.findViewById(R.id.character_image);
            Glide.with(this).load(this.character.getThumbnail().getImageUrl(ImageDto.Size.FULLSIZE)).into(imageView);

            final List<String> seriesList = this.character.getSeries().getItems().stream().map(ResourceDto::getName).collect(Collectors.toList());
            final ListView seriesListView = this.findViewById(R.id.character_series_list);
            final CharacterSeriesAdapter seriesAdapter = new CharacterSeriesAdapter(this, seriesList);
            seriesListView.setAdapter(seriesAdapter);

        } catch (final ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        final ImageView characterFavorite = this.findViewById(R.id.character_favorite);

        characterFavorite.setOnClickListener(v -> {
            final Favorite favorite = new Favorite("character", this.character.getId());
            if (characterFavorite.getDrawable().getConstantState() == this.getDrawable(this.notFavoriteIcon).getConstantState()) {
                characterFavorite.setImageResource(this.favoriteIcon);
                this.favoriteTableManager.addFavorite(favorite);
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                characterFavorite.setImageResource(this.notFavoriteIcon);
                this.favoriteTableManager.removeFavorite(favorite);
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}