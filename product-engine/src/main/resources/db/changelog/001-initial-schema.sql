CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE products
(
    product_code           varchar PRIMARY KEY,
    min_term               int     NOT NULL,
    max_term               int     NOT NULL,
    min_interest           numeric NOT NULL,
    max_interest           numeric NOT NULL,
    min_principal_amount   numeric NOT NULL,
    max_principal_amount   numeric NOT NULL,
    min_origination_amount numeric NOT NULL,
    max_origination_amount numeric NOT NULL
);
INSERT INTO products
VALUES ('CL-1.0', 3, 24, '8', '15', '50000', '500000', '2000', '10000');
CREATE TABLE agreements
(
    agreement_id        uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    product_code        varchar REFERENCES products,
    client_id           uuid    NOT NULL,
    status              varchar CHECK ( status in ('NEW', 'ACTIVE', 'CLOSED') ),
    term                int     NOT NULL,
    interest            numeric NOT NULL,
    principal_amount    numeric NOT NULL,
    disbursement_amount numeric NOT NULL,
    origination_amount  numeric NOT NULL,
    disbursement_date   date,
    next_payment_date   date
);
CREATE TABLE payment_schedules
(
    schedule_id  uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    agreement_id uuid REFERENCES agreements,
    version      int NOT NULL
);
CREATE TABLE payments
(
    payment_id        uuid PRIMARY KEY DEFAULT uuid_generate_v1(),
    schedule_id       uuid REFERENCES payment_schedules,
    status            varchar CHECK ( status in ('PAID', 'OVERDUE', 'FUTURE') ),
    payment_date      date,
    interest_payment  numeric,
    principal_payment numeric,
    period_number     int
);
