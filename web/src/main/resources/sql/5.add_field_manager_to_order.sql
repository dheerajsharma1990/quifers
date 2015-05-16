alter table orders add field_manager_id varchar(255);
commit;

alter table orders
add constraint orders_fk1
foreign key (field_manager_id)
references field_manager(user_id);
commit;