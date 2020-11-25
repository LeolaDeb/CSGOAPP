package fr.android.progmob_poject;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.kimkevin.cachepot.CachePot;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private CardView viewMatches, addMatch;
    private MainActivity mainActivity;
    private BottomNavigationView bottomNavigationView;
    private ImageButton buttonLanguage;
    private String languageToLoad;
    //private static int imageToLoad;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        viewMatches = rootView.findViewById(R.id.viewMatchesCardView);
        addMatch = rootView.findViewById(R.id.addMatchCardView);
        buttonLanguage = rootView.findViewById(R.id.ButtonLanguage);
        buttonLanguage.setOnClickListener(this);
        viewMatches.setOnClickListener(this);
        addMatch.setOnClickListener(this);
        mainActivity = (MainActivity) getActivity();
        bottomNavigationView = mainActivity.bottomNav;
        languageToLoad = CachePot.getInstance().pop("languageToLoad");
        if (languageToLoad == null){
            //CachePot.getInstance().push("languageToLoad", "en");
            languageToLoad = "en";
        }
        if (languageToLoad == "fr")
            buttonLanguage.setImageResource(R.drawable.ic_france);
        if (languageToLoad == "en")
            buttonLanguage.setImageResource(R.drawable.ic_united_kingdom);
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
            case R.id.ButtonLanguage:
                //LocaleHelper.setLocale(mainActivity,"en");

                Toast.makeText(mainActivity, "Language changed", Toast.LENGTH_LONG).show();
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                if (languageToLoad == "en"){
                    languageToLoad = "fr";
                }
                if (languageToLoad == "fr"){
                    languageToLoad = "en";
                }
                CachePot.getInstance().push("languageToLoad", languageToLoad);
                getResources().updateConfiguration(config, mainActivity.getResources().getDisplayMetrics());
                mainActivity.finish();
                mainActivity.overridePendingTransition(0,0);
                startActivity(mainActivity.getIntent());
                mainActivity.overridePendingTransition(0, 0);

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
