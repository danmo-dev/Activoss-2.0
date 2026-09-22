-- V3__schema_AuditFixes.sql
-- Agregar soporte para Soft-Delete en relaciones de activos
ALTER TABLE asset_relationships ADD COLUMN is_active BOOLEAN DEFAULT TRUE NOT NULL;

-- Agregar trazabilidad de persona externa en préstamos inter-empresariales
ALTER TABLE asset_loans ADD COLUMN external_person_id UUID NULL;
