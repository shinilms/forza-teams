package shinil.teams.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import shinil.teams.R;
import shinil.teams.util.RetrofitHelper;
import shinil.teams.model.Team;
import shinil.teams.adapter.TeamListAdapter;
import shinil.teams.api.TeamApiServiceImp;
import shinil.teams.presenter.TeamPresenterImp;
import shinil.teams.util.DisposableManager;

public class MainActivity extends AppCompatActivity
        implements TeamView {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        new TeamPresenterImp(new TeamApiServiceImp(RetrofitHelper.getRetrofit()), this).getTeams();
    }

    @Override
    public void onShowProgress(boolean isShow) {
        if(isShow) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTeamLoaded(List<Team> teams) {
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new TeamListAdapter(teams));
    }

    @Override
    public void onLoadFailed(String error) {
        Toast.makeText(this, "Failed to load Teams. Reason: "+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        DisposableManager.dispose();
        super.onDestroy();
    }
}
