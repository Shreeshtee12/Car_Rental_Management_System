## About

This is a desktop application developed to help car rental offices manage their rentals efficiently.  
**Idea**: Provide an easy-to-use system for managing clients, cars, and rentals.  
**Goal**: Demonstrate the implementation of software design principles and patterns (MVC, DAO, Dependency Injection, Singleton, etc.).

## How to Get Started

1. Clone this repository to your local machine.
2. Initialize the MySQL database by running the script located at: `/sql/DB.sql`.
3. Configure the database connection parameters in the file: `/src/Model/SingletonConnection.java`.
4. Compile and run the application using CLI or your preferred IDE. IntelliJ IDEA is recommended.

## Features

- Manage cars, clients, and rentals in a car rental office.
- Search and filter cars and clients based on various criteria.
- Add, update, and delete records for rentals, cars, and clients.
- Export data to Excel files using Apache POI.

## Screenshots

Home View  
![Home View](./png/homeViewScreenshot.png?raw=true)

Clients View  
![Clients View](./png/clientsViewScreenshot.png?raw=true)

Cars View  
![Cars View](./png/carsViewScreenshot.png?raw=true)

Rentals View  
![Rentals View](./png/rentalsViewScreenshot.png?raw=true)

Add Rental View  
![Add Rental View](./png/addRentalViewScreenshot.png?raw=true)

## Technologies Used

- **Programming Language**: Java (Swing for GUI)
- **Database**: MySQL
- **Libraries**: Apache POI for Excel export
- **Development Environment**: IntelliJ IDEA

## Future Enhancements

- Add user authentication for secured access.
- Implement REST APIs for remote access.
- Migrate to a more modern UI framework for improved user experience.
