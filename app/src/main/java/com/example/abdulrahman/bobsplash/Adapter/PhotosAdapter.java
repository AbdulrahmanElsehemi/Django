package com.example.abdulrahman.bobsplash.Adapter;

import android.app.Activity;

import android.arch.paging.PagedListAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.abdulrahman.bobsplash.ImageDetail;
import com.example.abdulrahman.bobsplash.R;
import com.example.abdulrahman.bobsplash.Util.ListItemClickListener;

import com.example.abdulrahman.bobsplash.model.PhotosResponse;
import com.example.abdulrahman.bobsplash.model.Result;
import com.example.abdulrahman.bobsplash.model.Urls;
import com.example.abdulrahman.bobsplash.model.User;
import com.example.abdulrahman.bobsplash.repository.NetworkState;
import com.example.abdulrahman.bobsplash.repository.Status;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;




public class PhotosAdapter extends PagedListAdapter<PhotosResponse,RecyclerView.ViewHolder> {

    private static final String TAG = "PhotoAdapter";
    private NetworkState networkState;

    private ListItemClickListener itemClickListener;
    Activity mActivity;

    private ArrayList<Result> itemList;





    public PhotosAdapter(ListItemClickListener itemClickListener, Activity activity) {
        super(PhotosResponse.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
        mActivity = activity;
    }

    public PhotosAdapter(Activity activity,ArrayList<Result> itemList)
    {
        super(Result.DIFF_CALLBACK);

        this.mActivity=activity;
        this.itemList=itemList;

    }

    //append photos to list
    public void addItems(List<Result> itemList) {
        for (Result result:itemList) {
            this.itemList.add(result);
            notifyDataSetChanged();
        }
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == R.layout.photo_item) {
            view = layoutInflater.inflate(R.layout.photo_item, parent, false);
            return new PhotoItemViewHolder(view,mActivity);
        } else if (viewType == R.layout.network_state_item) {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view, itemClickListener);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.photo_item:
                ((PhotoItemViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.photo_item;
        }
    }
    public void setNetworkState(NetworkState newNetworkState) {

        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    static class PhotoItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CircleImageView photographer_img;

        Activity mActivity;

        TextView photographerName;
        TextView photographerLoaction;
        TextView photographerLikes;
        TextView imageDec;




        public PhotoItemViewHolder(View itemView, Activity activity) {
            super(itemView);
            mActivity = activity;


            imageView = itemView.findViewById(R.id.image);
            photographerName=itemView.findViewById(R.id.photographer_name);
            photographerLoaction=itemView.findViewById(R.id.photographer_location);
            photographer_img=itemView.findViewById(R.id.photographer_img);
            photographerLikes=itemView.findViewById(R.id.image_likes);
            imageDec=itemView.findViewById(R.id.image_dec);


        }


        public void bindTo(final PhotosResponse objects) {

            final Urls urls = objects.getPhotoUrls();
            final User user=objects.getPhotoUser();



            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(mActivity,"Photo  "+ objects.getPhotoID(), Toast.LENGTH_SHORT).show();

                    Intent intent =new Intent(mActivity, ImageDetail.class);

                    intent.putExtra(ImageDetail.KEY_PHOTO_URL , urls.getRegular());
                    intent.putExtra(ImageDetail.KEY_PHOTOGRAPHER_IMG , user.getProfileImage().getLarge());
                    intent.putExtra(ImageDetail.KEY_USER_LOCATION , user.getLocation());
                    intent.putExtra(ImageDetail.KEY_USER_NAME , user.getFirstName()+" "+user.getLastName());
                    intent.putExtra(ImageDetail.KEY_PHOTO_TIME,objects.getCreatedAt());

                    mActivity.startActivity(intent);


                }
            });

            Picasso.with(mActivity).load(urls.getRegular()) .into(imageView);
            Picasso.with(mActivity).load(user.getProfileImage().getLarge())
                    .placeholder(R.drawable.ic_action_person).into(photographer_img);

            photographerName.setText(user.getFirstName()+" "+user.getLastName());
            photographerLoaction.setText(user.getLocation());
            photographerLikes.setText(objects.getPhotoLikes().toString());
            imageDec.setText(user.getBio());






        }
    }

    static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;
        private final TextView errorMsg;
        private Button button;

        public NetworkStateItemViewHolder(View itemView, final ListItemClickListener listItemClickListener) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            errorMsg = itemView.findViewById(R.id.error_msg);
            button = itemView.findViewById(R.id.retry_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listItemClickListener.onItemClick(view, getAdapterPosition());

                }
            });
        }
        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == Status.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == Status.FAILED) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }



}

