Todo:
    - buildArrayListFrom()
    - mark where to throw errors

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

private addKeyFrom(string s) {
    find first "
        mark that spot
    find next "
        mark that spot
    
    this.keys.add(string from marker1 to marker 2, uninclusive)
}

private addValFrom(string s) {
    find second "
    find : after that
    discover data type:
        options are string, int, boolean, array
        if first char is "
            string
        if first car is number
            int
        if first char is t or f
            boolean
        if first char is [
            array
        if first char is {
            object

    switch statement based on data type
    case string:
        mark first "
        mark second "
        this.values.add(string from marker1 to marker2 uninclusive)
        break;
    case int:
        mark first number location
        find final number location
        convert string from marker1 to marker2 to int
        this.values.add(that int)
        break;
    case boolean:
        if first char is t
            this.values.add(true)
        if first char is f
            this.values.add(false)
        else throw error
        break;
    case array:
        mark [
        keep iterating
        mark ]
        new arraylist = buildArrayListFrom(string from marker1 to marker2 inclusive)
        this.values.add(that array list ^)
        break;
    case object:
        mark first {
        keep iterating
        keep track of {}'s
        find this objects }
        mark that spot
        new Bucket (string from marker 1 to marker 2)
        this.values.add(that new bucket)
        break;
}