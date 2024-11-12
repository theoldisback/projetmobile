package com.example.projetmobile.View.Userview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetmobile.Model.User;
import com.example.projetmobile.R;
import com.example.projetmobile.Service.UserService;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> userList;
    private Context context;


    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.oneuserdashboard_layout, parent, false);
        return new UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        UserService userService = new UserService(context);
        int userId = user.getId();
        holder.id=user.getId();

        holder.userName.setText(user.getUsername());
        holder.userEmail.setText(user.getEmail());
        holder.userfirstname.setText(user.getFirstname());
        holder.userlastname.setText(user.getLastname());
        holder.useradress.setText(user.getAdress());
        holder.userbirthday.setText(user.getBirthdate().toString());
        byte[] imageBytes = userService.getUserImage(holder.id);

        Bitmap userImage = byteArrayToBitmap(imageBytes);
        holder.userImage.setImageBitmap(userImage);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity
                UserService u = new UserService(context);
                u.deleteUser(holder.id);
                Intent intent = new Intent(context, DashboarduserFragment.class);
                context.startActivity(intent);

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a new activity
                Intent intent = new Intent(context, UpdateprofiledashboardActivity.class);
                intent.putExtra("USER_ID", holder.id); // 'USER_ID' is the key you can use to retrieve the id later

                context.startActivity(intent);

            }
        });
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userEmail,userfirstname,userlastname,useradress,userbirthday;
        ImageView userImage;
        int id;
        Button editButton, deleteButton ;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.oneuserusername);
            userEmail = itemView.findViewById(R.id.oneuseremail);
            userfirstname = itemView.findViewById(R.id.oneuserfirstname);
            userlastname = itemView.findViewById(R.id.oneuserlastname);
            useradress = itemView.findViewById(R.id.oneuseradress);
            userbirthday = itemView.findViewById(R.id.oneuserbirthday);
            editButton = itemView.findViewById(R.id.editimagebuttonforoneuser);
            deleteButton = itemView.findViewById(R.id.deleteimagebutton);
            userImage = itemView.findViewById(R.id.imageviewuser);


        }
    }
    public Bitmap byteArrayToBitmap(byte[] imageBytes) {
        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        return null;
    }


    public Bitmap resizeImage(Bitmap originalImage, int maxWidth, int maxHeight) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;

        if (ratioMax > ratioBitmap) {
            finalWidth = (int) (maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) (maxWidth / ratioBitmap);
        }

        return Bitmap.createScaledBitmap(originalImage, finalWidth, finalHeight, true);
    }
}
