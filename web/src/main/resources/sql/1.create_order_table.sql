create table orders (order_id bigint,
name varchar(255),
mobile_number bigint,
email varchar(255),
vehicle varchar(100),
from_address_house_number varchar(255),
from_address_society varchar(255),
from_address_area varchar(255),
from_address_city varchar(255),
to_address_house_number varchar(255),
to_address_society varchar(255),
to_address_area varchar(255),
to_address_city varchar(255),
labours integer,
estimate varchar(255),
distance varchar(255),
pick_up_floors integer(255),
pick_up_lift_working boolean,
drop_off_floors integer(255),
drop_off_lift_working boolean,
primary key(order_id));
commit;