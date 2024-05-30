create table accounts
(
    account_id   uuid primary key default uuid_generate_v1(),
    agreement_id uuid references agreements,
    account_code varchar check ( account_code in ('STANDARD', 'OVERDUE') ),
    balance      numeric not null default 0
)