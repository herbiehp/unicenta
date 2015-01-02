--    uniCenta oPOS - Touch Friendly Point Of Sale
--    Copyright (C) 2009-2014 uniCenta
--    http://www.unicenta.net

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

-- Database upgrade script for HSQLDB
-- v3.1Beta - v3.01

UPDATE ROLES SET PERMISSIONS = $FILE{/com/openbravo/pos/templates/Role.Administrator.xml} WHERE ID='0';
UPDATE ROLES SET PERMISSIONS = $FILE{/com/openbravo/pos/templates/Role.Manager.xml} WHERE ID='1';
UPDATE ROLES SET PERMISSIONS = $FILE{/com/openbravo/pos/templates/Role.Employee.xml} WHERE ID='2';
UPDATE ROLES SET PERMISSIONS = $FILE{/com/openbravo/pos/templates/Role.Guest.xml} WHERE ID='3';

DROP TABLE RESOURCES;
CREATE TABLE RESOURCES (
    ID VARCHAR(255) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    RESTYPE INTEGER NOT NULL,
    CONTENT VARBINARY(2),
    PRIMARY KEY (ID)
);
CREATE UNIQUE INDEX RESOURCES_NAME_INX ON RESOURCES(NAME);

INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('0', 'Menu.Root', 0, $FILE{/com/openbravo/pos/templates/Menu.Root.txt});

INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('1', 'coin.2', 1, $FILE{/com/openbravo/pos/templates/coin.2.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('2', 'coin.1', 1, $FILE{/com/openbravo/pos/templates/coin.1.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('3', 'coin.50', 1, $FILE{/com/openbravo/pos/templates/coin.50.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('4', 'coin.20', 1, $FILE{/com/openbravo/pos/templates/coin.20.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('5', 'coin.10', 1, $FILE{/com/openbravo/pos/templates/coin.10.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('6', 'coin.05', 1, $FILE{/com/openbravo/pos/templates/coin.05.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('7', 'coin.02', 1, $FILE{/com/openbravo/pos/templates/coin.02.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('8', 'coin.01', 1, $FILE{/com/openbravo/pos/templates/coin.01.png});

INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('9', 'img.cash', 1, $FILE{/com/openbravo/pos/templates/img.cash.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('10', 'img.cashdrawer', 1, $FILE{/com/openbravo/pos/templates/img.cashdrawer.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('11', 'img.discount', 1, $FILE{/com/openbravo/pos/templates/img.discount.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('12', 'img.empty', 1, $FILE{/com/openbravo/pos/templates/img.empty.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('13', 'img.heart', 1, $FILE{/com/openbravo/pos/templates/img.heart.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('14', 'img.no_photo', 1, $FILE{/com/openbravo/pos/templates/img.no_photo.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('15', 'img.kit_print', 1, $FILE{/com/openbravo/pos/templates/img.kit_print.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('16', 'img.refundit', 1, $FILE{/com/openbravo/pos/templates/img.refundit.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('17', 'img.run_script', 1, $FILE{/com/openbravo/pos/templates/img.run_script.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('18', 'img.ticket_print', 1, $FILE{/com/openbravo/pos/templates/img.ticket_print.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('19', 'img.user', 1, $FILE{/com/openbravo/pos/templates/img.user.png});


INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('20', 'note.50', 1, $FILE{/com/openbravo/pos/templates/note.50.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('21', 'note.20', 1, $FILE{/com/openbravo/pos/templates/note.20.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('22', 'note.10', 1, $FILE{/com/openbravo/pos/templates/note.10.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('23', 'note.5', 1, $FILE{/com/openbravo/pos/templates/note.5.png});

INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('24', 'payment.cash', 0, $FILE{/com/openbravo/pos/templates/payment.cash.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('25', 'Printer.CloseCash', 0, $FILE{/com/openbravo/pos/templates/Printer.CloseCash.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('26', 'Printer.CustomerPaid', 0, $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('27', 'Printer.CustomerPaid2', 0, $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid2.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('28', 'Printer.FiscalTicket', 0, $FILE{/com/openbravo/pos/templates/Printer.FiscalTicket.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('29', 'Printer.Inventory', 0, $FILE{/com/openbravo/pos/templates/Printer.Inventory.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('30', 'Printer.OpenDrawer', 0, $FILE{/com/openbravo/pos/templates/Printer.OpenDrawer.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('31', 'Printer.PartialCash', 0, $FILE{/com/openbravo/pos/templates/Printer.PartialCash.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('32', 'Printer.Start', 0, $FILE{/com/openbravo/pos/templates/Printer.Start.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('33', 'Printer.Ticket', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('34', 'Printer.Ticket2', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket2.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('35', 'Printer.TicketKitchen', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketKitchen.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('36', 'Printer.TicketLine', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketLine.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('37', 'Printer.TicketPreview', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketPreview.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('38', 'Printer.TicketTotal', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketTotal.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('39', 'Printer.Ticket.Logo', 1, $FILE{/com/openbravo/pos/templates/Printer.Ticket.Logo.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('40', 'Ticket.TicketLineTaxesIncluded', 1, $FILE{/com/openbravo/pos/templates/Ticket.TicketLineTaxesIncluded.xml});

INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('41', 'script.Event.Total', 0, $FILE{/com/openbravo/pos/templates/script.Event.Total.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('42', 'script.linediscount', 0, $FILE{/com/openbravo/pos/templates/script.linediscount.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('43', 'script.ReceiptConsolidate', 0, $FILE{/com/openbravo/pos/templates/script.ReceiptConsolidate.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('44', 'script.Refundit', 0, $FILE{/com/openbravo/pos/templates/script.Refundit.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('45', 'script.SendOrder', 0, $FILE{/com/openbravo/pos/templates/script.SendOrder.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('46', 'script.SetPerson', 0, $FILE{/com/openbravo/pos/templates/script.SetPerson.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('47', 'script.StockCurrentAdd', 0, $FILE{/com/openbravo/pos/templates/script.StockCurrentAdd.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('48', 'script.StockCurrentSet', 0, $FILE{/com/openbravo/pos/templates/script.StockCurrentSet.txt});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('49', 'script.totaldiscount', 0, $FILE{/com/openbravo/pos/templates/script.totaldiscount.txt});

INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('50', 'Ticket.Buttons', 0, $FILE{/com/openbravo/pos/templates/Ticket.Buttons.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('51', 'Ticket.Close', 0, $FILE{/com/openbravo/pos/templates/Ticket.Close.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('52', 'Ticket.Discount', 0, $FILE{/com/openbravo/pos/templates/Ticket.Discount.xml});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('53', 'Ticket.Line', 0, $FILE{/com/openbravo/pos/templates/Ticket.Line.xml});

INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('54', 'Window.Logo', 1, $FILE{/com/openbravo/pos/templates/Window.Logo.png});
INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('55', 'Window.Title', 0, $FILE{/com/openbravo/pos/templates/Window.Title.txt});

-- Employee Presence Management
DROP TABLE LEAVES;
DROP TABLE SHIFT_BREAKS;
DROP TABLE SHIFTS;
DROP TABLE BREAKS;

CREATE TABLE SHIFTS (
  ID VARCHAR(255) NOT NULL,
  STARTSHIFT TIMESTAMP NOT NULL,
  ENDSHIFT TIMESTAMP,
  PPLID VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID)
);

INSERT INTO SHIFTS(ID, STARTSHIFT, ENDSHIFT, PPLID) VALUES ('0', '2013-09-01 00:00:00.001', '2013-09-01 00:00:00.002','0');

CREATE TABLE LEAVES (
  ID VARCHAR(255) NOT NULL,
  PPLID VARCHAR(255) NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  STARTDATE TIMESTAMP NOT NULL,
  ENDDATE TIMESTAMP NOT NULL,
  NOTES VARCHAR(255),
  PRIMARY KEY (ID),
  CONSTRAINT lEAVES_PPLID FOREIGN KEY (PPLID) REFERENCES PEOPLE(ID)
);

CREATE TABLE BREAKS (
  ID VARCHAR(255) NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  NOTES VARCHAR(255),
  VISIBLE BOOLEAN NOT NULL,
  PRIMARY KEY (ID)
);

INSERT INTO BREAKS(ID, NAME, VISIBLE, NOTES) VALUES ('0', 'Lunch Break', TRUE, NULL);
INSERT INTO BREAKS(ID, NAME, VISIBLE, NOTES) VALUES ('1', 'Tea Break', TRUE, NULL);
INSERT INTO BREAKS(ID, NAME, VISIBLE, NOTES) VALUES ('2', 'Mid Break', TRUE, NULL);

CREATE TABLE SHIFT_BREAKS (
  ID VARCHAR(255) NOT NULL,
  SHIFTID VARCHAR(255) NOT NULL,
  BREAKID VARCHAR(255) NOT NULL,
  STARTTIME TIMESTAMP NOT NULL,
  ENDTIME TIMESTAMP,
  PRIMARY KEY (ID),
  CONSTRAINT SHIFT_BREAKS_BREAKID FOREIGN KEY (BREAKID) REFERENCES BREAKS(ID),
  CONSTRAINT SHIFT_BREAKS_SHIFTID FOREIGN KEY (SHIFTID) REFERENCES SHIFTS(ID)
);

INSERT INTO SHIFT_BREAKS(ID, SHIFTID, BREAKID, STARTTIME, ENDTIME) VALUES ('0', '0', '0', '2013-09-01 00:00:00.003', '2013-09-01 00:00:00.004');

-- final script
DELETE FROM SHAREDTICKETS;

UPDATE APPLICATIONS SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};