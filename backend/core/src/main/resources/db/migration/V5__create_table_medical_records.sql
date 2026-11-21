ALTER TABLE people ADD PRIMARY KEY (id);

CREATE TYPE blood_type AS ENUM (
    'A_POSITIVE',
    'A_NEGATIVE',
    'B_POSITIVE',
    'B_NEGATIVE',
    'AB_POSITIVE',
    'AB_NEGATIVE',
    'O_POSITIVE',
    'O_NEGATIVE'
);


CREATE TABLE medical_records(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    person_id INTEGER NOT NULL UNIQUE REFERENCES people(id) ON DELETE CASCADE,

    blood_type blood_type,
    allergies TEXT,
    comorbidities TEXT,
    continuous_use_medicines TEXT,
    general_observations TEXT,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    sync_id UUID NOT NULL UNIQUE
);