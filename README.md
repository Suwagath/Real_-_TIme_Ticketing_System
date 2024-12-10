# Real-Time Event Ticketing System

## Project Overview
The Real-Time Event Ticketing System is a multi-component application designed for efficient ticket management using a command-line interface (CLI), a backend API built with Spring Boot, and a front-end application developed in Angular. This system allows vendors and customers to interact with the ticketing system in real-time, leveraging multi-threading and concurrency to ensure optimal performance.

Key features include:
* Multi-threaded vendor and customer simulation.
* VIP customer prioritization for higher access priority.
* SQLite database integration for persistence.
* Dynamic configuration of system parameters via CLI.
* API backend for real-time data management.
* Angular front-end for visualization and user management.

<hr>

## System Components
### 1. Command-Line Interface (CLI)
The CLI is a core component that provides direct interaction with the system.

Main Menu
```java
===== Real-Time Event Ticketing System =====
1. Configure System Parameters
2. Vendors Management
3. Ticket Management
4. View Sales Log
0. Exit
============================================
```

Submenus
1. Configure System Parameters
   * Manage vendors dynamically, with options to add, update, or remove
   ```java
   ===== Manage Vendors =====
   1. Show All Vendors
   2. Add Vendor
   3. Update Vendor
   4. Remove Vendor
   5. Back to Main Menu
   ===========================================
   ```
2. Vendors Management
   * Manage vendors dynamically, with options to add, update, or remove
   ```java
   ===== Manage Vendors =====
   1. Show All Vendors
   2. Add Vendor
   3. Update Vendor
   4. Remove Vendor
   5. Back to Main Menu
   ===========================================
   ```
3. Ticket Management
   * Monitor and control the ticketing system operations
   ```java
   ===== Ticket Management =====
   1. Show Status
   2. Start System
   3. Stop System
   4. Restart System
   5. Back to Main Menu
   ===========================================
   ```
4. Sales Log
   * View real-time transaction logs of ticket sales.

### 2. API (Spring Boot)
The Spring Boot API acts as the backend layer, handling:
* Integration with SQLite database for storing vendors, customers, and tickets.
* RESTful endpoints for system management and real-time data retrieval.
* Synchronization of CLI and front-end interactions.

### 3. Front-End (Angular)
The Angular-based front-end provides:
* A user-friendly interface to visualize ticket availability, sales logs, and system statistics.
* Real-time updates from the backend.
* Vendor and customer management tools.

<hr>

## Key Features
### Multi-threading and Concurrency
* Each vendor and customer operates in a separate thread.
* VIP customers are prioritized using a custom queuing mechanism.
* Thread-safe operations are implemented using synchronized methods and thread-safe collections.

### SQLite Database Integration
* Persistent storage of vendor, customer, and ticket data.
* Automatic runtime updates:
   * Vendors added or removed from the database are reflected dynamically in the running system.

### Dynamic Configuration
* Parameters such as ticket release rate and maximum ticket capacity can be updated at runtime via the CLI.

<hr>

## Installation and Setup
<b>Prerequisites</b>
* Java 21+
* Node.js 18+
* Angular CLI
* SQLite

<hr>

## Usage
### CLI
* Access the main menu to manage configurations, vendors, ticketing operations, and logs.
* Start or stop the ticketing system and monitor real-time status updates.

### API
* Use RESTful endpoints for integration or management tasks.
* Refer to the API Documentation for detailed endpoint descriptions.

### Front-End
* Access the Angular application at http://localhost:4200.
* Manage vendors, visualize ticket availability, and view logs

<hr>

## Technologies Used
* Java: Core application logic and CLI.
* Spring Boot: Backend API development.
* Angular: Front-end user interface.
* SQLite: Persistent database storage.
* Multi-threading: Vendor and customer simulation.


