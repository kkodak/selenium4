Feature: New Window
  Scenario: Open website with cookies popup, close popup. Check that popup has not appeared in new window

    Given Open "https://www.drakecircus.com/"
    When Close cookie popup
    When Open "https://www.drakecircus.com/" in new window
    Then Cookie popup is not shown