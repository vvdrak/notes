# Демонстрационное приложение "Записки"
Данный проект написан для демонстрации знаний и используемого стека в разработке мобильных приложений для Android.

## Используемый стек
1. [Data Binding Library](https://developer.android.com/topic/libraries/data-binding) -- Привязка данных;
2. [Jetpack Navigation](https://developer.android.com/guide/navigation/) -- Навигация;
3. [Jetpack ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) -- View Model реализация;
4. [Jetpack LiveData](https://developer.android.com/topic/libraries/architecture/livedata) -- Livecycle Data Holder;
6. [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room) -- База данных;
7. [Dagger2](https://github.com/google/dagger) -- Инструмент внедрения зависимостей;

## Архитектура
Приложение использует принципы Clean Architecture, построено на шаблоне MVVM и соответствует SOLID

### Clean Architecture
В изученных мной источниках были описаны размные мнения о том, сколько слоев должно быть выделено.
В данном проекте я выделил 4 слоя. Каждый внутренний слой содержит свои зависимости и не знает о наружних слоях.
Для упрощения слои были разнесены по Java модулям.

Собственно слои:
1. `app` - Содержит пользовательский интерфейс и механизмы для взаимодействия с ним а также конкретные реализации некоторых абстракций нижних слоев, зависящие от платформы или фреймворка.
2. `interactors` - Действия, которые может запускать пользователь в приложении.
3. `data` - Абстракции для доступа к источникам данных.
4. `domain` - Модели и бизнес-правила приложения.