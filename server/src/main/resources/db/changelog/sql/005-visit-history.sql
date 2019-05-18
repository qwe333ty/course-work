create sequence visit_history_id_sequence;

create table visit_history
(
    id        integer   default nextval('visit_history_id_sequence'::regclass),
    user_id   integer not null,
    date_time timestamp default current_date,

    constraint visit_history_to_user foreign key (user_id) REFERENCES users (id)
)