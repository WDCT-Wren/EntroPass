# EntroPass

EntroPass is a JavaFX desktop password manager focused on practical local security: master-password authentication, encrypted vault storage, and a configurable password builder.

This project is designed as a real desktop application, not a demo screen flow. It persists data locally with SQLite, protects vault secrets with authenticated encryption, and gates access through a hashed master credential.

## Tech Stack

- Language: Java
- UI: JavaFX 21.0.6 (FXML + CSS scene architecture)
- Build Tool: Maven
- Local Data Store: SQLite (`sqlite-jdbc`)
- Password Hashing: jBCrypt (master password verification)
- Key Derivation: PBKDF2-HMAC-SHA256 (session key derivation)
- Vault Encryption: AES-GCM (`AES/GCM/NoPadding`)
- Password Generation: Passay

## Current Features

### Authentication

- Register and store a master password hash in SQLite
- Login validation against stored BCrypt hash
- Session-based vault access after successful authentication
- First-run routing to registration, returning-user routing to login

### Vault

- Load and display saved vault entries
- Decrypt and view selected password details
- Search entries by service name or username
- Copy username/password to clipboard
- Delete entries from vault

### Password Builder

- Generate passwords with configurable options:
  - Length (8-64)
  - Digits
  - Special characters
  - Mixed-case letters
- Manual entry mode with real-time strength feedback
- Entropy-based strength scoring and progress visualization
- Save generated/manual passwords to encrypted vault entries

## In Progress / Planned

- Update existing vault entries (edit save/cancel handlers are currently placeholders)
- Add automated tests (`src/test` is currently empty)
- Continue codebase cleanup and UI refinements from `TODO-list.md`

## Setup and Run

### Prerequisites

- JDK compatible with project compiler/preview settings
- Maven 3.6+

### Run Commands

```bash
mvn clean install
mvn javafx:run
```

### Current Build Note

At the moment, `mvn -q -DskipTests compile` fails locally with:

- `invalid source release 24 with --enable-preview`
- `preview language features are only supported for release 25`

This is a Java source/preview configuration mismatch in the current build setup.

## Security Design

EntroPass follows a layered local-security model:

1. The master password is never stored in plaintext. A BCrypt hash is stored in the `master` table.
2. On successful login, PBKDF2-HMAC-SHA256 derives an AES key from the entered master password plus stored salt.
3. Vault passwords are encrypted with AES-GCM before writing to SQLite.
4. A fresh IV is generated per encryption operation and prepended to ciphertext for decryption.
5. Decryption only occurs during an authenticated session, using the in-memory session key.

Database file location:

- `${user.home}/EntroPass/PasswordDatabase.sqlite`

Current tables:

```sql
CREATE TABLE IF NOT EXISTS master (
  id INTEGER PRIMARY KEY CHECK (id = 1),
  hash TEXT NOT NULL,
  salt TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS vault (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  service_name TEXT NOT NULL,
  username TEXT NOT NULL,
  encrypted_password TEXT NOT NULL,
  notes TEXT,
  created_date TEXT
);
```

## Project Structure

```text
EntroPass/
|- pom.xml
|- README.md
|- TODO-list.md
|- src/
|  |- main/
|  |  |- java/
|  |  |  |- Database/
|  |  |  |- Encryption/
|  |  |  |- GUI/
|  |  |  |- org/Password_Generator/
|  |  |  \- module-info.java
|  |  \- resources/org/password_generator_gui/
|  |     |- Scenes/
|  |     \- Stylesheets/
\- target/
```

## License

Not yet specified.
