ALTER TABLE user ADD COLUMN active BOOLEAN;

UPDATE user set active = 1 WHERE 1 = 1;