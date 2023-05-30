package pl.wsei.marvel.ui.comics;

import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import core.api.clients.SeriesClient;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.SerieDto;
import core.api.models.DTOs.SeriesDto;
import core.api.models.SerieRow;
import core.api.queries.SeriesQuery;
import core.api.utils.ApiKeysManager;
import pl.wsei.marvel.R;
import pl.wsei.marvel.adapters.ComicAdapter;
import pl.wsei.marvel.cache.SeriesCacheSingleton;
import pl.wsei.marvel.databinding.FragmentComicsBinding;
import retrofit2.HttpException;

public class ComicsFragment extends Fragment {
    private ApiKeysManager apiKeysManager;
    private List<SerieRow> series = new ArrayList<>();
    private ComicAdapter adapter;
    private LruCache<String, List<SerieRow>> cache;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentComicsBinding binding = FragmentComicsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cache = SeriesCacheSingleton.getInstance().getCache();

        apiKeysManager = new ApiKeysManager(this.getActivity());
        String publicKey = apiKeysManager.getPublicKey();
        String privateKey = apiKeysManager.getPrivateKey();

        adapter = new ComicAdapter(series);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        binding.comicsRecyclerView.setLayoutManager(layoutManager);
        binding.comicsRecyclerView.setAdapter(adapter);
        progressBar = binding.progressBar;
        showProgressBar();

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<BaseResponse<SeriesDto>> callable;
        Future<BaseResponse<SeriesDto>> future;

        List<SerieRow> cachedData = cache.get("series");
        if (cachedData != null) {
            series = cachedData;
            adapter.updateData(series);
        } else {
            callable = () -> {
                MarvelApiConfig marvelApiConfig = new MarvelApiConfig.Builder(publicKey, privateKey).build();

                SeriesClient seriesClient = new SeriesClient(marvelApiConfig);

                SeriesQuery seriesQuery = new SeriesQuery.Builder().withLimit(100).build();

                return seriesClient.getAll(seriesQuery);
            };

            future = executorService.submit(callable);

            executorService.execute(() -> {
                try {
                    BaseResponse<SeriesDto> all = future.get();

                    getActivity().runOnUiThread(() -> {
                        List<SerieDto> serieDtos = all.getResponse().getSeries();
                        for (SerieDto serieDto : serieDtos) {
                            SerieRow serieRow = new SerieRow(serieDto.getId(), serieDto.getTitle(), serieDto.getDescription(), serieDto.getThumbnail().getPath() + "." + serieDto.getThumbnail().getExtension());
                            series.add(serieRow);
                        }
                        cache.put("series", series);
                        adapter.updateData(series);
                        hideProgressBar();
                    });
                } catch (HttpException e) {
                    if (e.code() == 401) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getActivity(), "Unauthorized. Check if your API Key is correct", Toast.LENGTH_SHORT).show();
                            adapter.updateData(new ArrayList<>());
                            hideProgressBar();
                        });
                    } else {
                        throw new RuntimeException(e);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    getActivity().runOnUiThread(() -> {
                        Log.e("ComicsFragment", "Error while fetching data from API", e);
                        hideProgressBar();
                    });
                }
            });
        }

        return root;
    }

    private void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}