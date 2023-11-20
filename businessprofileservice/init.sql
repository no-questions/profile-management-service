-- Query 1
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '00', 'Success');

-- Query 2
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1001', 'Internal Server Error');

-- Query 3
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1002', 'Service Unavailable');

-- Query 4
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1003', 'Database Error');

-- Query 5
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1004', 'Network Timeout');

-- Query 6
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1005', 'Unexpected Error');

-- Query 7
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2001', 'Invalid Input');

-- Query 8
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2002', 'Missing Parameter');

-- Query 9
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2003', 'Invalid Token');

-- Query 10
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2004', 'Access Denied');

-- Query 11
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2005', 'Validation Error');

-- Query 12
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2006', 'Duplicate Entry');

-- Query 13
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, true, '3001', 'Customer Not Found (Retry Eligible)');

-- Query 14
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, true, '3002', 'Transaction Failed (Retry Eligible)');

-- Query 15
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, true, '3003', 'Payment Timeout (Retry Eligible)');

-- Query 16
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4001', 'Not Found');

-- Query 17
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4002', 'Resource Unavailable');

-- Query 18
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4003', 'Unauthorized Access');

-- Query 19
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4004', 'Invalid Request');

-- Query 20
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4005', 'Authentication Failed');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, 'DC', 'Duplicate Legal Name');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, 'FE', 'Failed');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, 'BR', 'Bad Request');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, 'RLE', 'Rate limit exceeded');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, 'DNE', 'Product not subscribed');




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