create table if not exists role
    (
    id    integer    generated    always as    identity    primary    key,
    name    varchar(50) not null
    );

ALTER TABLE account
    Add role_id int8 references role(id)
    ;