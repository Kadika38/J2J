# Project JSONBucket
JSON to Java Object converter.

<a name="Description"></a>

## Description

JSONBucket is a class that takes JSON as input and creates a Java Object of the type JSONBucket that makes the JSON data easily accessible within a Java environment.  There are Java packages out there that do this quite well, such as Jackson, but I wanted to try and build a simple one myself.

# Table of Contents

- [Description](#Description)

- [Usage](#Usage)

- [How It Works](#How-It-Works)

- [Notes](#Notes)

- [Contact](#Contact)

<br>

<a name="Usage"></a>

## Usage Information

Below I give a short description of the public methods available to users.

Constructor
* Takes in a String "json"
* Instantiates a JSONBucket from the JSON input
* The input string will go through a validation process to check that it is valid JSON - an error will be thrown if it fails at any point

getOriginalJSON
* No input
* Returns the original JSON that was passed to the constructor

getValue
* Takes a String "key" as input
* Returns the Object value associated with the input key
* Throws an error if the key is not found

containsKey
* Takes a String "key"
* Returns true if the key is present in the JSONBucket's keys
* Returns false otherwise

put
* Takes a String "key" and an Object "value"
* Adds this key/value pair to the JSONBucket

remove
* Takes a String "key"
* Removes the key from the JSONBucket's keys, and removes the associated value
* Does nothing if the key was not present in the JSONBucket's keys

toString
* @Override
* No input
* Builds a String with the structure of JSON from the JSONBucket's key/value pairs, and returns it

print
* No input
* Simply prints the String representation of the JSONBucket (given by toString) to the terminal

<br>

<a name="How-It-Works"></a>

## How It Works

The JSONBucket constructor takes a String as input.  It saves this String for use in the getOriginalJSON public method.  It instiates two ArrayLists: keys - String type, and values - Object type.  It then calls the private method splitJson on the input JSON String.  splitJson performs a simple validation to check that the String includes a beginning { and a closing }.  It ignores white space for this validation.  It will throw an error if this validation fails, thus cancelling the constructor.  Upon succeeding validation, an String type ArrayList is instantiated and the private method getKeyValStrings is called with the JSON String and this ArrayList as input.  The method getKeyValStrings recursively finds the first key value pair within the String that is passed into it, and adds it to the ArrayList that was passed to it.  Once it has gone through the entire JSON String, the ArrayList has been populated with key/value Strings.  splitJson returns this newly populated ArrayList to the constructor.  The constructor then iterates through this ArrayList and calls the private methods addKeyFrom and addValFrom on each key/value String.  The addKeyFrom method finds the first String within the String passed to it and assumes this is the key, and adds it to this JSONBucket's keys ArrayList.  The addValFrom method finds the value portion of the String passed to it and adds it to this JSONBucket's values ArrayList.  This one is a little more complex.  It keeps track of whether it is iterating through Strings, Arrays, and Objects, and notes te type of value that it finds.  When it finds an Array value, it builds a new ArrayList to add to the JSONBucket's values because they are easier to work with than simple Arrays.  This uses the private method buildArrayListFrom, which recursively iterates through the String value it is given to build an ArrayList.  When addValFrom finds an Object type value, it calls the JSONBucket constructor on the String that represents that Object.  Thus just like Objects can be nested in the JSON, the JSONBucket may have nested JSONBuckets in it's values ArrayList.  At the end of this process, the JSONBucket has been constructed.

<br>

<a name="Notes"></a>

## Notes

* This repository is only meant to be a project for my portfolio - an example of my abilities with Java.

* planning.md file is where I leave notes for myself for later.  It is updated regularly when I am working in this repository.  Just because I have something in the todo list does not mean I am guranteed to come back and actually do it, I may have more important projects.  If you wish to see my planning process, the commit history for that file may be of interest to you.  When I begin my projects, the planning.md file is often quite built out, and as the code is actually written, the file morphs into a basic todo list.

* App.java is there for testing purposes and to declutter the actual JSONBucket file.  It holds the main method where I test things as I work.  Right now it uses a Nasa API to get JSON that I used for testing the JSONBucket class.  There are simpler ways to get JSON for testing, but that entertained me so it is what it is and it's there to stay for a while.

<br>

<a name="Contact"></a>

## Contact

Interested in contacting me?<br>
My email is dryan10101@gmail.com<br>
My LinkedIn is https://www.linkedin.com/in/david-ryan-305860183