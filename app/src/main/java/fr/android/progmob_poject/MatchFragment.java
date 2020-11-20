package fr.android.progmob_poject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import androidx.fragment.app.Fragment;
import fr.android.progmob_poject.jdbc.Controller;
import fr.android.progmob_poject.model.Match;

public class MatchFragment extends Fragment implements View.OnClickListener {
    public TextView teams;
    public TextView address;
    public TextView coordinates;
    public TextView date;
    public TextView scores;
    public Button location;
    public MainActivity mainActivity;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_match, container, false);
        teams = rootView.findViewById(R.id.teams);
        scores = rootView.findViewById(R.id.scores);
        address = rootView.findViewById(R.id.address);
        date = rootView.findViewById(R.id.date);
        location = rootView.findViewById(R.id.buttonLocation);
        location.setOnClickListener(this);
        mainActivity = (MainActivity) getActivity();
        Match match = CachePot.getInstance().pop(Match.class);
        teams.setText(match.getTeam_a() + "  VS  " + match.getTeam_b());
        scores.setText(match.getScore_team_a() + "  -  " + match.getScore_team_b());
        address.setText(match.getAddress());
        date.setText(match.getDate_match().format(DateTimeFormatter.ofPattern("d/MM/yyyy")));
        return rootView;
    }
/*
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

 */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLocation:
                Toast.makeText(mainActivity, "button location clicked", Toast.LENGTH_LONG).show();

                break;

        }

    }
/*
    private class AsyncCaller extends AsyncTask<Match, Void, String> {
        @Override
        protected String doInBackground(Match... matches) {
            Match match = matches[0];
            try {
                Controller.addMatch(match);
                return null;
            } catch (Exception e) {

                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(mainActivity, "Match Added", Toast.LENGTH_LONG).show();
                mainActivity.bottomNav.setSelectedItemId(R.id.nav_home);
                teamA.getText().clear();
                teamB.getText().clear();
                address.getText().clear();
                coordinates.getText().clear();
                date.getText().clear();
            } else {
                Toast.makeText(mainActivity, result, Toast.LENGTH_LONG).show();
            }

        }

    }
*/
}
