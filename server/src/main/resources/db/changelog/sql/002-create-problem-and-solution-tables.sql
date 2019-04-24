create sequence problem_id_sequence;
create sequence solution_id_sequence;

create table problem
(
    id         integer default nextval('problem_id_sequence'::regclass),
    manager_id integer      not null,
    header     varchar(250) not null,

    constraint problem_pk primary key (id),
    constraint manager_id_to_user_id_fk foreign key (manager_id) references users (id)
);

create table solution
(
    id             integer default nextval('solution_id_sequence'::regclass),
    problem_id     integer      not null,
    rating         decimal      not null,
    header         varchar(250) not null,
    solution_order integer      not null,

    constraint solution_pk primary key (id),
    constraint problem_id_to_problem_id_fk foreign key (problem_id) references problem (id)
);