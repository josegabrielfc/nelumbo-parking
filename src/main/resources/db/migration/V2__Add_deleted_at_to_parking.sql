ALTER TABLE parking ADD COLUMN deleted_at DATETIME;
ALTER TABLE vehicle_record ADD COLUMN deleted_at DATETIME;
ALTER TABLE parking_history ADD COLUMN deleted_at DATETIME;
