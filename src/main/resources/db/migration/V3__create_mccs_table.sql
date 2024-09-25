CREATE TABLE public.mccs (
     id varchar(255) DEFAULT uuid_generate_v4() NOT NULL,
     mcc varchar(20) NOT NULL,
     balance_kind varchar(255) NOT NULL,
     CONSTRAINT mccs_pk PRIMARY KEY (id),
     CONSTRAINT mccs_mcc_unique UNIQUE (mcc)
);
