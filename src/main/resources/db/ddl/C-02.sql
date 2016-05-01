CREATE SEQUENCE FOUND_ID_GENERATOR
START WITH 33
INCREMENT BY 1
NOMAXVALUE
/

CREATE TABLE FOUND (
  id       INT,
  code     VARCHAR2(100),
  balance  NUMBER,
  currency INTEGER
)
/


CREATE OR REPLACE PROCEDURE SAVE_FOUND(c_id OUT   INT,
                                       c_code     VARCHAR2,
                                       c_balance  NUMBER,
                                       c_currency INTEGER)
AS
  BEGIN
    c_id := FOUND_ID_GENERATOR.nextval;
    INSERT INTO FOUND (id,
                       code,
                       balance,
                       currency) VALUES (c_id,
                                         c_code,
                                         c_balance,
                                         c_currency);
  END;
/


CREATE OR REPLACE PROCEDURE LOAD_FOUND(c_id           INT,
                                       c_code     OUT VARCHAR2,
                                       c_balance  OUT NUMBER,
                                       c_currency OUT INTEGER)
AS
  BEGIN
    SELECT
      code,
      balance,
      currency
    INTO
      c_code,
      c_balance,
      c_currency
    FROM FOUND
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_FOUND(c_id       INT,
                                         c_code     VARCHAR2,
                                         c_balance  NUMBER,
                                         c_currency INTEGER)
AS
  BEGIN
    UPDATE FOUND
    SET
      code     = c_code,
      balance  = c_balance,
      currency = c_currency
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_FOUND
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM FOUND;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_FOUND(c_id INT)
AS
  BEGIN
    DELETE FOUND
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE FOUND_TYPE AS OBJECT (id       INT,
                                             code     VARCHAR2(100),
                                             balance  NUMBER,
                                             currency INTEGER)
/
CREATE OR REPLACE TYPE FOUND_ARRAY AS TABLE OF FOUND_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_FOUND
  RETURN FOUND_ARRAY
IS
  r      FOUND%ROWTYPE;
  founds FOUND_ARRAY := FOUND_ARRAY();
  f      FOUND_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM FOUND) LOOP
      founds.extend;
      f := FOUND_TYPE(r.id, r.code, r.balance, r.currency);
      founds(founds.count) := f;
    END LOOP;
    RETURN founds;
  END;
/