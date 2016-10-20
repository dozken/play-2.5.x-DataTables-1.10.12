# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contact (
  id                            bigserial not null,
  name                          varchar(255),
  title                         varchar(255),
  email                         varchar(255),
  constraint pk_contact primary key (id)
);


# --- !Downs

drop table if exists contact cascade;

