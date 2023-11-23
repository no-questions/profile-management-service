##### profile-management-service

Services to validate creation and updation of user profiles

before starting the application run up.sh to start cache and db servers
required configuration to connect to them is already hardcoded

##### create the table using following scripts or let it be created automatically on business profile service startup

CREATE TABLE public.address (
id INT8 NOT NULL,
city VARCHAR(255) NOT NULL,
country VARCHAR(255) NOT NULL,
line1 VARCHAR(255) NOT NULL,
line2 VARCHAR(255) NULL,
state VARCHAR(255) NOT NULL,
zip VARCHAR(255) NOT NULL,
CONSTRAINT address_pkey PRIMARY KEY (id ASC)
);

-- public.auditlog definition

-- Drop table

-- DROP TABLE public.auditlog;

CREATE TABLE public.auditlog (
id INT8 NOT NULL,
action VARCHAR(255) NOT NULL,
currentprofile VARCHAR(30000) NOT NULL,
identifier VARCHAR(255) NOT NULL,
CONSTRAINT auditlog_pkey PRIMARY KEY (id ASC)
);

-- public.businessprofile definition

-- Drop table

-- DROP TABLE public.businessprofile;

CREATE TABLE public.businessprofile (
id UUID NOT NULL,
companyname VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
ismodified BOOL NOT NULL,
legalname VARCHAR(255) NOT NULL,
modifieddate TIMESTAMP(6) NULL,
website VARCHAR(255) NOT NULL,
businessaddress_id INT8 NULL,
eindetails_id INT8 NULL,
legaladdress_id INT8 NULL,
pandetails_id INT8 NULL,
CONSTRAINT businessprofile_pkey PRIMARY KEY (id ASC),
CONSTRAINT fk3jt8n9wcwjpkr15qoto7274p3 FOREIGN KEY (businessaddress_id) REFERENCES public.address(id),
CONSTRAINT fkpumh9n8rlxchwqutvw2fpnbo3 FOREIGN KEY (eindetails_id) REFERENCES public.taxidentifiers(id),
CONSTRAINT fkntcvu4h1x0hoxoqa6b231tjvo FOREIGN KEY (legaladdress_id) REFERENCES public.address(id),
CONSTRAINT fkn3nj663wd600klbkqajxsar7 FOREIGN KEY (pandetails_id) REFERENCES public.taxidentifiers(id),
INDEX idx1itgiujll9xdoqb8d5jcmdbo (legalname ASC),
INDEX idxb3ss7wx5ejo19s8s6ckxq8fgk (id ASC),
UNIQUE INDEX uk_t9auvohelwhc9x1nnc96r9t6i (businessaddress_id ASC),
UNIQUE INDEX uk_6suq5wufwg0k9ebnltsmvwdbg (eindetails_id ASC),
UNIQUE INDEX uk_tidccv6k49gn191suqjfxsn4i (legaladdress_id ASC),
UNIQUE INDEX uk_ef8o60cxq5xcv1shqrlve6jio (pandetails_id ASC)
);

-- public.businessprofile foreign keys

ALTER TABLE public.businessprofile ADD CONSTRAINT fk3jt8n9wcwjpkr15qoto7274p3 FOREIGN KEY (businessaddress_id)
REFERENCES public.address(id);
ALTER TABLE public.businessprofile ADD CONSTRAINT fkn3nj663wd600klbkqajxsar7 FOREIGN KEY (pandetails_id) REFERENCES
public.taxidentifiers(id);
ALTER TABLE public.businessprofile ADD CONSTRAINT fkntcvu4h1x0hoxoqa6b231tjvo FOREIGN KEY (legaladdress_id) REFERENCES
public.address(id);
ALTER TABLE public.businessprofile ADD CONSTRAINT fkpumh9n8rlxchwqutvw2fpnbo3 FOREIGN KEY (eindetails_id) REFERENCES
public.taxidentifiers(id);

-- public.errorcodes definition

-- Drop table

-- DROP TABLE public.errorcodes;

CREATE TABLE public.errorcodes (
errorcode VARCHAR(255) NOT NULL,
errormessage VARCHAR(255) NOT NULL,
isfailure BOOL NOT NULL,
isretryeligible BOOL NOT NULL,
CONSTRAINT errorcodes_pkey PRIMARY KEY (errorcode ASC)
);

-- public.taxidentifiers definition

-- Drop table

-- DROP TABLE public.taxidentifiers;

CREATE TABLE public.taxidentifiers (
id INT8 NOT NULL,
identifier VARCHAR(255) NOT NULL,
type VARCHAR(255) NOT NULL,
CONSTRAINT taxidentifiers_pkey PRIMARY KEY (id ASC)
);

#### post this insert below data in errorcodes table

-- Query 1
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '00', '
Success');

-- Query 2
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1001', '
Internal Server Error');

-- Query 3
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1002', 'Service
Unavailable');

-- Query 4
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1003', '
Database Error');

-- Query 5
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1004', 'Network
Timeout');

-- Query 6
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, '1005', '
Unexpected Error');

-- Query 7
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2001', '
Invalid Input');

-- Query 8
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2002', '
Missing Parameter');

-- Query 9
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2003', '
Invalid Token');

-- Query 10
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2004', 'Access
Denied');

-- Query 11
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2005', '
Validation Error');

-- Query 12
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '2006', '
Duplicate Entry');

-- Query 13
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, true, '3001', '
Customer Not Found (Retry Eligible)');

-- Query 14
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, true, '3002', '
Transaction Failed (Retry Eligible)');

-- Query 15
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, true, '3003', '
Payment Timeout (Retry Eligible)');

-- Query 16
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4001', 'Not
Found');

-- Query 17
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4002', '
Resource Unavailable');

-- Query 18
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4003', '
Unauthorized Access');

-- Query 19
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4004', '
Invalid Request');

-- Query 20
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, '4005', '
Authentication Failed');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, 'DC', '
Duplicate Legal Name');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, 'FE', '
Failed');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, 'BR', 'Bad
Request');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, true, 'RLE', 'Rate
limit exceeded');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, 'DNE', '
Product not subscribed');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (false, false, 'LCC', 'Legal
Name cannot be changed');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, '404', 'No data
found');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage) VALUES (true, false, 'UC', 'Unchanged profile');


####### now you can start cacheservice and validation service and open swagger UI to check docs
