CREATE TABLE product (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    status varchar(32) NOT NULL,
    fulfillment_center varchar(8) NOT NULL,
    quantity int NOT NULL,
    value decimal(10, 2) NOT NULL,

    CONSTRAINT quantity_positive CHECK (quantity > 0),
    CONSTRAINT value_positive CHECK (value > 0)
);