Project: uniCenta oPOS v3.60
Topic:	Locales
Author:	Jack Gerrard
Date: 	5th April 2014

Acknowledgement: The content of the language/Locale files are compiled from the efforts of
Openbravo, Openbravo POS Community Members and uniCenta, and we appreciate and 
acknowledge everyone who has contributed to making this distribution possible.

All files, including these Locale files, are made available under the GPL v3 License and within those terms must be passed on to any person who requests them
*******************************************************************************
Please refer to Locales Guide for installation details.

uniCenta oPOS supports 15 languages.

English UK is the default language set.

Not all locales are completely translated

The locale files shipped with this version of uniCenta oPOS are the latest at the date of this release.

Latest locales can be found here: www.unicenta.com/downloads

Application Locales (Default):
beans_messages.properties is the Java generic message file
data_messages.properties is relevant to the data interface 
erp_messages.properties - no longer used (ex-Openbravo POS)
pos_messages.properties is the MAIN application label and Messages file

Reports:
Each report has a .properties file associated with it.
You will find the files in the uniCenta oPOS installation folder in the
\reports\com\openbravo\reports

HOW TO CHANGE YOUR LANGUAGE
1. Copy the locale you need from the language sub-folder \locales\languagename
   into the \unicentaopos\locales parent folder
2. Copy the content of reports folder from the language sub-folder
   \locales\languagename\reports folder into the \unicentaopos\reports folder 
3. Set the uniCenta oPOS Configuration>Locale to your required language