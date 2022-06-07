package com.example.recyclerviewtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutmanager;
    private LinkedList<HashMap<String,String>> data;
    private myAdapter MyAdapter;
    // 資料量大，資料亦改變使用RecylerView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true); //針對大小進行設定

        //RecyclerView透過LayoutManager進行內部配置
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutmanager);

        doData();

        MyAdapter = new myAdapter();
        recyclerView.setAdapter(MyAdapter);
    }

    public void doData(){
        data = new LinkedList<>();
        for (int i=0; i<100;i++){
            HashMap<String, String> row = new HashMap<>();
            int number= (int)(Math.random()*100);
            int number2= (int)(Math.random()*200);
            row.put("title", "title" + number);
            row.put("date", "date" + number2);
            data.add(row);
        }
    }
    //泛型是指在定義方法、介面或類的時候，不預先指定具體的型別，而使用的時候再指定一個型別的一個特性。 : <myAdapter.ViewHolder>
    //官方網站 :https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=zh-tw
    private class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder>{

        class ViewHolder extends RecyclerView.ViewHolder {
            public View itemView;
            public TextView title, date;

            public ViewHolder(View view) {
                super(view);
                itemView = view;

                title = itemView.findViewById(R.id.item_title);
                date = itemView.findViewById(R.id.item_date);
            }
        }

        @NonNull
        @Override
        public myAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //介紹要做的item給viewHolder認識
            View itemview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            ViewHolder vh= new ViewHolder(itemview);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull myAdapter.ViewHolder holder, final int position) {
            //變更裡面內容
            holder.title.setText(data.get(position).get("title"));
            holder.date.setText(data.get(position).get("date"));

            //可以針對每個元素去做反應
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("brad", "Click : " + position);
                }
            });
        }

        @Override
        public int getItemCount() {
            //總共有幾筆資料
            return data.size();
        }
    }
    // 修改
    public void test1(View view) {
        data.get(3).put("title", "bard");
        data.get(3).put("date", "2022-06-07");
        MyAdapter.notifyDataSetChanged();
    }
    //刪除
    public void test2(View view) {
        data.removeFirst();
        MyAdapter.notifyDataSetChanged();
    }
}