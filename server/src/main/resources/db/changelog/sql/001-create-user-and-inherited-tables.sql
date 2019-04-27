create sequence user_id_sequence;

create table users
(
    id                integer               default nextval('user_id_sequence'::regclass),
    username          varchar(100) not null,
    email             varchar(100) not null,
    password          varchar(40)  not null,
    registration_date TIMESTAMP    not null default current_date,

    constraint user_pk primary key (id),
    constraint username_uni unique (username)
);

create table admins
(
    id         integer,
    boss_email varchar(100) not null,
    address    varchar(100) not null,

    constraint admin_pk primary key (id),
    constraint fk_admin_user foreign key (id) references users (id)
);

create table managers
(
    id                 integer,
    company            VARCHAR(100) not null,
    manager_experience smallint     not null default 0,
    company_info       text,

    constraint managers_pk primary key (id),
    constraint fk_manager_user foreign key (id) references users (id)
);

create table experts
(
    id                integer,
    expert_experience smallint not null default 0,
    prev_projects     smallint not null default 0,

    constraint experts_pk primary key (id),
    constraint fk_expert_user foreign key (id) references users (id)
);
