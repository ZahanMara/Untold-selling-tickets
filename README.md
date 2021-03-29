# Untold-selling-tickets

This is a JAVA Desktop application desired for selling tickets for Untold festival. 
The application have two types of users (cashier and admin) which must provide a username and a password in order to use the application. 
These users work on data that is stored in a database. 
The database is implemented in MySQL Workbench. There are three different tables in the database: Users, Ticket, Concert. 
Users table is populated with data about application users. 
The data about them are: a unique ID, username, password and type of user (cashier or admin). 
Ticket table contains data about tickets sold. The columns of this table are: ID, name of the buyer, number of tickets purchased and the desired concert.
Concert table contains records regarding concerts at the festival. 
Each record contains a unique ID of the concert, the name of the artist who will perform, the musical genre of the artist, the title of the show, the date and time when the concert will take place and the number of tickets available for the concert.

•	The admin user can perform the following operations:
o	CRUD on cashiers’ information
o	CRUD on the concerts at Untold
o	Export all the tickets that were sold for a certain show
•	The cashier user can perform the following operations:
o	Sell tickets to a concert
o	CRUD on tickets
•	The system notifies the users when:
o	The input data is not valid
o	The number of tickets per concert was exceeded
•	Data is stored in a relational database
•	Layers architectural pattern is used to organize the application
•	User interface
