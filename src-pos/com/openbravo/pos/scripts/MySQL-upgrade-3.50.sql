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
-- v3.50 - v3.55
--

--
-- UPDATE existing tables
--
UPDATE ROLES SET PERMISSIONS = $FILE{/com/openbravo/pos/templates/Role.Administrator.xml} WHERE ID = '0';

UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Menu.Root.txt} WHERE ID = '0';
UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Printer.CloseCash.xml} WHERE ID = '25';
UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid.XML} WHERE ID = '26';
UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid2.xml} WHERE ID = '27';
UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Printer.PartialCash.xml} WHERE ID = '31';
UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Printer.Ticket.xml} WHERE ID = '33';
UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Printer.Ticket2.xml} WHERE ID = '34';
UPDATE RESOURCES SET CONTENT = $FILE{/com/openbravo/pos/templates/Printer.TicketPreview.xml} WHERE ID = '37';

--
-- ALTER existing tables
--
ALTER TABLE CSVIMPORT MODIFY PRICEBUY DOUBLE DEFAULT NULL;
ALTER TABLE CSVIMPORT MODIFY PRICESELL DOUBLE DEFAULT NULL;
ALTER TABLE CSVIMPORT MODIFY PREVIOUSBUY DOUBLE DEFAULT NULL;
ALTER TABLE CSVIMPORT MODIFY PREVIOUSSELL DOUBLE DEFAULT NULL;

ALTER TABLE PAYMENTS MODIFY TENDERED DOUBLE DEFAULT NULL;
ALTER TABLE PAYMENTS ADD COLUMN CARDNAME VARCHAR(255);
UPDATE PAYMENTS SET TENDERED = TOTAL WHERE TENDERED = 0;

ALTER TABLE PRODUCTS MODIFY STOCKCOST DOUBLE DEFAULT NULL;
ALTER TABLE PRODUCTS MODIFY STOCKVOLUME DOUBLE DEFAULT NULL;

ALTER TABLE STOCKCURRENT MODIFY UNITS DOUBLE DEFAULT NULL;

ALTER TABLE STOCKDIARY MODIFY UNITS DOUBLE DEFAULT NULL;
ALTER TABLE STOCKDIARY MODIFY PRICE DOUBLE DEFAULT NULL;

ALTER TABLE STOCKLEVEL MODIFY STOCKSECURITY DOUBLE DEFAULT NULL;
ALTER TABLE STOCKLEVEL MODIFY STOCKMAXIMUM DOUBLE DEFAULT NULL;

ALTER TABLE TICKETLINES MODIFY UNITS DOUBLE DEFAULT NULL;
ALTER TABLE TICKETLINES MODIFY PRICE DOUBLE DEFAULT NULL;

-- UPDATE App' version
UPDATE APPLICATIONS SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};

-- final script
DELETE FROM SHAREDTICKETS;