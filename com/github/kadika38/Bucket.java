package com.github.kadika38;

import java.util.ArrayList;

public class Bucket {
    ArrayList<String> keys;
    ArrayList<Object> values;

    public Bucket(String json) {
        keys = new ArrayList<String>();
        values = new ArrayList<Object>();

        ArrayList<String> splitJson = splitJson(json);

        for (String s : splitJson) {
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
                if ('"' == json.charAt(i)) {
                    marker1 = i;
                    iteratingWithinAString = !iteratingWithinAString;
                }
            } else if (marker2 == null) {
                if ('"' == json.charAt(i)) {
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

    // Finds the key name within the string passed to it; assumes that the first set of characters surrounded by "" is the key name
    private void addKeyFrom(String s) {
        // marker1 is the " that begins the key name
        Integer marker1 = null;
        // marker2 is the " that ends the key name
        Integer marker2 = null;

        for (int i = 0; i < s.length(); i++) {
            if (marker1 == null) {
                if ('"' == s.charAt(i)) {
                    marker1 = i;
                }
            } else if (marker2 == null) {
                if ('"' == s.charAt(i)) {
                    marker2 = i;
                }
            } else if (marker1 != null && marker2 != null) {
                break;
            }
        }

        if (marker1 == null || marker2 == null) {
            throw new Error("Invalid JSON!  Opening and/or closing \" not found while looking for key.");
        }

        this.keys.add(s.substring(marker1+1, marker2));
    }

    private void addValFrom(String s) {
        // marker1 is the first : found outside of a string; this should come right after the key name
        Integer marker1 = null;
        // self explanatory variable
        boolean iteratingWithinAString = false;
        // valType is the data type of the value we find - used for a switch statement.  1 == string | 2 == int | 3 == boolean | 4 == array | 5 == object
        Integer valType = null;
        // marker2 is the location of the start of the value
        Integer marker2 = null;

        for (int i = 0; i < s.length(); i++) {
            if (marker1 == null) {
                if ('"' == s.charAt(i)) {
                    iteratingWithinAString = !iteratingWithinAString;
                }
                if (!iteratingWithinAString && ':' == s.charAt(i)) {
                    marker1 = i;
                }
            } else if (valType == null) {
                if (' ' == s.charAt(i) || '\n' == s.charAt(i)) {
                    continue;
                } else if ('"' == s.charAt(i)) {
                    valType = 1;
                    marker2 = i;
                } else if (Character.isDigit(s.charAt(i))) {
                    valType = 2;
                    marker2 = i;
                } else if ('t' == s.charAt(i) || 'f' == s.charAt(i)) {
                    valType = 3;
                    marker2 = i;
                } else if ('[' == s.charAt(i)) {
                    valType = 4;
                    marker2 = i;
                } else if ('{' == s.charAt(i)) {
                    valType = 5;
                    marker2 = i;
                }
            } else if (valType != null) {
                break;
            }
        }

        switch (valType) {
            // case string
            case 1: 
                Integer stringValEnd = null;
                for (int i = marker2+1; i < s.length(); i++) {
                    if ('"' == s.charAt(i)) {
                        stringValEnd = i;
                        break;
                    }
                }
                if (stringValEnd == null) {
                    throw new Error("Invalid JSON!  Error found while looking for string value.");
                }
                this.values.add(s.substring(marker2+1, stringValEnd));
                break;

            // case int
            case 2:
                Integer intValEnd = null;
                for (int i = marker2+1; i < s.length(); i++) {
                    if (Character.isDigit(s.charAt(i))) {
                        intValEnd = i;
                        continue;
                    } else if (!Character.isDigit(s.charAt(i))) {
                        break;
                    }
                }
                if (intValEnd == null) {
                    throw new Error("Invalid JSON!  Error found while looking for integer value.");
                }
                try {
                    this.values.add(Integer.parseInt(s.substring(marker2, intValEnd+1)));
                } catch (NumberFormatException e) {
                    throw new Error("Error while converting string to integer!");
                }
                break;

            // case boolean
            case 3:
                if ('t' == s.charAt(marker2) && 'r' == s.charAt(marker2+1) && 'u' == s.charAt(marker2+2) && 'e' == s.charAt(marker2+3)) {
                    this.values.add(true);
                } else if ('f' == s.charAt(marker2) && 'a' == s.charAt(marker2+1) && 'l' == s.charAt(marker2+2) && 's' == s.charAt(marker2+3) && 'e' == s.charAt(marker2+4)) {
                    this.values.add(false);
                } else {
                    throw new Error("Invalid JSON! Error while double checking true/false value.");
                }
                break;

            // case array
            case 4:
                Integer arrayEndVal = null;
                for (int i = marker2+1; i < s.length(); i++) {
                    if (']' == s.charAt(i)) {
                        arrayEndVal = i;
                        break;
                    }
                }
                if (arrayEndVal == null) {
                    throw new Error("Invalid JSON! Closing ] never found while reading array value.");
                }
                ArrayList<Object> a = new ArrayList<Object>();
                if (marker2+1 == arrayEndVal) {
                    // empty array
                    this.values.add(a);
                    break;
                }
                buildArrayListFrom(s.substring(marker2+1, arrayEndVal+1));
                this.values.add(a);
                break;

            // case object
            case 5:
                // depth is the current depth within inner objects (i.e. {}'s) of the iterator
                int depth = 0;
                
                Integer objectEndVal = null;

                for (int i = marker2+1; i < s.length(); i++) {
                    if ('{' == s.charAt(i)) {
                        depth++;
                    } else if ('}' == s.charAt(i)) {
                        depth--;
                        if (depth < 0) {
                            objectEndVal = i;
                            break;
                        }
                    }
                }
                if (objectEndVal == null) {
                    throw new Error("Invalid JSON! Closing } never found while reading object value.");
                }

                Bucket innerBucket = new Bucket(s.substring(marker1, objectEndVal+1));
                this.values.add(innerBucket);
                break;

            default:
                throw new Error("Value type error.");
        }
    }
}
