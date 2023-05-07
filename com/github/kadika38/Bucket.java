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

        getKeyValStrings(json, a);

        return a;
    }

    // Recursively finds the key value strings in JSON and adds them to a passed in ArrayList
    private void getKeyValStrings(String json, ArrayList<String> a) {
        // marker1 is the location of first " in the string
        Integer marker1 = null;
        // marker2 is the location of the end of this key value string (either an , or an })
        Integer marker2 = null;
        // marker2type is true if there is more json, and false if the final } was marked
        Boolean marker2type = null;
        // depth is the current depth within inner objects (i.e. {}'s) of the iterator
        int depth = 0;
        // self explanatory variable
        boolean iteratingWithinAString = false;

        for (int i = 0; i < json.length(); i++) {
            if (marker1 == null) {
                if ('"' == json.charAt(i) || '\"' == json.charAt(i)) {
                    marker1 = i;
                    iteratingWithinAString = !iteratingWithinAString;
                }
            } else if (marker2 == null) {
                if ('"' == json.charAt(i) || '\"' == json.charAt(i)) {
                    iteratingWithinAString = !iteratingWithinAString;
                } else if ('{' == json.charAt(i)) {
                    depth++;
                } else if ('}' == json.charAt(i)) {
                    depth--;
                    if (depth < 0) {
                        marker2 = i;
                        marker2type = false;
                    }
                } else if (',' == json.charAt(i) && depth == 0 && !iteratingWithinAString) {
                    marker2 = i;
                    marker2type = true;
                }
            } else if (marker1 != null && marker2 != null) {
                a.add(json.substring(marker1, marker2));
                if (marker2type) {
                    getKeyValStrings(json.substring(marker2, json.length()-1), a);
                } else {
                    return;
                }
            }
        }

    }
}
