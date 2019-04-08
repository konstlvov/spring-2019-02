insert into Author (AuthorID, AuthorName) values (1, 'Vladimir Nabokov');
insert into Author (AuthorID, AuthorName) values (2, 'George Orwell');
insert into Author (AuthorID, AuthorName) values (3, 'John R. R. Tolkien');

insert into Genre(GenreID, GenreName) values (1, 'Fiction');
insert into Genre(GenreID, GenreName) values (2, 'Fantasy');
insert into Genre(GenreID, GenreName) values (3, 'Dystopia');

insert into Book(BookID, BookName, AuthorID, GenreID) values(1, 'The Gift', 1, 1);
insert into Book(BookID, BookName, AuthorID, GenreID) values(2, '1984', 2, 3);
insert into Book(BookID, BookName, AuthorID, GenreID) values(3, 'The Lord of the Rings', 3, 2);
commit;
/
