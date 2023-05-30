package pl.wsei.marvel.ui.creators;

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
import core.api.clients.CreatorClient;
import core.api.clients.SeriesClient;
import core.api.models.CreatorRow;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CreatorDto;
import core.api.models.DTOs.CreatorsDto;
import core.api.models.DTOs.SerieDto;
import core.api.models.DTOs.SeriesDto;
import core.api.models.SerieRow;
import core.api.queries.CreatorsQuery;
import core.api.queries.SeriesQuery;
import core.api.utils.ApiKeysManager;
import pl.wsei.marvel.adapters.CreatorAdapter;
import pl.wsei.marvel.cache.CreatorsCacheSingleton;
import pl.wsei.marvel.databinding.FragmentCreatorsBinding;
import retrofit2.HttpException;

public class CreatorsFragment extends Fragment {
    private ApiKeysManager apiKeysManager;
    private List<CreatorRow> creators = new ArrayList<>();
    private CreatorAdapter adapter;
    private LruCache<String, List<CreatorRow>> cache;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCreatorsBinding binding = FragmentCreatorsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cache = CreatorsCacheSingleton.getInstance().getCache();

        apiKeysManager = new ApiKeysManager(this.getActivity());
        String publicKey = apiKeysManager.getPublicKey();
        String privateKey = apiKeysManager.getPrivateKey();

        adapter = new CreatorAdapter(creators);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        binding.creatorsRecyclerView.setLayoutManager(layoutManager);
        binding.creatorsRecyclerView.setAdapter(adapter);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<BaseResponse<CreatorsDto>> callable;
        Future<BaseResponse<CreatorsDto>> future;

        List<CreatorRow> cachedData = cache.get("creators");

        if (cachedData != null) {
            creators = cachedData;
            adapter.updateData(creators);
        } else {
            callable = () -> {
                MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).build();

                CreatorClient creatorClient = new CreatorClient(marvelApiConfig);

                CreatorsQuery creatorsQuery = new CreatorsQuery.Builder().withLimit(100).build();

                return creatorClient.getAll(creatorsQuery);
            };

            future = executorService.submit(callable);

            try {
                BaseResponse<CreatorsDto> all = future.get();

                getActivity().runOnUiThread(() -> {
                    List<CreatorDto> creatorDtos = all.getResponse().getCreators();
                    for (CreatorDto creatorDto : creatorDtos) {
                        CreatorRow creatorRow = new CreatorRow(creatorDto.getId(), creatorDto.getFullName(), creatorDto.getThumbnail().getPath() + "." + creatorDto.getThumbnail().getExtension());
                        creators.add(creatorRow);
                    }
                    cache.put("creators", creators);
                    adapter.updateData(creators);
                });
            } catch (HttpException e) {
                if (e.code() == 401) {
                    Toast.makeText(getActivity(), "Unauthorized. Check if your API Key is correct", Toast.LENGTH_SHORT).show();
                    adapter.updateData(new ArrayList<>());
                } else {
                    throw new RuntimeException(e);
                }
            } catch (ExecutionException | InterruptedException e) {
                Log.e("CreatorsFragment", "Error while fetching data from API", e);
            }
        }

        return root;
    }

}
