package com.example.birdsystem.presenter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsystem.R;
import com.example.birdsystem.View.Activity.BirdActivity;
import com.example.birdsystem.View.Flagment.BirdFragment;
import com.example.birdsystem.model.Bird;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import kotlin.UByteArray;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerHolder>{
    private List<String> datas=new ArrayList<>();
    private Vector<Bitmap> bitmaps=new Vector<>();
    FragmentTransaction transaction;
    //private LayoutInflater inflater;
    private Context mContext;
    private Activity activity;
    Vector<Bird> birdVector;
    //Vector<Bitmap> imgVector=new ;
    Bitmap bitmap;
    public RecyclerviewAdapter(RecyclerView recyclerView, Activity activity, Vector<Bird> birdVector){
        this.mContext = recyclerView.getContext();
        this.activity=activity;
        //inflater=LayoutInflater.from(context);
        //this.birdVector=birdVector;
        //System.out.println(birdVector.size());
        for(int i=0;i<birdVector.size();i++){
            //System.out.println("aaaa"+i);
            datas.add(birdVector.get(i).getBirdName());
        }
        this.birdVector=birdVector;
    }
    //创建每一行的View 用RecyclerView.ViewHolder包装
    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bird, parent, false);
        return new RecyclerHolder(view);
    }
    //给每一行View填充数据
    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        holder.textView.setText(datas.get(position));
        getImgFrom(birdVector.get(position).getBirdImg(),holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // transaction.replace(R.id.addfragment,new BirdFragment()).commit();
                Intent intent=new Intent(activity, BirdActivity.class);
                //System.out.println(position);
                //System.out.println(birdVector.get(position).getBirdName());
                intent.putExtra("birdName",birdVector.get(position).getBirdName());
                intent.putExtra("habitat",birdVector.get(position).getBirdHabitat()+"");
                intent.putExtra("lifestyle",birdVector.get(position).getBirdLifestyle()+"");
                intent.putExtra("residence",birdVector.get(position).getBirdResidence()+"");
                intent.putExtra("detail",birdVector.get(position).getBirdDetails());
                //intent.putExtra("img",birdVector.get(position).getBirdImg());
                intent.putExtra("url",birdVector.get(position).getBirdUrl());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmaps.get(position).compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                //Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //System.out.println(bitmap);
               // System.out.println(bitmaps.size());
                intent.putExtra("img",data);
                activity.startActivity(intent);

                //Toast.makeText(mContext, position+"我被点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //数据源的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.recommend_textview);
            imageView=(ImageView)itemView.findViewById(R.id.recommend_image);
        }
    }
    private static byte[] transcodeProcess(byte[] data, int width, int height){

        YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 80, baos);

        byte[] jdata = baos.toByteArray();

        return jdata;

    }

    public void getImgFrom(final byte[] myimg, final ImageView imageView) {
        System.out.println(myimg);
        //System.out.println(myimg.length);
        //byte[] jdata = transcodeProcess(myimg, 10, 10);
        bitmap=BitmapFactory.decodeByteArray(myimg, 0, myimg.length);
        //System.out.println(bitmap);
        if(bitmap==null)
        {

            Resources r = activity.getResources();
            Bitmap bmp=BitmapFactory.decodeResource(r, R.drawable.no_img);
            //Bitmap newb = Bitmap.createBitmap( 300, 300, Config.ARGB_8888 );
            //bitmap=bmp;
            bitmaps.add(bmp);
            //imageView.setImageBitmap((bmp));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] data = baos.toByteArray();
           // System.out.println(data);
            //jdata1.
            bmp=BitmapFactory.decodeByteArray(data, 0, data.length);
            imageView.setImageBitmap((bmp));
            //System.out.println(BitmapFactory.decodeByteArray(data, 0, data.length));

        }else{
            bitmaps.add(bitmap);
            imageView.setImageBitmap((bitmap));
        }

        /*AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://192.168.0.100:8080/my/" + myimg)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {

                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        InputStream inputStream = response.body().byteStream();//得到图片的流
                        //Bitmap oldmap;
                       // oldmap=bitmap;
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        //imageView.setImageBitmap(bitmap);
                        // System.out.println(bitmap.toString());

                        if(bitmap==null)
                        {
                            Resources r = activity.getResources();
                            Bitmap bmp=BitmapFactory.decodeResource(r, R.drawable.no_img);
                            //Bitmap newb = Bitmap.createBitmap( 300, 300, Config.ARGB_8888 );
                            bitmap=bmp;
                            bitmaps.add(bmp);
                        }else{
                            bitmaps.add(bitmap);
                        }
                        //System.out.println(bitmap);
                        publishProgress(bitmap);

                    }
                });
                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);

                //System.out.println((Bitmap)values[0]);
                imageView.setImageBitmap((bitmap));
            }
        }.execute();*/
    }


}
