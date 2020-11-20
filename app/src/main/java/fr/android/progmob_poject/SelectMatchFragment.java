package fr.android.progmob_poject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import fr.android.progmob_poject.jdbc.Controller;
import fr.android.progmob_poject.model.Match;

public class SelectMatchFragment extends Fragment implements View.OnClickListener {
    private Spinner spinner;
    private MainActivity mainActivity;
    private Button select;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_match, container, false);
        select = (Button) rootView.findViewById(R.id.buttonSelect);
        spinner = (Spinner) rootView.findViewById(R.id.match_spinner);
        mainActivity = (MainActivity) getActivity();
        select.setOnClickListener(this);

        AsyncCallerGetAllMatches task = new AsyncCallerGetAllMatches();
        task.execute();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSelect:
                String[] match = spinner.getSelectedItem().toString().split("[ ]+");
                String teamA = match[0];
                String teamB = match[2];
                String date = match[3];

                System.out.println(match);
                Toast.makeText(mainActivity, teamA + " " + teamB + " " + date, Toast.LENGTH_LONG).show();
                AsyncCallerGetMatch task = new AsyncCallerGetMatch();
                task.execute(match);
                break;
        }
    }
    /*
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SelectMatchFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

     */
    private class AsyncCallerGetAllMatches extends AsyncTask<Void, Void, List<String>> {
        List<Match> matches = new ArrayList<Match>();
        List<String> strMatches = new ArrayList<String>();
        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                matches = Controller.getAllMatches();
                for (Match m : matches){
                    strMatches.add(m.getTeam_a() + " VS " + m.getTeam_b() + " " + m.getDate_match().format(DateTimeFormatter.ofPattern("d/MM/yyyy")).toString());
                }
                return strMatches;
            } catch (Exception e) {

                e.printStackTrace();
                strMatches.add(0,"error");
                strMatches.add(1,e.getMessage());
                return strMatches;
            }
        }


        @Override
        protected void onPostExecute(List<String> matches) {
            if (matches.get(0).equals("error")) {
                Toast.makeText(mainActivity, matches.get(1), Toast.LENGTH_LONG).show();

            } else {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, matches);
                spinner.setAdapter(arrayAdapter);

            }

        }

    }

    private class AsyncCallerGetMatch extends AsyncTask<String, Void, Match> {
        Match match = null;
        List<String> strMatches = new ArrayList<String>();
        @Override
        protected Match doInBackground(String... strings) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                LocalDate ld = LocalDate.parse("1970-01-01");
                ld = LocalDate.parse(strings[3], formatter);
                match = Controller.getMatch(strings[0], strings[2], ld.toString());
                return match;
            } catch (Exception e) {

                e.printStackTrace();
                return match;
            }
        }

        @Override
        protected void onPostExecute(Match match) {
            if (match == null) {
                Toast.makeText(mainActivity, "Match not Found", Toast.LENGTH_LONG).show();

            } else {
                CachePot.getInstance().push(match);
                mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MatchFragment()).commit();
            }

        }

    }

}

