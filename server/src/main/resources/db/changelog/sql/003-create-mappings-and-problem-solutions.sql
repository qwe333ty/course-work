create sequence problem_mapping_id_sequence;
create sequence problem_solutions_table_number_sequence;

create table problem_mapping
(
    id            integer default nextval('problem_mapping_id_sequence'::regclass),
    problem_id    integer      not null,
    ps_table_name varchar(100) not null,

    constraint problem_mapping_id_pk primary key (id),
    constraint problem_id_fk foreign key (problem_id) references problem (id),
    constraint unique_problem_id unique (problem_id)
);