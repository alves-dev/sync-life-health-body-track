ALTER TABLE sleep_tracking ADD accounted BIT NULL AFTER action;
ALTER TABLE sleep_tracking ADD minutes_slept INT NULL AFTER accounted;