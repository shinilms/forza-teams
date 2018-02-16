package shinil.teams.api;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Retrofit;
import shinil.teams.model.Team;

/**
 * @author shinilms
 */

public class TeamApiServiceImp implements TeamApiService {
    private TeamApiService apiService;

    public TeamApiServiceImp(Retrofit retrofit) {
        apiService = retrofit.create(TeamApiService.class);
    }

    @Override
    public Observable<List<Team>> getTeams() {
        return apiService.getTeams();
    }
}
