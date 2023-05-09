package pl.wsei.marvel.ui.characters;

import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import core.MarvelApiConfig;
import core.api.clients.CharacterClient;
import core.api.models.CharacterRow;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CharacterDto;
import core.api.models.DTOs.CharactersDto;
import core.api.queries.CharactersQuery;
import core.api.utils.ApiKeysManager;
import pl.wsei.marvel.BuildConfig;
import pl.wsei.marvel.adapters.CharacterAdapter;
import pl.wsei.marvel.cache.CharacterCacheSingleton;
import pl.wsei.marvel.databinding.FragmentCharactersBinding;
import retrofit2.HttpException;

public class CharactersFragment extends Fragment {
    private ApiKeysManager apiKeysManager;
    private List<CharacterRow> characters = new ArrayList<>();
    private CharacterAdapter adapter;
    private LruCache<String, List<CharacterRow>> cache;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCharactersBinding binding = FragmentCharactersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cache = CharacterCacheSingleton.getInstance().getCache();

        apiKeysManager = new ApiKeysManager(this.getActivity());
        String publicKey = apiKeysManager.getPublicKey();
        String privateKey = apiKeysManager.getPrivateKey();

        adapter = new CharacterAdapter(characters);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        binding.charactersRecyclerView.setLayoutManager(layoutManager);
        binding.charactersRecyclerView.setAdapter(adapter);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<BaseResponse<CharactersDto>> callable;
        Future<BaseResponse<CharactersDto>> future;

        List<CharacterRow> cachedData = cache.get("characters");
        if (cachedData != null) {
            characters = cachedData;
            adapter.updateData(characters);
        } else {
            callable = () -> {
                MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).build();

                CharacterClient characterClient = new CharacterClient(marvelApiConfig);

                CharactersQuery query = CharactersQuery.Builder.create().withLimit(100).build();

                return characterClient.getAll(query);
            };

            future = executorService.submit(callable);

            try {
                BaseResponse<CharactersDto> all = future.get();

                getActivity().runOnUiThread(() -> {
                    List<CharacterDto> characterDtos = all.getResponse().getCharacters();
                    for (CharacterDto characterDto : characterDtos) {
                        CharacterRow character = new CharacterRow(characterDto.getId(), characterDto.getName(), characterDto.getDescription(), characterDto.getThumbnail().getPath() + "." + characterDto.getThumbnail().getExtension());
                        characters.add(character);
                    }
                    cache.put("characters", characters);
                    adapter.updateData(characters);
                });
            } catch (HttpException e) {
                if (e.code() == 401) {
                    Toast.makeText(getActivity(), "Unauthorized. Check if your API Key is correct", Toast.LENGTH_SHORT).show();
                    adapter.updateData(new ArrayList<>());
                } else {
                    throw new RuntimeException(e);
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CharactersFragment", "Error while fetching data from API", e);
            }
        }

        return root;
    }
}