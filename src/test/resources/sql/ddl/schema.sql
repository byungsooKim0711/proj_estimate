
-- SENDER Table Create SQL
CREATE TABLE SENDER
(
     SPID_ID      INT            NOT NULL    AUTO_INCREMENT, -- COMMENT  발신인 아이디 , 
     SPID_DEPT    VARCHAR(45)    NULL, --        COMMENT  본부 명 , 
     SPID_NAME    VARCHAR(45)    NULL, --        COMMENT  이름 , 
     SPID_TEL1    VARCHAR(45)    NULL, --        COMMENT  전화번호1 , 
     SPID_TEL2    VARCHAR(45)    NULL, --        COMMENT  전화번호2 , 
     SPID_EMAIL   VARCHAR(45)    NULL, --        COMMENT  이메일 , 
     JOB          VARCHAR(45)    NULL, --        COMMENT  직책 , 
    PRIMARY KEY (SPID_ID)
);


-- VENDER Table Create SQL
CREATE TABLE VENDER
(
     VENDER_ID     INT            NOT NULL    AUTO_INCREMENT, -- COMMENT  벤더사 아이디 , 
     VENDER_NAME   VARCHAR(45)    NULL, --        COMMENT  벤더사 이름 , 
     SPID_ID       INT            NULL, --        COMMENT  발신인 아이디 , 
    PRIMARY KEY (VENDER_ID)
);

ALTER TABLE VENDER
    ADD CONSTRAINT FK_VENDER_SPID_ID_SENDER_SPID_ID FOREIGN KEY (SPID_ID)
        REFERENCES SENDER (SPID_ID) ON DELETE SET NULL ON UPDATE RESTRICT;


-- CUSTOMER Table Create SQL
CREATE TABLE CUSTOMER
(
     CUSTOMER_ID        INT            NOT NULL    AUTO_INCREMENT, -- COMMENT  고객사 아이디 , 
     CUSTOMER_COMPANY   VARCHAR(45)    NULL, --        COMMENT  회사명 , 
     CUSTOMER_NAME      VARCHAR(45)    NULL, --        COMMENT  담당자 정보 , 
     CUSTOMER_TEL       VARCHAR(45)    NULL, --        COMMENT  연락처 , 
     CUSTOMER_EMAIL     VARCHAR(45)    NULL, --        COMMENT  이메일 , 
    PRIMARY KEY (CUSTOMER_ID)
);


-- TOOL Table Create SQL
CREATE TABLE TOOL
(
     TOOL_ID             INT             NOT NULL    AUTO_INCREMENT, -- COMMENT  도구 아이디 , 
     TOOL_NAME           VARCHAR(100)    NULL, --        COMMENT  도구 이름 , 
     TOOL_MODEL_NUMBER   VARCHAR(45)     NULL, --        COMMENT  모델번호 , 
     TOOL_LICENSE        VARCHAR(45)     NULL, --        COMMENT  라이센스(단위) , 
     VENDER_ID           INT             NULL, --        COMMENT  벤더사 아이디 , 
     TOOL_DETAILS        VARCHAR(200)    NULL, --        COMMENT  도구 상세내역 , 
    PRIMARY KEY (TOOL_ID)
);

ALTER TABLE TOOL
    ADD CONSTRAINT FK_TOOL_VENDER_ID_VENDER_VENDER_ID FOREIGN KEY (VENDER_ID)
        REFERENCES VENDER (VENDER_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- ESTIMATE Table Create SQL
CREATE TABLE ESTIMATE
(
     ESTIMATE_ID      INT             NOT NULL    AUTO_INCREMENT, -- COMMENT  견적서 번호 , 
     TITLE            VARCHAR(45)     NULL, --        COMMENT  제목 , 
     ESTIMATE_PRICE   INT             NULL, --        COMMENT  총 견적금액 , 
     SPID_ID          INT             NULL, --        COMMENT  발신인 아이디 , 
     ESTIMATE_DATE    DATETIME        NULL, --        COMMENT  견적날짜 , 
     ESTIMATE_NOTE    VARCHAR(300)    NULL, --        COMMENT  비고 , 
     CUSTOMER_ID      INT             NULL, --        COMMENT  고객사 아이디 , 
    PRIMARY KEY (ESTIMATE_ID)
);

ALTER TABLE ESTIMATE
    ADD CONSTRAINT FK_ESTIMATE_SPID_ID_SENDER_SPID_ID FOREIGN KEY (SPID_ID)
        REFERENCES SENDER (SPID_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE ESTIMATE
    ADD CONSTRAINT FK_ESTIMATE_CUSTOMER_ID_CUSTOMER_CUSTOMER_ID FOREIGN KEY (CUSTOMER_ID)
        REFERENCES CUSTOMER (CUSTOMER_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- PRICE Table Create SQL
CREATE TABLE PRICE
(
     PRICE_ID        INT      NOT NULL    AUTO_INCREMENT, -- COMMENT  가격표 아이디 , 
     KRW             FLOAT    NULL, --        COMMENT  원가(KRW) , 
     EXCHANGE_RATE   FLOAT    NULL, --        COMMENT  환율 , 
     TOOL_ID         INT      NULL, --        COMMENT  도구 아이디 , 
     EUR             FLOAT    NULL, --        COMMENT  원가(EUR) , 
     QUANTITY        INT      NULL, --        DEFAULT 1 COMMENT  개수 , 
    PRIMARY KEY (PRICE_ID)
);

ALTER TABLE PRICE
    ADD CONSTRAINT FK_PRICE_TOOL_ID_TOOL_TOOL_ID FOREIGN KEY (TOOL_ID)
        REFERENCES TOOL (TOOL_ID) ON DELETE CASCADE ON UPDATE RESTRICT;


-- ESTIMATE_DETAIL Table Create SQL
CREATE TABLE ESTIMATE_DETAIL
(
     ESTIMATE_DETAIL_ID   INT             NOT NULL    AUTO_INCREMENT, --   COMMENT  견적상세 아이디 , 
     ESTIMATE_DETAILS     VARCHAR(200)    NULL, --        COMMENT  상세 내역 , 
     QUANTITY             INT             NULL, --        COMMENT  수량 , 
     UNIT                 VARCHAR(45)     NULL, --        COMMENT  단위 , 
     UNIT_PRICE           INT             NULL, --        COMMENT  단가 , 
     SUPPLY_PRICE         INT             NULL, --        COMMENT  공급가(제안가) , 
     ESTIMATE_ID          INT             NULL, --        COMMENT  견적서 번호 , 
     DISCOUNT_RATE        INT             NULL, --        COMMENT  할인율 , 
     START_MAINTENANCE    DATE            NULL, --        COMMENT  유지보수 시작 , 
     END_MAINTENANCE      DATE            NULL, --        COMMENT  유지보수 끝 , 
     TOOL_NAME            VARCHAR(100)    NULL, --        COMMENT  도구이름 , 
     TOOL_LICENSE         VARCHAR(45)     NULL, --        COMMENT  라이센스(단위) , 
    PRIMARY KEY (ESTIMATE_DETAIL_ID)
);

ALTER TABLE ESTIMATE_DETAIL
    ADD CONSTRAINT FK_ESTIMATE_DETAIL_ESTIMATE_ID_ESTIMATE_ESTIMATE_ID FOREIGN KEY (ESTIMATE_ID)
        REFERENCES ESTIMATE (ESTIMATE_ID) ON DELETE CASCADE ON UPDATE RESTRICT;
