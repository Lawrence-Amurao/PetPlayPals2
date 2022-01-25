# Brittany's ChangeLog
Check here for changes I've made to the project while working independently. I'll try to keep everyone updated in Slack as well, but I'm going to keep track of those changes here as well so that nothing gets missed when we backread.

## Completed Changes
~~* Changed "Date" to "LocalDate" across the project.
  * Added "if" statements to LocalDate fields to avoid null pointer exception. We shouldn't need those since date is not optional anywhere it's used, but just in case.~~

## Intended Changes
* Adding a separate field for start time to make sure that this info serializes and deserializes correctly. I've been warned not to use "LocalDateTime" because it's apparently a witch and a half.
  * This will mean adding a separate field in the database as well, I think, unless we...read it in as a string and then split the value into date and time? I guess we could do that.
  * **NOTE** : Okay, I really think we need some help on this one, as most of the examples I'm finding are a little to advanced for me to actually read, and I'm getting the sim "tense" moodlet while doing so.
* Testing existing endpoints in Postman to clean up any SQL errors.
* Reading about how to convert date/time information to UTC when sending to database and back to local when reading out. **Lauren, we might need Kyle's help with that one.**

## Front End Tasks
* Once the above is completed, I'm going to start building out the components for the front-end portion of what we've already got completed.


# "Required" Reading
There are some requirements I feel a little fuzzy on, like handling authorization on the front end. I'm leaving a header in this document for those so that we can keep track of stuff we want to read up on and share links. Put your name next to any topics you add here so we know who's struggling in those areas and can try to get everyone on the same page.

* Authorization -- How it interacts from front to back **(Brittany)**
  * How in the actual heck do we pass the token back to say "yes, this is authorized"? I know this is referenced in the lecture code somewhere, so it should be easy to find out, at least.
* Deserializing LocalDate **(Brittany)**
  * Seriously, why is it so obnoxious?