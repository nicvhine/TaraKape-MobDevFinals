package com.example.wheretogo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.CafeViewHolder> {
    private List<Cafe> cafes;
    private Context context;
    private List<Cafe> favoriteCafes = new ArrayList<>();

    public CafeAdapter(Context context, List<Cafe> cafes) {
        this.context = context;
        this.cafes = cafes;
        loadFavorites();
    }

    @NonNull
    @Override
    public CafeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item_cafe, parent, false);
        return new CafeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CafeViewHolder holder, int position) {
        Cafe cafe = cafes.get(position);

        holder.cafeName.setText(cafe.getName());
        holder.cafeLocation.setText(cafe.getLocation());
        holder.cafeDescription.setText(cafe.getDescription());

        String base64Image = cafe.getImageBase64();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            holder.cafeImage.setImageBitmap(decodedImage);
        }

        if (favoriteCafes.contains(cafe)) {
            holder.favoriteIcon.setImageResource(R.drawable.heart_filled_red);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.outline_favorite_border_24);
        }

        holder.favoriteIcon.setOnClickListener(v -> {
            if (favoriteCafes.contains(cafe)) {
                favoriteCafes.remove(cafe);
                holder.favoriteIcon.setImageResource(R.drawable.outline_favorite_border_24);
                Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                favoriteCafes.add(cafe);
                holder.favoriteIcon.setImageResource(R.drawable.heart_filled_red);
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
            }

            updateFavorites();
        });
    }

    @Override
    public int getItemCount() {
        return cafes.size();
    }

    private void loadFavorites() {
        SharedPreferences prefs = context.getSharedPreferences("UserFavorites", Context.MODE_PRIVATE);
        String json = prefs.getString("favorites", null);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Cafe>>() {}.getType();
            favoriteCafes = gson.fromJson(json, type);
        }

        if (favoriteCafes == null) {
            favoriteCafes = new ArrayList<>();
        }
    }

    private void updateFavorites() {
        SharedPreferences prefs = context.getSharedPreferences("UserFavorites", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoriteCafes);
        editor.putString("favorites", json);
        editor.apply();
    }

    public static class CafeViewHolder extends RecyclerView.ViewHolder {
        TextView cafeName, cafeLocation, cafeDescription;
        ImageView cafeImage, favoriteIcon;

        public CafeViewHolder(@NonNull View itemView) {
            super(itemView);
            cafeName = itemView.findViewById(R.id.cafeName);
            cafeLocation = itemView.findViewById(R.id.cafeLocation);
            cafeDescription = itemView.findViewById(R.id.description);
            cafeImage = itemView.findViewById(R.id.cafeImage);
            favoriteIcon = itemView.findViewById(R.id.favoriteicon);
        }
    }
}
