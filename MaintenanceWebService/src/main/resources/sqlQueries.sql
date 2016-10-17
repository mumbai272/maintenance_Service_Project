create database troserv;
insert into gm_addbook(clientId,company_Id,short_id,add_desc,rec_status) values
(1,1,"UPN","Upnal CNC", "A"),
(2,1,"TRO","Trolion", "A"),
(3,1,"MT","Mindtree", "A"),
(4,1,"INTC","Intel", "A");

Insert into Users(USER_ID,USER_NAME,PASSWORD,EMAIL_ID,FIRST_NAME,LAST_NAME,GENDER,PHONE_NO,ROLE_TYPE_Id,COMPANY_ID,STATUS) values
(1,"vinayak123","password123","vinayak.s.mumbai@gmail.com","vinayak","mumbai","M","97420256792",1,1,"A");

INSERT INTO address(ADD_DESC,STREET1,STREET2,STREET3,CITY,STATE,ZIP_CODE,COUNTRY,WEBSITE,MOBILE_NO,LOCATION,MAIL_ID,FAX_NO) VALUES
("UPN MAIN OFFICE","no.1876 6th main","KSL-II stage","Penya","Bangalore","Karnataka","560078","India","test2.org","9448123456","Peny","test@test.com","080-2111111111"),
("Trolion OFFICE","no 29 SVS","KSL-II stage","Hebbal","Bangalore","Karnataka","560021","India","test.rg","9448123956","Hebbal","test1@test.com","080-3111111111");

insert into GM_DEPARTMENT(company_id,dept_id,dept_name,rec_status) values
(1,1,"HR","A"),
(1,2,"RD","A"),
(1,3,"Design","A");

 insert into GM_DESIGNATION(company_id,designation_id,designation_name,rec_status) values
(1,1,"VP","A"),
(1,2,"MD","A"),
(1,3,"ENGG","A");

insert into CS_M_TYPE(company_id,m_id,m_name,m_desc,rec_status,mod_by,mod_date,auth_by,auth_date)values
(1,1,"SERVER","Server machines","A",NULL,NULL,NULL,NULL),
(1,2,"Desktop","Desktop machines","A",NULL,NULL,NULL,NULL),
(1,3,"laptop","laptop machine","A",NULL,NULL,NULL,NULL),
(1,4,"tab","tablets","A",NULL,NULL,NULL,NULL);

insert into CS_M_MAKE(company_id,m_id,m_name,m_desc,rec_status,mod_by,mod_date,auth_by,auth_date)values
(1,1,"HP","hp company product","A",NULL,NULL,NULL,NULL),
(1,2,"DELL","","A",NULL,NULL,NULL,NULL),
(1,3,"SONY","","A",NULL,NULL,NULL,NULL);

insert into CS_M_MODEL(company_id,m_id,m_name,m_desc,rec_status,mod_by,mod_date,auth_by,auth_date) values
(1,1,"HPSLIM","HP SLIM MODEL","A",NULL,NULL,NULL,NULL),
(1,2,"MACPRO","MAC PRO","A",NULL,NULL,NULL,NULL),
(1,3,"MACAIR","MAC AIR","A",NULL,NULL,NULL,NULL);

insert into CS_M_SPARE(company_id,m_id,m_name,m_desc,rate,rec_status,mod_by,mod_date,auth_by,auth_date)values
(1,1,"HDD","Hard disk",100,"A",NULL,NULL,NULL,NULL),
(1,2,"CD","CD drive",100,"A",NULL,NULL,NULL,NULL),
(1,3,"SDD","SD hard disk",200,"A",NULL,NULL,NULL,NULL);

INSERT INTO cs_maint_type(TYPE_ID,AUTH_BY,AUTH_DATE,BREAKDOWN,COMPANY_ID,ENTRY_BY,ENTRY_DATE,MOD_BY,MOD_DATE,PLANNED_MAINT,REC_STATUS,TYPE_CODE,TYPE_DESC) VALUES 
( 1,'vinayak123','2016-10-17','F','1','vinayak123','2016-10-17 22:30:57','vinayak123','2016-10-17','T','A','Planned','Planned Maintenance'),
( 2,'vinayak123','2016-10-17','T','1','vinayak123','2016-10-17 22:30:57','vinayak123','2016-10-17','F','A','BreakDown','BreakDown Maintenance'); 

insert into GM_STAT_COMP(company_id,stat_comp_id,stat_comp_name,show_in_report,sorting_order,rec_status) values
(1,1,"VAT","YES",1,"A"),
(1,2,"CIN","YES",2,"A"),
(1,3,"CST","YES",3,"A");

insert into GM_ADDBOOK_STATCOMP(company_id,client_id,stat_comp_id,eff_date,st_value,status) values
(1,1,1,NULL,"AKHPM7237J","A"),
(1,2,1,NULL,"AKHPM7237K","A"),
(1,3,1,NULL,"AKHPM7237L","A"),
(1,4,1,NULL,"AKHPM7237M","A");


