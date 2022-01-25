BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE availability_day (
  id serial,
  day varchar(10) NOT NULL,
  CONSTRAINT PK_availability_day PRIMARY KEY (id)
);

CREATE TABLE availability_time (
  id serial,
  time_block varchar(10) NOT NULL,
  CONSTRAINT PK_availability_time PRIMARY KEY (id)
);

CREATE TABLE user_availability (
  user_id int,
  day_id int,
  time_id int,
  UNIQUE (user_id, day_id, time_id),
  CONSTRAINT FK_user_availability_day
    FOREIGN KEY (day_id) REFERENCES availability_day(id),
  CONSTRAINT FK_user_availability_time
    FOREIGN KEY (time_id) REFERENCES availability_time(id),
  CONSTRAINT FK_user_availability
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE pet (
  id serial,
  pet_name varchar(200) NOT NULL,
  pet_type varchar(50) NOT NULL,
  owner_id int NOT NULL,
  breed varchar(100),
  size varchar(50) NOT NULL,
  energy_level varchar(20),
  age varchar(20) NOT NULL,
  pet_description varchar(1000),
  likes varchar(1000),
  dislikes varchar(1000),
  UNIQUE(pet_name, owner_id),
  CONSTRAINT PK_pet PRIMARY KEY (id),
  CONSTRAINT FK_pet
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

CREATE TABLE playdate (
  id serial,
  owner_id int NOT NULL,
  street_address varchar(200) NOT NULL,
  location_name varchar(200) NOT NULL,
  location_type varchar(100) NOT NULL,
  activity varchar(100),
  event_date date NOT NULL,
  start_time time NOT NULL,
  duration_in_minutes int NOT NULL,
  max_attendees int NOT NULL DEFAULT 1,
  rating int,
  playdate_description varchar(2000),
  CONSTRAINT PK_playdate PRIMARY KEY (id),
  CONSTRAINT FK_playdate
    FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

CREATE TABLE playdate_attendee (
  pet_id int NOT NULL,
  playdate_id int NOT NULL,
  request_status varchar(10),
  UNIQUE(pet_id, playdate_id),
  CONSTRAINT FK_playdate_attendee_playdate
    FOREIGN KEY (playdate_id) REFERENCES playdate(id),
  CONSTRAINT FK_playdate_attendee_pet
    FOREIGN KEY (pet_id) REFERENCES pet(id)
);

CREATE TABLE forum (
  id serial,
  forum_name varchar(100) NOT NULL,
  forum_description varchar(1000),
  CONSTRAINT PK_forum PRIMARY KEY (id)
);

CREATE TABLE topic (
  id serial,
  topic_name varchar(500) NOT NULL,
  created timestamptz DEFAULT CURRENT_TIMESTAMP,
  forum_id int NOT NULL,
  CONSTRAINT PK_topic PRIMARY KEY (id),
  CONSTRAINT FK_topic
    FOREIGN KEY (forum_id) REFERENCES forum(id)
);

CREATE TABLE forum_message (
  id serial,
  topic_id int NOT NULL,
  user_id int NOT NULL,
  time_stamp timestamptz DEFAULT CURRENT_TIMESTAMP,
  message_text varchar(4000) NOT NULL,
  CONSTRAINT PK_message PRIMARY KEY (id),
  CONSTRAINT FK_message_topic
    FOREIGN KEY (topic_id)
      REFERENCES topic(id),
  CONSTRAINT FK_message_user
    FOREIGN KEY (user_id)
      REFERENCES users(user_id)
);



INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

/* Group Generated User Data */
INSERT INTO users (username, password_hash, role) VALUES ('absoluteDadLad', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('imATestUser', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('catMom286', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('amNotRobot1992', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('worldsBestSnarent', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');


/* Group Generated Pet Data */
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (1, 'Foie Gras', 'Duck', 'Duck', 'Small', 'Active', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (1, 'Untitled', 'Goose', 'Canadian Goose', 'Medium', 'Active', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (1, 'Nugget', 'Chicken', 'Silky', 'Small', 'Active', 'Adult');

INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (3, 'Buddy', 'Dog', 'Mixed Breed', 'Medium', 'Athletic', 'Young');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (3, 'Tiger', 'Cat', 'Maine Coon', 'Large', 'Lazy', 'Senior');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (3, 'Sport', 'Rabbit', 'Dutch', 'Large', 'Athletic', 'Adult');

INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (4, 'Ellie', 'Dog', 'Siberian Husky', 'Large', 'Lazy', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (4, 'Eden', 'Dog', 'Mixed Breed', 'Small', 'Active', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (4, 'Tucker', 'Dog', 'Beagle', 'Medium', 'Moderate', 'Senior');

INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (5, 'Wicca', 'Cat', 'Russian Blue', 'Small', 'Moderate', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (5, 'Pepchik', 'Cat', 'American Mediumhair', 'Medium', 'Lazy', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (5, 'Professor John Smallman', 'Cat', 'American Shorthair', 'Medium', 'Active', 'Young');

INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (6, 'Guiness', 'Dog', 'Siberian Husky', 'Large', 'Moderate', 'Senior');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (6, 'Oscar', 'Dog', 'Mixed Breed', 'Medium', 'Lazy', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (6, 'Yugo', 'Dog', 'Mixed Breed', 'Medium', 'Active', 'Adult');

INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (7, 'Lucius', 'Snake', 'Ball Python', 'Small', 'Lazy', 'Adult');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (7, 'Regulus', 'Snake', 'Cube Python', 'Large', 'Lazy', 'Young');
INSERT INTO pet (owner_id, pet_name, pet_type, breed, size, energy_level, age) VALUES (7, 'Tom', 'Snake', 'Dodecahedron Python', 'Itty Bitty', 'Lazy', 'Senior');

/* Group Generated Playdate Data */
INSERT INTO playdate (owner_id, street_address, location_name, location_type, activity, event_date, start_time, duration_in_minutes, max_attendees) VALUES (1, 'Sycamore Street Between Fell and, S Linden St, Normal, IL 61761', 'Hidden Creek Nature Sanctuary', 'Wildlife Sanctuary', 'Bread Feeding', '2021-10-02', '12:00:00', 60, 10);
INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) VALUES (1, 1, 'Main');

INSERT INTO playdate (owner_id, street_address, location_name, location_type, activity, event_date, start_time, duration_in_minutes, max_attendees) VALUES (3, '1850 Gregory St, Normal, IL 61761', 'Champion Fields', 'Baseball Field', 'Bunny Baseball', '2021-10-10', '10:00:00', 60, 9);
INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) VALUES (5, 2, 'Main');

INSERT INTO playdate (owner_id, street_address, location_name, location_type, activity, event_date, start_time, duration_in_minutes, max_attendees) VALUES (4, '1200 Greenbriar Dr, Normal, IL 61761', 'DESTIHL Brewery and Beer Hall', 'Brewery', 'Beer and Snuggles', '2021-10-09', '16:00:00', 120, 8);
INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) VALUES (8, 3, 'Main');

INSERT INTO playdate (owner_id, street_address, location_name, location_type, activity, event_date, start_time, duration_in_minutes, max_attendees) VALUES (5, '126 E Beaufort St, Normal, IL 61761', 'Maggie Mileys', 'Irish Pub', 'Wine and Whiskers', '2021-10-08', '18:00:00', 120, 5);
INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) VALUES (12, 4, 'Main');

INSERT INTO playdate (owner_id, street_address, location_name, location_type, activity, event_date, start_time, duration_in_minutes, max_attendees) VALUES (6, '701 Hershey Rd, Normal, IL 61761', 'Shepard Dog Park', 'Dog Park', 'Flat Earth Meetup and Frisbee Golf', '2021-10-16', '15:00:00', 180, 50);
INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) VALUES (13, 5, 'Main');

INSERT INTO playdate (owner_id, street_address, location_name, location_type, activity, event_date, start_time, duration_in_minutes, max_attendees) VALUES (7, '100 Anaconda Smelter Rd, Anaconda, MT 59711', 'Anaconda Smoke Stack State Park', 'State Park', 'Noodle Race', '2021-10-31', '18:00:00', 60, 3);
INSERT INTO playdate_attendee (pet_id, playdate_id, request_status) VALUES (18, 6, 'Main');

/* Group generated forum data */
INSERT INTO forum (forum_name, forum_description) VALUES ('Pet Food', 'We are talking about food options for any pet.');
INSERT INTO forum (forum_name, forum_description) VALUES ('Pet Toys', 'We are talking about toy options for any pet.');
INSERT INTO forum (forum_name, forum_description) VALUES ('Pet Stories', 'Share all of your funny pet stories.');

/* Group generated topic data */
INSERT INTO topic (topic_name, forum_id) VALUES ('Omnivore Food', 1);
INSERT INTO topic (topic_name, forum_id) VALUES ('Herbivore Food', 1);

INSERT INTO topic (topic_name, forum_id) VALUES ('Are Treats Toys?', 2);
INSERT INTO topic (topic_name, forum_id) VALUES ('The New Fad: Beer Can Plushies', 2);

INSERT INTO topic (topic_name, forum_id) VALUES ('What is the dumbest thing your cat has done?', 3);
INSERT INTO topic (topic_name, forum_id) VALUES ('How do your pets interact with others?', 3);

/* Group generated message data */
INSERT INTO forum_message (topic_id, user_id, message_text) VALUES (1, 4, 'I love Bark Eats for my dogs. They deliver pre portioned food pouches to my door and the food is a kibble mix of meat and vegetables that are great for them!');
INSERT INTO forum_message (topic_id, user_id, message_text) VALUES (2, 3, 'Sport here gets only the best hand chopped lettuce and veggies, prepared while I pack my kids their daily lunches.');

INSERT INTO forum_message (topic_id, user_id, message_text) VALUES (3, 7, 'Do mice count as toys? Because then yes my snakes love their frozen mice treats.');
INSERT INTO forum_message (topic_id, user_id, message_text) VALUES (3, 6, 'lmaoo cutee');

INSERT INTO forum_message (topic_id, user_id, message_text) VALUES (5, 7, 'My snakes are not cats, but they do dumb and silly things when they escape and create "DANGEROUS: POISONOUS SNAKE ON THE LOOSE" headlines. It is just so cute and funny.');
INSERT INTO forum_message (topic_id, user_id, message_text) VALUES (5, 5, 'OMG one of my cats launched himself off of the bed into the blinds because he saw the shadow of a squirrel... the shadow.. not the squirrel. The other typically locks this one into a cabinet.. maybe to keep him away from the squirrel shadows.');
INSERT INTO forum_message (topic_id, user_id, message_text) VALUES (6, 1, 'Considering I have all different types of poultry, they do not interact well with normal animals. Sorry.');

COMMIT TRANSACTION;
