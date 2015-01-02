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



-- Add new column to paces table
ALTER TABLE PLACES ADD COLUMN CUSTOMER VARCHAR;
ALTER TABLE PLACES ADD COLUMN WAITER VARCHAR;
ALTER TABLE PLACES ADD COLUMN TICKETID VARCHAR;
ALTER TABLE PLACES ADD COLUMN TABLEMOVED BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE PRODUCTS ADD COLUMN WARRANTY BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE SHAREDTICKETS ADD COLUMN PICKUPID INTEGER NOT NULL DEFAULT 0;
CREATE SEQUENCE PICKUP_NUMBER START WITH 1;
ALTER TABLE STOCKDIARY ADD COLUMN AppUser VARCHAR;

UPDATE APPJL SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};


