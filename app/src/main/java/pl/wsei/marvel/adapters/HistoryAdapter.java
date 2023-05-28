package pl.wsei.marvel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import core.db.models.HistoryEntry;
import pl.wsei.marvel.R;
import pl.wsei.marvel.TypeToIconDictionary;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final List<HistoryEntry> historyList;

    public HistoryAdapter(List<HistoryEntry> historyList) {
        this.historyList = historyList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_popup_item, parent, false);

        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryEntry item = historyList.get(position);

        holder.typeImageView.setImageResource(TypeToIconDictionary.getIcon(item.getType()));
        holder.nameTextView.setText(item.getName());
        holder.timestampTextView.setText(android.text.format.DateFormat.format("dd-MM-yyyy HH:mm", item.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public ImageView typeImageView;
        public TextView nameTextView, timestampTextView;

        public HistoryViewHolder(View view) {
            super(view);
            typeImageView = view.findViewById(R.id.history_item_type_image_view);
            nameTextView = view.findViewById(R.id.history_item_name_text_view);
            timestampTextView = view.findViewById(R.id.history_item_timestamp_text_view);
        }
    }
}
