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
    private FavoriteTableManager favoriteTableManager = new FavoriteTableManager(this);
    private int favoriteIcon = R.drawable.star_24;
    private int notFavoriteIcon = R.drawable.star_outline_24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_card);

        String characterId = getIntent().getStringExtra("character_id");

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<BaseResponse<CharacterDto>> callable = () -> {
            MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.PUBLIC_KEY, BuildConfig.PRIVATE_KEY).build();

            CharacterClient characterClient = new CharacterClient(marvelApiConfig);

            return characterClient.getCharacter(characterId);
        };

        Future<BaseResponse<CharacterDto>> future = executorService.submit(callable);

        try {
            BaseResponse<CharacterDto> response = future.get();
            character = response.getResponse();
            Boolean isFavorite = favoriteTableManager.isFavorite(new Favorite("character", character.getId()));

            TextView nameTextView = findViewById(R.id.character_name);
            nameTextView.setText(character.getName());

            ImageView characterFavorite = findViewById(R.id.character_favorite);
            characterFavorite.setImageDrawable(isFavorite ? getDrawable(favoriteIcon) : getDrawable(notFavoriteIcon));

            TextView descriptionTextView = findViewById(R.id.character_description);

            descriptionTextView.setText(character.getDescription());
            if (character.getDescription().isEmpty()) {
                NestedScrollView characterDescriptionNestedScroll = findViewById(R.id.character_description_scroll);
                characterDescriptionNestedScroll.getLayoutParams().height = 0;
            }

            ImageView imageView = findViewById(R.id.character_image);
            Glide.with(this).load(character.getThumbnail().getImageUrl(ImageDto.Size.FULLSIZE)).into(imageView);

            List<String> seriesList = character.getSeries().getItems().stream().map(ResourceDto::getName).collect(Collectors.toList());
            ListView seriesListView = findViewById(R.id.character_series_list);
            CharacterSeriesAdapter seriesAdapter = new CharacterSeriesAdapter(this, seriesList);
            seriesListView.setAdapter(seriesAdapter);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ImageView characterFavorite = findViewById(R.id.character_favorite);

        characterFavorite.setOnClickListener(v -> {
            Favorite favorite = new Favorite("character", character.getId());
            if (characterFavorite.getDrawable().getConstantState() == getDrawable(notFavoriteIcon).getConstantState()) {
                characterFavorite.setImageResource(favoriteIcon);
                favoriteTableManager.addFavorite(favorite);
                Toast.makeText(this, String.format("Added %s to favorites", character.getName()), Toast.LENGTH_SHORT).show();
            } else {
                characterFavorite.setImageResource(notFavoriteIcon);
                favoriteTableManager.removeFavorite(favorite);
                Toast.makeText(this, String.format("Removed %s from favorites", character.getName()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteTableManager.closeDbConnection();
    }
}