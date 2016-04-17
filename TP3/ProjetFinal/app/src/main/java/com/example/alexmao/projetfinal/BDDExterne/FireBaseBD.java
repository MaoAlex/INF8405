package com.example.alexmao.projetfinal.BDDExterne;

/**
 * Classe pour communiquer avec un serveur Firebase,
 * les résultats sont obtenus via des callback
 * les classes ayant le mot "local" dans leur nom possède une callback qui est appelée
 * qd la données est récupérée
 */

import android.content.Context;
import android.util.Log;

import com.example.alexmao.projetfinal.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FireBaseBD implements RemoteBD {
    private static String TAG = "FireBaseBD";
    private Firebase myFireBaseRef;

    public FireBaseBD(Context context) {
        //connection to BD
        Firebase.setAndroidContext(context);
        //setter l'url du projet firebase
        myFireBaseRef = new Firebase(context.getString(R.string.myFireBaseUrl));
    }

    @Override
    public void updateLocationOnServer(UtilisateurProfilEBDD user, String id) {
        myFireBaseRef.child("users").child(id).setValue(user);
    }

    @Override
    public String getLastDataFromServer(String path) {
        return myFireBaseRef.child("message").getKey();
    }

    @Override
    public String addUserProfil(UtilisateurProfilEBDD user) {
        Firebase userBD = myFireBaseRef.child("users").child("profil").push();
        userBD.setValue(user);

        addUserToID(userBD.getKey(), user.getMailAdr());
        return userBD.getKey();
    }

    @Override
    public void addUserToGroup(final String groupID, final String userID) {
        final Firebase groupBD = myFireBaseRef.child("groups").child(groupID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MyGroupEBDD groupSnapshot = snapshot.getValue(MyGroupEBDD.class);
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
    public void listenToChangeOnUser(final LocalUserProfilEBDD user, final String userBDID) {
        myFireBaseRef.child("users").child("profil").child(userBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("onDataChanged", "Child of id: " + userBDID + " modified");
                UtilisateurProfilEBDD userFromBD = snapshot.getValue(UtilisateurProfilEBDD.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    @Override
    public void changeGroupName(MyLocalGroupEBDD myLocalGroup, String newName) {
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
    public void changeMail(LocalUserProfilEBDD localUserProfil, String newMail) {
        String oldMail = localUserProfil.getMailAdr();
        localUserProfil.setMailAdr(newMail);
        Firebase userBD = myFireBaseRef.child("users").child("profil").child(localUserProfil.getDataBaseId());
        userBD.setValue(localUserProfil);
        Firebase userToID = myFireBaseRef.child("userToID").child(oldMail.replace(".", ")"));
        userToID.setValue(null);
        userToID = myFireBaseRef.child("userToID").child(newMail.replace(".", ")"));
        userToID.setValue(localUserProfil.getDataBaseId());
    }

    @Override
    public String addGroup(MyGroupEBDD myGroupEBDD) {
        Firebase groupBD = myFireBaseRef.child("groups").push();
        groupBD.setValue(myGroupEBDD);
//        addGroupNameToID(myGroupEBDD.getGroupName(), groupBD.getKey());
        return groupBD.getKey();
    }

    private void addGroupToID(String groupID, String groupName) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(groupName);
        groupBD.setValue(groupID);
    }

    @Override
    public void getUserProfil(String id, final UtilisateurProfilEBDD user, final OnUserProfilReceived onUserProfilReceivedCallback) {
        Firebase userBD = myFireBaseRef.child("users").child("profil").child(id);
        userBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UtilisateurProfilEBDD userSnapshot = snapshot.getValue(UtilisateurProfilEBDD.class);
                user.update(userSnapshot);
                onUserProfilReceivedCallback.onUserProfilReceived(userSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getUserProfilFromMail(String mailAdr, final LocalUserProfilEBDD user,
                                      final OnUserProfilReceived onUserProfilReceivedCallback) {
        Firebase groupBD = myFireBaseRef.child("userToID").child(mailAdr.replace('.', ')'));
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String id = (String) snapshot.getValue();
                user.setDataBaseId(id);
                getUserProfil(id, user, onUserProfilReceivedCallback);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getGroup(String groupID, final MyLocalGroupEBDD myGroup,
                         final OnGroupReceived onGroupReceived) {
        Firebase groupBD = myFireBaseRef.child("groups").child(groupID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                MyGroupEBDD groupSnapshot = snapshot.getValue(MyGroupEBDD.class);
                myGroup.update(groupSnapshot);
                onGroupReceived.onGroupReceived(myGroup);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getGroupFromName(String name, final MyLocalGroupEBDD myGroup,
                                 final OnGroupReceived onGroupReceived) {
        Firebase groupBD = myFireBaseRef.child("groupToID").child(name);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String groupID = (String) snapshot.getValue();
                myGroup.setDatabaseID(groupID);
                getGroup(groupID, myGroup, onGroupReceived);
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
    public void listenToChangeOnGroup(final MyGroupEBDD group, final String groupBDID) {
        myFireBaseRef.child("groups").child(groupBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("group of id: " + groupBDID + " modified");
                MyGroupEBDD groupFromBD = snapshot.getValue(MyGroupEBDD.class);
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
    public void getMdp(String mail, final OnStringReceived onMdpReceivedCallback) {
        Firebase mdpBD = myFireBaseRef.child("mdp").child(mail.replace('.', ')'));
        mdpBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String mdp = "";
                if (snapshot.exists()) {
                    mdp = (String) snapshot.getValue();
                }
                onMdpReceivedCallback.onStringReceived(mdp);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void getExistGroup(String name, final OnBooleanReceived onBooleanReceived) {
        Firebase groups = myFireBaseRef.child("groups").child(name);
        groups.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = false;
                if (snapshot.exists()) {
                    exist = true;
                }
                onBooleanReceived.onBooleanReceived(exist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });

    }

    @Override
    public void getExistUser(String mailADR, final OnBooleanReceived onBooleanReceived) {
        Firebase usersBD = myFireBaseRef.child("userToID").child(mailADR);
        usersBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean exist = false;
                if (snapshot.exists()) {
                    exist = true;
                }
                onBooleanReceived.onBooleanReceived(exist);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("error");
            }
        });
    }

    @Override
    public void addPicToUser(String userID, Picture picture) {
        Firebase picBD = myFireBaseRef.child("pictures").child(userID);
        picBD.setValue(picture);
    }

    @Override
    public void getUserPIc(final LocalUserProfilEBDD localUserProfil, final String userID,
                           final OnPictureReceived onPictureReceivedCallback) {
        Firebase picBD = myFireBaseRef.child("pictures").child(userID);
        picBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picture picture = dataSnapshot.getValue(Picture.class);
                localUserProfil.setPicture(picture);
                onPictureReceivedCallback.onPictureReceived(picture);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void getUserPIc(final Picture localPicture, String userID,
                           final OnPictureReceived onPictureReceivedCallback) {
        Firebase picBD = myFireBaseRef.child("pictures").child(userID);
        picBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Picture picture = dataSnapshot.getValue(Picture.class);
                localPicture.setStringChunks(picture.getStringChunks());
                onPictureReceivedCallback.onPictureReceived(picture);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public String addDiscussion(ConversationEBDD discussion) {
        Firebase discusionBD = myFireBaseRef.child("discussions").push();
        discusionBD.setValue(discussion);

        return discusionBD.getKey();
    }

    @Override
    public void getDiscussion(String discussionID, final OnConversationReceived onConversationRecieved) {
        Firebase discusionBD = myFireBaseRef.child("discussions").child(discussionID);
        discusionBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ConversationEBDD conversationEBDD = dataSnapshot.getValue(ConversationEBDD.class);
                onConversationRecieved.onConversationRecieved(conversationEBDD);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public String addMsgToDiscussion(String discussionID, MessageEBDD conversation) {
        Firebase discusionBD = myFireBaseRef.child("discussions").child(discussionID).child("messages").push();
        discusionBD.setValue(conversation);

        return discusionBD.getKey();
    }

    @Override
    public String notifyUserForMsg(String userID, MessageEBDD conversation, String conversationID) {
        Firebase discusionBD = myFireBaseRef.child("users").child("unread").child(userID).child(conversationID).push();
        discusionBD.setValue(conversation);

        return discusionBD.getKey();
    }

    @Override
    public void listenToConversation(final String conversationID,
                                     final String userBDID,
                                     final OnMessageReceiveCallback onMessageReceiveCallback ) {
        myFireBaseRef.child("users").child("unread").child(userBDID).child(conversationID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.d("onDataChanged", "User " + userBDID + " has a new message");
                        ArrayList<String> ids = new ArrayList<String>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MessageEBDD conversation = dataSnapshot.getValue(MessageEBDD.class);
                            onMessageReceiveCallback.onNewMessage(conversation);
                            ids.add(dataSnapshot.getKey());
                        }

                        for (String id : ids) {
                            myFireBaseRef.child("users").child("unread").child(userBDID)
                                    .child(conversationID).child(id).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
    }

    @Override
    public void addPositionToUser(String userID, Position position) {
        Firebase positionBD = myFireBaseRef.child("users").child("position").child(userID);
        positionBD.setValue(position);
    }

    @Override
    public void getUserPosition(String userID, final OnPositionReceived onPositionReceived) {
        Firebase positionBD = myFireBaseRef.child("users").child("position").child(userID);
        positionBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Position position = dataSnapshot.getValue(Position.class);
                onPositionReceived.onPostionReceived(position);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void listenToPositionChanges(final String userID,
                                       final OnPositionReceivedForUser onPositionReceivedCallback) {
        Firebase positionBD = myFireBaseRef.child("users").child("position").child(userID);
        positionBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Position position = dataSnapshot.getValue(Position.class);
                onPositionReceivedCallback.onPositionReceivedForUser(position, userID);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void updateTimeLastChange(String userID, long timeMilli) {
        Firebase timeLastChangeBD = myFireBaseRef.child("users").child("timeLastChange").child(userID);
        timeLastChangeBD.setValue(timeMilli);
    }

    @Override
    public void getTimeLastChange(String userID, final OnTimeReceived timeCallback) {
        Firebase timeLastChangeBD = myFireBaseRef.child("users").child("timeLastChange").child(userID);
        timeLastChangeBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long time = (long) dataSnapshot.getValue();
                timeCallback.onTimeReceived(time);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public String addMsgAndNotify(String localUserID, MessageEBDD message,
                                  String conversationID, MyGroupEBDD receivers) {
        String msgID = addMsgToDiscussion(conversationID, message);

        for (String userID: receivers.getMembersID()) {
            if (!userID.equals(localUserID)) {
                notifyUserForMsg(userID, message, conversationID);
            }
        }

        return msgID;
    }

    @Override
    public String addEvent(MyEventEBDD myEvent) {
        Firebase eventBD = myFireBaseRef.child("events").push();
        eventBD.setValue(myEvent);

        return eventBD.getKey();
    }

    @Override
    public void addEventToGroup(String eventID, String groupID) {
        Firebase groupBD = myFireBaseRef.child("groupToEnvent").child(groupID);
        groupBD.setValue(eventID);
    }

    @Override
    public void getEventFromGroup(String groupID, final OnStringReceived onStringReceived) {
        Firebase groupBD = myFireBaseRef.child("groupToEnvent").child(groupID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String eventID = (String) dataSnapshot.getValue();
                onStringReceived.onStringReceived(eventID);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void addUserParam(UserParamsEBDD userParamsEBDD, String userID) {
        Firebase paramBD = myFireBaseRef.child("users").child("params").child(userID);
        paramBD.setValue(userParamsEBDD);
    }

    @Override
    public void getUserParam(String userID, final OnUserParamReceived onUserParamReceivedCallback) {
        Firebase paramBD = myFireBaseRef.child("users").child("params").child(userID);
        paramBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserParamsEBDD userParamsEBDD = dataSnapshot.getValue(UserParamsEBDD.class);
                onUserParamReceivedCallback.onUserParamReceived(userParamsEBDD);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public String addNotificationToUser(String userID, NotificationBDD notificationBDD) {
        Firebase notificationBD = myFireBaseRef.child("users").child("notifications")
                .child(userID).push();
        notificationBD.setValue(notificationBDD);

        return notificationBD.getKey();
    }

    /**
     *
     * @param userBDID id firebase de l'utilisateur
     * @param typeToActionCallback map précisant pour chaque type quelle action faire
     */
    @Override
    public void listenToNotification(final String userBDID, final Map<String,
            OnNotificationReceived> typeToActionCallback) {
        Firebase notificationBD = myFireBaseRef.child("users").child("notifications")
                .child(userBDID);
        notificationBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<String> ids = new ArrayList<String>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    NotificationBDD notificationBDD = dataSnapshot.getValue(NotificationBDD.class);

                    typeToActionCallback.get(notificationBDD.getType())
                            .onNotificationReceived(notificationBDD);

                    ids.add(dataSnapshot.getKey());
                }

                for (String id : ids) {
                    myFireBaseRef.child("users").child("notifications").child(userBDID)
                            .child(id).removeValue();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG, "onCancelled: " + firebaseError.getMessage());
            }
        });
    }
}
