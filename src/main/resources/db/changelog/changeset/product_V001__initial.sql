CREATE TABLE product (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name varchar(64) NOT NULL,
    status varchar(32) NOT NULL,
    fulfillment_center varchar(8) NOT NULL,
    quantity int NOT NULL,
    price decimal(10, 2) NOT NULL,

    CONSTRAINT quantity_positive CHECK (quantity > 0),
    CONSTRAINT value_positive CHECK (price > 0)
);