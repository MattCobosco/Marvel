package pl.wsei.marvel.ui.characters;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import core.MarvelApiConfig;
import core.clients.CharacterClient;
import core.models.DTOs.BaseResponse;
import core.models.DTOs.CharacterDto;
import core.models.DTOs.ImageDto;
import pl.wsei.marvel.BuildConfig;
import pl.wsei.marvel.R;
import pl.wsei.marvel.adapters.CharacterSeriesAdapter;

public class CharacterCardActivity extends AppCompatActivity {

    private CharacterDto character;

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

            TextView nameTextView = findViewById(R.id.character_name);
            nameTextView.setText(character.getName());

            TextView descriptionTextView = findViewById(R.id.character_description);
            descriptionTextView.setText(character.getDescription());

            ImageView imageView = findViewById(R.id.character_image);
            Glide.with(this).load(character.getThumbnail().getImageUrl(ImageDto.Size.FULLSIZE)).into(imageView);

            List<String> seriesList = character.getSeries().getItems().stream().map(seriesResourceDto -> seriesResourceDto.getName()).collect(Collectors.toList());
            ListView seriesListView = findViewById(R.id.character_series_list);
            CharacterSeriesAdapter seriesAdapter = new CharacterSeriesAdapter(this, seriesList);
            seriesListView.setAdapter(seriesAdapter);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}