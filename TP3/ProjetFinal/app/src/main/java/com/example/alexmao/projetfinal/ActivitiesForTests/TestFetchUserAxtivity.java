package com.example.alexmao.projetfinal.ActivitiesForTests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alexmao.projetfinal.BDDExterne.FetchFullDataFromEBDD;
import com.example.alexmao.projetfinal.BDDExterne.FireBaseBD;
import com.example.alexmao.projetfinal.BDDExterne.LocalUserProfilEBDD;
import com.example.alexmao.projetfinal.BDDExterne.OnFullUserData;
import com.example.alexmao.projetfinal.BDDExterne.Picture;
import com.example.alexmao.projetfinal.BDDExterne.Position;
import com.example.alexmao.projetfinal.BDDExterne.RemoteBD;
import com.example.alexmao.projetfinal.BDDExterne.UserParamsEBDD;
import com.example.alexmao.projetfinal.BDDExterne.UtilisateurProfilEBDD;
import com.example.alexmao.projetfinal.R;

import java.util.LinkedList;
import java.util.List;

public class TestFetchUserAxtivity extends AppCompatActivity {
    private String userID;
    private RemoteBD remoteBD;
    private Button launchButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fetch_user);
        launchButton = (Button) findViewById(R.id.retrieve_full_user_test);
        textView = (TextView) findViewById(R.id.show_user_full_retrieve);

        remoteBD = new FireBaseBD(this);
        userID = initUser(remoteBD);

        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveUser(userID);
            }
        });
    }

    private String initUser(RemoteBD remoteBD) {
        String id;
        UtilisateurProfilEBDD currentUser = new UtilisateurProfilEBDD("test", "fulluser","test.fulluser@polytest.ca");
        List<String> sports = new LinkedList<>();
        sports.add("curling");
        sports.add("boxe en tutu");
        currentUser.setSports(sports);
        List<String> interets = new LinkedList<>();
        interets.add("id1");
        interets.add("id2");
        currentUser.setListeInteretsID(interets);
        List<String> participations = new LinkedList<>();
        participations.add("participationID");
        currentUser.setListeParticipationsID(participations);
        Position mPosition  = new Position(0xDEAD,0xBEAF);
        UserParamsEBDD mParams = new UserParamsEBDD(10, true, true);
        Picture mPicture = new Picture();
        List<String> strings = new LinkedList<>();
        strings.add("success");
        mPicture.setStringChunks(strings);

        id = remoteBD.addUserProfil(currentUser);
        remoteBD.addPositionToUser(id, mPosition);
        remoteBD.addUserParam(mParams, id);
        remoteBD.addPicToUser(id, mPicture);

        return id;
    }

    private void retrieveUser(String userID) {
        FetchFullDataFromEBDD.fetchUser(userID, remoteBD, new OnFullUserData() {
            @Override
            public void onFullUserData(LocalUserProfilEBDD localUserProfilEBDD, Position position, Picture picture, UserParamsEBDD params) {
                String toString = userToString(localUserProfilEBDD);
                toString += positionToString(position);
                toString += pictureToString(picture);
                toString += paramToString(params);
                setTextOnView(toString);
            }
        });
    }

    private String userToString(LocalUserProfilEBDD  localUserProfilEBDD) {
        String printableBuffer = "local user: \n";
        printableBuffer += "nom: " + localUserProfilEBDD.getLastName() + "\n";
        printableBuffer += "prenom: " + localUserProfilEBDD.getFirstName() + "\n";
        printableBuffer +="mail: " + localUserProfilEBDD.getMailAdr() + "\n";
        printableBuffer += "date: " + localUserProfilEBDD.getDateBirth() + "\n";
        printableBuffer +="sports: ";
        for (String sportid:localUserProfilEBDD.getSports()) {
            printableBuffer += sportid + " ";
        }
        printableBuffer += "\n";
        printableBuffer +="interets: ";
        for (String interestid:localUserProfilEBDD.getListeInteretsID()) {
            printableBuffer += interestid + " ";
        }
        printableBuffer += "\n";
        printableBuffer +="participations: ";
        for (String participationsid:localUserProfilEBDD.getListeInteretsID()) {
            printableBuffer += participationsid + " ";
        }
        printableBuffer += "\n";

        return printableBuffer;
    }

    private String positionToString(Position position) {
        String printableBuffer = "position: \n";
        printableBuffer += "latitude: " + position.getLatitude();
        printableBuffer += ",";
        printableBuffer += "longitude: " + position.getLongitude();
        printableBuffer += "\n";

        return printableBuffer;
    }

    private String pictureToString(Picture picture) {
        String printableBuffer = "picture:\n";
        for (String chunk :picture.getStringChunks()) {
            printableBuffer += chunk + " ";
        }
        printableBuffer += "\n";

        return printableBuffer;
    }

    private String paramToString(UserParamsEBDD param) {
        String printableBuffer = "params: \n";
        printableBuffer += "rayon: " + param.getRayon();
        printableBuffer += ",";
        printableBuffer += "localisation: " + param.isLocalisation();
        printableBuffer += ",";
        printableBuffer += "visible: " + param.isMasquerNom();
        printableBuffer += "\n";

        return printableBuffer;
    }

    private void setTextOnView(String textOnView) {
        textView.setText(textOnView);
    }

}
