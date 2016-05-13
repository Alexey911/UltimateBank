CREATE SEQUENCE CREDITCARD_ID_GENERATOR
START WITH 1000
INCREMENT BY 1
NOMAXVALUE
/


CREATE TABLE CREDITCARD (
  id             INT,
  code           VARCHAR2(100),
  validity       VARCHAR2(100),
  cvc            INT,
  validationCode INT,
  credit         INT
)
/

CREATE OR REPLACE PROCEDURE SAVE_CREDITCARD(c_id OUT          INT,
                                            c_code            VARCHAR2,
                                            c_validity        VARCHAR2,
                                            c_cvc             INT,
                                            c_validation_code INT,
                                            c_credit          INT)
AS
  BEGIN
    IF (c_id IS NULL)
    THEN
      c_id := CREDITCARD_ID_GENERATOR.nextval;
    END IF;
    INSERT INTO CREDITCARD (id,
                            code,
                            validity,
                            cvc,
                            validationCode,
                            credit) VALUES (c_id,
                                            c_code,
                                            c_validity,
                                            c_cvc,
                                            c_validation_code,
                                            c_credit);
  END;
/

CREATE OR REPLACE PROCEDURE LOAD_CREDITCARD(c_id                  INT,
                                            c_code            OUT VARCHAR2,
                                            c_validity        OUT VARCHAR2,
                                            c_cvc             OUT INT,
                                            c_validation_code OUT INT,
                                            c_credit          OUT INT)
AS
  BEGIN
    SELECT
      code,
      validity,
      cvc,
      validationCode,
      credit
    INTO
      c_code,
      c_validity,
      c_cvc,
      c_validation_code,
      c_credit
    FROM CREDITCARD
    WHERE id = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_CREDITCARD(c_id              INT,
                                              c_code            VARCHAR2,
                                              c_validity        VARCHAR2,
                                              c_cvc             INT,
                                              c_validation_code INT,
                                              c_credit          INT)
AS
  BEGIN
    UPDATE CREDITCARD
    SET
      code           = c_code,
      validity       = c_validity,
      cvc            = c_cvc,
      validationCode = c_validation_code,
      credit         = c_credit
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_CREDITCARD
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM CREDITCARD;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_CREDITCARD(c_id INT)
AS
  BEGIN
    DELETE CREDITCARD
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE CREDITCARD_TYPE AS OBJECT (id             INT,
                                                  code           VARCHAR2(100),
                                                  validity       VARCHAR2(100),
                                                  cvc            INT,
                                                  validationCode INT,
                                                  credit         INT)
/
CREATE OR REPLACE TYPE CREDITCARD_ARRAY AS TABLE OF CREDITCARD_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_CREDITCARD
  RETURN CREDITCARD_ARRAY
IS
  r            BILLCARD%ROWTYPE;
  credit_cards CREDITCARD_ARRAY := CREDITCARD_ARRAY();
  c            CREDITCARD_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM CREDITCARD) LOOP
      credit_cards.extend;
      c := CREDITCARD_TYPE(r.id, r.code, r.validity, r.cvc, r.validationCode, r.credit);
      credit_cards(credit_cards.count) := c;
    END LOOP;
    RETURN credit_cards;
  END;
/


CREATE OR REPLACE PROCEDURE CLEAR_CREDITCARD
AS
  BEGIN
    DELETE CREDITCARD;
  END;
/