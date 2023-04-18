create table crypto_info (id bigint not null auto_increment, asset_name varchar(255), code varchar(255), market_cap float(53), price decimal(38,2), primary key (id)) engine=InnoDB;
create table roles (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table transaction (id bigint not null auto_increment, amount float(53), execution_time datetime(6), message varchar(255), outcome varchar(255), receiver varchar(255), sender varchar(255), primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, email varchar(255) not null, first_name varchar(255) not null, full_name varchar(255) not null, last_name varchar(255) not null, password varchar(255) not null, wallet_id bigint, primary key (id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB;
create table wallet (id bigint not null auto_increment, address varchar(255), asset_name varchar(255) not null, btc_amount float(53) not null, code varchar(255) not null, currency_amount float(53), private_key varchar(255), user_id bigint, primary key (id)) engine=InnoDB;
alter table roles add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table wallet add constraint UK_3y9jbtvtnd4rm8xk3iu4wi6up unique (address);
alter table wallet add constraint UK_akoxq3en1u3r4mebgsfy5fxw9 unique (private_key);
alter table users add constraint FK2ndfo1foff7a36v7f6sst12ix foreign key (wallet_id) references wallet (id);
alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles (id);
alter table users_roles add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users (id);
alter table wallet add constraint FKgbusavqq0bdaodex4ee6v0811 foreign key (user_id) references users (id);
create table crypto_info (id bigint not null auto_increment, asset_name varchar(255), code varchar(255), market_cap float(53), price decimal(38,2), primary key (id)) engine=InnoDB;
create table roles (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table transaction (id bigint not null auto_increment, amount float(53), execution_time datetime(6), message varchar(255), outcome varchar(255), receiver varchar(255), sender varchar(255), primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, email varchar(255) not null, first_name varchar(255) not null, full_name varchar(255) not null, last_name varchar(255) not null, password varchar(255) not null, wallet_id bigint, primary key (id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB;
create table wallet (id bigint not null auto_increment, address varchar(255), asset_name varchar(255) not null, btc_amount float(53) not null, code varchar(255) not null, currency_amount float(53), private_key varchar(255), user_id bigint, primary key (id)) engine=InnoDB;
alter table roles add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table wallet add constraint UK_3y9jbtvtnd4rm8xk3iu4wi6up unique (address);
alter table wallet add constraint UK_akoxq3en1u3r4mebgsfy5fxw9 unique (private_key);
alter table users add constraint FK2ndfo1foff7a36v7f6sst12ix foreign key (wallet_id) references wallet (id);
alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles (id);
alter table users_roles add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users (id);
alter table wallet add constraint FKgbusavqq0bdaodex4ee6v0811 foreign key (user_id) references users (id);
create table crypto_info (id bigint not null auto_increment, asset_name varchar(255), code varchar(255), market_cap float(53), price decimal(38,2), primary key (id)) engine=InnoDB;
create table roles (id bigint not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table transaction (id bigint not null auto_increment, amount float(53), execution_time datetime(6), message varchar(255), outcome varchar(255), receiver varchar(255), sender varchar(255), primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, email varchar(255) not null, first_name varchar(255) not null, full_name varchar(255) not null, last_name varchar(255) not null, password varchar(255) not null, wallet_id bigint, primary key (id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB;
create table wallet (id bigint not null auto_increment, address varchar(255), asset_name varchar(255) not null, btc_amount float(53) not null, code varchar(255) not null, currency_amount float(53), private_key varchar(255), user_id bigint, primary key (id)) engine=InnoDB;
alter table roles add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table wallet add constraint UK_3y9jbtvtnd4rm8xk3iu4wi6up unique (address);
alter table wallet add constraint UK_akoxq3en1u3r4mebgsfy5fxw9 unique (private_key);
alter table users add constraint FK2ndfo1foff7a36v7f6sst12ix foreign key (wallet_id) references wallet (id);
alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles (id);
alter table users_roles add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users (id);
alter table wallet add constraint FKgbusavqq0bdaodex4ee6v0811 foreign key (user_id) references users (id);