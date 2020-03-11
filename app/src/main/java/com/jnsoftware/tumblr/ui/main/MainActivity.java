package com.jnsoftware.tumblr.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.jnsoftware.tumblr.R;
import com.jnsoftware.tumblr.data.network.pojo.TumblrPost;
import com.jnsoftware.tumblr.ui.base.BaseActivity;
import com.jnsoftware.tumblr.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends BaseActivity implements MainMvpView, TumblrFeedAdapter.Callback {

    RecyclerView mRecyclerView;

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    TumblrFeedAdapter mTumblrFeedAdapter;

    LinearLayoutManager mLayoutManager;

    SearchView searchView;

    ImageButton searchButton;

    SwipeRefreshLayout swipeRefreshLayout;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        mPresenter.onAttach(MainActivity.this);

        setUp();
    }

    @Override
    protected void setUp() {
        mRecyclerView = findViewById(R.id.tumblrFeed);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshContainer);
        searchView = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchButton);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.fetchNewTumblrFeedBatch(query);
                CommonUtils.hideKeyboard(MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                swipeRefreshLayout.setEnabled
                        (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshTumblrFeed();
            }
        });

        mPresenter.onViewPrepared();

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

    @Override
    public void updateFeed(List<TumblrPost> feedItemList) {
        mTumblrFeedAdapter.updateItems(feedItemList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initializeFeed(List<TumblrPost> feedItemList) {
        mTumblrFeedAdapter = new TumblrFeedAdapter(feedItemList, MainActivity.this);
        mRecyclerView.setAdapter(mTumblrFeedAdapter);
        //mTumblrFeedAdapter.initializeItems(feedItemList);
        swipeRefreshLayout.setRefreshing(false);

        mTumblrFeedAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                mPresenter.fetchNextTumblrFeedBatch(position);
            }
        });
    }

    @Override
    public void showDuplicateSearchToast(){
        showMessage(getResources().getString(R.string.duplicate_search_error));
    }

    @Override
    public void onEmptyViewRetryClick() {
        mPresenter.onViewPrepared();
    }
}
