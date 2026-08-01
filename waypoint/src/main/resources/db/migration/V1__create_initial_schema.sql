CREATE TABLE company (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    website VARCHAR(255),
    industry VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE resume (
    id UUID PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    target_role VARCHAR(255),
    file_url VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE application (
    id UUID PRIMARY KEY,
    company_id UUID NOT NULL REFERENCES company(id),
    resume_id UUID REFERENCES resume(id),
    position VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    work_mode VARCHAR(50),
    salary_min NUMERIC(19, 2),
    salary_max NUMERIC(19, 2),
    salary_currency VARCHAR(10) DEFAULT 'KES',
    job_url VARCHAR(500),
    date_applied DATE NOT NULL,
    application_deadline DATE,
    current_stage VARCHAR(50) NOT NULL DEFAULT 'APPLIED',
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_application_company_id ON application(company_id);
CREATE INDEX idx_application_current_stage ON application(current_stage);
CREATE INDEX idx_application_date_applied ON application(date_applied);

CREATE TABLE application_stage_history (
    id UUID PRIMARY KEY,
    application_id UUID NOT NULL REFERENCES application(id) ON DELETE CASCADE,
    stage VARCHAR(50) NOT NULL,
    changed_at TIMESTAMPTZ NOT NULL,
    notes TEXT
);

CREATE INDEX idx_stage_history_application_id
    ON application_stage_history(application_id);

CREATE TABLE contact (
    id UUID PRIMARY KEY,
    company_id UUID NOT NULL REFERENCES company(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(255),
    linkedin_url VARCHAR(500),
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_contact_company_id ON contact(company_id);

CREATE TABLE reminder (
    id UUID PRIMARY KEY,
    application_id UUID NOT NULL REFERENCES application(id) ON DELETE CASCADE,
    reminder_type VARCHAR(50) NOT NULL,
    due_date DATE NOT NULL,
    is_completed BOOLEAN NOT NULL DEFAULT FALSE,
    notes TEXT,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_reminder_due_date_incomplete
    ON reminder(due_date, is_completed);
CREATE INDEX idx_reminder_application_id ON reminder(application_id);
