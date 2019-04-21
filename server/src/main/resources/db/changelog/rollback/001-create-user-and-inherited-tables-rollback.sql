drop sequence user_id_sequence;

alter table admins
    drop constraint fk_admin_user;
alter table managers
    drop constraint fk_manager_user;
alter table experts
    drop constraint fk_expert_user;
alter table users
    drop constraint username_uni;

drop table admins;
drop table managers;
drop table experts;

drop table users;

