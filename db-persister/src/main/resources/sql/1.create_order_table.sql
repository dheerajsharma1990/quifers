create table orders (order_id varchar(255),
vehicle varchar(100),
labours integer,
estimate varchar(255),
distance integer,
pick_up_floors integer,
pick_up_lift_working boolean,
drop_off_floors integer(255),
drop_off_lift_working boolean,
primary key(order_id));
commit;