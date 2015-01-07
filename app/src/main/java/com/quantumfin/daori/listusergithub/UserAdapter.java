package com.quantumfin.daori.listusergithub;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by daori on 12/15/14.
 */
public class UserAdapter extends ArrayAdapter<Users> {
    private final List<Users> items;
    private final int resource;
    private final Context context;

    public UserAdapter(Context context, int resource, List<Users> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
        this.resource = resource;
    }

    private int getItemSize(){
        return items.size();
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_user, null);

            ViewHolder holder = new ViewHolder();
            holder.loginName = (TextView) rowView.findViewById(R.id.login_name);
            holder.avatarImage = (ImageView) rowView.findViewById(R.id.avatar_url);
            holder.removeItem = (Button) rowView.findViewById(R.id.delete_button);
            rowView.setTag(holder);

        } else {
            rowView.getTag();
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        Users users = items.get(position);
        holder.loginName.setText(users.getLogin());
        holder.avatarImage.setTag(users.getAvatarUrl());
        holder.removeItem.setTag(position);

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
                showMessage(String.valueOf(getItemSize()));
            }
        });
        new LoadImage().execute(holder.avatarImage);

        return rowView;
    }

    private void showMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private class LoadImage extends AsyncTask<ImageView, Void, Bitmap>{

        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... params) {
            this.imageView = params[0];
            return getBitmapFromURL((String)imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    private class ViewHolder {
        TextView loginName;
        ImageView avatarImage;
        Button removeItem;
    }
}
