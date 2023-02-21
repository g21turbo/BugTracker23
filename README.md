# Bug Tracker

Java Bug Tracker is a simple bug tracking application built in JavaFX. 
The application allows users to create and track bugs, with features including bug creation, updating, and status tracking.
See 'Demo Photos' above the 'src' folder for a preview

## Installation

To run the application, you will need to have Java 8 or higher installed on your machine. 
You can download the latest version of Java from the official Java website: https://www.java.com/en/download/

To build the application, open the project in IntelliJ IDEA and run the Main class.

## Usage

The Java Bug Tracker application provides a simple interface for creating and tracking bugs.
Must be connected to a MYSQL database("jdbc:mysql://localhost:3306/userlogin", "root", "root").
Upon launching the application, you will be presented with a main screen where you can log in or create an account.
After a successful login, the bug stage will appear
From here, you can:

    View submitted bugs in the table
    Submit a new bug by clicking the "Submit A Bug" button in top left corner of the stage
    Refresh the submitted bug table with the "Refresh" button below the "Submit A Bug" button
    Edit an existing bug by double-clicking on a bug in the table
    Update the status of a bug by selecting it in the table and selecting the status from to combobox

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)