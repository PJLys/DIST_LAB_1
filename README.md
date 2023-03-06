# DIST_LAB_1
TCP/UDP communication in Java + Git intro

## TCP Branch
The TCP Branch creates a 1 server 1 client connection.

The client acts as a terminal read device, sends the data over a TCP socket to a server.
The server prints the received TCP data into the terminal.

## UDP Branch

1 client communicates with 1 client

No connection setup!! -> UDP
We can check this, because both server and client have the same type of socket (DatagramSocket).
When checking this with TCP, there is a ServerSocket and a Socket. Which are used to create a connection.

The functionality of the UDP communication is the same as the TCP one

## MultiClient TCP

Multiple clients connect to a single server

The server creates threads for every connection it has and communicates accordingly.

## MultiClient UDP

No multithreading in the server, because we don't have a CO communication!!
Small update to the client in order to distinguish between the different client objects.
