start /D "C:\Program Files\MongoDB\Server\4.0\bin" mongo.exe 192.168.17.129:27017
rem type show collections to show collections
rem type db.<collection_name>.find({}) to show all items from collection
rem e. g. db.books.find({}) - for collection named "books"
rem type exit to exit