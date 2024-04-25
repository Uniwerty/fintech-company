create table agreement_outbox
(
    message_id uuid primary key default uuid_generate_v1(),
    payload    json not null,
    status     varchar check ( status in ('NEW', 'PROCESSING', 'COMPLETED') )
);