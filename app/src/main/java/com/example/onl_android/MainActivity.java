package com.example.onl_android;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button add;
    Button remove;
    Button cancel;
    EditText name;
    List<User> users;
    ArrayList<User> arrayList;

    User u_Tong  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ds);
        lv = (ListView)findViewById(R.id.list_student);
        name = (EditText)findViewById(R.id.name);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Test2_onl").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();
//        User u2 = new User();
//        u2.firstName = "Đỗ Anh Bôn";
//        u2.lastName = u2.firstName;
//
//        User u3 = new User();
//        u3.firstName = "Hoàng Quốc Cường";
//        u3.lastName = u3.firstName;
//
//        userDao.insertUser(u2);
//        userDao.insertUser(u3);

        users = userDao.getAll();

        arrayList = (ArrayList<User>)users;
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(adapter);
//        Log.i("TAG", "onCreate: ");

        add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString() ;
                if(userName.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập tên", Toast.LENGTH_SHORT).show();
                }
                else{
                    User u = new User();
                    u.firstName = userName;
                    u.lastName = userName;
                    userDao.insertUser(u);
                    arrayList.clear();
                    users = userDao.getAll();

                    arrayList.addAll(users);
                    adapter.notifyDataSetChanged();
                    name.setText("");
                    name.setHint("Put name to add");
                }

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,arrayList.get(i) + "",Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this,arrayList.get(i).uid + "",Toast.LENGTH_SHORT).show();
                name.setText(arrayList.get(i).firstName);

//                u_Tong = userDao.findByName(arrayList.get(i).firstName,arrayList.get(i).lastName);
//                u_Tong = userDao.loadUserById(arrayList.get(i).uid);
                int a[] = {arrayList.get(i).uid};
                u_Tong  = userDao.loadAllByIds(a).get(0);
            }
        });

        remove = (Button)findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(u_Tong == null){
                    Toast.makeText(MainActivity.this, "Chọn người muốn xóa", Toast.LENGTH_SHORT).show();
                }else{
                    userDao.delete(u_Tong);
                    arrayList.clear();
                    users = userDao.getAll();

                    arrayList.addAll(users);
                    adapter.notifyDataSetChanged();
                    name.setText("");
                    name.setHint("Put name to add");
                }
            }
        });
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                name.setHint("Put name to add");
                u_Tong = null;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}