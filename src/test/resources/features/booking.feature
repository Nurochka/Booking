Feature: Search on booking.com
  Scenario Outline: Search by city criteria
    Given User is looking for hotel in 'London' city
    When User does search
    Then Hotel "<Hotel>" should be on the first page
    And Hotel "<Hotel>" rating is "<Rating>"
    Examples:
      |Hotel  | Rating |
      |Royal Lancaster London|9,0|
