package com.example.alexmao.tp2final.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alexmao.tp2final.R;

public class TestProposalActivity extends ConnectedMapActivity {
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_proposal);
        setLocalUser((LocalUser) getIntent().getParcelableExtra("localUser"));

        testButton = (Button) findViewById(R.id.test_places);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testPlace(getIntent().getStringExtra("groupID"));
            }
        });

        getMyRemoteBD().setOnQuery(new OnQuery() {
            @Override
            public void onPlaceQuery() {
                getMyRemoteBD().setPlaceChoice(0, getLocalUser().getDataBaseId());
            }

            @Override
            public void onTimeQuey() {

            }

            @Override
            public void onAvaibilitiesQuery() {

            }
        });
    }

    void testPlace(String groupid) {
        PlaceProposals proposals = new PlaceProposals();
        MeetingPlace meetingPlace = new MeetingPlace("chezLulu", "café", 0.0, 0.0);
        proposals.addPlace(meetingPlace);


        meetingPlace = new MeetingPlace("chezEnzo", "pizzeria", 0.0, 0.0);
        proposals.addPlace(meetingPlace);

        meetingPlace = new MeetingPlace("chezFilou", "bar", 0.0, 0.0);
        proposals.addPlace(meetingPlace);

        getMyRemoteBD().addPlacesProposal(proposals, groupid);
        getMyRemoteBD().requestChoicePlace(getLocalUser().getDataBaseId());
    }
}
