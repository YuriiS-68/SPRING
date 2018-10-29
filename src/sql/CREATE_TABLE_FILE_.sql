CREATE TABLE FILE_(
  FILE_ID NUMBER NOT NULL PRIMARY KEY,
  STORAGE_ID_F NUMBER,
  CONSTRAINT STORAGE_FK FOREIGN KEY(STORAGE_ID_F)REFERENCES STORAGE_(STORAGE_ID),
  NAME_FILE NVARCHAR2(20) NOT NULL,
  FORMAT_FILE NVARCHAR2(10) NOT NULL,
  SIZE_FILE NUMBER NOT NULL
);