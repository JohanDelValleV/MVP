package com.example.mvplogin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvplogin.fragments.DialogFragment;
import com.example.mvplogin.fragments.ShowDataFragment;
import com.example.mvplogin.home.Career;
import com.example.mvplogin.home.MyAdapter;
import com.example.mvplogin.login.presenter.LoginPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class HomeActivity extends AppCompatActivity implements DialogFragment.DialogListener, ShowDataFragment.DialogListener, ShowDataFragment.DialogListenerDelete {
    private ArrayList<Career> careers = new ArrayList<>();
    private MyAdapter adapter;
    protected String token;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private ProgressBar mProgressBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getData();

        FloatingActionButton create = findViewById(R.id.floatingActionButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        mProgressBar = findViewById(R.id.loading);
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                openEditAndDeleteDialog(careers.get(position).getId(), careers.get(position).getName(), careers.get(position).getSlug(), position);
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            token = bundle.getString("Token", null);
            client.addHeader("Authorization", "Token " + token);
        }
    }

    public void openDialog() {
        DialogFragment dialog = new DialogFragment();
        dialog.show(getSupportFragmentManager(), "Create new career");
    }

    public void openEditAndDeleteDialog(int id, String name, String slug, int position) {
        ShowDataFragment dialog = new ShowDataFragment(id, name, slug, position);
        dialog.show(getSupportFragmentManager(), "Edit o delete career");
    }

    public void getData() {
        client.get(LoginPresenter.BASE_URL + "careers/", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                mProgressBar.setVisibility(View.GONE);
                dynamic(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });
    }

    public void dynamic(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject data = response.getJSONObject(i);
                Career career = new Career(data.getInt("id"), data.getString("name"), data.getString("slug"));
                careers.add(career);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new MyAdapter(this, R.layout.list_item, careers);
        listView.setAdapter(adapter);
    }

    private void delete(int id, final int position) {
        client.delete(LoginPresenter.BASE_URL + "/careers/" + id, new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "Deleted item", Toast.LENGTH_SHORT).show();
                careers.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Failed put method!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void put(int id, String name, String slug) {
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("slug", slug);
        client.put(LoginPresenter.BASE_URL + "careers/" + id, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "Updated item", Toast.LENGTH_SHORT).show();
                careers.clear();
                getData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Failed put method!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    private void post(String name, String slug) {
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("slug", slug);
        client.post(LoginPresenter.BASE_URL + "careers/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Career career = new Career(json.getInt("id"), json.getString("name"), json.getString("slug"));
                    careers.add(career);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Created item", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Failed post method!" + responseBody.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @Override
    public void applyTextDelete(int id, int position) {
        delete(id, position);
    }

    @Override
    public void applyText(String name, String slug) {
        post(name, slug);
    }

    @Override
    public void applyTextEdit(int id, String name, String slug) {
        put(id, name, slug);
    }
}
