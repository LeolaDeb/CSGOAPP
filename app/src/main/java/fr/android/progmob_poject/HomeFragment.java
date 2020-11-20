package fr.android.progmob_poject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private CardView viewMatches, addMatch;
    private MainActivity mainActivity;
    private BottomNavigationView bottomNavigationView;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        viewMatches = rootView.findViewById(R.id.viewMatchesCardView);
        addMatch = rootView.findViewById(R.id.addMatchCardView);
        viewMatches.setOnClickListener(this);
        addMatch.setOnClickListener(this);
        mainActivity = (MainActivity) getActivity();
        bottomNavigationView = mainActivity.bottomNav;
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewMatchesCardView:
                bottomNavigationView.setSelectedItemId(R.id.nav_matches);
                //mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SelectMatchFragment()).commit();
                break;
            case R.id.addMatchCardView:
                bottomNavigationView.setSelectedItemId(R.id.nav_add);
                //mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragment()).commit();
                break;
        }

    }

   /* public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
    */

}
