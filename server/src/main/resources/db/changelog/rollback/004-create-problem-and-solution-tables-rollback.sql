drop sequence problem_id_sequence, solution_id_sequence;

alter table solution
    DROP constraint expert_id_to_user_id_fk;
alter table solution
    DROP constraint problem_id_to_problem_id_fk;
alter table problem
    drop constraint manager_id_to_user_id_fk;

drop table solution;
drop table problem;