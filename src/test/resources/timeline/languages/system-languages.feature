Feature: Complete Details About The Languages Supported By The System

  Scenario Outline: The System Should Be Multilingual
    Then Verify that system supports: "<language code>"
    Examples:
    | language code |
    | Ar            |
    | Ur            |
    | En            |