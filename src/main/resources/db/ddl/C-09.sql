CREATE SEQUENCE CREDIT_ID_GENERATOR
START WITH 300
INCREMENT BY 1
NOMAXVALUE
/

CREATE TABLE CREDIT (
  id         INT,
  amount     NUMBER,
  validFor   TIMESTAMP,
  margin     NUMBER,
  fee        INT,
  isActive   INT,
  penalty    NUMBER,
  creditCard INT,
  currency   INT,
  client     INT
)
/

CREATE OR REPLACE PROCEDURE SAVE_CREDIT(c_id OUT      INT,
                                        c_amount      NUMBER,
                                        c_valid_for   TIMESTAMP,
                                        c_margin      NUMBER,
                                        c_fee         INT,
                                        c_is_active   INT,
                                        c_penalty     NUMBER,
                                        c_credit_card INT,
                                        c_currency    INT,
                                        c_client      INT)
AS
  BEGIN
    IF (c_id IS NULL)
    THEN
      c_id := CREDIT_ID_GENERATOR.nextval;
    END IF;
    INSERT INTO CREDIT (id,
                        amount,
                        validFor,
                        margin,
                        fee,
                        isActive,
                        penalty,
                        creditCard,
                        currency,
                        client) VALUES (c_id,
                                        c_amount,
                                        c_valid_for,
                                        c_margin,
                                        c_fee,
                                        c_is_active,
                                        c_penalty,
                                        c_credit_card,
                                        c_currency,
                                        c_client);
  END;
/


CREATE OR REPLACE PROCEDURE LOAD_CREDIT(c_id              INT,
                                        c_amount      OUT NUMBER,
                                        c_valid_for   OUT TIMESTAMP,
                                        c_margin      OUT NUMBER,
                                        c_fee         OUT INT,
                                        c_is_active   OUT INT,
                                        c_penalty     OUT NUMBER,
                                        c_credit_card OUT INT,
                                        c_currency    OUT INT,
                                        c_client      OUT INT)
AS
  BEGIN
    SELECT
      amount,
      validFor,
      margin,
      fee,
      isActive,
      penalty,
      creditCard,
      currency,
      client
    INTO
      c_amount,
      c_valid_for,
      c_margin,
      c_fee,
      c_is_active,
      c_penalty,
      c_credit_card,
      c_currency,
      c_client
    FROM CREDIT
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE PROCEDURE UPDATE_CREDIT(c_id          INT,
                                          c_amount      NUMBER,
                                          c_valid_for   TIMESTAMP,
                                          c_margin      NUMBER,
                                          c_fee         INT,
                                          c_is_active   INT,
                                          c_penalty     NUMBER,
                                          c_credit_card INT,
                                          c_currency    INT,
                                          c_client      INT)
AS
  BEGIN
    UPDATE CREDIT
    SET
      amount     = c_amount,
      validFor   = c_valid_for,
      margin     = c_margin,
      fee        = c_fee,
      isActive   = c_is_active,
      penalty    = c_penalty,
      creditCard = c_credit_card,
      currency   = c_currency,
      client     = c_client
    WHERE ID = c_id;
  END;
/

CREATE OR REPLACE FUNCTION COUNT_CREDIT
  RETURN INT
IS
  c INT;
  BEGIN
    SELECT COUNT(*)
    INTO c
    FROM CREDIT;

    RETURN c;
  END;
/


CREATE OR REPLACE PROCEDURE REMOVE_CREDIT(c_id INT)
AS
  BEGIN
    DELETE CREDIT
    WHERE id = c_id;
  END;
/


CREATE OR REPLACE TYPE CREDIT_TYPE AS OBJECT (id         INT,
                                              amount     NUMBER,
                                              validFor   TIMESTAMP,
                                              margin     NUMBER,
                                              fee        INT,
                                              isActive   INT,
                                              penalty    NUMBER,
                                              creditCard INT,
                                              currency   INT,
                                              client     INT)
/
CREATE OR REPLACE TYPE CREDIT_ARRAY AS TABLE OF CREDIT_TYPE
/

CREATE OR REPLACE FUNCTION LOAD_ALL_CREDIT
  RETURN CREDIT_ARRAY
IS
  r       CREDIT%ROWTYPE;
  credits CREDIT_ARRAY := CREDIT_ARRAY();
  c       CREDIT_TYPE;
  BEGIN
    FOR r IN (SELECT *
              FROM CREDIT) LOOP
      credits.extend;
      c := CREDIT_TYPE(r.id, r.amount, r.validFor, r.margin,
                       r.fee, r.isActive, r.penalty, r.creditCard,
                       r.currency, r.client);
      credits(credits.count) := c;
    END LOOP;
    RETURN credits;
  END;
/


CREATE OR REPLACE PROCEDURE CLEAR_CREDIT
AS
  BEGIN
    DELETE CREDIT;
  END;
/