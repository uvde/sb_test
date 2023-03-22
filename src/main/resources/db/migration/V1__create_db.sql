
CREATE TABLE IF NOT EXISTS account(
    id bigint generated always as identity primary key,
    name varchar (20),
    value decimal
)