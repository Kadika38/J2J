package com.github.kadika38;

import java.util.ArrayList;

public class Bucket {
    ArrayList<String> keys;
    ArrayList<Object> values;

    public Bucket(String json) {
        keys = new ArrayList<String>();
        values = new ArrayList<Object>();

        ArrayList<String> splitJson = splitJson(json);

        for (String string : splitJson) {
            addKeyFrom(s);
            addValFrom(s);
        }
    }
}
