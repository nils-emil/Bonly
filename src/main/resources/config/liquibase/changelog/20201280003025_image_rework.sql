--liquibase formatted sql
--changeset Nils-Emil:20201280003025>

CREATE TABLE image
(
    id      bigint PRIMARY KEY,
    content bytea NOT NULL
);

ALTER TABLE prize
    DROP COLUMN image;
ALTER TABLE prize
    DROP COLUMN image_content_type;
ALTER TABLE prize
    DROP COLUMN winner_chosen_at;
ALTER TABLE prize
    ADD COLUMN image_id bigint;

ALTER TABLE prize
    ADD CONSTRAINT fk_prize_image_id
        FOREIGN KEY (image_id)
            REFERENCES image(id);

ALTER TABLE advertisement
    DROP COLUMN image;
ALTER TABLE advertisement
    DROP COLUMN image_content_type;
ALTER TABLE advertisement
    ADD COLUMN image_id bigint;

ALTER TABLE advertisement
    ADD CONSTRAINT fk_advertisement_image_id
        FOREIGN KEY (image_id)
            REFERENCES image(id);
