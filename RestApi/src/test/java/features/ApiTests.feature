Feature: API Testing

  Scenario: Create an object with valid data
    When I send a POST request to "https://api.restful-api.dev/objects" with payload
    """
    {
      "name": "Apple MacBook Pro 16",
      "data": {
          "year": 2019,
          "price": 1849.99,
          "CPU model": "Intel Core i9",
          "Hard disk size": "1 TB"
      }
    }
    """
    Then the response status code should be 200
    And I store the ID from the response and passed into get request to "https://api.restful-api.dev/objects/{id}"
    And the response should contain the following data
      | expectedName              | expectedYear |
      | Apple MacBook Pro 16     | 2019         |




    And I send a PUT request to "https://api.restful-api.dev/objects/{id}" with payload
    """
    {
      "name": "Apple MacBook Pro 16",
      "data": {
          "year": 2019,
          "price": 2049.99,
          "CPU model": "Intel Core i9",
          "Hard disk size": "1 TB",
          "color": "silver"
      }
    }
    """



    And I send a DELETE request to "https://api.restful-api.dev/objects/{id}" with the stored ID



  Scenario: Retrieve an object with invalid ID
    When I send a GET request to "https://api.restful-api.dev/objects/1234"
    Then the response status codes should be 404

  Scenario: Validate response time for the Post request
    When I send a post request to "https://api.restful-api.dev/objects" with payload
    """
    {
      "name": "Apple MacBook Pro 16",
      "data": {
          "year": 2019,
          "price": 1849.99,
          "CPU model": "Intel Core i9",
          "Hard disk size": "1 TB"
      }
    }
    """
    Then the response time should be less than 10000 ms

  Scenario: Create an object with missing name
    When I send a POST request to "https://api.restful-api.dev/objects" with payload
    """
    {
      "data": {
          "year": 2019,
          "price": 1849.99,
          "CPU model": "Intel Core i9",
          "Hard disk size": "1 TB"
      }
    }
    """
    Then the response status code should be 200

  Scenario: Create an object with invalid year
    When I send a POST request to "https://api.restful-api.dev/objects" with payload
    """
    {
      "name": "Apple MacBook Pro 16",
      "data": {
          "year": "invalid_year",
          "price": 1849.99,
          "CPU model": "Intel Core i9",
          "Hard disk size": "1 TB"
      }
    }
    """
    Then the response status code should be 200
