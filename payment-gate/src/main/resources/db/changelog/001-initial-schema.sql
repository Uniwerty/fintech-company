create table disbursements
(
    agreement_id uuid primary key,
    client_id    uuid    not null,
    status       varchar check ( status in ('PROCESSING', 'COMPLETED') ),
    amount       numeric not null
)