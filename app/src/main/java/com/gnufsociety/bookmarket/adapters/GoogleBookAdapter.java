package com.gnufsociety.bookmarket.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gnufsociety.bookmarket.R;
import com.gnufsociety.bookmarket.models.GoogleBook;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoogleBookAdapter extends RecyclerView.Adapter<GoogleBookAdapter.GoogleBookHolder> {

    private List<GoogleBook> books;

    public GoogleBookAdapter(List<GoogleBook> books) {
        this.books = books;
    }



    @NonNull
    @Override
    public GoogleBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card,
                parent,
                false);

        return new GoogleBookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoogleBookHolder holder, int position) {
        GoogleBook book = this.books.get(position);

        holder.title.setText(book.title());
        holder.author.setText(book.author());
        holder.price.setText(book.price());
        holder.link = book.link();

        Glide.with(holder.itemView.getContext())
                .load(book.image())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.books.size();
    }

    public class GoogleBookHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_title)
        TextView title;
        @BindView(R.id.book_author)
        TextView author;
        @BindView(R.id.book_price)
        TextView price;
        @BindView(R.id.bookImage)
        ImageView image;
        private String link;

        public GoogleBookHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onClickedBook(){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(link));
            image.getContext().startActivity(browserIntent);
        }


    }

}
