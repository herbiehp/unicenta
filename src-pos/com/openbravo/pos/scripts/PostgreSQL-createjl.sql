--    uniCenta oPOS - Touch Friendly Point Of Sale
--    Copyright (C) 2009-2014 uniCenta
--    http://www.unicenta.net
--
--    This file is part of uniCenta oPOS.
--
--    uniCenta oPOS is free software: you can redistribute it and/or modify
--    it under the terms of the GNU General Public License as published by
--    the Free Software Foundation, either version 3 of the License, or
--    (at your option) any later version.
--
--    uniCenta oPOS is distributed in the hope that it will be useful,
--    but WITHOUT ANY WARRANTY; without even the implied warranty of
--    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--    GNU General Public License for more details.
--
--    You should have received a copy of the GNU General Public License
--    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

-- Database updater options
-- JDL 

CREATE TABLE APPJL (
    ID VARCHAR NOT NULL,
    NAME VARCHAR NOT NULL,
    VERSION VARCHAR NOT NULL,
    PRIMARY KEY (ID)
  );


-- Add new resource file
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('56', 'Printer.TicketClose', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketClose.xml});


-- PRODUCT
ALTER TABLE PRODUCTS ADD COLUMN ISVPRICE BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE PRODUCTS ADD COLUMN ISVERPATRIB BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE PRODUCTS ADD COLUMN TEXTTIP VARCHAR DEFAULT '';
ALTER TABLE PRODUCTS ADD COLUMN WARRANTY BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE CATEGORIES ADD COLUMN TEXTTIP VARCHAR DEFAULT '';
ALTER TABLE CATEGORIES ADD COLUMN CATSHOWNAME BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE PLACES ADD COLUMN CUSTOMER VARCHAR;
ALTER TABLE PLACES ADD COLUMN WAITER VARCHAR;
ALTER TABLE PLACES ADD COLUMN TICKETID VARCHAR;
ALTER TABLE PLACES ADD COLUMN TABLEMOVED BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE STOCKDIARY ADD COLUMN AppUser VARCHAR;
ALTER TABLE SHAREDTICKETS ADD COLUMN PICKUPID INTEGER NOT NULL DEFAULT 0;



CREATE TABLE MOORERS (
  VESSELNAME VARCHAR,
  SIZE INTEGER,
  DAYS INTEGER,
  POWER BOOLEAN NOT NULL DEFAULT FALSE
  );


CREATE TABLE CSVIMPORT (
  ID VARCHAR NOT NULL,
  ROWNUMBER VARCHAR,
  CSVERROR VARCHAR,
  REFERENCE VARCHAR,
  CODE VARCHAR,
  NAME VARCHAR,
  PRICEBUY double precision,
  PRICESELL double precision,
  PREVIOUSBUY double precision,
  PREVIOUSSELL double precision,
  PRIMARY KEY (ID)
);

CREATE SEQUENCE PICKUP_NUMBER START WITH 1;

INSERT INTO APPJL (ID, NAME, VERSION) VALUES($APP_ID{}, $APP_NAME{}, $APP_VERSION{});