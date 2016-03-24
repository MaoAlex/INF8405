package worktest.filou.activitymaptest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by filou on 23/03/16.
 */
public class DisponibilitiesExtractor {
    private Map<String, List<LocalTimeSlot>> dateToTimeslot;
    private Map<String, List<CustomTimeSlot>> dateToDispo;
    private int groupSize;

    public DisponibilitiesExtractor(List<List<LocalTimeSlot>> listTime) {
        groupSize = listTime.size();
        dateToTimeslot = new HashMap<>();
        dateToDispo = new HashMap<>();
        fillDateToTimeSlots(listTime);
        fillDateToDispo();
    }
    
    private void fillDateToTimeSlots(List<List<LocalTimeSlot>> listTime) {
        for (List<LocalTimeSlot> localTimeSlots: listTime) {
            for (LocalTimeSlot localTimeSlot: localTimeSlots) {
                List<LocalTimeSlot> list = dateToTimeslot.get(localTimeSlot.getDate());
                if (list == null) {
                    list = new LinkedList<LocalTimeSlot>();
                    dateToTimeslot.put(localTimeSlot.getDate(), list);
                }
                if (!dateToDispo.containsKey(localTimeSlot.getDate())) {
                    dateToDispo.put(localTimeSlot.getDate(), new LinkedList<CustomTimeSlot>());
                }
                list.add(localTimeSlot);
            }
        }
    }
    
    private void fillDateToDispo() {
        for (String date : dateToDispo.keySet()) {
            List<CustomTimeSlot> dispos = dateToDispo.get(date);
            for (int h = 8; h < 22; ++h) {
                dispos.add(new CustomTimeSlot(h, h + 1, date, groupSize));
            }
        }
    }

    private void colorTime() {
        for (String date : dateToTimeslot.keySet()) {
            List<CustomTimeSlot> disposGeneral = dateToDispo.get(date);
            List<LocalTimeSlot> disposparticular = dateToTimeslot.get(date);
            for (LocalTimeSlot localTimeSlot: disposparticular) {
                for (CustomTimeSlot customTimeSlot: disposGeneral) {
                    if (localTimeSlot.getBeginHour() == customTimeSlot.getBeginHour() &&
                            localTimeSlot.getEndHour() == customTimeSlot.getEndHour()) {
                        if (!customTimeSlot.isIDin(localTimeSlot.getId())) {
                            customTimeSlot.addID(localTimeSlot.getId());
                        }
                    }
                }
            }
        }
    }

    private void filterOK() {
        Map<String, List<CustomTimeSlot>> dateToDispoDef = new HashMap<>();
        for (String date : dateToTimeslot.keySet()) {
            List<CustomTimeSlot> disposGeneral = dateToDispo.get(date);
            for (CustomTimeSlot customTimeSlot: disposGeneral) {
                if (customTimeSlot.isOKforEvery()) {
                    List<CustomTimeSlot> lt = dateToDispoDef.get(date);
                    if (lt == null) {
                        lt = new LinkedList<>();
                        dateToDispoDef.put(date, lt);
                    }
                    lt.add(customTimeSlot);
                }
            }
        }

        dateToDispo = dateToDispoDef;
    }

    public  Map<String, List<CustomTimeSlot>> extractFreeTimeSlot() {
        colorTime();
        filterOK();

        return dateToDispo;
    }
}
