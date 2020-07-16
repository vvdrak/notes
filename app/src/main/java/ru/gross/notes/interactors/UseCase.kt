package ru.gross.notes.interactors

/**
 * Вариант использования.
 * @param Args Тип аргументов варианта использования.
 * @param Result Тип возвращаемого значения варианта использования.
 * @author vva2@gelicon.biz
 */
interface UseCase<in Args, out Result> {

    /**
     * Описывает главную функцию варианта использования.
     * @param args Аргументы.
     */
    operator fun invoke(args: Args): Result
}