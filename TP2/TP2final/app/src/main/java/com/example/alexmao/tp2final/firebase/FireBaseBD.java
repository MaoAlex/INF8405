package com.example.alexmao.tp2final.firebase;

/**
 * Classe pour communiquer avec un serveur Firebase,
 * les résultats sont obtenus via des callback
 * les classes ayant le mot "local" dans leur nom possède une callback qui est appelée
 * qd la données est récupérée
 */

import android.content.Context;
import android.util.Log;

import com.example.alexmao.tp2final.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class FireBaseBD implements RemoteBD {
    private static String TAG = "FireBaseBD";
    private Firebase myFireBaseRef;
    private OnQuery onQuery;

    public void setOnQuery(OnQuery onQuery) {
        this.onQuery = onQuery;
    }

    public FireBaseBD(Context context) {
        //connection to BD
        Firebase.setAndroidContext(context);
        //setter l'url du projet firebase
        myFireBaseRef = new Firebase(context.getString(R.string.myFireBaseUrl));
    }

    @Override
    public void updateLocationOnServer(UserFirebase user, String id) {
        myFireBaseRef.child("users" ).child(id).setValue(user);
    }

    @Override
    public String getLastDataFromServer(String path) {
        return myFireBaseRef.child("message").getKey();
    }

    @Override
    public String addUser(UserFirebase user) {
        Firebase userBD = myFireBaseRef.child("users").push();
        userBD.setValue(user);

        Firebase requestBD = myFireBaseRef.child("requests").child(userBD.getKey());
        requestBD.setValue("none");

        addUserToID(userBD.getKey(), user.getMailAdr());
        return userBD.getKey();
    }

    @Override
    public void addUserToGroup(final String groupID, final String userID) {
        final Firebase groupBD = myFireBaseRef.child("groups").child(groupID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MyGroup groupSnapshot = snapshot.getValue(MyGroup.class);
                groupSnapshot.addMember(userID);
                groupBD.setValue(groupSnapshot);
                Firebase userToGroup = myFireBaseRef.child("usersToGroup").child(userID);
                userToGroup.setValue(groupID);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    private void addUserToID(String id, String mailAdr) {
        Firebase userBD = myFireBaseRef.child("userToID").child(mailAdr.replace('.', ')'));
        userBD.setValue(id);
    }

    //update user when modified on server
    @Override
    public void listenToChangeOnUser(final LocalUser user, final String userBDID) {
        myFireBaseRef.child("users").child(userBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("onDataChanged", "Child of id: " + userBDID + " modified");
                UserFirebase userFromBD = snapshot.getValue(UserFirebase.class);
                user.setLat(userFromBD.getLat());
                Log.d("onDataChange: ", "new latitude " + user.getLat());
                user.setLongi(userFromBD.getLongi());
                Log.d("onDataChange: ", "new longitude " + user.getLongi());
                user.update();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public void changeGroupName(MyLocalGroup myLocalGroup, String newName) {
        String oldName = myLocalGroup.getGroupName();
        myLocalGroup.setGroupName(newName);
        Firebase groupBD = myFireBaseRef.child("groups").child(myLocalGroup.getDatabaseID());
        groupBD.setValue(myLocalGroup);
        Firebase groupNameToIDBD = myFireBaseRef.child("groupToID").child(oldName);
        groupNameToIDBD.setValue(null);
        groupNameToIDBD = myFireBaseRef.child("groupToID").child(newName);
        groupNameToIDBD.setValue(myLocalGroup.getDatabaseID());
    }

    @Override
    public void changeMail(LocalUser localUser, String newMail) {
        String oldMail = localUser.getMailAdr();
        localUser.setMailAdr(newMail);
        Firebase userBD = myFireBaseRef.child("users").child(localUser.getDataBaseId());
        userBD.setValue(localUser);
        Firebase userToID = myFireBaseRef.child("userToID").child(oldMail.replace(".", ")"));
        userToID.setValue(null);
        userToID = myFireBaseRef.child("userToID").child(newMail.replace(".", ")"));
        userToID.setValue(localUser.getDataBaseId());
    }

    @Override
    public String addGroup(MyGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groups").push();
        groupBD.setValue(myGroup);
        addGroupNameToID(myGroup.getGroupName(), groupBD.getKey());
        return groupBD.getKey();
    }

    private void addGroupToID(String groupID, String groupName) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(groupName);
        groupBD.setValue(groupID);
    }

    @Override
    public void getUser(String id, final LocalUser user) {
        Firebase userBD = myFireBaseRef.child("users").child(id);
        userBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserFirebase userSnapshot = snapshot.getValue(UserFirebase.class);
                user.update(userSnapshot);
                user.update();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getUserFromMail(String mailAdr, final LocalUser user) {
        Firebase groupBD = myFireBaseRef.child("userToID").child(mailAdr.replace('.', ')'));
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String id = (String) snapshot.getValue();
                user.setDataBaseId(id);
                getUser(id, user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getGroup(String groupID, final MyLocalGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groups").child(groupID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MyGroup groupSnapshot = snapshot.getValue(MyGroup.class);
                myGroup.update(groupSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getGroupFromName(String name, final MyLocalGroup myGroup) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(name);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String groupID = (String) snapshot.getValue();
                myGroup.setDatabaseID(groupID);
               getGroup(groupID, myGroup);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    private void addGroupNameToID(String groupName, String groupID) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(groupName);
        groupBD.setValue(groupID);
    }

    @Override
    public void listenToChangeOnGroup(final MyGroup group, final String groupBDID) {
        myFireBaseRef.child("groups").child(groupBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("group of id: " + groupBDID + " modified");
                MyGroup groupFromBD = snapshot.getValue(MyGroup.class);
                group.setMembersID(groupFromBD.getMembersID());
                group.setGroupName(groupFromBD.getGroupName());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public void addMdpToUser(String mail, String mdp) {
        Firebase mdpOnBD = myFireBaseRef.child("mdp").child(mail.replace('.', ')'));
        mdpOnBD.setValue(mdp);
    }

    @Override
    public void getUserPref(String id, final LocalUserPreferences preferences) {
        Firebase mdpBD = myFireBaseRef.child("preferences").child(id);
        mdpBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserPreferences userPreferencesBD = snapshot.getValue(UserPreferences.class);
                for (String pref : userPreferencesBD.getPreferences()) {
                    preferences.addPreference(pref);
                }
                preferences.update();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void update(final LocalUser localUser, final MyLocalGroup myLocalGroup, final LocalUserPreferences localUserPreferences, final OnUpdateComplete onUpdateComplete) {
        final IntWrapper i = new IntWrapper(0);
        final LocalUser.ChangeListener changeListener = localUser.getChangeListener();
        final MyLocalGroup.ChangeListener changeListenerGroup = myLocalGroup.getChangeListener();
        final LocalUserPreferences.OnRetrieve onRetrieve = localUserPreferences.getOnRetrieve();

        localUser.setChangeListener(new LocalUser.ChangeListener() {
            @Override
            public void onPositionChanged(LocalUser localUser) {
                i.setI(i.getI() + 1);
                changeListener.onPositionChanged(localUser);
                if ( i.getI() == 3) {
                    onUpdateComplete.onUpdateComplete(localUser, myLocalGroup, localUserPreferences);
                    localUser.setChangeListener(changeListener);
                }
            }
        });
        getUser(localUser.getDataBaseId(), localUser);

        myLocalGroup.setChangeListener(new MyLocalGroup.ChangeListener() {
            @Override
            public void onChange(MyLocalGroup myLocalGroup) {
                i.setI(i.getI() + 1);
                changeListenerGroup.onChange(myLocalGroup);
                if (i.getI() == 3) {
                    onUpdateComplete.onUpdateComplete(localUser, myLocalGroup, localUserPreferences);
                    myLocalGroup.setChangeListener(changeListenerGroup);
                }
            }
        });
        getGroup(myLocalGroup.getDatabaseID(), myLocalGroup);

        localUserPreferences.setOnRetrieve(new LocalUserPreferences.OnRetrieve() {
            @Override
            public void onRetrieve(LocalUserPreferences localUserPreferences) {
                i.setI(i.getI() + 1);
                onRetrieve.onRetrieve(localUserPreferences);
                if (i.getI() == 3) {
                    onUpdateComplete.onUpdateComplete(localUser, myLocalGroup, localUserPreferences);
                    localUserPreferences.setOnRetrieve(onRetrieve);
                }
            }
        });
        getUserPref(localUser.getDataBaseId(), localUserPreferences);
    }

    @Override
    public void addUserPref(String id, UserPreferences pref) {
        Firebase prefOnBD = myFireBaseRef.child("preferences").child(id);
        prefOnBD.setValue(pref);
    }

    @Override
    public void getMdp(String mail, final MdpWrapper mdpWrapper) {
        Firebase mdpBD = myFireBaseRef.child("mdp").child(mail.replace('.', ')'));
        mdpBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String mdp = null;
                if (snapshot.exists()) {
                    mdp = (String) snapshot.getValue();
                }
                mdpWrapper.update(mdp);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getExistGroup(String name, final ExistWrapper existWrapper) {
        Firebase groups = myFireBaseRef.child("groups").child(name);
        groups.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = false;
                if (snapshot.exists()) {
                    exist = true;
                }
                existWrapper.update(exist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });

    }

    @Override
    public void getExistUser(String mailADR, final ExistWrapper existWrapper) {
        Firebase usersBD = myFireBaseRef.child("userToID").child(mailADR);
        usersBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = false;
                if (snapshot.exists()) {
                    exist = true;
                }
                existWrapper.update(exist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void requestAvailabilities(String userID) {
        Firebase requestBD = myFireBaseRef.child("requests").child(userID);
        requestBD.setValue("AvailibilitiesRequest");
    }

    @Override
    public void requestChoicePlace(String userID) {
        Firebase requestBD = myFireBaseRef.child("requests").child(userID);
        requestBD.setValue("PlaceRequest");
    }

    @Override
    public void requestChoiceTime(String userID) {
        Firebase requestBD = myFireBaseRef.child("requests").child(userID);
        requestBD.setValue("TimeRequest");
    }

    @Override
    public void listenToRequest(String userID) {
        final Firebase requestBD = myFireBaseRef.child("requests").child(userID);
        requestBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists())
                    return;
                String requestType = (String) snapshot.getValue();
                switch (requestType) {
                    case "AvailibilitiesRequest":
                        Log.d(TAG, "listenToRequest onDataChange: " + "availabilities");

                        break;
                    case "PlaceRequest":
                        Log.d(TAG, "listenToRequest onDataChange: " + "PlaceRequest");
                        if (onQuery != null)
                            onQuery.onPlaceQuery();
                        break;
                    case "TimeRequest":
                        Log.d(TAG, "listenToRequest onDataChange: " + "TimeRequest");

                        break;
                    default:
                        System.out.println("none or invalid");
                        Log.d(TAG, "listenToRequest onDataChange: " + "none or invalid");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void listenToTimeRespond(String userID) {
        Firebase timeBD = myFireBaseRef.child("timeChoice").child(userID);
        timeBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int choice = (int) snapshot.getValue();
                System.out.println(choice);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void listenToPlaceRespond(String userID) {
        Firebase placeBD = myFireBaseRef.child("placeChoice").child(userID);
        placeBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int choice = (int) snapshot.getValue();
                System.out.println(choice);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });

    }

    @Override
    public void listenToAvailabilities(String userID) {
        Firebase timeBD = myFireBaseRef.child("availabilities").child(userID);
        timeBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int choice = (int) snapshot.getValue();
                System.out.println(choice);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void addMeeting(MeetingFinalChoice meetingFinalChoice, String groupID) {
        Firebase meetingBD = myFireBaseRef.child("meetings").child(groupID).push();
        meetingBD.setValue(meetingFinalChoice);
    }

    @Override
    public void addTimeProposal(TimeSlots timeSlots, String groupID) {
        Firebase meetingBD = myFireBaseRef.child("timeProposal").child(groupID).push();
        meetingBD.setValue(timeSlots);
    }

    @Override
    public void getTimeProposal(final LocalTimeSlots timeSlots, String groupID) {
        Firebase timeProposal = myFireBaseRef.child("timeProposal").child(groupID);
        timeProposal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TimeSlots proposalsBD = dataSnapshot.getValue(TimeSlots.class);
                timeSlots.update(proposalsBD);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public void setPlaceChoice(int index, String userID) {
        Firebase meetingBD = myFireBaseRef.child("placeChoice").child(userID);
        meetingBD.setValue(index);
    }

    @Override
    public void setTimeChoice(int index, String userID) {
        Firebase meetingBD = myFireBaseRef.child("timeChoice").child(userID);
        meetingBD.setValue(index);
    }

    @Override
    public void addPlacesProposal(PlaceProposals meetingPlace, String groupID) {
        Firebase meetingBD = myFireBaseRef.child("meetingProposal").child(groupID);
        meetingBD.setValue(meetingPlace);
    }

    @Override
    public void getPlaceProposal(String groupID, final LocalPlaceProposals placeProposals) {
        Firebase placeProposalBD = myFireBaseRef.child("meetingProposal").child(groupID);
        placeProposalBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PlaceProposals proposalsBD = dataSnapshot.getValue(PlaceProposals.class);
                for (MeetingPlace meetingPlace : proposalsBD.getPlaces()) {
                    placeProposals.addPlace(meetingPlace);
                }
                placeProposals.update();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
