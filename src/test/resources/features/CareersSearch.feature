Feature: Careers Search

  Scenario: Search for automation tester jobs
    Given I open the risk.lexisnexis website
    And I accept cookies
    When I navigate to the Careers page
    Then I should see the Careers page
    When I click on Search jobs
    And I search for "automation tester"
    Then I should see the exact job title "automation tester" in the search results
