# Real-Time Ticketing System

## 1. Overview of the System
The system is designed to manage ticket sales in real time, simulating how tickets are released by vendors (producers) and purchased by customers (consumers). Key features include managing ticket availability, real-time updates, and organizing data in a way that can handle multiple vendors and types of tickets.

## 2. Component Roles: What Each Part Does

### Command-Line Interface (CLI)
**Purpose**: CLI is mainly for system administrators to configure the system. It allows configuration of parameters, management of vendors, and viewing ticket status in real time.

**CLI Functions**:
- **System Configuration**: Set up initial values like total ticket count and how quickly tickets are released.
- **Manage Vendors**: Add or remove vendors who will sell tickets.
- **Monitor Real-Time Status**: View the current ticket inventory by vendor and type to ensure everything is working correctly.
- **Sales Log**: Look at past sales to see when tickets were purchased and by whom.

### Frontend (User Interface)
**Purpose**: The frontend is a visual interface for admins. Admins can monitor sales and ticket status.

**Frontend Functions**:
- **Display Ticket Availability**: Show customers which tickets are available in real time.
- **Control Panel for Admins**: Start/stop ticket release operations and adjust system settings.
- **Real-Time Notifications**: Display messages about ticket status, sales updates, and any errors (e.g., if no tickets are available).

### API (Backend Logic and Database Interaction)
**Purpose**: The API, built with Spring Boot, is the brain of the system. It controls all operations related to ticket handling, connects the frontend and CLI to the database, and manages real-time data.

**API Functions**:
- **Producer-Consumer Pattern**: Control how tickets are added (producers) and sold (consumers), ensuring real-time updates.
- **Thread Safety**: Manage multiple vendors and customers without errors, using synchronization.
- **Database Operations**: Add, update, or retrieve data on tickets, sales, vendors, and customers.

### Database
**Purpose**: The database stores all essential information about vendors, tickets, and sales. It keeps a record of what’s happening in the system for both real-time operations and historical data.

**Database Tables**:
- **Vendors Table**: Stores vendor details, like vendor name and ticket release rate.
- **Tickets Table**: Tracks each ticket, including its type, availability, and vendor association.
- **Sales Log Table**: Records each sale, including ticket ID, customer info, and timestamp.

## 3. How Components Work Together
Here’s how each part interacts in a typical workflow:

### System Setup (CLI and API)
- The CLI is used by an admin to set up vendors, and general system settings. This information is saved in the Database (through API requests).
- For example, an admin might add a vendor with a specific release rate.

### Ticket Handling (API and Database)
- Once vendors and ticket types are set up, the API starts handling tickets in real time:
  - Vendors (producers) add tickets to the pool at a regular interval.
  - Customers (consumers) remove tickets from the pool when they make purchases.
  - The API uses multi-threading to simulate vendors releasing tickets and customers buying tickets simultaneously. It also makes sure this happens without errors (using synchronization).

### Real-Time Updates (Frontend, API, and Database)
- The Frontend shows real-time ticket availability by pulling data from the API.
- The API frequently updates ticket statuses in the Database so that the frontend reflects the latest information.
- If a customer tries to buy a ticket, the frontend sends a request to the API, which updates the Database to reflect the new ticket count.

### Sales Logging (API and Database)
- Each time a ticket is purchased, the API logs this sale in the Sales Log Table in the Database.
- The CLI or Frontend can then retrieve this data, allowing admins to view sales history and check how many tickets have been sold.

## 4. Example Workflow
Let’s say the system is up and running. Here’s how a user would experience it:

### Admin Setup (CLI)
- The admin opens the CLI to configure vendors and ticket types.
- They add two vendors.
- This setup information is stored in the Database.

### Customer Purchase (Frontend) - Simulation
- A customer visits the frontend to purchase a ticket.
- They see available ticket types and click to buy a VIP ticket.
- The frontend sends a purchase request to the API.
- The API processes the purchase, updating the Tickets Table and logging the sale in the Sales Log Table.

### Real-Time Status Check (CLI/Frontend)
- The admin can check the real-time status of ticket availability via the CLI.
- The admin on the frontend sees updated availability immediately after the purchase.

## 5. Key Relationships
- **Vendors and Tickets**: Each vendor has specific ticket types, managed by linking vendor_id in the Tickets Table.
- **Tickets and Sales Log**: Each sale links to a specific ticket, with the sale’s ticket_id tracking which ticket was sold.
- **API and Database**: The API acts as the intermediary, managing ticket availability, sales, and customer interactions by reading/writing to the database.

This setup ensures that all parts of the system configuration, ticket handling, real-time updates, and logging are working together in sync.
