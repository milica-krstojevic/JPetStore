JpetStore
Version: 1.0.0

JpetStore is used for testing functionalities
Target application URL is: https://petstore.octoperf.com/.
Target browser: Chrome
Browser version: Version 80.0.3987.149

Run test suite: testng.xml Total tests run: 11

Used libraries:

TestNG automation testing framework
Selenium portable framework for testing web applications
Apache POI library for manipulating various file formats
JavaDoc is located in doc folder.

The following website functionalities are tested:

entering the site
access to menu's links (siderbar, main, nav, sign in)
all links validation
register
sign in
adding items to cart
empty cart by deleting cookies
total sum 


This project contains 3 packages:
pages
tests
utils

All packages are in the src folder.

Package pages contains 6 classes:
HomePage
PetStoreMenuPage
RegistrationPage
SignInPage
StoreItemPage
CartPage

Package tests contains 5 classes:
HomePageTest
PetStoreTest
RegistrationTest
SignInTest
CartTest


