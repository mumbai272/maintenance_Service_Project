create database troserv;
insert into gm_addbook(clientId,company_Id,short_id,add_desc,rec_status) values
(1,1,"UPN","Upnal CNC", "ACTIVE"),
(2,1,"TRO","Trolion", "ACTIVE"),
(3,1,"MT","Mindtree", "ACTIVE"),
(4,1,"INTC","Intel", "ACTIVE");

insert into address(Linke_Id,Linke_Type,ADD_DESC,STREET1,STREET2,STREET3,CITY,STATE,ZIP_CODE,COUNTRY,WEBSITE,MOBILE_NO,LOCATION,MAIL_ID,FAX_NO) values(1,"COMPANY","UPN MAIN OFFICE","no.1876 6th main","KSL-II stage","Penya","Bangalore","Karnataka","560078","India","","9448123456","Peny","test@test.com","080-2111111111"),
(2,"COMPANY","Trolion OFFICE","no 29 SVS","KSL-II stage","Hebbal","Bangalore","Karnataka","560021","India","","9448123456","Hebbal","test@test.com","080-3111111111");

insert into GM_DEPARTMENT(company_id,dept_id,dept_name,rec_status) values
(1,1,"HR","ACTIVE"),
(1,2,"RD","ACTIVE"),
(1,3,"Design","ACTIVE");

 insert into GM_DESIGNATION(company_id,designation_id,designation_name,rec_status) values
(1,1,"VP","ACTIVE"),
(1,2,"MD","ACTIVE"),
(1,3,"ENGG","ACTIVE");

insert into CS_M_TYPE(company_id,m_id,m_name,m_desc,rec_status,mod_by,mod_date,auth_by,auth_date)values
(1,1,"SERVER","Server machines","ACTIVE",NULL,NULL,NULL,NULL),
(1,2,"Desktop","Desktop machines","ACTIVE",NULL,NULL,NULL,NULL),
(1,3,"laptop","laptop machine","ACTIVE",NULL,NULL,NULL,NULL),
(1,4,"tab","tablets","ACTIVE",NULL,NULL,NULL,NULL);

insert into CS_M_MAKE(company_id,m_id,m_name,m_desc,rec_status,mod_by,mod_date,auth_by,auth_date)values
(1,1,"HP","","ACTIVE",NULL,NULL,NULL,NULL),
(1,2,"DELL","","ACTIVE",NULL,NULL,NULL,NULL),
(1,3,"SONY","","ACTIVE",NULL,NULL,NULL,NULL);

insert into CS_M_MODEL(company_id,m_id,m_name,m_desc,rec_status,mod_by,mod_date,auth_by,auth_date) values
(1,1,"HPSLIM","HP SLIM MODEL","ACTIVE",NULL,NULL,NULL,NULL),
(1,2,"MACPRO","MAC PRO","ACTIVE",NULL,NULL,NULL,NULL),
(1,3,"MACAIR","MAC AIR","ACTIVE",NULL,NULL,NULL,NULL);

insert into CS_M_SPARE(company_id,m_id,m_name,m_desc,rate,rec_status,mod_by,mod_date,auth_by,auth_date)values
(1,1,"HDD","Hard disk",100,"ACTIVE",NULL,NULL,NULL,NULL),
(1,2,"CD","CD drive",100,"ACTIVE",NULL,NULL,NULL,NULL),
(1,3,"SDD","SD hard disk","ACTIVE",NULL,NULL,NULL,NULL);

insert into GM_STAT_COMP(company_id,stat_comp_id,stat_comp_name,show_in_report,sorting_order,rec_status) values
(1,1,"VAT","YES",1,"ACTIVE"),
(1,2,"CIN","YES",2,"ACTIVE"),
(1,3,"CST","YES",3,"ACTIVE");

insert into GM_ADDBOOK_STATCOMP(company_id,client_id,stat_comp_id,eff_date,st_value,status) values
(1,1,1,NULL,"AKHPM7237J","ACTIVE"),
(1,2,1,NULL,"AKHPM7237K","ACTIVE"),
(1,3,1,NULL,"AKHPM7237L","ACTIVE"),
(1,4,1,NULL,"AKHPM7237M","ACTIVE");

