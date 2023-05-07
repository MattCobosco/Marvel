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
import core.api.clients.CharacterClient;
import core.api.models.CharacterRow;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CharacterDto;
import core.api.models.DTOs.CharactersDto;
import core.api.queries.CharactersQuery;
import pl.wsei.marvel.BuildConfig;
import pl.wsei.marvel.adapters.CharacterAdapter;
import pl.wsei.marvel.databinding.FragmentCharactersBinding;

public class CharactersFragment extends Fragment {

    private final List<CharacterRow> characters = new ArrayList<>();
    private CharacterAdapter adapter;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        pl.wsei.marvel.databinding.FragmentCharactersBinding binding = FragmentCharactersBinding.inflate(inflater, container, false);
        final View root = binding.getRoot();

        this.adapter = new CharacterAdapter(this.characters);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        binding.charactersRecyclerView.setLayoutManager(layoutManager);
        binding.charactersRecyclerView.setAdapter(this.adapter);

        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<BaseResponse<CharactersDto>> callable = () -> {
            final MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.PUBLIC_KEY, BuildConfig.PRIVATE_KEY).build();

            final CharacterClient characterClient = new CharacterClient(marvelApiConfig);

            final CharactersQuery query = CharactersQuery.Builder.create().withLimit(100).build();

            return characterClient.getAll(query);
        };

        final Future<BaseResponse<CharactersDto>> future = executorService.submit(callable);

        try {
            final BaseResponse<CharactersDto> all = future.get();

            this.getActivity().runOnUiThread(() -> {
                final List<CharacterDto> characterDtos = all.getResponse().getCharacters();
                for (final CharacterDto characterDto : characterDtos) {
                    final CharacterRow character = new CharacterRow(characterDto.getId(), characterDto.getName(), characterDto.getDescription(), characterDto.getThumbnail().getPath() + "." + characterDto.getThumbnail().getExtension());
                    this.characters.add(character);
                }
                this.adapter.notifyDataSetChanged();
            });
        } catch (final ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return root;
    }
}