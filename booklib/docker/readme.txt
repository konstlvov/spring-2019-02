10. Установите в ваш докер образы openjdk:11-oraclelinux7 и mongo:latest

20. Сделайте в  application.yml профиль, нацеленный на машину с MongoDB. Выберите в
application.properties этот профиль.

30. Соберите файл booklib-0.1.0.jar (например, с помощью файла build_package.cmd, в котором надо
указать путь к JDK 11) и положите в текущую директорию.

40. Соберите образ докера с помощью скрипта build_docker_image_booklib.sh

50. Отредактируйте файл run_mongo_in_docker.sh, указав в нем вместо
/appdata/dockerData/dockerAppData/mongodb/data директорию для хранения данных на хост-машине докера.

60. Запустите контейнер с MongoDB с помощью скрипта run_mongo_in_docker.sh

70. Запустите приложение с помощью скрипта run_booklib.sh

80. Проверьте, что все нормально запустилось с помощью команд docker ps, docker logs <id>,
docker inspect <id>
