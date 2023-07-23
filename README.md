# Курсовой проект "Сетевой чат"
___
Проект решает задачу обмена текстовыми сообщениями по сети с помощью консоли (терминала) между двумя и более пользователями.
Состоит из двух приложений: клиентского и сервеного.

Настройка подключения как для клиента, так и для сервера происходит через файл настроек (settings.json);

### Возможности сервера
- Установка порта для подключения клиентов через файл настроек формата json;
- Параллельное подключение к серверу и присоединие к чату новых клиентов;
- Пересылка поступивших сообщений клиентам;
- Запись всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки;
- Логгирование системных событий.

### Возможности клиента
- Выбор имени при подключении для участия в чате;
- Установка настроек приложения из файла json;
- Подключение к указанному в настройках серверу;
- Параллельные прием и отправка сообщений;
- Выхода из чата по команду выхода - “/exit”;
- Записывать всех сообщений чата в текстовый файл.