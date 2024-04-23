alter table applications
    add column last_update_date date;

update applications
set last_update_date = creation_date;
