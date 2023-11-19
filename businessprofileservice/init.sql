-- CREATE TABLE business_profile (
--   id SERIAL PRIMARY KEY,
--   company_name VARCHAR(255) NOT NULL,
--   legal_name VARCHAR(255) NOT NULL,
--   email VARCHAR(255) NOT NULL,
--   website VARCHAR(255) NOT NULL
-- );

-- CREATE TABLE address (
--   id SERIAL PRIMARY KEY,
--   line1 VARCHAR(255) NOT NULL,
--   line2 VARCHAR(255),
--   city VARCHAR(255) NOT NULL,
--   state VARCHAR(255) NOT NULL,
--   zip VARCHAR(255) NOT NULL,
--   country VARCHAR(255) NOT NULL
-- );

-- CREATE TABLE tax_identifier (
--   id SERIAL PRIMARY KEY,
--   type VARCHAR(255) NOT NULL,
--   identifier VARCHAR(255) NOT NULL,
--   business_profile_id INTEGER REFERENCES business_profile(id)
-- );

-- CREATE TABLE business_profile_address (
--   business_profile_id INTEGER REFERENCES business_profile(id),
--   address_id INTEGER REFERENCES address(id),
--   type VARCHAR(255) NOT NULL
-- );

-- ALTER TABLE tax_identifier
--   ADD CONSTRAINT fk_tax_identifier_business_profile
--   FOREIGN KEY (business_profile_id)
--   REFERENCES business_profile(id);

-- ALTER TABLE business_profile_address
--   ADD CONSTRAINT fk_business_profile_address_business_profile
--   FOREIGN KEY (business_profile_id)
--   REFERENCES business_profile(id);

-- ALTER TABLE business_profile_address
--   ADD CONSTRAINT fk_business_profile_address_address
--   FOREIGN KEY (address_id)
--   REFERENCES address(id);