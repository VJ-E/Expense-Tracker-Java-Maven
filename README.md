# Expense Tracker - Java Maven Application

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

A desktop-based Expense Tracker application built with Java Swing and MySQL. This application helps users manage their expenses by category, track spending patterns, and maintain financial records in a user-friendly interface.

## Features

- Add, view, update, and delete expenses
- Categorize expenses for better organization
- View expense history with detailed information
- Track expenses by date
- Search and filter expenses
- Intuitive graphical user interface
- Secure database storage

## Prerequisites

- Java JDK 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.8+
- Git (for cloning the repository)

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/Expense-Tracker-Java-Maven.git
   cd Expense-Tracker-Java-Maven
   ```

2. **Set up the database**
   - Create a MySQL database named `expense_tracker`
   - Update the database credentials in the `.env` file
   ```env
   DB_PASSWORD=your_mysql_password
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.expenseTracker.Main"
   ```

## Project Structure

```
src/main/java/com/expenseTracker/
├── Main.java                 # Application entry point
├── dao/
│   └── ExpenseDAO.java       # Data Access Object for expenses
├── model/
│   ├── Category.java         # Category model
│   └── Expense.java          # Expense model
├── gui/
│   ├── CategoryGui.java      # Category management UI
│   ├── ExpenseGui.java       # Expense management UI
│   └── MainGui.java          # Main application window
└── util/
    └── DatabaseConnection.java # Database connection handler
```

## Dependencies

- MySQL Connector/J - Database connectivity
- dotenv-java - Environment variable management
- Java Swing - GUI components

## Usage

1. **Adding an Expense**
   - Click on "Add Expense" button
   - Fill in the amount, select a category, and add a description
   - Click "Save" to record the expense

2. **Managing Categories**
   - Navigate to the Categories section
   - Add, edit, or delete expense categories as needed

3. **Viewing Expenses**
   - The main dashboard displays a summary of expenses
   - Use the table to view all recorded expenses
   - Filter expenses by date range or category

## Database Schema

The application uses the following database tables:

### Categories Table
- `category_id` (PK, Auto Increment)
- `category_name` (VARCHAR)
- `created_at` (TIMESTAMP)

### Expenses Table
- `expense_id` (PK, Auto Increment)
- `amount` (INT)
- `description` (TEXT)
- `category_id` (FK to Categories AND CASDE ON DELETE)
- `created_at` (TIMESTAMP)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built with Java and MySQL
- Uses Maven for dependency management
- Inspired by the need for simple personal finance management

---

<div align="center">
  Made with ❤️ by VIJAY
</div>
