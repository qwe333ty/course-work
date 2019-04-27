create sequence solution_history_id_sequence;

create table solution_history
(
    id         integer default nextval('solution_history_id_sequence'::regclass),
    problem_id integer not null,
    user_id    integer not null,
    row_       integer not null,
    column_    integer not null,
    value_     integer default 0,

    primary key (id),
    foreign key (problem_id) references problem (id),
    foreign key (user_id) references users (id)
);
