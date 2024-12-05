
ALTER TABLE uplatnica ADD user_id BIGINT;

ALTER TABLE uplatnica ADD FOREIGN KEY (user_id) REFERENCES jhi_user(id);

