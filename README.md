# Демонстрационное приложение "Заметки"

Данный проект написан для демонстрации знаний и используемого стека в разработке мобильных
приложений для Android.

## Используемый стек

1. [Jetpack Compose](https://developer.android.com/jetpack/compose) -- UI toolkit;
2. [Jetpack Navigation](https://developer.android.com/guide/navigation/) -- Навигация;
3. [Jetpack ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) -- View
   Model;
4. [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) -- Механизмы
   неблокирующего программирования;
6. [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) --
   База данных;
7. [DaggerHilt](https://dagger.dev/hilt/) -- Инструмент внедрения зависимостей;

## Архитектура

Приложение использует принципы Clean Architecture, построено на MVI в сочитании
с [Unidirectional data flow](https://developer.android.com/jetpack/compose/architecture#udf).
Соответствует SOLID