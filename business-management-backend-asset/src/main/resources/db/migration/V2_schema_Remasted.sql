-- V2__add_assets_fields_and_loans.sql
 
-- 1. Modificar tabla 'assets'
ALTER TABLE assets
    ADD COLUMN IF NOT EXISTS qr_code VARCHAR(500),
    ADD COLUMN IF NOT EXISTS version BIGINT;
 
-- 2. Modificar tabla 'asset_assignments'
ALTER TABLE asset_assignments
    ADD COLUMN IF NOT EXISTS status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    ADD COLUMN IF NOT EXISTS acceptance_date timestamptz,
    ADD COLUMN IF NOT EXISTS pdf_path VARCHAR(500),
    ADD COLUMN IF NOT EXISTS state VARCHAR(50),
    ADD COLUMN IF NOT EXISTS rejection_reason VARCHAR(500);
 
-- 3. Modificar tabla 'people'
ALTER TABLE people
    ADD COLUMN IF NOT EXISTS is_asset_manager BOOLEAN NOT NULL DEFAULT false;
 
-- 4. Crear tabla 'asset_loans'
CREATE TABLE IF NOT EXISTS asset_loans (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    asset_id uuid NOT NULL REFERENCES assets(id) ON DELETE CASCADE,
    origin_company_id uuid NOT NULL REFERENCES companies(id),
    destination_company_id uuid NOT NULL REFERENCES companies(id),
    start_date timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    end_date timestamptz,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    observation TEXT,
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);
 
-- 5. Índices adicionales (si no existían antes)
CREATE UNIQUE INDEX IF NOT EXISTS assets_code_idx ON assets(code);
CREATE UNIQUE INDEX IF NOT EXISTS people_document_number_idx ON people(document_number);