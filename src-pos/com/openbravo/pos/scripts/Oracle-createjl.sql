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

-- Database updater
-- JDL 
CREATE TABLE APPJL (
    ID VARCHAR2(256) NOT NULL,
    NAME VARCHAR2(1024) NOT NULL,
    VERSION VARCHAR2(1024) NOT NULL,
    PRIMARY KEY (ID)
  );

-- Add new resource file
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('56', 'Printer.TicketClose', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketClose.txt});


-- PRODUCT
ALTER TABLE PRODUCTS ADD COLUMN ISVPRICE  NUMERIC(1) DEFAULT 0 NOT NULL;
ALTER TABLE PRODUCTS ADD COLUMN ISVERPATRIB  NUMERIC(1) DEFAULT 0 NOT NULL;
ALTER TABLE PRODUCTS ADD COLUMN TEXTTIP VARCHAR(256) DEFAULT '';
ALTER TABLE PRODUCTS ADD COLUMN WARRANTY  NUMERIC(1) DEFAULT 0 NOT NULL;
ALTER TABLE CATEGORIES ADD COLUMN TEXTTIP VARCHAR(256) DEFAULT '';
ALTER TABLE CATEGORIES ADD COLUMN CATSHOWNAME NUMERIC(1) DEFAULT 1 NOT NULL;
ALTER TABLE PLACES ADD COLUMN CUSTOMER VARCHAR(256);
ALTER TABLE PLACES ADD COLUMN WAITER VARCHAR(256);
ALTER TABLE PLACES ADD COLUMN TICKETID VARCHAR(256);
ALTER TABLE PLACES ADD COLUMN TABLEMOVED  NUMERIC(1) DEFAULT 0 NOT NULL;
ALTER TABLE SHAREDTICKETS ADD COLUMN PICKUPID INTEGER DEFAULT 0 NOT NULL;
ALTER TABLE STOCKDIARY ADD COLUMN AppUser VARCHAR(256);


CREATE TABLE MOORERS (
  VESSELNAME VARCHAR(256),
  SIZE INTEGER,
  DAYS INTEGER,
  POWER NUMERIC(1) DEFAULT 0 NOT NULL
  );


CREATE SEQUENCE PICKUP_NUMBER START WITH 1;

CREATE TABLE CSVIMPORT(
  ID VARCHAR2(256) NOT NULL,
  ROWNUMBER VARCHAR2(256),
  CSVERROR VARCHAR2(1024),
  REFERENCE VARCHAR2(1024),
  CODE VARCHAR2(1024),
  NAME VARCHAR2(1024),
  PRICEBUY double precision,
  PRICESELL double precision,
  PREVIOUSBUY double precision,
  PREVIOUSSELL double precision,
  PRIMARY KEY (ID)
);

INSERT INTO APPJL (ID, NAME, VERSION) VALUES($APP_ID{}, $APP_NAME{}, $APP_VERSION{});