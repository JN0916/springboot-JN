create table blog
(
id                 int primary key AUTO_INCREMENT,
user_id            bigint,
title              varchar(100),
description        varchar(100),
content            TEXT,
created_at         datetime,
updated_at         datetime
)
