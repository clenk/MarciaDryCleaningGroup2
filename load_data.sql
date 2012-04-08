INSERT INTO CUSTOMER_DATA(First, Last, Street, City, State, Zip, IsClubMember) VALUES(
    'John','Doe','1234 Hoops St.','Greenfield','OH','23272','1'
);
INSERT INTO CUSTOMER_DATA(First, Last, Street, City, State, Zip) VALUES(
    'John','Smith','3467 Ticket Rd.','New York','NY','97864'
);
INSERT INTO CUSTOMER_DATA(First, Last, Street, City, State, Zip, IsClubMember) VALUES(
    'Michael','Smith','1088 Half and Half Drive','Fresno','CA','93721','0'
);
INSERT INTO CUSTOMER_DATA(First, Last, Street, City, State, Zip) VALUES(
    'Anne','Brown','724 Kincheloe Road','Mcminnville','OR','97128'
);

INSERT INTO EMAIL(EmailAddr) VALUES(
    'jdoe@gmail.com'
);
INSERT INTO EMAIL(EmailAddr) VALUES(
    'johns@yahoo.com'
);
INSERT INTO EMAIL(EmailAddr) VALUES(
    'mikesmith@hotmail.com'
);
INSERT INTO EMAIL(EmailAddr) VALUES(
    'anne.brown@gmail.com'
);
INSERT INTO EMAIL(EmailAddr) VALUES(
    'm.smith@gmail.com'
);

INSERT INTO CUSTOMER_DATA_HAS_EMAIL(CUSTOMER_DATA_idCustomer, EMAIL_idEmail) VALUES(
    '1','1'
);
INSERT INTO CUSTOMER_DATA_HAS_EMAIL(CUSTOMER_DATA_idCustomer, EMAIL_idEmail) VALUES(
    '2','2'
);
INSERT INTO CUSTOMER_DATA_HAS_EMAIL(CUSTOMER_DATA_idCustomer, EMAIL_idEmail) VALUES(
    '3','3'
);
INSERT INTO CUSTOMER_DATA_HAS_EMAIL(CUSTOMER_DATA_idCustomer, EMAIL_idEmail) VALUES(
    '4','4'
);
INSERT INTO CUSTOMER_DATA_HAS_EMAIL(CUSTOMER_DATA_idCustomer, EMAIL_idEmail) VALUES(
    '3','5'
);

INSERT INTO PHONE(PhoneNum) VALUES(
    '154-325-5701'
);
INSERT INTO PHONE(PhoneNum) VALUES(
    '434-789-4834'
);
INSERT INTO PHONE(PhoneNum) VALUES(
    '275-596-1067'
);
INSERT INTO PHONE(PhoneNum) VALUES(
    '266-933-2964'
);

INSERT INTO CUSTOMER_DATA_HAS_PHONE(CUSTOMER_DATA_idCustomer, PHONE_idPhone) VALUES(
    '1','1'
);
INSERT INTO CUSTOMER_DATA_HAS_PHONE(CUSTOMER_DATA_idCustomer, PHONE_idPhone) VALUES(
    '2','3'
);
INSERT INTO CUSTOMER_DATA_HAS_PHONE(CUSTOMER_DATA_idCustomer, PHONE_idPhone) VALUES(
    '3','2'
);
INSERT INTO CUSTOMER_DATA_HAS_PHONE(CUSTOMER_DATA_idCustomer, PHONE_idPhone) VALUES(
    '4','4'
);

INSERT INTO SERVICE_DATA(ServiceDescription, Price, TimeRequired) VALUES(
    'Wash','0.50','00:50:00'
);
INSERT INTO SERVICE_DATA(ServiceDescription, Price, TimeRequired) VALUES(
    'Dry clean','0.75','01:00:00'
);
INSERT INTO SERVICE_DATA(ServiceDescription, Price, TimeRequired) VALUES(
    'Press','0.50','00:20:00'
);

/*INSERT INTO ORDER_DATA(DateDroppedOff, DatePromised, DatePickedUp, Price, Tax, Total, TimeRequired) VALUES(
    'Press','0.50','00:20:00'
);*/








