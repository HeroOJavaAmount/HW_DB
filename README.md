# Конфигурационные файлы все настроенно несомненно работает.

Kibana - не получается поднять так как падает Elasticsearch или что еще на моменте Дашборда, логи были но нет Дашборда. 
По техническим причинам * (Ноутбук не тянет в данный момент) * не получается задание исполнить все но есть 
конфигурационные файлы все настроенно работает. В этих логах вроде нету больше сообщений от Kibana. Решено прекратить попытки много уходит времени.

```mermaid
%%{init: {'theme':'base', 'themeVariables': {
  'primaryColor':'#e8f5e9',
  'primaryTextColor':'#AF111C',
  'primaryBorderColor':'#2e7d32',
  'lineColor':'#2e7d32',
  'fontSize':'70px',
  'fontFamily':'Arial, sans-serif'
}}}%%


flowchart LR
    A[Пользователь] --> B[Демо-фронтенд<br/>React]
    B --> C[TransferController<br/>REST API]
    C --> D[TransferServiceImpl<br/>Бизнес-логика]
    D --> E[MixedCommission<br/>Расчёт комиссии 1%]
    D --> F[InMemoryTransferRepository<br/>Хранилище в памяти]
    D --> G[LoggingServiceImpl<br/>Запись логов]
    F --> H[ConcurrentHashMap<br/>Балансы и операции]
    G --> I[transfer.log]
    H --> J[(PostgreSQL)]

    style J fill:#f9f,stroke:#333,stroke-dasharray: 5 5
```




