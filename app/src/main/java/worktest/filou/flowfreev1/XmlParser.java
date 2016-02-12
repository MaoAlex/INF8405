package worktest.filou.flowfreev1;

/**
 * Created by filou on 05/02/16.
 */

import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class XmlParser {
    private static final String TAG = "xmlParser";
    private static final String ns = null;

    private void gotoText(XmlResourceParser xmlResourceParser)throws XmlPullParserException, IOException {
        while (xmlResourceParser.next() != XmlResourceParser.TEXT);
    }

    private void gotoEnd(XmlResourceParser xmlResourceParser)throws XmlPullParserException, IOException {
        while (xmlResourceParser.next() != XmlResourceParser.END_TAG);
    }

    public LevelsBySize parse(XmlResourceParser xmlParser) throws XmlPullParserException, IOException {
        LevelsBySize levelsBySize = null;
        try {
            //skip version
            xmlParser.next();
            //skip listOfLevels tag
            xmlParser.next();
            levelsBySize = readFeed(xmlParser);
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            xmlParser.close();
        }

        return levelsBySize;
    }

    private LevelsBySize readFeed(XmlResourceParser xmlParser) throws XmlPullParserException, IOException {
       LevelsBySize levelsBySize = new LevelsBySize();

        while (xmlParser.next() != XmlResourceParser.END_DOCUMENT) {
            if (xmlParser.getEventType() != XmlResourceParser.START_TAG) {
                continue;
            }
            String name = xmlParser.getName();
            if (name.matches("levelsOfSize(.*)")) {
                levelsBySize.addLevels(readListOfLevels(xmlParser));
            } else {
                Log.d(TAG, "readFeed: " + name + " doesn't match");
            }
        }
        return levelsBySize;
    }

    private Levels readListOfLevels(XmlResourceParser xmlParser) throws XmlPullParserException, IOException {
        Levels levels = new Levels();
        while (xmlParser.next() != XmlResourceParser.END_TAG) {
            if (xmlParser.getEventType() != XmlResourceParser.START_TAG) {
                continue;
            }
            String name = xmlParser.getName();
            if (name.matches("level")) {
                levels.addLevel(readLevel(xmlParser));
            } else {
                Log.d(TAG, "readListOfLevels: " + name + " doesn't match");
            }
        }
        return levels;
    }

    private Level readLevel(XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        //read the attribut of the current tag : id
        int id = Integer.parseInt(xmlResourceParser.getAttributeValue(0));

        gotoText(xmlResourceParser);
        boolean unlocked = Boolean.parseBoolean(xmlResourceParser.getText());

        gotoText(xmlResourceParser);;
        int width = Integer.parseInt(xmlResourceParser.getText());

        gotoText(xmlResourceParser);
        int height = Integer.parseInt(xmlResourceParser.getText());
        xmlResourceParser.next();
        xmlResourceParser.next();

        Level level = new Level(id, width, height, unlocked);

        String name = xmlResourceParser.getName();
        if (name.matches("listOfComponents")) {
            while (xmlResourceParser.next() != XmlResourceParser.END_TAG) {
                if (xmlResourceParser.getEventType() != XmlResourceParser.START_TAG) {
                    continue;
                }
                String loopName = xmlResourceParser.getName();
                if (loopName.matches("component")) {
                    Delimiter[] delimiters = readListOfComponents(xmlResourceParser);
                    level.addDelimiter(delimiters[0]);
                    level.addDelimiter(delimiters[1]);
                } else {
                    Log.d(TAG, "readLevel: " + loopName + " doesn't match");
                }
            }
        } else {
            Log.d(TAG, "readLevel: " + name + " doesn't match");
        }

        while (!(xmlResourceParser.next() == XmlResourceParser.END_TAG
                && xmlResourceParser.getName().matches("level")))
            gotoEnd(xmlResourceParser);

        return level;
    }

    private Delimiter[] readListOfComponents(XmlResourceParser xmlResourceParser) throws XmlPullParserException, IOException {
        Delimiter[] delimiters = new Delimiter[2];
        gotoText(xmlResourceParser);
        String color = xmlResourceParser.getText();

        for (int k=0; k<2; k++) {
            gotoText(xmlResourceParser);;
            int i = Integer.parseInt(xmlResourceParser.getText());
            gotoText(xmlResourceParser);
            int j = Integer.parseInt(xmlResourceParser.getText());

             delimiters[k] = new Delimiter(color, i, j);
        }

        while (!(xmlResourceParser.next() == XmlResourceParser.END_TAG
                && xmlResourceParser.getName().matches("component")))
            gotoEnd(xmlResourceParser);

        return delimiters;
    }
}
