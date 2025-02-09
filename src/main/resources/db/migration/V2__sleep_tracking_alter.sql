ALTER TABLE sleep_tracking ADD computed bit(1) NULL AFTER action;
ALTER TABLE sleep_tracking ADD minutes_slept INT NULL AFTER computed;

UPDATE sleep_tracking SET computed = FALSE, minutes_slept = 0;

ALTER TABLE sleep_tracking MODIFY COLUMN computed bit(1) NOT NULL;
ALTER TABLE sleep_tracking MODIFY COLUMN minutes_slept int NOT NULL;