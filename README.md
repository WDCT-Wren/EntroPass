# EntroPass - Secure Password Generator

**EntroPass** is a JavaFX-based desktop application that generates cryptographically secure passwords with customizable parameters and stores them safely in a local SQLite database with BCrypt encryption.

## Features

### Password Generation
- **Customizable Length**: Generate passwords between 8-128 characters
- **Flexible Character Sets**:
  - Uppercase letters (A-Z)
  - Numbers (0-9)
  - Special characters (!@#$...)
- **Entropy-Based Strength Analysis**: Real-time password strength evaluation using the Password Entropy Formula
- **Passay Integration**: Leverages the Passay library for robust password generation

### Password Storage
- **SQLite Database**: Local storage for generated passwords
- **BCrypt Encryption**: Industry-standard password hashing with a cost factor of 12
- **Organized Storage**: Save passwords with associated metadata:
  - Service name
  - Username
  - Notes
  - Creation date

### User Interface
- **Modern JavaFX GUI**: Clean, intuitive interface with Comic Sans MS styling
- **Interactive Controls**: Checkboxes, sliders, and text fields for password customization
- **Real-Time Feedback**: Instant password strength indicators
- **Scene Navigation**: Multiple views for different application functions

## Technology Stack

### Core Technologies
- **Java 24**: Latest Java features and performance improvements
- **JavaFX 21**: Modern UI framework for rich desktop applications
- **Maven**: Dependency management and build automation

### Libraries & Dependencies
- **Passay 1.6.6**: Password generation and validation
- **SQLite JDBC 3.51.1.0**: Embedded database connectivity
- **jBCrypt 0.4** & **BCrypt 0.10.2**: Password hashing and security
- **JUnit Jupiter 5.12.1**: Unit testing framework

### Additional Components
- **ControlsFX 11.2.1**: Enhanced JavaFX controls
- **FormsFX 11.6.0**: Form validation and handling
- **ValidatorFX 0.6.1**: Input validation
- **Ikonli 12.3.1**: Icon library
- **BootstrapFX 0.4.0**: Bootstrap-inspired styling

## Project Structure

```
Password_Generator_GUI/
├── src/
│   └── main/
│       ├── java/
│       │   ├── Database/
│       │   │   ├── DatabaseManager.java    # Singleton database connection manager
│       │   │   ├── Connector.java          # Database operations (insert/update/delete)
│       │   │   └── PasswordHasher.java     # BCrypt password hashing utility
│       │   ├── GUI/
|       |   |   ├── Controllers/
|       |   |   |   ├── Builder.java                # FXML controller for Password Builder UI logic
|       |   |   |   ├── Menu.java                   # FXML controller for Main Menu UI logic
|       |   |   |   └── Vault.java                  # FXML controller for Password Vault UI logic
│       │   │   ├── Application.java        # Main application entry point
│       │   │   └── Launcher.java           # Application launcher
│       │   └── org/password_generator/
│       │       ├── PasswordBuilder.java            # Password generation orchestrator
│       │       ├── PasswordConfiguration.java      # Configuration for password rules
│       │       └── PasswordStrengthChecker.java    # Entropy-based strength evaluation
│       └── resources/org
│           ├── data/
│           │   └── PasswordDataBase.sqlite  # SQLite database file (hidden for obvious reasons)
│           └── password_generator_gui/
│               └── Scenes/
│                   ├── StartingMenu.fxml        # Main menu UI
│                   └── PasswordBuilder.fxml     # Password generation UI
│               └── Stylesheets/
│                   ├── SOON
├── pom.xml                                  # Maven configuration
└── README.md                                # This file
```

## Architecture & Design Principles

### Clean Code Implementation
- **Separation of Concerns**: Clear distinction between GUI, business logic, and data layers
- **Encapsulation**: Private fields with controlled access through getter methods
- **Single Responsibility**: Each class has a well-defined purpose
- **Dependency Injection**: Singleton pattern for database management

### Design Patterns
- **Singleton Pattern**: `DatabaseManager` ensures single database connection
- **Builder Pattern**: `PasswordBuilder` constructs passwords based on configuration
- **Strategy Pattern**: `PasswordConfiguration` defines rules for password generation

### Security Considerations
- **BCrypt Hashing**: Passwords stored with secure, salted hashes (cost factor: 12)
- **SQL Injection Prevention**: Prepared statements for all database operations
- **Input Validation**: Regex-based validation for password length and parameters

## Password Strength Algorithm

EntroPass uses the **Password Entropy Formula** to evaluate password strength:

```
Entropy (bits) = Password Length × log₂(Character Pool Size)
```

### Strength Thresholds
- **Strong**: ≥ 100 bits of entropy
- **Medium**: ≥ 72 bits of entropy
- **Weak**: ≥ 44 bits of entropy
- **Very Weak**: < 44 bits of entropy

### Character Pool Sizes
- Lowercase letters: 26 characters
- Mixed case (upper + lower): 52 characters
- Numbers: 10 characters
- Special characters: 32 characters

**Example**: A 12-character password with mixed case, numbers, and special characters has:
- Pool size: 52 + 10 + 32 = 94 characters
- Entropy: 12 × log₂(94) ≈ 78 bits → **Medium strength**

## Installation & Setup

### Prerequisites
- **Java Development Kit (JDK) 24** or later
- **Maven 3.6+**
- **JavaFX 21** (included as Maven dependency)

### Build Instructions

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd Password_Generator_GUI
   ```

2. **Build with Maven**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn javafx:run
   ```

### Database Setup

The application automatically creates the SQLite database at:
```
src/main/resources/data/PasswordDataBase.sqlite
```

**Database Schema**:
```sql
CREATE TABLE passwords (
    id INTEGER PRIMARY KEY,
    service_name TEXT NOT NULL,
    username TEXT,
    encrypted_password TEXT NOT NULL,
    notes TEXT,
    created_date TEXT
);
```

## Usage

### Generating a Password

1. Launch the application
2. Navigate to the **Password Builder** screen
3. Set your desired password length (8-128 characters)
4. Select character type options:
   - ☑ Uppercase characters
   - ☑ Numbers
   - ☑ Special characters
5. Click **Generate Password**
6. View the generated password and its strength rating

### Saving a Password

1. After generating a password:
   - Enter the **Username**
   - Enter the **Service Name** (e.g., "Gmail", "GitHub")
   - Add optional **Notes**
2. Click **Save Password**
3. The password is encrypted with BCrypt and stored in the database

## Current Limitations & Roadmap

### In Development
- [ ] Password retrieval/viewing interface
- [ ] Password decryption for viewing saved passwords
- [ ] Input validation for username and service name fields

### Planned Features
- [ ] Search and filter saved passwords
- [ ] Edit and delete password entries
- [ ] Password expiration reminders
- [ ] Export/import password database
- [ ] Master password protection
- [ ] Dark mode theme
- [ ] Comprehensive unit tests

## Contributing

This project follows clean code principles and object-oriented design patterns. When contributing:

1. Maintain separation between GUI, business logic, and database layers
2. Follow existing naming conventions
3. Add JavaDoc comments for public methods
4. Write unit tests for new features
5. Ensure BCrypt is used for all password storage

## License

[Soon(?)]

## Acknowledgments

- **Passay**: Password generation library
- **BCrypt**: Secure password hashing algorithm
- **SQLite**: Lightweight embedded database
- **JavaFX**: Modern UI framework

---

**Version**: 1.1-SNAPSHOT
**Last Updated**: March 9 2026
**Artifact Name**: EntroPass
