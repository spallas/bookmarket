package com.gnufsociety.bookmarket.adapters;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gnufsociety.bookmarket.R;
import com.gnufsociety.bookmarket.models.Ad;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by spallas on 04/02/2019.
 */
public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.CardViewHolder> {

    public List<Ad> list;
    public MyCardAdapter(List<Ad> list) {
        this.list = list;
//        fstorage = FirebaseStorage.getInstance();
//        storage = fstorage.getReferenceFromUrl("gs://openchallenge-81990.appspot.com");
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,
                parent,
                false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Ad ad = list.get(position);

//        StorageReference img = storage.child("challenges/"+ad.imageLocation);
//        StorageReference userImg = storage.child("users/"+ad.organizer.proPicLocation);
//

//        holder.desc.setText(ad.desc);
//        holder.title.setText(ad.name);
//        holder.org.setText(ad.organizer.name);
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//        holder.when.setText(format.format(ad.when));
//        holder.where.setText(ad.address.split(",")[0]);
//        holder.rate.setRating((float) ad.organizer.rating);

//        Glide.with(holder.desc.getContext())
//                .using(new FirebaseImageLoader())
//                .load(img)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(holder.img);
//
//        Glide.with(holder.desc.getContext())
//                .using(new FirebaseImageLoader())
//                .load(userImg)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(holder.user_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.card_title) TextView title;
//        @BindView(R.id.card_when)  TextView when;
//        @BindView(R.id.card_where) TextView where;
//        @BindView(R.id.card_descr) TextView desc;
//        @BindView(R.id.card_rate)  RatingBar rate;
//        @BindView(R.id.card_img)   ImageView img;
//        @BindView(R.id.card_organizer) TextView org;
//        @BindView(R.id.card_favorite) FavoriteButton button;
//        @BindView(R.id.card_user_img)  CircleImageView user_img;

//
//        @OnClick(R.id.card_user_img)
//        public void openUserActivity() {
//            Intent user = new Intent(user_img.getContext(), UserActivity.class);
//            Bundle extra = new Bundle();
//            extra.putSerializable("currentUser", list.get(getAdapterPosition()).organizer);
//            user.putExtras(extra);
//            user_img.getContext().startActivity(user);
//        }


        public CardViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
