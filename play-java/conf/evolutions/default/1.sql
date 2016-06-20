# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table front_page_text (
  id                            integer not null,
  front_text                    TEXT,
  constraint pk_front_page_text primary key (id)
);
create sequence front_page_text_seq;

create table word (
  id                            integer not null,
  word                          varchar(255),
  description                   TEXT,
  constraint pk_word primary key (id)
);
create sequence word_seq;


# --- !Downs

drop table if exists front_page_text;
drop sequence if exists front_page_text_seq;

drop table if exists word;
drop sequence if exists word_seq;

