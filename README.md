# Менеджер завдань (ЛР №5: рефакторинг з паттернами GoF)

Консольний застосунок на Java для ведення списку завдань. Розвиток проєкту з ЛР №1–4.

## Сутність
`Task`: назва, пріоритет (High / Medium / Low), оцінка часу (год), дедлайн (дні), статус виконання.

## Можливості
- додавання, перегляд, виконання та видалення завдань (валідація даних, власні винятки з ЛР3);
- рейтинг «що робити першим?» за обраною стратегією: за дедлайном, за пріоритетом, за навантаженням (ЛР4);
- автоматичне збереження у файл `tasks.csv` та завантаження при запуску;
- попередження про термінові завдання та журнал подій.

## Запуск
Потрібна Java 11+.

**Windows:** `run.bat`  
**Linux / macOS:** `./run.sh`

Вручну (з кореня проєкту):
```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out app.Main
```
Дані зберігаються у `tasks.csv` у папці, з якої запущено програму.

## Структура
| Пакет | Класи | Роль |
|---|---|---|
| `app` | `Main`, `ConsoleInput` | меню та введення |
| `model` | `Task`, `Exportable` | сутність і її інтерфейс експорту |
| `exceptions` | `DomainException`, `InvalidPriorityException`, `InvalidEstimateException`, `UnknownStrategyException` | ієрархія доменних винятків |
| `strategy` | `UrgencyCalculator`, `AbstractUrgencyCalculator`, `DeadlineUrgency`, `PriorityUrgency`, `WorkloadUrgency`, `TaskPlanner`, `UrgencyCalculatorFactory` | стратегії оцінки терміновості, фабрика |
| `observer` | `TaskObserver`, `LogObserver`, `DeadlineAlertObserver`, `AutoSaveObserver` | спостерігачі |
| `service` | `TaskManager` | список завдань (суб'єкт Observer) |
| `storage` | `TaskStorage`, `LegacyFileStore`, `LegacyStorageAdapter` | сховище та адаптер |

## Застосовані паттерни

### 1. Factory (рівень 1): `UrgencyCalculatorFactory`
**Було (ЛР4):** об'єкти стратегій створювалися напряму через `new DeadlineUrgency()`, `new PriorityUrgency()`, `new WorkloadUrgency()` у клієнтському коді (`Main`).
**Стало:** клієнт просить стратегію за кодом: `UrgencyCalculatorFactory.create("workload")`. Вибір класу (`switch`) знаходиться в одному місці.
**Проблема, яку це вирішує:** додавання нової стратегії вимагає правки лише фабрики (+ новий клас), а не кожного місця створення. Клієнт залежить від інтерфейсу `UrgencyCalculator`, а не від конкретних класів. Невідомий код дає `UnknownStrategyException`.

### 2. Observer (рівень 2): `TaskManager` + `TaskObserver`
**Ситуація:** при зміні списку завдань (додано / виконано / видалено) потрібно кілька незалежних реакцій.
**Реалізація:** `TaskManager` (суб'єкт) зберігає список спостерігачів і сповіщає їх. Підписано три спостерігачі:
- `LogObserver`: журнал подій;
- `DeadlineAlertObserver`: попередження про завдання з дедлайном ≤ 1 дня;
- `AutoSaveObserver`: автоматичне збереження у файл.

**Проблема, яку це вирішує:** `TaskManager` нічого не знає про логування, попередження чи файли. Нову реакцію на зміни можна додати новим класом без змін у `TaskManager`.

### 3. Adapter (рівень 3): `LegacyStorageAdapter`
**Ситуація:** є «сторонній» клас `LegacyFileStore` зі старим API (`putRecord`, `fetchAllRecords`, `clearAll`, працює з сирими рядками). Наш код очікує інтерфейс `TaskStorage` (`save(List<Task>)`, `load()`).
**Реалізація:** `LegacyStorageAdapter implements TaskStorage` перекладає виклики: `save` → `clearAll` + `putRecord(task.toCsvLine())`, `load` → `fetchAllRecords` + розбір рядків у `Task`.
**Чому саме Adapter, а не переписування класу:** код сторонньої/застарілої бібліотеки ми не можемо або не повинні змінювати (інші проєкти залежать від нього). Адаптер лишає його незмінним, а нашу програму ізолює від його API. Якщо сховище заміниться (база даних), достатньо написати інший `TaskStorage`.

## Git
```bash
git checkout -b lr5/task-manager-patterns
git add .
git commit -m "LR5: Factory, Observer, Adapter refactoring"
git push -u origin lr5/task-manager-patterns
# далі створити Pull Request у main
```
