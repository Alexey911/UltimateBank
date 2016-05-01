CREATE SEQUENCE BILLCARD_ID_GENERATOR
START WITH 1000
INCREMENT BY 1
NOMAXVALUE
/


CREATE TABLE BILLCARD (
  id             INT,
  code           VARCHAR2(100),
  password       VARCHAR2(100),
  validity       VARCHAR2(100),
  cvc            INT,
  validationCode INT,
  bill           INT
)
/

CREATE OR REPLACE PROCEDURE SAVE_BILLCARD(c_id OUT          INT,
                                          c_code            VARCHAR2,
                                          c_password        VARCHAR2,
                                          c_validity        VARCHAR2,
                                          c_cvc             INT,
                                          c_validation_code INT,
                                          c_bill            INT)
AS
  BEGIN
    IF (c_id IS NULL)
    THEN
      c_id := BILLCARD_ID_GENERATOR.nextval;
    END IF;
    INSERT INTO BILLCARD (id,
                          code,
                          password,
                          validity,
                          cvc,
                          validationCode,
                          bill) VALUES (c_id,
                                        c_code,
                                        c_password,
                                        c_validity,
                                        c_cvc,
                                        c_validation_code,
                                        c_bill);
  END;
/

CREATE OR REPLACE PROCEDURE LOAD_BILLCARD(c_id                  INT,
                                          c_code            OUT VARCHAR2,
                                          c_password        OUT VARCHAR2,
                                          c_validity        OUT VARCHAR2,
                                          c_cvc             OUT INT,
                                          c_validation_code OUT INT,
                                          c_bill            OUT INT)
AS
  BEGIN
    SELECT
      code,
      password,
      validity,
      cvc,
      validationCode,
      bill
    INTO
      c_code,
      c_password,
      c_validity,
      c_cvc,
      c_validation_code,
      c_bill
    FROM BILLCARD
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_BILLCARD(c_id              INT,
                                            c_code            VARCHAR2,
                                            c_password        VARCHAR2,
                                            c_validity        VARCHAR2,
                                            c_cvc             INT,
                                            c_validation_code INT,
                                            c_bill            INT)
AS
  BEGIN
    UPDATE BILLCARD
    SET
      code           = c_code,
      password       = c_password,
      validity       = c_validity,
      cvc            = c_cvc,
      validationCode = c_validation_code,
      bill           = c_bill
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_BILLCARD
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM BILLCARD;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_BILLCARD(c_id INT)
AS
  BEGIN
    DELETE BILLCARD
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE BILLCARD_TYPE AS OBJECT (id             INT,
                                                code           VARCHAR2(100),
                                                password       VARCHAR2(100),
                                                validity       VARCHAR2(100),
                                                cvc            INT,
                                                validationCode INT,
                                                bill           INT)
/
CREATE OR REPLACE TYPE BILLCARD_ARRAY AS TABLE OF BILLCARD_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_BILLCARD
  RETURN BILLCARD_ARRAY
IS
  r          BILLCARD%ROWTYPE;
  bill_cards BILLCARD_ARRAY := BILLCARD_ARRAY();
  c          BILLCARD_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM BILLCARD) LOOP
      bill_cards.extend;
      c := BILLCARD_TYPE(r.id, r.code, r.password, r.validity, r.cvc, r.validationCode, r.bill);
      bill_cards(bill_cards.count) := c;
    END LOOP;
    RETURN bill_cards;
  END;
/


CREATE OR REPLACE PROCEDURE CLEAR_BILLCARD
AS
  BEGIN
    DELETE BILLCARD;
  END;
/