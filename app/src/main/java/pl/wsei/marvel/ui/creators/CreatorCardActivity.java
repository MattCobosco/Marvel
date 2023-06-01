package pl.wsei.marvel.ui.creators;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import core.api.MarvelApiConfig;
import core.api.clients.CreatorClient;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CreatorDto;
import core.api.models.DTOs.ImageDto;
import core.api.models.DTOs.SeriesResourceDto;
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
import pl.wsei.marvel.adapters.CreatorSeriesAdapter;

public class CreatorCardActivity extends AppCompatActivity {
    private ApiKeysManager apiKeysManager;
    private ConfigManager configManager;
    private FileManager fileManager;
    private PermissionManager permissionManager;
    private CreatorDto creator;

    private final FavoriteTableManager favoriteTableManager = new FavoriteTableManager(this);
    private final HistoryTableManager historyTableManager = new HistoryTableManager(this);
    private final int favoriteIcon = R.drawable.star_24;
    private final int notFavoriteIcon = R.drawable.star_outline_24;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_card);
        apiKeysManager = new ApiKeysManager(this);
        configManager = new ConfigManager(this);
        fileManager = new FileManager(this);
        permissionManager = new PermissionManager(this);
        boolean isHistoryEnabled = configManager.isHistoryEnabled();

        String creatorId = getIntent().getStringExtra("id");
        String creatorName = getIntent().getStringExtra("name");
        String publicKey = apiKeysManager.getPublicKey();
        String privateKey = apiKeysManager.getPrivateKey();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        if(isHistoryEnabled) {
            executorService.submit(() -> {
                HistoryEntry historyEntry = new HistoryEntry(Type.CREATOR, creatorId, creatorName,System.currentTimeMillis());
                historyTableManager.addHistoryEntry(historyEntry);
            });
        }

        Callable<BaseResponse<CreatorDto>> callable = () -> {
            MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).build();
            CreatorClient creatorClient = new CreatorClient(marvelApiConfig);
            return creatorClient.getCreator(creatorId);
        };

        Future<BaseResponse<CreatorDto>> future = executorService.submit(callable);

        try {
            BaseResponse<CreatorDto> response = future.get();
            creator = response.getResponse();
            Boolean isFavorite = favoriteTableManager.isFavorite(new Favorite(Type.CREATOR, creator.getId(), creator.getFullName()));

            TextView creatorNameTextView = findViewById(R.id.creator_full_name);
            creatorNameTextView.setText(creator.getFullName());

            ImageView creatorFavoriteImageView = findViewById(R.id.creator_favorite);
            creatorFavoriteImageView.setImageResource(isFavorite ? favoriteIcon : notFavoriteIcon);

            ImageView imageView = findViewById(R.id.creator_image);
            Glide.with(this).load(creator.getThumbnail().getImageUrl(ImageDto.Size.FULLSIZE)).into(imageView);

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

            List<SeriesResourceDto> seriesList = new ArrayList<>(creator.getSeries().getItems());
            ListView seriesListView = findViewById(R.id.creator_comics_list);
            CreatorSeriesAdapter seriesListAdapter = new CreatorSeriesAdapter(this, seriesList);
            seriesListView.setAdapter(seriesListAdapter);
            if(seriesList.isEmpty()) {
                LinearLayout creatorSeriesLayout = findViewById(R.id.creator_comics);
                creatorSeriesLayout.setVisibility(View.GONE);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        ImageView creatorFavorite = findViewById(R.id.creator_favorite);

        creatorFavorite.setOnClickListener(v -> {
            Favorite favorite = new Favorite(Type.CREATOR, creator.getId(), creator.getFullName());
            if (creatorFavorite.getDrawable().getConstantState() == getDrawable(notFavoriteIcon).getConstantState()) {
                creatorFavorite.setImageResource(favoriteIcon);
                favoriteTableManager.addFavorite(favorite);
                Toast.makeText(this, String.format("Added %s to favorites", creator.getFullName()), Toast.LENGTH_SHORT).show();
            } else {
                creatorFavorite.setImageResource(notFavoriteIcon);
                favoriteTableManager.removeFavorite(favorite);
                Toast.makeText(this, String.format("Removed %s from favorites", creator.getFullName()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveImage(Bitmap bitmap) {
        fileManager.saveImageToGallery(bitmap);
        Toast.makeText(CreatorCardActivity.this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
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
