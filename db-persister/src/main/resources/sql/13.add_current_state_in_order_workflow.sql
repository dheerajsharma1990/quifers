alter table orders_workflow
add column current_state boolean;
commit;
