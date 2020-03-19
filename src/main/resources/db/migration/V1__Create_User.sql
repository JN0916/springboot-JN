create table user
(
id                 int primary key AUTO_INCREMENT,
username           varchar(100) ,
encrypted_password varchar(100),
avatar             varchar(100),
created_at         datetime,
updated_at         datetime,
UNIQUE(username)
)