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
end;
/
