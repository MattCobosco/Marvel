package pl.wsei.marvel.ui.characters;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import core.api.utils.ApiKeysManager;
import core.db.FavoriteTableManager;
import core.db.HistoryTableManager;
import core.db.models.Favorite;
import core.db.models.HistoryEntry;
import core.enums.Type;
import core.utils.ConfigManager;
import core.utils.FileManager;
import core.utils.PermissionManager;
import pl.wsei.marvel.R;
import pl.wsei.marvel.adapters.CharacterSeriesAdapter;

public class CharacterCardActivity extends AppCompatActivity {
    private ApiKeysManager apiKeysManager;
    private ConfigManager configManager;
    private FileManager fileManager;
    private PermissionManager permissionManager;
    private CharacterDto character;
    private FavoriteTableManager favoriteTableManager = new FavoriteTableManager(this);
    private HistoryTableManager historyTableManager = new HistoryTableManager(this);
    private int favoriteIcon = R.drawable.star_24;
    private int notFavoriteIcon = R.drawable.star_outline_24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_card);
        apiKeysManager = new ApiKeysManager(this);
        configManager = new ConfigManager(this);
        fileManager = new FileManager(this);
        permissionManager = new PermissionManager(this);
        boolean isHistoryEnabled = configManager.isHistoryEnabled();

        String characterId = getIntent().getStringExtra("id");
        String characterName = getIntent().getStringExtra("name");
        String publicKey = apiKeysManager.getPublicKey();
        String privateKey = apiKeysManager.getPrivateKey();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        if (isHistoryEnabled) {
            executorService.submit(() -> {
                historyTableManager.addHistoryEntry(new HistoryEntry(Type.CHARACTER, characterId, characterName, System.currentTimeMillis()));
            });
        }

        Callable<BaseResponse<CharacterDto>> callable = () -> {
            MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).build();

            CharacterClient characterClient = new CharacterClient(marvelApiConfig);

            return characterClient.getCharacter(characterId);
        };

        Future<BaseResponse<CharacterDto>> future = executorService.submit(callable);

        try {
            BaseResponse<CharacterDto> response = future.get();
            character = response.getResponse();
            Boolean isFavorite = favoriteTableManager.isFavorite(new Favorite(Type.CHARACTER, character.getId(), character.getName()));

            TextView nameTextView = findViewById(R.id.character_name);
            nameTextView.setText(character.getName());

            ImageView characterFavorite = findViewById(R.id.character_favorite);
            characterFavorite.setImageResource(isFavorite ? favoriteIcon : notFavoriteIcon);

            String characterDescription = character.getDescription();
            TextView descriptionTextView = findViewById(R.id.character_description);
            descriptionTextView.setText(characterDescription);
            if (characterDescription == null || characterDescription.isEmpty()) {
                NestedScrollView characterDescriptionNestedScroll = findViewById(R.id.character_description_scroll);
                characterDescriptionNestedScroll.getLayoutParams().height = 0;
            }

            ImageView imageView = findViewById(R.id.character_image);
            Glide.with(this).load(character.getThumbnail().getImageUrl(ImageDto.Size.FULLSIZE)).into(imageView);

            imageView.setOnLongClickListener(v -> {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                if (!permissionManager.isStoragePermissionGranted()) {
                    permissionManager.requestStoragePermission(() -> saveImage(bitmap));
                } else {
                    saveImage(bitmap);
                }
                return true;
            });

            List<String> seriesList = character.getSeries().getItems().stream().map(ResourceDto::getName).collect(Collectors.toList());
            ListView seriesListView = findViewById(R.id.character_series_list);
            CharacterSeriesAdapter seriesAdapter = new CharacterSeriesAdapter(this, seriesList);
            seriesListView.setAdapter(seriesAdapter);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ImageView characterFavorite = findViewById(R.id.character_favorite);

        characterFavorite.setOnClickListener(v -> {
            Favorite favorite = new Favorite(Type.CHARACTER, character.getId(), character.getName());
            if (characterFavorite.getDrawable().getConstantState() == getDrawable(notFavoriteIcon).getConstantState()) {
                characterFavorite.setImageResource(favoriteIcon);
                favoriteTableManager.addFavorite(favorite);
                Toast.makeText(this, String.format("Added %s to favorites", character.getId()), Toast.LENGTH_SHORT).show();
            } else {
                characterFavorite.setImageResource(notFavoriteIcon);
                favoriteTableManager.removeFavorite(favorite);
                Toast.makeText(this, String.format("Removed %s from favorites", character.getName()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveImage(Bitmap bitmap) {
        fileManager.saveImageToGallery(bitmap);
        Toast.makeText(CharacterCardActivity.this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteTableManager.closeDbConnection();
        historyTableManager.closeDbConnection();
    }
}