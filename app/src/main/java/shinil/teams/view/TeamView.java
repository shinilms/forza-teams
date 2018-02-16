package shinil.teams.view;

import java.util.List;

import shinil.teams.model.Team;

/**
 * @author shinilms
 */

public interface TeamView {
    void onShowProgress(boolean isShow);
    void onTeamLoaded(List<Team> teams);
    void onLoadFailed(String error);
}
