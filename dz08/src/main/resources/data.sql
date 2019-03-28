insert into AUTHOR (AUTHORID, AUTHORNAME) values (1, 'Vladimir Nabokov');
insert into AUTHOR (AUTHORID, AUTHORNAME) values (2, 'George Orwell');
insert into AUTHOR (AUTHORID, AUTHORNAME) values (3, 'John R. R. Tolkien');

insert into GENRE (GENREID, GENRENAME) values (1, 'Fiction');
insert into GENRE (GENREID, GENRENAME) values (2, 'Fantasy');
insert into GENRE (GENREID, GENRENAME) values (3, 'Dystopia');

insert into Book(BookID, BookName, AuthorID, GenreID) values(1, 'The Gift', 1, 1);
insert into Book(BookID, BookName, AuthorID, GenreID) values(2, '1984', 2, 3);
insert into Book(BookID, BookName, AuthorID, GenreID) values(3, 'The Lord of the Rings', 3, 2);
