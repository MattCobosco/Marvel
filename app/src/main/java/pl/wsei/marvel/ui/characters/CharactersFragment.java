package pl.wsei.marvel.ui.characters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import core.clients.CharacterClient;
import core.models.CharacterRow;
import core.models.DTOs.BaseResponse;
import core.models.DTOs.CharacterDto;
import core.models.DTOs.CharactersDto;
import core.queries.CharactersQuery;
import pl.wsei.marvel.BuildConfig;
import pl.wsei.marvel.adapters.CharacterAdapter;
import pl.wsei.marvel.databinding.FragmentCharactersBinding;

public class CharactersFragment extends Fragment {

    private FragmentCharactersBinding binding;
    private List<CharacterRow> characters = new ArrayList<>();
    private CharacterAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCharactersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new CharacterAdapter(characters);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.charactersRecyclerView.setLayoutManager(layoutManager);
        binding.charactersRecyclerView.setAdapter(adapter);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<BaseResponse<CharactersDto>> callable = () -> {
            MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.PUBLIC_KEY, BuildConfig.PRIVATE_KEY).build();

            CharacterClient characterClient = new CharacterClient(marvelApiConfig);

            CharactersQuery query = CharactersQuery.Builder.create().withLimit(100).build();

            return characterClient.getAll(query);
        };

        Future<BaseResponse<CharactersDto>> future = executorService.submit(callable);

        try {
            BaseResponse<CharactersDto> all = future.get();

            getActivity().runOnUiThread(() -> {
                List<CharacterDto> characterDtos = all.getResponse().getCharacters();
                for (CharacterDto characterDto : characterDtos) {
                    CharacterRow character = new CharacterRow(characterDto.getId(), characterDto.getName(), characterDto.getDescription(), characterDto.getThumbnail().getPath() + "." + characterDto.getThumbnail().getExtension());
                    characters.add(character);
                }
                adapter.notifyDataSetChanged();
            });
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return root;
    }
}