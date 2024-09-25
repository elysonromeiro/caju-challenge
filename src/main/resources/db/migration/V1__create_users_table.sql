CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS public.users (
  id varchar(255) DEFAULT uuid_generate_v4() NOT NULL,
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  "pass" varchar(255) NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY (id),
  CONSTRAINT users_unique_email UNIQUE (email)
);
