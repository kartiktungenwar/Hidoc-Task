package com.hidoc_task.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hidoc_task.R;
import com.hidoc_task.data.APIUrl;
import com.hidoc_task.model.ResponseModel;

import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentViewNews extends Fragment {

    private static final String TAG = "FragmentViewNews";
    private MainActivity activity;
    private View view;
    private RecyclerView viewListRecyclerView;
    private Button btnRetry;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private TextView txtError;
    private NewsAdapter adapter;

    public FragmentViewNews(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.main_progress);

        errorLayout = (LinearLayout) view.findViewById(R.id.error_layout);
        btnRetry = (Button) view.findViewById(R.id.error_btn_retry);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNewsList();
            }
        });
        txtError = (TextView) view.findViewById(R.id.error_txt_cause);

        viewListRecyclerView = (RecyclerView) view.findViewById(R.id.viewListRecyclerView);
        viewListRecyclerView.setLayoutManager(new LinearLayoutManager(activity,RecyclerView.VERTICAL,false));

        callNewsList();

        return view;
    }

    private void callNewsList() {
        hideErrorView();
        viewListRecyclerView.setVisibility(View.GONE);
        //calling the api
        activity.apiServiceNews.newsAPI(APIUrl.KEY_NEWS,"in").enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                hideErrorView();
                progressBar.setVisibility(View.GONE);
                try{
                    System.out.println(TAG+ " Response "+response.body().toString());
                    if(response.body().getStatus().equalsIgnoreCase("ok")) {
                        if(!response.body().getArticles().isEmpty()){
                            adapter = new NewsAdapter(activity);
                            viewListRecyclerView.setAdapter(adapter);
                            adapter.setListArray(response.body().getArticles());
                            viewListRecyclerView.setVisibility(View.VISIBLE);
                            errorLayout.setVisibility(View.GONE);

                        }else {
                            showErrorView("no data found");
                            viewListRecyclerView.setVisibility(View.GONE);
                        }

                    }else{
                        showErrorView("no data found");
                        viewListRecyclerView.setVisibility(View.GONE);
                    }
                }catch (NullPointerException | NumberFormatException e)
                {
                    showErrorView("no data found error "+e.getMessage());
                    viewListRecyclerView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                viewListRecyclerView.setVisibility(View.GONE);
                showErrorView(t);
                System.out.println(TAG+ " Response "+t.getMessage().toString());
            }
        });
    }

    private void showErrorView(Throwable throwable) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(fetchErrorMessage(throwable));
        }
    }

    private  void showErrorView(String error) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            txtError.setText(error);
        }
    }

    private void hideErrorView() {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!activity.isNetworkAvailable()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }
}
