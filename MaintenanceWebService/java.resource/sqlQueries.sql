create database troserv;
insert into gm_addbook(clientId,company_Id,short_id,add_desc,rec_status) values
(1,1,"UPN","Upnal CNC", "ACTIVE"),
(2,1,"TRO","Trolion", "ACTIVE"),
(3,1,"MT","Mindtree", "ACTIVE"),
(4,1,"INTC","Intel", "ACTIVE");

insert into address(Linke_Id,Linke_Type,ADD_DESC,STREET1,STREET2,STREET3,CITY,STATE,ZIP_CODE,COUNTRY,WEBSITE,MOBILE_NO,LOCATION,MAIL_ID,FAX_NO) values(1,"COMPANY","UPN MAIN OFFICE","no.1876 6th main","KSL-II stage","Penya","Bangalore","Karnataka","560078","India","","9448123456","Peny","test@test.com","080-2111111111"),
(2,"COMPANY","Trolion OFFICE","no 29 SVS","KSL-II stage","Hebbal","Bangalore","Karnataka","560021","India","","9448123456","Hebbal","test@test.com","080-3111111111");