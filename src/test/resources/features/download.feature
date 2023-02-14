Feature: Download of files
Scenario: Download file and check it content

Given Open "https://the-internet.herokuapp.com/download"
When Download file "example.json"
When Read json from file
Then Json contains "email" attribute with value "hello@cypress.io"