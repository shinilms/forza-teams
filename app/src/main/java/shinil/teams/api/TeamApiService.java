package shinil.teams.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import shinil.teams.model.Team;

/**
 * @author shinilms
 */

public interface TeamApiService {
    @GET("teams.json")
    Observable<List<Team>> getTeams();
}
