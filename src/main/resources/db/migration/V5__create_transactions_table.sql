CREATE TABLE public.transactions (
     id varchar(255) DEFAULT uuid_generate_v4() NOT NULL,
     account varchar(255) NOT NULL,
     total_amount int8 NOT NULL,
     balance_before int8 NOT NULL,
     balance_after int8 NOT NULL,
     balance_id varchar(255) NOT NULL,
     user_id varchar(255) NOT NULL,
     mcc_id varchar(255),
     CONSTRAINT transactions_pk PRIMARY KEY (id),
     CONSTRAINT transactions_to_balances_fk FOREIGN KEY (balance_id) REFERENCES public.balances(id),
     CONSTRAINT transactions_to_users_fk FOREIGN KEY (user_id) REFERENCES public.users(id),
     CONSTRAINT transactions_to_mccs_fk FOREIGN KEY (mcc_id) REFERENCES public.mccs(id)
);
