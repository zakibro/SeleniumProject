Feature: Create and Delete New Address after login

  Scenario Outline: user can create and delete a new address
    Given user is logged in my store
    And user is on Addresses page
    When user clicks on Create new address button
    And user enters fields with "<alias>", "<company>", "<address>", "<postcode>", "<city>" and "<phone>"
    And user saves information
    Then User sees "Address successfully added!"
    And the address has been correctly added with provided "<alias>", "<company>", "<address>", "<postcode>", "<city>" and "<phone>"
    When user deletes new address
    Then users sees "Address successfully deleted!"
    And address has been deleted
    And quit the browser

    Examples:
      | alias | company | address | postcode | city     | phone     |
      | Pan   | Tesla   | Adres 1 | 1234     | Katowice | 123456789 |
