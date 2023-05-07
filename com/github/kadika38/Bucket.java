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

    // Splits the input json into key value strings and returns them all in an ArrayList
    private ArrayList<String> splitJson(String json) {
        //check that json starts with { and ends with }
        boolean check1 = false;
        boolean check2 = false;
        for (int i = 0; i < json.length(); i++) {
            if (' ' == json.charAt(i) || '\n' == json.charAt(i)) {
                continue;
            } else if ('{' == json.charAt(i)) {
                check1 = true;
            } else {
                break;
            }
        }
        for (int i = json.length()-1; i > 0; i--) {
            if (' ' == json.charAt(i) || '\n' == json.charAt(i)) {
                continue;
            } else if ('}' == json.charAt(i)) {
                check1 = true;
            } else {
                break;
            }
        }
        if (!check1) {
            throw new Error("Invalid JSON!  No opening '{' was found.");
        } else if (!check2) {
            throw new Error("Invalid JSON!  No closing '}' was found.");
        }

        // Basic validation has been succeeded, continue splitting the json into key value strings
        ArrayList<String> a = new ArrayList<String>();

        getKeyValueStrings(json, a);

        return a;
    }
}
