-- Query 1
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, '00', 'Success'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '00');

-- Query 2
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, true, '1001', 'Internal Server Error'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '1001');

-- Query 3
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, true, '1002', 'Service Unavailable'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '1002');

-- Query 4
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, true, '1003', 'Database Error'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '1003');

-- Query 5
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, true, '1004', 'Network Timeout'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '1004');

-- Query 6
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, true, '1005', 'Unexpected Error'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '1005');

-- Query 7
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, '2001', 'Invalid Input'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '2001');

-- Query 8
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, '2002', 'Missing Parameter'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '2002');

-- Query 9
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, '2003', 'Invalid Token'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '2003');

-- Query 10
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, '2004', 'Access Denied'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '2004');

-- Query 11
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, '2005', 'Validation Error'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '2005');

-- Query 12
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, '2006', 'Duplicate Entry'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '2006');

-- Query 13
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, true, '3001', 'Customer Not Found (Retry Eligible)'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '3001');

-- Query 14
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, true, '3002', 'Transaction Failed (Retry Eligible)'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '3002');

-- Query 15
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, true, '3003', 'Payment Timeout (Retry Eligible)'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '3003');

-- Query 16
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, '4001', 'Not Found'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '4001');

-- Query 17
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, '4002', 'Resource Unavailable'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '4002');

-- Query 18
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, '4003', 'Unauthorized Access'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '4003');

-- Query 19
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, '4004', 'Invalid Request'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '4004');

-- Query 20
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, '4005', 'Authentication Failed'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '4005');

-- Additional Queries
INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, 'DC', 'Duplicate Legal Name'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = 'DC');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, 'FE', 'Failed'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = 'FE');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, 'BR', 'Bad Request'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = 'BR');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, true, 'RLE', 'Rate limit exceeded'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = 'RLE');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, 'DNE', 'Product not subscribed'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = 'DNE');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT false, false, 'LCC', 'Legal Name cannot be changed'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = 'LCC');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, '404', 'No data found'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = '404');

INSERT INTO public.errorcodes (isfailure, isretryeligible, errorcode, errormessage)
SELECT true, false, 'UC', 'Unchanged profile'
WHERE NOT EXISTS (SELECT 1 FROM public.errorcodes WHERE errorcode = 'UC');