package com.quantumfin.daori.listusergithub;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;


public class MainActivity extends ActionBarActivity {

    private GithubAPI githubAPI;
    private ListView listUserView;
    private UserAdapter adapter;
    private TextView helloLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listUserView = (ListView) findViewById(R.id.list_item);
        helloLabel = (TextView) findViewById(R.id.hello_world);

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>(){

                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                    subscriber.onNext("Hello World");
                    subscriber.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubcriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                helloLabel.setText(s);
            }
        };

        myObservable.subscribe(mySubcriber);



        getGitHubService().userList(new Callback<List<Users>>() {

            @Override
            public void success(List<Users> userList, Response response) {
                adapter = new UserAdapter(MainActivity.this, R.id.list_item, userList);
                listUserView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void removeItem(View v){
        listUserView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showMessage(String message){
        Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();
    }

    private GithubAPI getGitHubService(){
        if(githubAPI == null){
            githubAPI = RestClientService.getService();
        }
        return githubAPI;
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
