package shinil.teams.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import shinil.teams.R;
import shinil.teams.model.Team;

/**
 * @author shinilms
 */

public final class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {
    private List<Team> teamList;

    public TeamListAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(teamList.get(position));
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView teamName, teamType, teamCountry;

        ViewHolder(View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.text_view_team_name);
            teamType = itemView.findViewById(R.id.text_view_team_type);
            teamCountry = itemView.findViewById(R.id.text_view_team_country);
        }

        private void setData(Team team) {
            teamName.setText(team.getName());
            teamType.setText(team.isNational() ? "National Team" : "Club");
            teamCountry.setText(team.getCountry_name());
        }
    }
}
