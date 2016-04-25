package com.example.alexmao.projetfinal.BDDExterne;

/**
 * Classe pour communiquer avec un serveur Firebase,
 * les résultats sont obtenus via des callback
 */

import android.content.Context;
import android.util.Log;

import com.example.alexmao.projetfinal.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FireBaseBD implements RemoteBD {
    private static String TAG = "FireBaseBD";
    private Firebase myFireBaseRef;

    public FireBaseBD(Context context) {
        //connection BD
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

    //Ajoute un utilisateur sur le servuer
    @Override
    public String addUserProfil(UtilisateurProfilEBDD user) {
        Firebase userBD = myFireBaseRef.child("users").child("profil").push();
        userBD.setValue(user);

        addUserToID(userBD.getKey(), user.getMailAdr());
        return userBD.getKey();
    }

    //Ajoute un utilisateur dans un groupe
    @Override
    public void addUserToGroup(final String groupID, final String userID) {
        final Firebase groupBD =  myFireBaseRef.child("usersToGroups").child(userID).push();
        groupBD.setValue(groupID);
    }

    @Override
    public void getGroupsFromUser(String userID, final OnIdsreceived callback) {
        Firebase groupBD = myFireBaseRef.child("usersToGroups").child(userID);
        groupBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> ids = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ids.add((String) snapshot
                            .getValue());
                }
                callback.onIdsreceived(ids);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //Ajoute la correspondance entre l'ID d'un utilisateur et son adresse mail
    private void addUserToID(String id, String mailAdr) {
        Firebase userBD = myFireBaseRef.child("userToID").child(mailAdr.replace('.', ')'));
        userBD.setValue(id);
    }

    //une fois appelée user est mis à jour automatiquement
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

    //permet de changer l'adresse mail d'un utilisateur existant sur la BD
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

    //Ajoute un groupe à la BD
    @Override
    public String addGroup(MyGroupEBDD myGroupEBDD) {
        Firebase groupBD = myFireBaseRef.child("groups").push();
        groupBD.setValue(myGroupEBDD);
        //Voir
        return groupBD.getKey();
    }

    //Récupére un utilisateur profil à partir d'un ID,
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //Récupére un utilisateur profil à partir d'un mail,
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //Récupére un groupe à partir d'un ID,
    //Garantie: lorsque la callback est appelée, on a reçu les données
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


    //met à jour la date de dernière modification
    @Override
    public void updateTimeLastChangeGroup(String groupID, long timeMillis) {
        Firebase timeLastChangeBD = myFireBaseRef.child("groups").child("timeLastChange").child(groupID);
        timeLastChangeBD.setValue(timeMillis);
    }

    //récupère la date de dernière modification
    //Garantie: lorsque la callback est appelée, on a reçu les données
    @Override
    public void getTimeLastChangeGroup(String groupID, final OnTimeReceived timeCallback) {
        Firebase timeLastChangeBD = myFireBaseRef.child("groups").child("timeLastChange").child(groupID);
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

    //Permet la mise à jour automatique d'un groupe
    @Override
    public void listenToChangeOnGroup(final MyGroupEBDD group, final String groupBDID) {
        myFireBaseRef.child("groups").child(groupBDID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("group of id: " + groupBDID + " modified");
                MyGroupEBDD groupFromBD = snapshot.getValue(MyGroupEBDD.class);
                group.setMembersID(groupFromBD.getMembersID());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    //Ajoute un mdp à un utilisateur
    @Override
    public void addMdpToUser(String mail, String mdp) {
        Firebase mdpOnBD = myFireBaseRef.child("mdp").child(mail.replace('.', ')'));
        mdpOnBD.setValue(mdp);
    }

    //Récupère le mot de passe d'un utilisateur
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //affirme si un utilisateur existe
    //Garantie: lorsque la callback est appelée, on a reçu les données
    @Override
    public void getExistUser(String mailADR, final OnBooleanReceived onBooleanReceived) {
        Firebase usersBD = myFireBaseRef.child("userToID").child(mailADR.replace(".", ")"));
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

    //Ajoute un photo à d'un utilisateur
    @Override
    public void addPicToUser(String userID, Picture picture) {
        Firebase picBD = myFireBaseRef.child("pictures").child(userID);
        picBD.setValue(picture);
    }

    //Récupère la photo d'un utilisateur
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //Récupère la photo d'un utilisateur
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //Ajoute une conversation sur le serveur
    @Override
    public String addDiscussion(ConversationEBDD discussion) {
        Firebase discusionBD = myFireBaseRef.child("discussions").push();
        discusionBD.setValue(discussion);

        return discusionBD.getKey();
    }

    //Récupère une discussion depuis le serveur
    //Garantie: lorsque la callback est appelée, on a reçu les données
    @Override
    public void getDiscussion(String discussionID, final OnConversationReceived onConversationRecieved) {
        Firebase discusionBD = myFireBaseRef.child("discussions").child(discussionID);
        discusionBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ConversationEBDD conversationEBDD = dataSnapshot.getValue(ConversationEBDD.class);
                conversationEBDD.setDataBaseId(dataSnapshot.getKey());
                onConversationRecieved.onConversationRecieved(conversationEBDD);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void updateDiscussion(String discussionID, ConversationEBDD conversationEBDD) {
        Firebase discusionBD = myFireBaseRef.child("discussions").child(discussionID);
        discusionBD.setValue(conversationEBDD);
    }

    //Ajoute un message sur le serveur
    @Override
    public String addMsgToDiscussion(String discussionID, MessageEBDD conversation) {
        Firebase discusionBD = myFireBaseRef.child("discussions").child(discussionID).child("messages").push();
        discusionBD.setValue(conversation);

        return discusionBD.getKey();
    }

    //prévient utilisateur qu'il a reçu un message
    @Override
    public String notifyUserForMsg(String userID, MessageEBDD conversation) {
        Firebase discusionBD = myFireBaseRef.child("users").child("unread").child(userID).push();
        discusionBD.setValue(conversation);

        return discusionBD.getKey();
    }

    //une fois appelée, dès que l'utilisateur reçoit un message de la conversation
    //la callback est appelée
    @Override
    public void listenToConversations(final String userBDID,
                                     final OnMessageReceiveCallback onMessageReceiveCallback ) {
        myFireBaseRef.child("users").child("unread").child(userBDID)
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
                                    .child(id).removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });
    }

    //Rajoute la position à un utilisateur sur le serveur
    @Override
    public void addPositionToUser(String userID, Position position) {
        Firebase positionBD = myFireBaseRef.child("users").child("position").child(userID);
        positionBD.setValue(position);
    }

    //Récupère la position d'un utilisateur
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //une fois appelée, lorsque la position est modifiée sur le serveur
    //la callback est appelée
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

    //met à jour la date de modification d'un utilisateur
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

    //Ajoute un message à une discusion, et prévient tous les utilisateurs
    //à utiliser en priorité par rapport à addMsg
    @Override
    public String addMsgAndNotify(String localUserID, MessageEBDD message,
                                  String conversationID, MyGroupEBDD receivers) {
        String msgID = addMsgToDiscussion(conversationID, message);

        for (String userID: receivers.getMembersID()) {
            if (!userID.equals(localUserID)) {
                message.setConversationID(conversationID);
                notifyUserForMsg(userID, message);
            }
        }

        return msgID;
    }

    //Ajoute un un événement sur la bd
    @Override
    public String addEvent(MyEventEBDD myEvent) {
        Firebase eventBD = myFireBaseRef.child("events").push();
        eventBD.setValue(myEvent);

        return eventBD.getKey();
    }

    @Override
    public void getEvent(String eventID, final OnEventReceived callback) {
        Firebase eventBD = myFireBaseRef.child("events").child(eventID);
        eventBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyEventEBDD myEventEBDD = dataSnapshot.getValue(MyEventEBDD.class);
                myEventEBDD.setDataBaseId(dataSnapshot.getKey());
                callback.onEventReceived(myEventEBDD);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //met à jour la date de dernière modification
    @Override
    public void updateTimeLastChangeEvent(String eventID, long timeMillis) {
        Firebase timeLastChangeBD = myFireBaseRef.child("events").child("timeLastChange").child(eventID);
        timeLastChangeBD.setValue(timeMillis);
    }

    //récupère la date de dernière modification
    //Garantie: lorsque la callback est appelée, on a reçu les données
    @Override
    public void getTimeLastChangeEvent(String eventID, final OnTimeReceived timeCallback) {
        Firebase timeLastChangeBD = myFireBaseRef.child("events").child("timeLastChange").child(eventID);
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

    //Rajoute un evenement à un group
    @Override
    public void addEventToGroup(String eventID, String groupID) {
        Firebase groupBD = myFireBaseRef.child("groupToEnvent").child(groupID);
        groupBD.setValue(eventID);
    }

    @Override
    public void updateEvent(String eventID, MyEventEBDD myEventEBDD) {
        Firebase eventBD = myFireBaseRef.child("events").child(eventID);
        eventBD.setValue(myEventEBDD);
    }

    @Override
    public void addEventToTemporary(String eventID) {
        Firebase eventBD = myFireBaseRef.child("TemporaryEvents").push();
        eventBD.setValue(eventID);
    }

    @Override
    public void getTemporaryEvent(final long date, final OnTemporaryEvents callback) {
        Firebase eventBD = myFireBaseRef.child("TemporaryEvents");
        eventBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<MyEventEBDD> myEventEBDDs = new LinkedList<MyEventEBDD>();
                final List<String> passedEventID = new LinkedList<String>();
                final long nbEvent = dataSnapshot.getChildrenCount();
                final long [] ndReceive = new long[1];

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String id = (String) snapshot.getValue();
                    final String key = snapshot.getKey();
                    getEvent(id, new OnEventReceived() {
                        @Override
                        public void onEventReceived(MyEventEBDD myEventEBDD) {
                            if (myEventEBDD.getDate() < date) {
                                passedEventID.add(key);
                            } else {
                                myEventEBDDs.add(myEventEBDD);
                            }
                            ndReceive[0]++;
                            if (ndReceive[0] == nbEvent) {
                                for (String id: passedEventID) {
                                    myFireBaseRef.child("TemporaryEvents").child(id).removeValue();
                                }
                            }
                        }
                    });
                }

                for (String passedID: passedEventID) {
                    myFireBaseRef.child("TemporaryEvents").child(passedID).removeValue();
                }
                callback.onTemporaryEvents(myEventEBDDs);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //Récupère un événement d'un groupe
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //Ajoute les param d'un utilisateur
    @Override
    public void addUserParam(UserParamsEBDD userParamsEBDD, String userID) {
        Firebase paramBD = myFireBaseRef.child("users").child("params").child(userID);
        paramBD.setValue(userParamsEBDD);
    }

    //Récupère les param d'un utilisateur
    //Garantie: lorsque la callback est appelée, on a reçu les données
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

    //Ajoute une notification à un utilisateur ex: invitation, ...
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
