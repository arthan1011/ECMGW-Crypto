Как запустить апплет ECMGW-CryptoClient

1. обновить Java Plugin в браузере. (32-битная Java)
2. Найти каталог, где установлена на Java, которая используется плагином (например C:\Program Files (x86)\Java\jre1.8.0_66)
3. Найти в этом каталоге файл lib -> security -> java.policy
4. Изменить содержание файла java.policy на
grant {
    permission java.security.AllPermission;
};
5. Определить URL страницы, содержащей апплет (именно сам апплет а не iframe с ним)
6. Открыть Java Control Panel. Вкладка Security -> Edit Site List... -> Add (добавить URL в белый список сайтов)
7. На этой же вкладке поставить флажок Enable Java content in the browser
8. На этой же вкладке выставить Security level на High
9. Вкладка General -> Temporary Internet Files -> Settings... (установить флажок Keep temporary files on my computer)
10. Перед сборко апплета командой mvn package необходимо создать Java хранилище ключей .keystore в домашнем каталоге
пользователя и создать в нем ключ с определенным alias (нужен для подписания апплета). Соответствующим образом изменить
файл pom.xml для апплета (maven-jarsigner-plugin)