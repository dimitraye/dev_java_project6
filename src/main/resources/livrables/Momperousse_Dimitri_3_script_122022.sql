/* Setting up db_buddies DB */
create database db_buddies;
use db_buddies;   
   
   create table account (
       id bigint not null auto_increment,
        balance double not null,
        primary key (id)
    );
 
    create table transfer (
       id bigint not null auto_increment,
        amount double precision,
        date datetime,
        description varchar(255),
        account_receiver_id bigint,
        account_sender_id bigint,
        primary key (id)
    );
 
    create table user (
       id bigint not null auto_increment,
        first_name varchar(255),
        last_name varchar(255),
        password varchar(255),
        roles varchar(255),
        username varchar(255),
        account_id bigint,
        primary key (id)
    );
    
    CREATE TABLE users_buddies (
       users_id BIGINT NOT NULL,
        buddies_id BIGINT NOT NULL
    );

    alter table user 
       add constraint uk_users_username unique (username);
 
    alter table users_buddies 
       add constraint uk_users_buddies_buddies_id UNIQUE (buddies_id);
 
    alter table transfer 
       add constraint fk_transfer_account_receiver_id 
       foreign key (account_receiver_id) 
       references account (id);
 
    alter table transfer 
       add constraint fk_transfer_account_sender_id 
       foreign key (account_sender_id) 
       references account (id);
 
    alter table user 
       add constraint fk_users_account_id 
       foreign key (account_id) 
       references account (id);
 
    alter table users_buddies 
       add constraint fk_users_buddies_buddies_id 
       foreign key (buddies_id) 
       references user (id);

    
    alter table users_buddies 
       add constraint fk_users_buddies_users_id 
       foreign key (users_id) 
       references user (id);
       
       