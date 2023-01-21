Feature: Interceptor in selenium 4
Scenario: open google.com and intercept some another code

Given selenium intercepts 'Creamy, delicious cheese!' message
When user navigates to google.com
Then user see 'Creamy, delicious cheese!' on page