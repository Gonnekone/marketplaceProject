create table "user"
(
    id       serial primary key,
    email    varchar(100) unique not null,
    password varchar(1000)       not null,
    role     varchar(100)        not null
);

create table good
(
    id          serial primary key,
    name        varchar(250) unique not null,
    price       double precision    not null check ( price >= 0 ),
    description varchar
);

create table "order"
(
    id         serial primary key,
    created_at timestamp    not null,
    changed_at timestamp,
    code       varchar(100),
    user_id    int          not null references "user" (id) on delete cascade,
    status     varchar(100) not null
);

create table category
(
    id   serial primary key,
    name varchar(100) not null
);

create table bucket
(
    id      serial primary key,
    user_id int not null unique references "user" (id) on delete cascade
);

create table good_category
(
    good_id     int references good (id) on delete cascade     not null,
    category_id int references category (id) on delete cascade not null
);

create table order_good_amount
(
    good_id  int references good (id) on delete cascade    not null,
    order_id int references "order" (id) on delete cascade not null,
    amount   int                                           not null,
    primary key (good_id, order_id)
);

create table good_bucket_amount
(
    good_id   int references good (id) on delete cascade   not null,
    bucket_id int references bucket (id) on delete cascade not null,
    amount    int                                          not null,
    primary key (good_id, bucket_id)
);