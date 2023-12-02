CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE clients
(
    client_id  uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    first_name varchar NOT NULL,
    last_name  varchar NOT NULL,
    email      varchar NOT NULL,
    salary     numeric NOT NULL
);
CREATE TABLE applications
(
    application_id   uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    client_id        uuid REFERENCES clients,
    requested_amount numeric NOT NULL,
    status           varchar CHECK ( status in ('NEW', 'SCORING', 'ACCEPTED', 'ACTIVE', 'CLOSED') ),
    creation_date    date    NOT NULL
);