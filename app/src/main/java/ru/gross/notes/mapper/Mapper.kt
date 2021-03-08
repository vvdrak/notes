package ru.gross.notes.mapper

/**
 * Преобразователь типа.
 *
 * Служит для преобзазований одного типа в другой не нарушая связности приложения. Например из *DTO* в *Модель бизнес логики* и т.д.
 * @author gross_va
 */
interface Mapper<in In, out Out> {

    /**
     * Выполняет преобразование из [In] в [Out].
     */
    fun apply(input: In): Out
}