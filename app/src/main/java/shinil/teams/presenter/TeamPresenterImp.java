package shinil.teams.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import shinil.teams.api.TeamApiService;
import shinil.teams.util.DisposableManager;
import shinil.teams.view.TeamView;

/**
 * @author shinilms
 */

public class TeamPresenterImp implements TeamPresenter {
    private TeamApiService apiServiceImp;
    private TeamView teamView;

    public TeamPresenterImp(TeamApiService apiServiceImp, TeamView teamView) {
        this.apiServiceImp = apiServiceImp;
        this.teamView = teamView;
    }

    @Override
    public void getTeams() {
        teamView.onShowProgress(true);

        DisposableManager.add(apiServiceImp.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(teams -> {
                    teamView.onTeamLoaded(teams);
                    teamView.onShowProgress(false);
                }, throwable -> {
                    teamView.onLoadFailed(throwable.getMessage());
                    teamView.onShowProgress(false);
                }));
    }
}
