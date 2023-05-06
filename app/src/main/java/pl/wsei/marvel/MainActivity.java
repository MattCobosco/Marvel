package pl.wsei.marvel;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import core.MarvelApiConfig;
import core.clients.CharacterClient;
import core.models.DTOs.BaseResponse;
import core.models.DTOs.CharactersDto;
import core.queries.CharactersQuery;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String publicKey = "xxx";
        String privateKey = "xxx";

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<BaseResponse<CharactersDto>> callable = new Callable<BaseResponse<CharactersDto>>() {
            @Override
            public BaseResponse<CharactersDto> call() throws Exception {
                MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).build();
                CharacterClient characterClient = new CharacterClient(marvelApiConfig);
                CharactersQuery query = CharactersQuery.Builder.create().build();
                return characterClient.getAll(query);
            }
        };
        Future<BaseResponse<CharactersDto>> future = executorService.submit(callable);
        try {
            BaseResponse<CharactersDto> all = future.get();
            // Update the UI on the main thread here
            runOnUiThread(() -> Toast.makeText(MainActivity.this, all.getResponse().getCharacters().get(0).getName(), Toast.LENGTH_LONG).show());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }
}