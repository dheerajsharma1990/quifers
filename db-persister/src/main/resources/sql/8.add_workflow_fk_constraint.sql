ALTER TABLE orders_workflow
ADD CONSTRAINT orders_workflow1
FOREIGN KEY (order_id)
REFERENCES orders(order_id);
commit;