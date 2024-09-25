CREATE TABLE IF NOT EXISTS public.balances
(
    id     varchar(255) DEFAULT uuid_generate_v4() NOT NULL,
    name   varchar(255)                            NOT NULL,
    kind   varchar(50)                             NOT NULL,
    amount int8         DEFAULT 0                  NOT NULL,
    user_id varchar(255)                           NOT NULL,
    CONSTRAINT balances_pk PRIMARY KEY (id),
    CONSTRAINT balance_to_user_fk FOREIGN KEY (user_id) REFERENCES public.users (id)
);
