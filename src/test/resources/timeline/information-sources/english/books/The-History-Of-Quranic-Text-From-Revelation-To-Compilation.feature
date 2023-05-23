Feature: Content Coverage Of Book "The History Of Quranic Text From Revelation To Compilation"

  Background: About The Book
    Given "The History Of Quranic Text From Revelation To Compilation" is published in year 2003 CE
    And It's author is "Muhammad Mustafa Al-Azami"
    And published by "UK ISLAMIC ACADEMY LEICESTER ENGLAND"

#  Page 23
  Scenario: Birth Of Prophet Muhammad In Year 53 BH
    Given last prophet Muhammad P.B.U.H was born in the year 53 "BH"
    Then system must contain the last Prophet's birth reference on page 23 of this book

  Scenario: Death Of Prophet Muhammad In Year 11 AH
    Given last prophet Muhammad P.B.U.H died in the year 11 "AH"
    Then system must contain the last Prophet's death reference on page 23 of this book