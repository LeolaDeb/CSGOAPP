package fr.android.progmob_poject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import androidx.fragment.app.Fragment;
import fr.android.progmob_poject.jdbc.Controller;
import fr.android.progmob_poject.model.Match;

public class AddFragment extends Fragment implements View.OnClickListener {
    public EditText teamA;
    public EditText teamB;
    public EditText address;
    public EditText coordinates;
    public EditText date;
    public EditText scoreTeamA;
    public EditText scoreTeamB;
    public Button submit;
    public Button location;
    public Button picture;
    public MainActivity mainActivity;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        teamA = rootView.findViewById(R.id.editTextTeamA);
        teamB = rootView.findViewById(R.id.editTextTeamB);
        scoreTeamA = rootView.findViewById(R.id.editTextScoreTeamA);
        scoreTeamB = rootView.findViewById(R.id.editTextScoreTeamB);
        address = rootView.findViewById(R.id.editTextAddress);
        coordinates = rootView.findViewById(R.id.editTextCoordinates);
        date = rootView.findViewById(R.id.editTextDate);
        submit = rootView.findViewById(R.id.buttonSubmit);
        location = rootView.findViewById(R.id.buttonLocation);
        picture = rootView.findViewById(R.id.buttonPicture);
        submit.setOnClickListener(this);
        location.setOnClickListener(this);
        picture.setOnClickListener(this);
        mainActivity = (MainActivity) getActivity();

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
            case R.id.buttonPicture:
                Toast.makeText(mainActivity, "button picture clicked", Toast.LENGTH_LONG).show();

                break;
            case R.id.buttonSubmit:


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                LocalDate ld = LocalDate.parse("1970-01-01");
                try {
                    ld = LocalDate.parse(date.getText(), formatter);
                } catch (DateTimeParseException e) {
                    System.err.println("Unable to parse the date!");
                    Toast.makeText(mainActivity, "Unable to parse the date. please use format (d/MM/yyyy) ", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                Match match = new Match(0, teamA.getText().toString(), teamB.getText().toString(), address.getText().toString(), coordinates.getText().toString(), ld, Integer.parseInt(scoreTeamA.getText().toString()), Integer.parseInt(scoreTeamB.getText().toString()));
                AsyncCaller task = new AsyncCaller();
                task.execute(new Match[] {match});
                break;
        }

    }

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

}
