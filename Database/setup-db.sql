-- Enable foreign key constraints (needed for foreign keys to work in SQLite) 
PRAGMA foreign_keys = ON;


CREATE TABLE IF NOT EXISTS system_config (
    id INTEGER PRIMARY KEY,
    config_key TEXT NOT NULL UNIQUE,
    config_value INT NOT NULL  -- Use INT if values are strictly integers, or TEXT for flexibility
);

INSERT OR IGNORE INTO system_config (config_key, config_value) 
VALUES 
    ("total_tickets", 50),
    ("ticket_release_rate", 60),
    ("customer_retrieval_rate", 60),
    ("max_ticket_capacity", 500);


-- Table for vendors
CREATE TABLE IF NOT EXISTS vendors (
    vendor_id INTEGER PRIMARY KEY AUTOINCREMENT,
    vendor_name TEXT UNIQUE NOT NULL,
    tickets_per_release INTEGER NOT NULL, -- Number of tickets added per release
    release_rate_sec INTEGER NOT NULL  -- Frequency of ticket release in seconds
);


INSERT OR IGNORE INTO vendors (vendor_name, tickets_per_release, release_rate_sec)
VALUES 
    ("vendor 1", 1, 30),
    ("vendor 2", 2, 60),
    ("vendor 3", 3, 120);


-- Table for sales log
CREATE TABLE IF NOT EXISTS sales_log (
    sale_id INTEGER PRIMARY KEY AUTOINCREMENT,
    date_time TEXT NOT NULL,
    log TEXT NOT NULL
);

