# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table word (
  id                            bigint not null,
  word                          varchar(255),
  description                   TEXT,
  constraint pk_word primary key (id)
);
create sequence word_seq;


# --- !Downs

drop table if exists word;
drop sequence if exists word_seq;

