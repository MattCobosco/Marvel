package pl.wsei.marvel.ui.comics;

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
import core.api.clients.SeriesClient;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.ImageDto;
import core.api.models.DTOs.ResourceDto;
import core.api.models.DTOs.ResourcesDto;
import core.api.models.DTOs.SerieDto;
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
import pl.wsei.marvel.adapters.ComicCreatorsAdapter;
import pl.wsei.marvel.ui.characters.CharacterCardActivity;

public class ComicCardActivity extends AppCompatActivity {
    private ApiKeysManager apiKeysManager;
    private ConfigManager configManager;
    private FileManager fileManager;
    private PermissionManager permissionManager;
    private SerieDto serie;
    private FavoriteTableManager favoriteTableManager = new FavoriteTableManager(this);
    private HistoryTableManager historyTableManager = new HistoryTableManager(this);
    private int favoriteIcon = R.drawable.star_24;
    private int notFavoriteIcon = R.drawable.star_outline_24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_card);
        apiKeysManager = new ApiKeysManager(this);
        configManager = new ConfigManager(this);
        fileManager = new FileManager(this);
        permissionManager = new PermissionManager(this);
        boolean isHistoryEnabled = configManager.isHistoryEnabled();

        String comicId = getIntent().getStringExtra("comic_id");
        String comicTitle = getIntent().getStringExtra("comic_title");
        String publicKey = apiKeysManager.getPublicKey();
        String privateKey = apiKeysManager.getPrivateKey();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        if (isHistoryEnabled) {
            executorService.submit(() -> {
                historyTableManager.addHistoryEntry(new HistoryEntry(Type.COMIC, comicId, comicTitle, System.currentTimeMillis()));
            });
        }

        Callable<BaseResponse<SerieDto>> callable = () -> {
            MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).build();
            SeriesClient seriesClient = new SeriesClient(marvelApiConfig);
            return seriesClient.getSerie(comicId);
        };

        Future<BaseResponse<SerieDto>> future = executorService.submit(callable);

        try {
            BaseResponse<SerieDto> response = future.get();
            serie = response.getResponse();
            Boolean isFavorite = favoriteTableManager.isFavorite(new Favorite(Type.COMIC, serie.getId(), serie.getTitle()));

            TextView comicTitleTextView = findViewById(R.id.comic_title);
            comicTitleTextView.setText(serie.getTitle());

            ImageView comicFavoriteImageView = findViewById(R.id.comic_favorite);
            comicFavoriteImageView.setImageResource(isFavorite ? favoriteIcon : notFavoriteIcon);

            String comicDescription = serie.getDescription();
            TextView comicDescriptionTextView = findViewById(R.id.comic_description);
            comicDescriptionTextView.setText(comicDescription);
            if(comicDescription == null || comicDescription.isEmpty())
            {
                NestedScrollView serieDescriptionNestedScroll = findViewById(R.id.comic_description_scroll);
                serieDescriptionNestedScroll.getLayoutParams().height = 0;
            }

            ImageView imageView = findViewById(R.id.comic_image);
            Glide.with(this).load(serie.getThumbnail().getImageUrl(ImageDto.Size.FULLSIZE)).into(imageView);

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

            List<String> creatorsList = serie.getCreators().getItems().stream().map(ResourceDto::getName).collect(Collectors.toList());
            ListView creatorsListView = findViewById(R.id.comic_creators_list);
            ComicCreatorsAdapter creatorsAdapter = new ComicCreatorsAdapter(this, creatorsList);
            creatorsListView.setAdapter(creatorsAdapter);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ImageView comicFavorite = findViewById(R.id.comic_favorite);

        comicFavorite.setOnClickListener(v -> {
            Favorite favorite = new Favorite(Type.COMIC, serie.getId(), serie.getTitle());
            if (comicFavorite.getDrawable().getConstantState() == getDrawable(notFavoriteIcon).getConstantState()) {
                comicFavorite.setImageResource(favoriteIcon);
                favoriteTableManager.addFavorite(favorite);
                Toast.makeText(this, String.format("Added %s to favorites", serie.getTitle()), Toast.LENGTH_SHORT).show();
            } else {
                comicFavorite.setImageResource(notFavoriteIcon);
                favoriteTableManager.removeFavorite(favorite);
                Toast.makeText(this, String.format("Removed %s from favorites", serie.getTitle()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveImage(Bitmap bitmap) {
        fileManager.saveImageToGallery(bitmap);
        Toast.makeText(ComicCardActivity.this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
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
