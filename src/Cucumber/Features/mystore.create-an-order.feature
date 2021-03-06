Feature: Create and order

  Scenario Outline: user can create a new order
    Given user is logged in My Store
    And user is on "Hummingbird Printed Sweater" product page
    And discount for the product is 20%
    When user selects size "<size>"
    And user selects quantity "<quantity>"
    And user adds the product to cart
    Then product with quantity "<quantity>" have been successfully added to cart
    And user sees products has been added message "Product successfully added to your shopping cart"
    And price is correctly calculated based on quantity "<quantity>"
    When user proceeds to checkout
    And confirms the address
    And confirms the shipping method
    And selects the payment methods
    And confirms the order
    Then user sees order confirmation message "YOUR ORDER IS CONFIRMED"
    When user goes to order history and details page
    Then the created order is on top of the list with status "Awaiting check payment"
    And closes the browser

    Examples:
      | size | quantity |
      | M    | 5        |

    #Czy zrobienie screenshota ma być stepem?

