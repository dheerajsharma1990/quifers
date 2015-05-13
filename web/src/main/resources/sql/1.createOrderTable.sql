create table orders (order_id bigint not null auto_increment,
name varchar(255),
mobile_number bigint,
email varchar(255),
from_address varchar(500),
to_address varchar(500),
booking_date timestamp,
primary key(order_id));
commit;