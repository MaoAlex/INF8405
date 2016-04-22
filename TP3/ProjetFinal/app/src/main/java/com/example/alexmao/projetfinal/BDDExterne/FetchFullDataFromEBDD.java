package com.example.alexmao.projetfinal.BDDExterne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filou on 16/04/16.
 */
public class FetchFullDataFromEBDD {
    public static void fetchUser(String userID, RemoteBD remoteBD, final OnFullUserData callback) {
        final int [] nbOpDone = new int[1];
        final int nbOP = 4;
        final LocalUserProfilEBDD profil = new LocalUserProfilEBDD();
        final Position mPosition  = new Position();
        final Picture mPicture = new Picture();
        final UserParamsEBDD mParams = new UserParamsEBDD();

        profil.setDataBaseId(userID);
        remoteBD.getUserProfil(userID, profil, new OnUserProfilReceived() {
            @Override
            public void onUserProfilReceived(UtilisateurProfilEBDD userProfilEBDD) {
                profil.update(userProfilEBDD);
                nbOpDone[0]++;
                if (nbOpDone[0] == nbOP)
                    callback.onFullUserData(profil, mPosition, mPicture, mParams);
            }
        });

        remoteBD.getUserPIc(profil, userID, new OnPictureReceived() {
            @Override
            public void onPictureReceived(Picture picture) {
                mPicture.update(picture);
                nbOpDone[0]++;
                if (nbOpDone[0] == nbOP)
                    callback.onFullUserData(profil, mPosition, mPicture, mParams);
            }
        });

        remoteBD.getUserPosition(userID, new OnPositionReceived() {
            @Override
            public void onPostionReceived(Position position) {
                mPosition.update(position);
                nbOpDone[0]++;
                if (nbOpDone[0] == nbOP)
                    callback.onFullUserData(profil, mPosition, mPicture, mParams);
            }
        });

        remoteBD.getUserParam(userID, new OnUserParamReceived() {
            @Override
            public void onUserParamReceived(UserParamsEBDD userParamsEBDD) {
                mParams.update(userParamsEBDD);
                nbOpDone[0]++;
                if (nbOpDone[0] == nbOP)
                    callback.onFullUserData(profil, mPosition, mPicture, mParams);
            }
        });
    }

    //attention, ne récupére pas l'event ni la conversation
    //il y aura peut etre des pb d'accès concurrent
    public static void fetchGroup(final String groupID, final RemoteBD remoteBD,
                                  final OnFullGroup callback) {
        final int [] nbOpDone = new int[1];
        final int [] sizeWrapper = new int[1];
        final MyLocalGroupEBDD groupEBDD = new MyLocalGroupEBDD();
        groupEBDD.setDatabaseID(groupID);
        final List<FullUserWrapper> mWrappers = new ArrayList<>();
        final ConversationEBDD mConversationEBDD = new ConversationEBDD();
        final MyLocalEventEBDD mEventEBDD = new MyLocalEventEBDD();

        remoteBD.getGroup(groupID, groupEBDD, new OnGroupReceived() {
            @Override
            public void onGroupReceived(MyGroupEBDD myGroupEBDD) {
                groupEBDD.update(myGroupEBDD);
                sizeWrapper[0] = groupEBDD.getMembersID().size();
                for (String id : groupEBDD.getMembersID()) {
                    fetchUser(id, remoteBD, new OnFullUserData() {
                        @Override
                        public void onFullUserData(LocalUserProfilEBDD localUserProfilEBDD,
                                                   Position position, Picture picture,
                                                   UserParamsEBDD params) {
                            final FullUserWrapper wrapper = new FullUserWrapper(localUserProfilEBDD,
                                    position, picture, params);
                            mWrappers.add(wrapper);
                            nbOpDone[0]++;
                            if (nbOpDone[0] == sizeWrapper[0]) {
                                remoteBD.getDiscussion(groupEBDD.getConversationID(),
                                        new OnConversationReceived() {
                                            @Override
                                            public void onConversationRecieved(ConversationEBDD conversationEBDD) {
                                                mConversationEBDD.update(conversationEBDD);
                                                remoteBD.getEventFromGroup(groupID, new OnStringReceived() {
                                                    @Override
                                                    public void onStringReceived(String s) {
                                                        remoteBD.getEventFromGroup(groupID, new OnStringReceived() {
                                                            @Override
                                                            public void onStringReceived(String s) {
                                                                mEventEBDD.setDataBaseId(s);
                                                                remoteBD.getEvent(s, new OnEventReceived() {
                                                                    @Override
                                                                    public void onEventReceived(MyEventEBDD myEventEBDD) {
                                                                        mEventEBDD.update(myEventEBDD);
                                                                        callback.onFullGroup(groupEBDD,
                                                                                mWrappers,
                                                                                mConversationEBDD,
                                                                                mEventEBDD);
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });
    }

    public void fetchallGroups(String userID, final RemoteBD remoteBD, final OnGroupsReady callback) {
        final List<FullGroupWrapper> groupWrappers = new ArrayList<>();
        remoteBD.getGroupsFromUser(userID, new OnIdsreceived() {
            @Override
            public void onIdsreceived(List<String> ids) {
                final int idSize = ids.size();
                for (String groupID: ids) {
                    fetchGroup(groupID, remoteBD, new OnFullGroup() {
                        @Override
                        public void onFullGroup(MyLocalGroupEBDD myLocalGroupEBDD,
                                                List<FullUserWrapper> wrappers,
                                                ConversationEBDD conversationEBDD,
                                                MyLocalEventEBDD myLocalEventEBDD) {
                            FullGroupWrapper groupWrapper = new FullGroupWrapper(conversationEBDD,
                                    myLocalGroupEBDD,
                                    myLocalEventEBDD,
                                    wrappers);
                            groupWrappers.add(groupWrapper);
                            if (groupWrappers.size() == idSize)
                                callback.onGroupsReady(groupWrappers);
                        }
                    });
                }
            }
        });
    }

}
