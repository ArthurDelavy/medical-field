ALTER TABLE people
ADD COLUMN emergency_contact_email VARCHAR(100),
ADD COLUMN sync_id UUID;

UPDATE people SET sync_id = gen_random_uuid() WHERE sync_id IS NULL;

ALTER TABLE people ALTER COLUMN sync_id SET NOT NULL;