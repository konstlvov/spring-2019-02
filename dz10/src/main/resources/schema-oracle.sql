-- ничего, небось не сахарные, выполним скрипт вручную через SQL Developer и кнопку F5
set serveroutput on;
declare
  cnt number;
  procedure dropTableIfExists(tableName varchar2) is
  begin
    cnt := 0;
    select count(*) into cnt from user_tables where UPPER(table_name) = UPPER(tableName);
    if cnt = 0 then
      dbms_output.put_line('table ' || tableName || ' not exists');
    else
      dbms_output.put_line('table ' || tableName || ' exists, about to drop it...');
      execute immediate('DROP TABLE ' || tableName);
    end if;
  end;
  procedure dropSequenceIfExists(seqName varchar2) is
  begin
    cnt := 0;
    select count(*) into cnt from user_sequences where UPPER(sequence_name) = UPPER(seqName);
    if cnt = 0 then
      dbms_output.put_line('sequence ' || seqName || ' not exists');
    else
      dbms_output.put_line('sequence ' || seqName || ' exists, about to drop it...');
      execute immediate('DROP sequence ' || seqName);
    end if;
  end;
begin
  dropTableIfExists('PERSONS');
  execute immediate('CREATE TABLE PERSONS(ID INT PRIMARY KEY, NAME VARCHAR(255))');
  -- Author, Book, Genre
  dropTableIfExists('AUTHOR');
  execute immediate('CREATE TABLE AUTHOR(AUTHORID INT PRIMARY KEY, AUTHORNAME VARCHAR(255))');
  dropTableIfExists('GENRE');
  execute immediate('CREATE TABLE GENRE(GENREID INT PRIMARY KEY, GENRENAME VARCHAR(255))');
  dropTableIfExists('BOOK');
  execute immediate('CREATE TABLE BOOK(BOOKID INT PRIMARY KEY, BOOKNAME VARCHAR(255) '
    || ' , AUTHORID INT  ' -- REFERENCES AUTHOR(AUTHORID)
    || ' , GENREID INT  '  -- REFERENCES GENRE(GENREID)
    || ' ) '
    );
  dropTableIfExists('COMMENT2');
  execute immediate('CREATE TABLE COMMENT2(COMMENTID INT PRIMARY KEY, COMMENTTEXT VARCHAR(255) '
    || ' , BOOKID INT  ' -- REFERENCES AUTHOR(AUTHORID)
    || ' ) '
    );
  dropSequenceIfExists('HIBERNATE_SEQUENCE');
  execute immediate 'CREATE SEQUENCE HIBERNATE_SEQUENCE INCREMENT BY 1 MINVALUE 1000 MAXVALUE 999999999999999999999999999 CACHE 20 NOCYCLE';
end;
/
