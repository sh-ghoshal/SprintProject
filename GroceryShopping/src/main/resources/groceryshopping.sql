CREATE TABLE user_details(
email_id VARCHAR2(20) primary key, 
user_id NUMBER(10),
first_name VARCHAR2(10), 
last_name VARCHAR2(10), 
mobile_no NUMBER(10),			
password VARCHAR2(10));

CREATE TABLE product_details(
code NUMBER(10) primary key,
name VARCHAR2(10),
price VARCHAR2(10),
descrption VARCHAR2(100));

CREATE TABLE order_details(
order_id NUMBER(10) primary key, 
order_date date,
login_id NUMBER(10) REFERENCES user_details(mobile_no),
address_id NUMBER(10)  REFERENCES address_details(address_id),
amount NUMBER(10));

CREATE TABLE address_details(
address_id NUMBER(10) primary key, 
country VARCHAR2(10), 
state VARCHAR2(10), 
district VARCHAR2(10),
pincode VARCHAR2(10), 
address VARCHAR2(10));

CREATE TABLE product_order_details(
order_id NUMBER(10) REFERENCES order_details(order_id),
product_id NUMBER(10) REFERENCES product_details(code),
quantity NUMBER(10),
PRIMARY KEY(order_id,product_id)); 