# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table firstnames (
  id                        bigserial not null,
  firstname                 varchar(255),
  origin                    varchar(255),
  meaning                   varchar(255),
  gender                    varchar(255),
  constraint pk_firstnames primary key (id))
;




# --- !Downs

drop table if exists firstnames cascade;

