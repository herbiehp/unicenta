03 November 2014
Release: v3.81

v3.80 contains further bug-fixes and Enhancement of v3.70

********* UPGRADING? *********
v3.81 requires v3.80 to be be installed first
v3.80 requires v3.70
If you try to upgrade from an ealier version then you will run into problems.

THIS APPLIES TO UPGRADES ONLY!
***********************************************************

********* KNOWN ISSUES *********
1.  There is a known issue with HSQLDB 2.n driver which does not handle BLOB
    (Categories, Products, Customers, People) correctly
    It causes an error when loading Sales panel - Clicking OK allows you to
    continue seemingly with no adverse effect
    Adding Images to (Categories, Products, Customers, People) forms is not
    affected.
    Suggestion: avoid HSQLDB database for now or revert to older 1.8 driver


***********************************************************

1.  Bug: Ticket numbers incrementing incorrectly on Derby DB
    Fix: Reverted back to Derby driver : 10.10.2
2.  Bug: Error in UpdateRecord for CSV Import
    Fix: Java class updated accordingly.
