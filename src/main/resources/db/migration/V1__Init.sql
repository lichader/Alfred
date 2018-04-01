CREATE TABLE alfred.tracking_parcel (
    id bigserial primary key,
    tracking_no VARCHAR(50) UNIQUE NOT NULL,
    destination VARCHAR(50) NOT NULL,
    delivered boolean NOT NULL default false
);

CREATE TABLE alfred.tracking_history (
    id bigserial primary key,
    parcel_id integer not null,
    time TIMESTAMP,
    location VARCHAR(50),
    status VARCHAR(50),
    CONSTRAINT tracking_history_parcel_id_fk FOREIGN KEY (parcel_id)
        REFERENCES alfred.tracking_parcel(id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);
