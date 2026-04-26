INSERT INTO card (number, name, cvv, valid_till, balance, currency_code) VALUES
('1111222233334444', 'Ivan Ivanov',     '123', '12/25', 100000.00, 'RUB'),
('5555666677778888', 'Petr Petrov',     '456', '06/27',  50000.00, 'RUB'),
('1234123412341234', 'Sergey Sergeev',  '789', '09/26',  75000.00, 'RUB'),
('4321432143214321', 'Anna Smirnova',   '321', '03/28', 120000.00, 'RUB'),
('1111000011110000', 'Elena Orlova',    '654', '11/25',  30000.00, 'RUB'),
('2222333344445555', 'Dmitry Kruglov',  '987', '07/26',  85000.00, 'RUB'),
('9999888877776666', 'Olga Petrova',    '111', '01/29',  95000.00, 'RUB'),
('1212121212121212', 'Anton Volkov',    '222', '04/27',  60000.00, 'RUB'),
('3434343434343434', 'Maria Kuznetsova','333', '10/25', 105000.00, 'RUB'),
('5656565656565656', 'Alexey Popov',    '444', '02/28',  80000.00, 'RUB')
ON CONFLICT (number) DO NOTHING;