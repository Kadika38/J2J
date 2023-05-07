Todo:
    - addKeyFrom()
    - addValFrom()

Bucket:

keys
values

public Bucket(string json) {
    arraylist sj = splitJson(json); // this is a list of strings that are the key value pairs within the json

    for each in sj {
        addKeyFrom(keyValString);
        addValFrom(keyValString);
    }
}

private splitJson(string json) {
    check that json starts and ends with { and }
        throw error if not

    instantiate arraylist a

    getKeyValStrings(json, a)
    
    return a;    
}

private getKeyValStrings(string json, arraylist a) {
    find first "
        mark that spot in the string
    find end of this key val pair
        how to do this:
        keep count of {}'s that are not within a string
        find the next , that isn't within a {} OR the next } that would mark the end of the json
        mark that spot
    add string from first marker to second marker (inclusive on the first, noninclusive on the second) to arraylist

    continue iterating through json if final } wasn't found
    if another " is found:
        mark its location
        recursively call getKeyValStrings with a sliced json string from the above marked location to the end of the string, and pass the same arraylist
    
    return;
}