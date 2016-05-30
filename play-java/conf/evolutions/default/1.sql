# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                            bigint not null,
  name                          varchar(255),
  password                      varchar(255),
  count                         integer,
  constraint pk_user primary key (id)
);
create sequence user_seq;

create table word (
  id                            bigint not null,
  word                          varchar(255),
  description                   TEXT,
  count                         bigint,
  constraint pk_word primary key (id)
);
create sequence word_seq;


# --- !Downs

drop table if exists user;
drop sequence if exists user_seq;

drop table if exists word;
drop sequence if exists word_seq;

