# Java CLI Bank System Project

## Project Overview

The idea of this project is to build a sort of simplistic banking system. We have 5 different classes that all work
with each other, with the ATM class being the overall driver of the project, as well as the interface. This 
is built using only Java.

## ATM.java

The ATM class holds the main method of this project, and is responsible for creating a CLI for users to use.
For input, we import a Scanner class to check for valid input, and depending on the input, we can use one
of five different commands for the banking system.

## Bank.java

The Bank class essentially holds all the information a bank would usually have, including customer data,
account data, and the overall name of the bank. The class is responsible for creating each customer and user, 
including a UUID (Universal Unique ID) for each customer and account

## Transaction.java

The transaction class holds data for every transaction from every customer. It holds the dollar amount,
the timestamp for the transaction, and a memo. It has some helper methods to retrieve data, as
well as outputting a summary line of the transaction

## Account.java

The account class holds information on a customers account. Each account has a name, a UUID, and a list of it's transactions.
It has helper functions that give data, as well as being able to print a transaction history.

## User.java

The user account holds information on each customer. It contains data such as the first name, last name, uuid, the pin, and 
a list of their accounts. The pin in stored as a hash, using MD5 encoding. The class contains many helper methods, a method
to validate a pin, and a way to print the summary of the account.
