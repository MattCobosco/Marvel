package pl.wsei.marvel;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import core.api.utils.ApiKeysManager;
import core.db.FavoriteTableManager;
import core.db.HistoryTableManager;
import core.db.models.HistoryEntry;
import pl.wsei.marvel.adapters.HistoryAdapter;
import pl.wsei.marvel.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {
    private ApiKeysManager apiKeysManager;
    private FavoriteTableManager favoriteTableManager;
    private HistoryTableManager historyTableManager;
    private ActivityNavigationBinding binding;
    private TypeToIconDictionary typeToIconDictionary;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        AppCompatActivity context = this;

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(ContextCompat.getColor(context, R.color.red), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            PopupWindow popupWindow = new PopupWindow(this);

            popupWindow.setWidth(700);
            popupWindow.setHeight(1150);
            popupWindow.setBackgroundDrawable(getDrawable(R.drawable.popup_background));
            View popupView = LayoutInflater.from(this).inflate(R.layout.info_popup, null);

            popupWindow.setContentView(popupView);

            popupWindow.setAnimationStyle(R.style.PopupAnimation);

            popupWindow.setFocusable(true);

            popupWindow.showAtLocation(findViewById(R.id.container), Gravity.CENTER, 0, 0);

            return true;
        } else if (id == R.id.action_history) {
            PopupWindow popupWindow = new PopupWindow(this);
            List<HistoryEntry> history = historyTableManager.getAllHistoryEntries();
            typeToIconDictionary = new TypeToIconDictionary();

            popupWindow.setWidth(800);
            popupWindow.setHeight(1150);
            popupWindow.setBackgroundDrawable(getDrawable(R.drawable.popup_background));
            View popupView = LayoutInflater.from(this).inflate(R.layout.history_popup, null);

            RecyclerView recyclerView = popupView.findViewById(R.id.history_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            HistoryAdapter historyAdapter = new HistoryAdapter(history);
            recyclerView.setAdapter(historyAdapter);

            popupWindow.setContentView(popupView);
            popupWindow.setAnimationStyle(R.style.PopupAnimation);
            popupWindow.setFocusable(true);
            popupWindow.showAtLocation(findViewById(R.id.container), Gravity.CENTER, 0, 0);

            return true;
        } else if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Settings");

            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.settings_dialog, null);
            builder.setView(dialogLayout);

            String publicKey = apiKeysManager.getPublicKey();
            String privateKey = apiKeysManager.getPrivateKey();
            EditText publicKeyEditText = dialogLayout.findViewById(R.id.public_key_edit_text);
            EditText privateKeyEditText = dialogLayout.findViewById(R.id.private_key_edit_text);

            Button clearFavoritesButton = dialogLayout.findViewById(R.id.clear_favorites_button);
            clearFavoritesButton.setOnClickListener(v -> {
                favoriteTableManager.removeAllFavorites();
                Toast.makeText(this, "Favorites cleared", Toast.LENGTH_SHORT).show();
            });

            Button clearHistoryButton = dialogLayout.findViewById(R.id.clear_browsing_history_button);
            clearHistoryButton.setOnClickListener(v -> {
                historyTableManager.removeAllHistoryEntries();
                Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
            });

            if (publicKey != null && !publicKey.isEmpty()) {
                publicKeyEditText.setText(publicKey);
            }

            if (privateKey != null && !privateKey.isEmpty()) {
                privateKeyEditText.setText(privateKey);
            }

            builder.setPositiveButton("Save", (dialog, which) -> {
                EditText publicKeyEditText1 = dialogLayout.findViewById(R.id.public_key_edit_text);
                EditText privateKeyEditText1 = dialogLayout.findViewById(R.id.private_key_edit_text);

                String publicKeyEdited = publicKeyEditText1.getText().toString().trim();
                String privateKeyEdited = privateKeyEditText1.getText().toString().trim();

                apiKeysManager.setPublicKey(publicKeyEdited);
                apiKeysManager.setPrivateKey(privateKeyEdited);
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog dialog = builder.create();

            dialog.setOnShowListener(dialogInterface -> {
                Button positiveButton = (dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = (dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                positiveButton.setBackgroundColor(getColor(R.color.red));
                positiveButton.setTextColor(Color.WHITE);

                negativeButton.setBackgroundColor(Color.WHITE);
                negativeButton.setTextColor(getColor(R.color.red));
            });

            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        apiKeysManager = new ApiKeysManager(getApplicationContext());
        favoriteTableManager = new FavoriteTableManager(getApplicationContext());
        historyTableManager = new HistoryTableManager(getApplicationContext());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_characters, R.id.navigation_comics, R.id.navigation_creators)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}