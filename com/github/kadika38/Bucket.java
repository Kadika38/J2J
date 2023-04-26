package com.github.kadika38;

import java.util.ArrayList;

public class Bucket {
    ArrayList<String> keys;
    ArrayList<Object> values;

    public Bucket(String json) throws Exception {
        keys = new ArrayList<String>();
        values = new ArrayList<Object>();
        while (json.length() > 0) {
            json = findAndSetNextKeyAndValue(json);
            json = reduce(json);
        }
    }

    private String findAndSetNextKeyAndValue(String s) throws Exception {
        String keyHolder = "";
        Integer step = 1;
        for (int i = 0; i < s.length(); i++) {
            Character current = s.charAt(i);
            switch (step) {
                case 1:
                    if (current == '"') {
                        step++;
                        continue;
                    } else {
                        continue;
                    }
                case 2:
                    if (current != '"') {
                        keyHolder += current;
                        continue;
                    } else {
                        this.keys.add(keyHolder);
                        step++;
                        continue;
                    }
                case 3:
                    s = findAndSetNextValue(s.substring(i));
                    return s;
            }
        }
        throw new Exception();
    }

    private String findAndSetNextValue(String s) throws Exception {
        Integer step = 1;
        for (int i = 0; i < s.length(); i++) {
            Character current = s.charAt(i);
            if (step == 1 && current == ':') {
                step++;
            }
            if (step == 2 && current != ' ') {
                step++;
                i--;
            }
            if (step == 3) {
                int layer = 0;
                int end = 0;
                switch (current) {
                    // object value
                    case '{':
                        layer = 0;
                        end = 0;
                        for (int j = i; j < s.length(); j++) {
                            if (s.charAt(j) == '{') {
                                layer++;
                            }
                            if (s.charAt(j) == '}') {
                                layer--;
                                if (layer == 0) {
                                    end = j;
                                    break;
                                }
                            }
                        }
                        if (end == 0) {
                            throw new Exception();
                        }
                        Bucket bucketValue = new Bucket(s.substring(i, end+1));
                        this.values.add(bucketValue);
                        return s.substring(end+1);
                    // array value
                    case '[':
                        layer = 0;
                        end = 0;
                        for (int j = i; j < s.length(); j++) {
                            if (s.charAt(j) == '[') {
                                layer++;
                            }
                            if (s.charAt(j) == ']') {
                                layer--;
                                if (layer == 0) {
                                    end = j;
                                    break;
                                }
                            }
                        }
                        if (end == 0) {
                            throw new Exception();
                        }
                        this.values.add(createArrayList(s.substring(i, end+1)));
                        return s.substring(end+1);
                    // string value
                    case '"':
                        end = 0;
                        int count = 0;
                        for (int j = i; j < s.length(); j++) {
                            if (s.charAt(j) == '"') {
                                count++;
                            }
                            if (count == 2) {
                                end = j;
                                break;
                            }
                        }
                        if (end == 0) {
                            throw new Exception();
                        }
                        String stringVal = s.substring(i, end+1);
                        this.values.add(stringVal);
                        return s.substring(end+1);
                    // boolean or int value
                    default:
                        if (current == 'f' || current == 't') {
                            // boolean value
                            if (current == 'f') {
                                this.values.add(false);
                                return s.substring(i+5);
                            } else {
                                this.values.add(true);
                                return s.substring(i+4);
                            }
                        } else {
                            // int value
                            end = 0;
                            String intValAsString = "";
                            for (int j = i; j < s.length(); j++) {
                                if (Character.isDigit(s.charAt(j))) {
                                    intValAsString += s.charAt(j);
                                    end++;
                                } else {
                                    break;
                                }
                            }
                            try {
                                Integer intVal = Integer.parseInt(intValAsString);
                                this.values.add(intVal);
                                return s.substring(i+end);
                            } catch (NumberFormatException e) {
                                throw new Exception();
                            }
                        }
                }
            }

        }
    }

    private String reduce(String s) {
        // checks to make sure there are still key values to be found
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"') {
                return s;
            }
        }
        return "";
    }

    public void print() {
        int i = 0;
        for (String key : this.keys) {
            System.out.println(key);
            if (this.values.get(i) instanceof Bucket) {
                this.values.get(i).print();
            } else {
                System.out.println(this.values.get(i));
            }
        }
    }
}
