package com.wj.android.room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wj.android.room.db.AppDatabase;
import com.wj.android.room.db.entiy.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private EditText mName;
    private Button mAddUser;
    private Button mQueryUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.name);
        mAddUser = findViewById(R.id.add_user);
        mQueryUser = findViewById(R.id.query_user);

        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mName.getText())) {
                    return;
                }
                Observable.create(new ObservableOnSubscribe<User>() {
                    @Override
                    public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                        try {
                            User user = new User();
                            user.setUserName(mName.getText().toString());
                            AppDatabase.getInstance(getApplicationContext()).userDao().insertUser(user);
                            emitter.onNext(user);
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Toast.makeText(MainActivity.this, user.getUserName(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        mQueryUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.getInstance(getApplicationContext()).userDao().getAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                StringBuffer sb = new StringBuffer();
                                for (User user : users) {
                                    sb.append(user.getUid()+"|"+user.getUserName()+",");
                                }
                                Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });
    }
}
