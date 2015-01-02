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

-- Database upgrade script for MySQL
-- v3.56 - v3.60

--
-- UPDATE existing tables
--

--
-- ALTER existing tables
--
ALTER TABLE CSVIMPORT ADD COLUMN CATEGORY VARCHAR(255) DEFAULT NULL;

UPDATE CUSTOMERS SET CURDEBT = NULL WHERE CURDEBT = 0;
ALTER TABLE CUSTOMERS MODIFY CURDEBT DOUBLE DEFAULT 0 NOT NULL;
--
-- ADD new tables
--
CREATE TABLE LINEREMOVED (
  REMOVEDDATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  NAME varchar(255) DEFAULT NULL,
  TICKETID varchar(255) DEFAULT NULL,
  PRODUCTID varchar(255) DEFAULT NULL,
  PRODUCTNAME varchar(255) DEFAULT NULL,
  UNITS DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- UPDATE App' version
UPDATE APPLICATIONS SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};

-- final script
DELETE FROM SHAREDTICKETS;