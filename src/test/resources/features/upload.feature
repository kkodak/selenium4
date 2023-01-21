Feature: Upload of files
Scenario: Upload file into input

Given Open "https://the-internet.herokuapp.com/upload"
When Upload file "pexels-photo-323705.jpeg"
Then Message that "pexels-photo-323705.jpeg" file has been upload appears