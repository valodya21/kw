--------------------------------------------------------
--  File created - Wednesday-January-27-2016   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USER_LOGIN_TABLE
--------------------------------------------------------

  CREATE TABLE "BATMAN"."USER_LOGIN_TABLE" 
   (	"LOGIN" VARCHAR2(20 BYTE), 
	"PASSWORD" VARCHAR2(32 BYTE), 
	"PERMISION" VARCHAR2(20 BYTE), 
	"ID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table USER_LAB
--------------------------------------------------------

  CREATE TABLE "BATMAN"."USER_LAB" 
   (	"USER_ID" VARCHAR2(20 BYTE), 
	"LAB_ID" VARCHAR2(20 BYTE), 
	"POINT" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table COURSE
--------------------------------------------------------

  CREATE TABLE "BATMAN"."COURSE" 
   (	"COURSE_NAME" VARCHAR2(20 BYTE), 
	"GRUP_ID" VARCHAR2(20 BYTE), 
	"ID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table LABS
--------------------------------------------------------

  CREATE TABLE "BATMAN"."LABS" 
   (	"LABA_NAME" VARCHAR2(20 BYTE), 
	"DEADLINE" VARCHAR2(20 BYTE), 
	"DESCRIPTION" VARCHAR2(1200 BYTE), 
	"COURSE_ID" VARCHAR2(20 BYTE), 
	"LAB_ID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table USER_TABLE
--------------------------------------------------------

  CREATE TABLE "BATMAN"."USER_TABLE" 
   (	"GRUP_ID" VARCHAR2(20 BYTE), 
	"PHONE" VARCHAR2(20 BYTE), 
	"EMAIL" VARCHAR2(20 BYTE), 
	"NAME" VARCHAR2(20 BYTE), 
	"USER_ID" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into BATMAN.USER_LOGIN_TABLE
SET DEFINE OFF;
Insert into BATMAN.USER_LOGIN_TABLE (LOGIN,PASSWORD,PERMISION,ID) values ('admin','21232f297a57a5a743894a0e4a801fc3','admin','1');
Insert into BATMAN.USER_LOGIN_TABLE (LOGIN,PASSWORD,PERMISION,ID) values ('user','ee11cbb19052e40b07aac0ca060c23ee','user','2');
REM INSERTING into BATMAN.USER_LAB
SET DEFINE OFF;
Insert into BATMAN.USER_LAB (USER_ID,LAB_ID,POINT) values ('2','1','4');
Insert into BATMAN.USER_LAB (USER_ID,LAB_ID,POINT) values ('2','2','3');
Insert into BATMAN.USER_LAB (USER_ID,LAB_ID,POINT) values ('2','3','4');
Insert into BATMAN.USER_LAB (USER_ID,LAB_ID,POINT) values ('2','4','5');
Insert into BATMAN.USER_LAB (USER_ID,LAB_ID,POINT) values ('6','4','4');
REM INSERTING into BATMAN.COURSE
SET DEFINE OFF;
Insert into BATMAN.COURSE (COURSE_NAME,GRUP_ID,ID) values ('MATAN','3','1');
Insert into BATMAN.COURSE (COURSE_NAME,GRUP_ID,ID) values ('MATAN','2','1');
Insert into BATMAN.COURSE (COURSE_NAME,GRUP_ID,ID) values ('ProgaC','3','2');
Insert into BATMAN.COURSE (COURSE_NAME,GRUP_ID,ID) values ('ProgaC','2','2');
Insert into BATMAN.COURSE (COURSE_NAME,GRUP_ID,ID) values ('BD','3','3');
REM INSERTING into BATMAN.LABS
SET DEFINE OFF;
Insert into BATMAN.LABS (LABA_NAME,DEADLINE,DESCRIPTION,COURSE_ID,LAB_ID) values ('Lab 1','12.3.2016','Star Wars is an American epic space opera franchise centered on a film series created by George Lucas. The opening crawl is the signature device featured in every film of the series. It opens with the static blue text, "A long time ago in a galaxy far, far away....", followed by the Star Wars logo and the crawl text, which describes the backstory and context of the film. The visuals are accompanied by the "Main Title Theme", composed and conducted by John Williams.','1','1');
Insert into BATMAN.LABS (LABA_NAME,DEADLINE,DESCRIPTION,COURSE_ID,LAB_ID) values ('Lab 2','27.03.2016','The sequence has been featured in every live-action Star Wars film produced by Lucasfilm. Although it retains the basic elements, it has significantly evolved throughout the series. It is one of the most immediately recognizable elements of the franchise and has been frequently parodied.','1','2');
Insert into BATMAN.LABS (LABA_NAME,DEADLINE,DESCRIPTION,COURSE_ID,LAB_ID) values ('Lab 1','21.02.2016','Each film opens with the static blue text, "A long time ago in a galaxy far, far away....", followed by the Star Wars logo shrinking in front of a field of stars. Initially the logo''s extremities are beyond the edge of the frame. While the logo is retreating, the "crawl" text begins, starting with film''s episode number and subtitle (with the exception of the original release of Star Wars – see below), and followed by a three-paragraph prologue to the film. The text scrolls up and away from the bottom of the screen towards a vanishing point above the top of the frame in a perspective projection. Each version of the opening crawl ends with a four-dot ellipsis, except for Return of the Jedi which has a three-dot ellipsis. When the text has nearly reached the vanishing point, it fades out, and the camera tilts down (or, in the case of Episode II: Attack of the Clones, up) and the film begins.[1]','2','3');
Insert into BATMAN.LABS (LABA_NAME,DEADLINE,DESCRIPTION,COURSE_ID,LAB_ID) values ('laba 0','0','Two typefaces are used in the text, both in yellow: News Gothic for the episode number and main body of the text, and Univers Ultra Condensed Light for the title of the film.[2] Several words are in all-capital letters to stress their importance: "DEATH STAR" in A New Hope, "GALACTIC EMPIRE" in Return of the Jedi, "ARMY OF THE REPUBLIC" in Attack of the Clones, and "FIRST ORDER", "REPUBLIC" and "RESISTANCE" in The Force Awakens (which is, until now, the only film in the saga to include more than one phrase in all-capital letters and the only odd-numbered film to have at least a phrase in all-capital letters). Each line of the text spans the width of the screen when it enters from the bottom. In the "fullscreen" (4:3 aspect ratio for standard-definition television) versions of the films, the full lines of text are cut off on the sides until they have scrolled further onto the screen. As a result, by the time the full lines are visible, the text is much smaller and harder to read. In addition, the viewer also has less time to read it.','3','4');
REM INSERTING into BATMAN.USER_TABLE
SET DEFINE OFF;
Insert into BATMAN.USER_TABLE (GRUP_ID,PHONE,EMAIL,NAME,USER_ID) values ('1','none','admin@ad.in','non name','1');
Insert into BATMAN.USER_TABLE (GRUP_ID,PHONE,EMAIL,NAME,USER_ID) values ('2','05038008553','non@e3','user name','2');
