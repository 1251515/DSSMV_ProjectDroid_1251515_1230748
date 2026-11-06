package com.example.soundcheck.UI.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.soundcheck.R;
import com.example.soundcheck.model.Artist;

import java.util.List;

public class artistAdapter extends RecyclerView.Adapter<artistAdapter.ArtistViewHolder> {

    public interface OnArtistClickListener {
        void onArtistClick (Artist artist);
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView txtArtistName;
        TextView txtArtistType;
        TextView txtArtistSeparator;
        TextView txtArtistGenres;
        TextView txtArrow;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            txtArtistName = itemView.findViewById(R.id.txtArtistName);
            txtArtistType = itemView.findViewById(R.id.txtArtistType);
            txtArtistSeparator = itemView.findViewById(R.id.txtArtistSeparator);
            txtArtistGenres = itemView.findViewById(R.id.txtArtistGenres);
            txtArrow = itemView.findViewById(R.id.txtArrow);
        }
    }

    private List<Artist> artists;
    private OnArtistClickListener listener;

    public artistAdapter(List<Artist> artists, OnArtistClickListener listener) {
        this.artists = artists;
        this.listener = listener;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist, parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {

        Artist artist = artists.get(position);

        holder.txtArtistName.setText(artist.getName());
        holder.txtArtistType.setText(artist.getArtistType());

        String genre = "";

        if (artist.getArtistGenres() != null && !artist.getArtistGenres().isEmpty()) {
            genre = artist.getArtistGenres().get(0);
        }

        String type = artist.getArtistType();

        holder.txtArtistGenres.setText(genre);
        holder.txtArtistType.setText(type);

        holder.txtArtistGenres.setVisibility(
                genre.isEmpty() ? View.GONE : View.VISIBLE
        );

        holder.txtArtistType.setVisibility(
                type == null || type.isEmpty() ? View.GONE : View.VISIBLE
        );

        holder.txtArtistSeparator.setVisibility(
                !genre.isEmpty() && type != null && !type.isEmpty()
                        ? View.VISIBLE
                        : View.GONE
        );

        if (artist.getArtistGenres() != null &&
                !artist.getArtistGenres().isEmpty()) {
            holder.txtArtistGenres.setText(artist.getArtistGenres().get(0));
        } else {
            holder.txtArtistGenres.setText("");
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onArtistClick(artist);
            }
        });

    }

    public void updateArtists(List<Artist> newArtists) {

        this.artists = newArtists;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

}