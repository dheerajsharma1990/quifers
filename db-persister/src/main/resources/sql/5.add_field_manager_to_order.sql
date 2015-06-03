alter table orders add field_executive_id varchar(255);
commit;

alter table orders
add constraint orders_fk1
foreign key (field_executive_id)
references field_executive(user_id);
commit;