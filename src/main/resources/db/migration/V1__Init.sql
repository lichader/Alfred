CREATE TABLE alfred.tracking_status (
    id bigserial primary key,
    code integer UNIQUE NOT NULL,
    description VARCHAR(50) NOT NULL,
    created_date timestamp with time zone,
    modified_date timestamp with time zone
);

CREATE TABLE alfred.tracking_parcel (
    id bigserial primary key,
    tracking_no VARCHAR(50) UNIQUE NOT NULL,
    status_id bigint not null,
    created_date timestamp with time zone,
    modified_date timestamp with time zone,
    CONSTRAINT tracking_parcel_status_id_fk FOREIGN KEY (status_id)
        REFERENCES alfred.tracking_status(id) MATCH SIMPLE
);

CREATE TABLE alfred.tracking_history (
    id bigserial primary key,
    parcel_id bigint not null,
    time TIMESTAMP,
    location VARCHAR(50),
    status VARCHAR(50),
    created_date timestamp with time zone,
    modified_date timestamp with time zone,
    CONSTRAINT tracking_history_parcel_id_fk FOREIGN KEY (parcel_id)
        REFERENCES alfred.tracking_parcel(id) MATCH SIMPLE
);



insert into alfred.tracking_status (code, description, created_date, modified_date)
values
    (-9, '数据库内部错误', current_timestamp, current_timestamp),
    (-5, '服务器版本类型不支持', current_timestamp, current_timestamp),
    (-4, '系统未获有效注册', current_timestamp, current_timestamp),
    (-3, '运单号长度不对(8-30)', current_timestamp, current_timestamp),
    (-2, '没有查询结果', current_timestamp, current_timestamp),
    (0, '未发送', current_timestamp, current_timestamp),
    (1, '已发送', current_timestamp, current_timestamp),
    (2, '转运中', current_timestamp, current_timestamp),
    (3, '送达', current_timestamp, current_timestamp),
    (4, '超时', current_timestamp, current_timestamp),
    (5, '扣关', current_timestamp, current_timestamp),
    (6, '地址错误', current_timestamp, current_timestamp),
    (7, '快件丢失', current_timestamp, current_timestamp),
    (8, '退件', current_timestamp, current_timestamp),
    (9, '其它异常', current_timestamp, current_timestamp),
    (10, '销毁', current_timestamp, current_timestamp);
