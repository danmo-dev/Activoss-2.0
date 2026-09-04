-- V1__schema_assets_uuid.sql
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- =====================================================================
-- Companies
-- =====================================================================
CREATE TABLE IF NOT EXISTS companies (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tax_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    company_type VARCHAR(50),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================================
-- Asset Types
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_types (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================================
-- Asset Relationship Types
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_relationship_types (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

-- =====================================================================
-- Ownership Types
-- =====================================================================
CREATE TABLE IF NOT EXISTS ownership_types (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

-- =====================================================================
-- Asset Statuses
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_statuses (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

-- =====================================================================
-- Locations
-- =====================================================================
CREATE TABLE IF NOT EXISTS locations (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    parent_location_id uuid REFERENCES locations(id),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL
);

-- =====================================================================
-- People
-- =====================================================================
CREATE TABLE IF NOT EXISTS people (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id),
    document_number VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(200),
    department VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================================
-- Owners
-- =====================================================================
CREATE TABLE IF NOT EXISTS owners (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id),
    ownership_type_id uuid NOT NULL REFERENCES ownership_types(id)
);

-- =====================================================================
-- Sub Asset Types
-- =====================================================================
CREATE TABLE IF NOT EXISTS sub_asset_types (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    asset_type_id uuid NOT NULL REFERENCES asset_types(id),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT true
);

-- =====================================================================
-- Field Groups
-- =====================================================================
CREATE TABLE IF NOT EXISTS field_groups (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    sub_asset_type_id uuid NOT NULL REFERENCES sub_asset_types(id),
    name VARCHAR(100) NOT NULL,
    display_order INT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true
);

-- =====================================================================
-- Field Definitions
-- =====================================================================
CREATE TABLE IF NOT EXISTS field_definitions (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    sub_asset_type_id uuid NOT NULL REFERENCES sub_asset_types(id),
    field_group_id uuid REFERENCES field_groups(id),
    name VARCHAR(100) NOT NULL,
    label VARCHAR(150),
    field_type VARCHAR(50) NOT NULL,
    is_required BOOLEAN DEFAULT false,
    is_visible BOOLEAN DEFAULT true,
    is_editable BOOLEAN DEFAULT true,
    is_unique BOOLEAN DEFAULT false,
    max_length INT,
    display_order INT,
    default_value VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true
);

-- =====================================================================
-- Assets
-- =====================================================================
CREATE TABLE IF NOT EXISTS assets (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    company_id uuid NOT NULL REFERENCES companies(id),
    asset_type_id uuid NOT NULL REFERENCES asset_types(id),
    sub_asset_type_id uuid NOT NULL REFERENCES sub_asset_types(id),
    ownership_type_id uuid NOT NULL REFERENCES ownership_types(id),
    asset_status_id uuid NOT NULL REFERENCES asset_statuses(id),
    location_id uuid NOT NULL REFERENCES locations(id),
    owner_id uuid REFERENCES owners(id),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    registration_date date NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT true
);

CREATE INDEX IF NOT EXISTS idx_assets_company_id ON assets(company_id);
CREATE INDEX IF NOT EXISTS idx_assets_asset_type_id ON assets(asset_type_id);
CREATE INDEX IF NOT EXISTS idx_assets_sub_asset_type_id ON assets(sub_asset_type_id);
CREATE INDEX IF NOT EXISTS idx_assets_owner_id ON assets(owner_id);
CREATE INDEX IF NOT EXISTS idx_assets_location_id ON assets(location_id);

-- =====================================================================
-- Asset Values
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_values (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    asset_id uuid NOT NULL REFERENCES assets(id) ON DELETE CASCADE,
    field_definition_id uuid NOT NULL REFERENCES field_definitions(id) ON DELETE CASCADE,
    value TEXT NOT NULL,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT asset_values_unique UNIQUE (asset_id, field_definition_id)
);

-- =====================================================================
-- Asset List Values
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_list_values (
    asset_id uuid NOT NULL REFERENCES assets(id) ON DELETE CASCADE,
    field_definition_id uuid NOT NULL REFERENCES field_definitions(id) ON DELETE CASCADE,
    catalog_id uuid NOT NULL,
    PRIMARY KEY (asset_id, field_definition_id, catalog_id)
);

-- =====================================================================
-- Asset History
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_history (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    asset_id uuid NOT NULL REFERENCES assets(id) ON DELETE CASCADE,
    event_date timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    event_type VARCHAR(100),
    executed_by VARCHAR(100),
    description TEXT
);

-- =====================================================================
-- Asset Assignments
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_assignments (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    asset_id uuid NOT NULL REFERENCES assets(id) ON DELETE CASCADE,
    person_id uuid NOT NULL REFERENCES people(id) ON DELETE CASCADE,
    relationship_type_id uuid REFERENCES asset_relationship_types(id),
    start_date timestamptz NOT NULL,
    end_date timestamptz,
    notes VARCHAR(1000),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================================
-- Asset Relationships
-- =====================================================================
CREATE TABLE IF NOT EXISTS asset_relationships (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    parent_asset_id uuid NOT NULL REFERENCES assets(id),
    child_asset_id uuid NOT NULL REFERENCES assets(id),
    relationship_type_id uuid NOT NULL REFERENCES asset_relationship_types(id),
    registration_date timestamptz
);

-- =====================================================================
-- Useful unique indexes (adjust as needed)
-- =====================================================================
CREATE UNIQUE INDEX IF NOT EXISTS companies_tax_id_idx ON companies(tax_id);
CREATE UNIQUE INDEX IF NOT EXISTS asset_types_code_idx ON asset_types(code);
CREATE UNIQUE INDEX IF NOT EXISTS sub_asset_types_code_idx ON sub_asset_types(code);
CREATE UNIQUE INDEX IF NOT EXISTS assets_code_idx ON assets(code);
CREATE UNIQUE INDEX IF NOT EXISTS people_document_number_idx ON people(document_number);
