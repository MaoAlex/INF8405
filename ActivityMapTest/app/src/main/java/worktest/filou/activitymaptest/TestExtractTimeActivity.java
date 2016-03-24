package worktest.filou.activitymaptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestExtractTimeActivity extends AppCompatActivity {
    private DisponibilitiesExtractor disponibilitiesExtractor;
    private Button testbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_extract_time);

        List<LocalTimeSlot> l1 = new LinkedList<>();
        List<LocalTimeSlot> l2 = new LinkedList<>();

        for (int h = 14; h < 22; ++h) {
            LocalTimeSlot localTimeSlot = new LocalTimeSlot(h, h+1, "01/02/13");
            localTimeSlot.setId("0");
            l1.add(localTimeSlot);
        }

        for (int h = 8; h < 15; ++h) {
            LocalTimeSlot localTimeSlot = new LocalTimeSlot(h, h+1, "01/02/13");
            localTimeSlot.setId("1");
            l2.add(localTimeSlot);
        }

        for (int h = 16; h < 18; ++h) {
            LocalTimeSlot localTimeSlot = new LocalTimeSlot(h, h+1, "02/02/13");
            localTimeSlot.setId("0");
            l1.add(localTimeSlot);
        }

        for (int h = 8; h < 13; ++h) {
            LocalTimeSlot localTimeSlot = new LocalTimeSlot(h, h+1, "02/02/13");
            localTimeSlot.setId("1");
            l2.add(localTimeSlot);
        }

        List<List<LocalTimeSlot>> listsTest = new LinkedList<>();
        listsTest.add(l1);
        listsTest.add(l2);

        disponibilitiesExtractor = new DisponibilitiesExtractor(listsTest);

        testbtn = (Button) findViewById(R.id.time_extractor_test);
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    private void test() {
        Map<String, List<CustomTimeSlot>> res = disponibilitiesExtractor.extractFreeTimeSlot();
    }
}
